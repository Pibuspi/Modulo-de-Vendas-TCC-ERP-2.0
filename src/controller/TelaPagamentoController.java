package controller;

import model.ClientePagamento;
import java.util.EnumMap;
import java.util.Map;

/** Eduardo Yuri: valida consumidor e controla recebimento em dinheiro, PIX e cartão. */
public class TelaPagamentoController {
    public enum Metodo { DINHEIRO, PIX, CARTAO }
    private final Map<Metodo, Double> recebimentos = new EnumMap<>(Metodo.class);
    public TelaPagamentoController() { for (Metodo m : Metodo.values()) recebimentos.put(m, 0.0); }
    public void registrarRecebimento(Metodo metodo, double valor) {
        if (metodo == null || valor < 0) throw new IllegalArgumentException("Método e valor inválidos.");
        recebimentos.put(metodo, valor);
    }
    public double totalPago() { return recebimentos.values().stream().mapToDouble(Double::doubleValue).sum(); }
    public double calcularTroco(double total) { return Math.max(0, totalPago() - total); }
    public boolean podeConfirmar(double total) { return total >= 0 && totalPago() >= total; }
    public void validarCliente(ClientePagamento cliente) {
        if (cliente == null || vazio(cliente.getNome()) || vazio(cliente.getCpf()) || vazio(cliente.getTelefone())
            || vazio(cliente.getCep()) || vazio(cliente.getEndereco()) || vazio(cliente.getNumero()) || vazio(cliente.getUf()))
            throw new IllegalArgumentException("Preencha os dados obrigatórios do consumidor.");
    }
    private boolean vazio(String valor) { return valor == null || valor.isBlank(); }
}
