package controller;

import model.Cliente;

public class ClienteController {

    public boolean validar(Cliente cliente) {

        // Verifica se o cliente foi informado
        if (cliente == null) {
            System.out.println("Cliente não informado.");
            return false;}
        
        // Verifica o CPF/CNPJ
        if (cliente.getCpfCnpj() == null) {
            System.out.println("CPF/CNPJ obrigatório.");
            return false;}
        
        String documento = cliente.getCpfCnpj().replaceAll("\\D", "");
        if (documento.length() != 11 &&
            documento.length() != 14) {
            System.out.println("CPF/CNPJ deve ter 11 ou 14 dígitos.");
            return false;}
        
        // Verifica a razão social
        if (cliente.getRazaoSocial() == null ||
            cliente.getRazaoSocial().trim().isEmpty()) {
            System.out.println("Razão social obrigatória.");
            return false;}
        
        // Verifica o limite de crédito
        if (cliente.getLimiteCredito() < 0) {
            System.out.println("Limite de crédito inválido.");
            return false;}
        
        // Veifica se o cliente está bloqueado
        if (cliente.isBloqueado()) {
            System.out.println("Cliente bloqueado para vendas.");
            return false;}
        
        // Verifica a condição de pagamento
        if (cliente.getCondicaoPagamento() == null ||
            cliente.getCondicaoPagamento().trim().isEmpty()) {
            System.out.println("Condição de pagamento obrigatória.");
            return false;}
        
        System.out.println("Cliente validado com sucesso!");
        return true;
    }
}
