
package controller;

import model.Cliente;

public class ClienteController {

    public boolean validar(Cliente cliente) {
        if (cliente == null) {
            return false;
        }

        if (cliente.getCpfCnpj() == null
                || cliente.getCpfCnpj().trim().isEmpty()) {
            return false;
        }

        String documento = cliente.getCpfCnpj()
                .replaceAll("\\D", "");

        if (!documento.matches("\\d{11}|\\d{14}")) {
            return false;
        }

        if (cliente.getRazaoSocial() == null
                || cliente.getRazaoSocial().trim().isEmpty()) {
            return false;
        }

        if (cliente.getCondicaoPagamento() == null
                || cliente.getCondicaoPagamento().trim().isEmpty()
                || cliente.getCondicaoPagamento().equals("Selecione")) {
            return false;
        }

        if (cliente.getLimiteCredito() < 0) {
            return false;
        }

        if (cliente.getStatus() == null
                || !cliente.getStatus().matches(
                    "PENDENTE|ATIVO|BLOQUEADO")) {
            return false;
        }

        return true;
    }
}
