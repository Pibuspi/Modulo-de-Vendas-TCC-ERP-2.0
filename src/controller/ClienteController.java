package controller;

import model.Cliente;

/** Pimenta/Rafael: validação de CPF/CNPJ, crédito, condição e bloqueios. */
public class ClienteController {
    public boolean validar(Cliente cliente) {
        if (cliente == null || cliente.getCpfCnpj() == null) return false;
        String documento = cliente.getCpfCnpj().replaceAll("\\D", "");
        if (documento.length() != 11 && documento.length() != 14) return false;
        if (cliente.getRazaoSocial() == null || cliente.getRazaoSocial().isBlank()) return false;
        if (cliente.getLimiteCredito() < 0 || cliente.isBloqueado()) return false;
        return cliente.getCondicaoPagamento() != null && !cliente.getCondicaoPagamento().isBlank();
    }
}
