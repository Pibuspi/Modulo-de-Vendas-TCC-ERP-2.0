package model;

/** Item de pedido com desconto monetário e percentual. */
public class ItemPedido {
    private int idItem, idPedido;
    private Produto produto;
    private double quantidade, precoUnitario, desconto, descontoPercentual, subtotalItem;
    public ItemPedido() {}
    public ItemPedido(int idItem, int idPedido, Produto produto, double quantidade, double precoUnitario,
                      double desconto, double descontoPercentual) { this.idItem=idItem;this.idPedido=idPedido;this.produto=produto;this.quantidade=quantidade;this.precoUnitario=precoUnitario;this.desconto=desconto;this.descontoPercentual=descontoPercentual;recalcular(); }
    public int getIdItem(){return idItem;} public void setIdItem(int v){idItem=v;}
    public int getIdPedido(){return idPedido;} public void setIdPedido(int v){idPedido=v;}
    public Produto getProduto(){return produto;} public void setProduto(Produto v){produto=v;}
    public int getIdProduto(){return produto==null?0:produto.getCodigo()==null?0:produto.getCodigo().hashCode();}
    public double getQuantidade(){return quantidade;} public void setQuantidade(double v){quantidade=v;recalcular();}
    public double getPrecoUnitario(){return precoUnitario;} public void setPrecoUnitario(double v){precoUnitario=v;recalcular();}
    public double getDesconto(){return desconto;} public void setDesconto(double v){desconto=v;recalcular();}
    public double getDescontoPercentual(){return descontoPercentual;} public void setDescontoPercentual(double v){descontoPercentual=v;recalcular();}
    public double getSubtotalItem(){recalcular();return subtotalItem;} public void setSubtotalItem(double v){subtotalItem=v;}
    public double getSubtotal(){recalcular();return subtotalItem;}
    public void recalcular(){double bruto=quantidade*precoUnitario; double percentual=bruto*descontoPercentual/100.0; subtotalItem=bruto-desconto-percentual;}
}
