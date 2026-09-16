package controller;

import model.ItemPedido;
import model.PedidoVenda;

/** Matheus Godoy, em parceria com Lucas de Lima: regras básicas de pedidos de venda. */
public class PedidoController {
    /** Adiciona um item ao pedido após validar quantidade, preço e estoque disponível. */
    public void adicionarItem(PedidoVenda pedido, ItemPedido item) {
        if (pedido == null || item == null || item.getQuantidade() <= 0 || item.getPrecoUnitario() < 0) {
            throw new IllegalArgumentException("Pedido e item devem possuir dados comerciais válidos.");
        }
        if (item.getProduto() == null || item.getQuantidade() > item.getProduto().getEstoqueDisponivel()) {
            throw new IllegalArgumentException("Quantidade superior ao estoque disponível.");
        }
        pedido.getItens().add(item);
    }

    /** Remove um item do pedido e deixa o total ser recalculado pela entidade. */
    public void removerItem(PedidoVenda pedido, ItemPedido item) {
        if (pedido == null || item == null || !pedido.getItens().remove(item)) {
            throw new IllegalArgumentException("Item não encontrado no pedido.");
        }
    }

    /** Recalcula/retorna o total líquido com base nos subtotais dos itens. */
    public double calcularTotal(PedidoVenda pedido) {
        if (pedido == null) throw new IllegalArgumentException("Pedido obrigatório.");
        return pedido.getTotal();
    }

    /** Executa validações comerciais básicas antes de salvar ou liberar o pedido. */
    public boolean validar(PedidoVenda pedido) {
        return pedido != null && pedido.getCliente() != null && !pedido.getItens().isEmpty()
            && pedido.getItens().stream().allMatch(item -> item.getSubtotal() >= 0);
    }
}
