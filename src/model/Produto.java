package model;
/** Produto comercial; Eduardo Yuri mantém SKU, fiscalidade, preço, custo e estoque. */
public class Produto {
    private Long id; private String sku, descricao, unidadeMedida, ncm; private double precoTabela, custo, estoqueDisponivel;
    public Long getId(){return id;} public void setId(Long v){id=v;} public String getSku(){return sku;} public void setSku(String v){sku=v;}
    public String getDescricao(){return descricao;} public void setDescricao(String v){descricao=v;} public String getUnidadeMedida(){return unidadeMedida;} public void setUnidadeMedida(String v){unidadeMedida=v;}
    public String getNcm(){return ncm;} public void setNcm(String v){ncm=v;} public double getPrecoTabela(){return precoTabela;} public void setPrecoTabela(double v){precoTabela=v;}
    public double getCusto(){return custo;} public void setCusto(double v){custo=v;} public double getEstoqueDisponivel(){return estoqueDisponivel;} public void setEstoqueDisponivel(double v){estoqueDisponivel=v;}
}