package model;

/** Eduardo Yuri: entidade de produto de balcão com os cinco atributos oficiais. */
public class Produto {
    private String codigo;
    private String produto;
    private double quantidade;
    private double vendas;
    private double preco;

    public Produto() {}
    public Produto(String codigo, String produto, double quantidade, double vendas, double preco) {
        this.codigo = codigo; this.produto = produto; this.quantidade = quantidade;
        this.vendas = vendas; this.preco = preco;
    }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }
    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }
    public double getVendas() { return vendas; }
    public void setVendas(double vendas) { this.vendas = vendas; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}
