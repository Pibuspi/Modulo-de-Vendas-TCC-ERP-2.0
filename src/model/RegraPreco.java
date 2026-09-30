package model;

/** Matheus Godoy: regra de preço por SKU e limites de desconto/alçada. */
public class RegraPreco {
    private String sku;
    private double precoCusto, precoMinimo, precoTabela, margemMinimaPercentual;
    private double descontoMaximoAutomatico = 5.0;
    private double descontoMaximoGerente = 10.0;
    public String getSku() { return sku; } public void setSku(String v) { sku = v; }
    public double getPrecoCusto() { return precoCusto; } public void setPrecoCusto(double v) { precoCusto = v; }
    public double getPrecoMinimo() { return precoMinimo; } public void setPrecoMinimo(double v) { precoMinimo = v; }
    public double getPrecoTabela() { return precoTabela; } public void setPrecoTabela(double v) { precoTabela = v; }
    public double getMargemMinimaPercentual() { return margemMinimaPercentual; } public void setMargemMinimaPercentual(double v) { margemMinimaPercentual = v; }
    public double getDescontoMaximoAutomatico() { return descontoMaximoAutomatico; } public void setDescontoMaximoAutomatico(double v) { descontoMaximoAutomatico = v; }
    public double getDescontoMaximoGerente() { return descontoMaximoGerente; } public void setDescontoMaximoGerente(double v) { descontoMaximoGerente = v; }
}
