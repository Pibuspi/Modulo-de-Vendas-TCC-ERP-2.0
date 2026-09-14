package model;
/** Item do pedido; subtotal = quantidade * preço - desconto. */
public class ItemPedido {
    private Produto produto; private double quantidade, precoUnitario, desconto;
    public Produto getProduto(){return produto;} public void setProduto(Produto v){produto=v;} public double getQuantidade(){return quantidade;} public void setQuantidade(double v){quantidade=v;}
    public double getPrecoUnitario(){return precoUnitario;} public void setPrecoUnitario(double v){precoUnitario=v;} public double getDesconto(){return desconto;} public void setDesconto(double v){desconto=v;}
    public double getSubtotal(){return quantidade * precoUnitario - desconto;}
}