package model;

import java.math.BigDecimal;

public class ItemPedido {
    private int idProduto;
    private int quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal descontoPercentual;
    private BigDecimal margemReal;
    private String statusPricing;

    public ItemPedido() {}

    public ItemPedido(int idProduto, int quantidade, BigDecimal precoUnitario, BigDecimal descontoPercentual) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario != null ? precoUnitario : BigDecimal.ZERO;
        this.descontoPercentual = descontoPercentual != null ? descontoPercentual : BigDecimal.ZERO;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario != null ? precoUnitario : BigDecimal.ZERO;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getDescontoPercentual() {
        return descontoPercentual != null ? descontoPercentual : BigDecimal.ZERO;
    }

    public void setDescontoPercentual(BigDecimal descontoPercentual) {
        this.descontoPercentual = descontoPercentual;
    }

    public BigDecimal getMargemReal() {
        return margemReal != null ? margemReal : BigDecimal.ZERO;
    }

    public void setMargemReal(BigDecimal margemReal) {
        this.margemReal = margemReal;
    }

    public String getStatusPricing() {
        return statusPricing;
    }

    public void setStatusPricing(String statusPricing) {
        this.statusPricing = statusPricing;
    }

    public String getStatusAprovacao() {
        return statusPricing;
    }

    // Método exigido pelo PedidoVenda.java para somar o total da nota
    public double getSubtotal() {
        if (precoUnitario == null) {
            return 0.0;
        }
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade)).doubleValue();
    }
}