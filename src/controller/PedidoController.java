package controller;

import model.ItemPedido;
import model.PedidoVenda;

/** Rafael: itens, totais e validações básicas de pedidos de venda. */
public class PedidoController {
    public void adicionarItem(PedidoVenda pedido, ItemPedido item) {
        if (pedido == null || item == null || item.getQuantidade() <= 0 || item.getPrecoUnitario() < 0) throw new IllegalArgumentException("Dados comerciais inválidos.");
        pedido.getItens().add(item);
    }
    public void removerItem(PedidoVenda pedido, ItemPedido item) {
        if (pedido == null || item == null || !pedido.getItens().remove(item)) throw new IllegalArgumentException("Item não encontrado.");
    }
    public double calcularTotal(PedidoVenda pedido) { if (pedido == null) throw new IllegalArgumentException("Pedido obrigatório."); return pedido.getTotal(); }
    public boolean validar(PedidoVenda pedido) { return pedido != null && pedido.getCliente() != null && !pedido.getItens().isEmpty() && pedido.getItens().stream().allMatch(i -> i.getSubtotal() >= 0); }
}
