package model;

/** Eduardo Yuri: item selecionado no carrinho da venda de balcão. */
public class ItemVenda {
    private final Produto produto;
    private double quantidade;
    public ItemVenda(Produto produto, double quantidade) { this.produto = produto; this.quantidade = quantidade; }
    public Produto getProduto() { return produto; }
    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }
    public double getSubtotal() { return quantidade * produto.getPreco(); }
}
