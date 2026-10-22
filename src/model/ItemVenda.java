package model;

/** Item selecionado no carrinho da venda de balcão. */
public class ItemVenda {
    private Produto produto; private double quantidade;
    public ItemVenda() {}
    public ItemVenda(Produto produto,double quantidade){this.produto=produto;this.quantidade=quantidade;}
    public Produto getProduto(){return produto;} public void setProduto(Produto v){produto=v;} public double getQuantidade(){return quantidade;} public void setQuantidade(double v){quantidade=v;}
    public double getSubtotal(){return produto==null?0:quantidade*produto.getPreco();}
}
