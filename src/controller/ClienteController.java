package controller;
import cliente.java;
public class ClienteController {
    
    public boolean validar(Cliente cliente) {
        if (cliente == null) {
            System.out.println("Cliente não informado.");
            return false;
        }
        // Valida CPF/CNPJ
        if (cliente.getCpfCnpj() == null) {
            System.out.println("CPF/CNPJ obrigatório.");
            return false;
        }
        String documento = cliente.getCpfCnpj().replaceAll("\\D", "");
        if (documento.length() != 11 &&
            documento.length() != 14) {
            System.out.println("CPF/CNPJ deve ter 11 ou 14 dígitos.");
            return false;
        }
        // Valida razão social
        if (cliente.getRazaoSocial() == null ||
            cliente.getRazaoSocial().trim().isEmpty()) {
            System.out.println("Razão social obrigatória.");
            return false;
        }
        // Valida limite de crédito
        if (cliente.getLimiteCredito() < 0) {
            System.out.println("Limite de crédito inválido.");
            return false;
        }
        // Verifica bloqueio comercial
        if (cliente.isBloqueado()) {
            System.out.println("Cliente bloqueado para vendas.");
        if (cliente.getMotivoBloqueio() != null) {
            System.out.println("Motivo: " + cliente.getMotivoBloqueio());
            }
            return false;
        }
        // Valida condição de pagamento
        if (cliente.getCondicaoPagamento() == null ||
            cliente.getCondicaoPagamento().trim().isEmpty()) {
            System.out.println("Condição de pagamento obrigatória.");
            return false;
        }
        System.out.println("Cliente validado com sucesso!");
        return true;}
}
