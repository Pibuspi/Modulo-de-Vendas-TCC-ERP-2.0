package model;

/** Matheus Godoy Motor de preços

import java.math.BigDecimal;
import java.util.List;

public class ResultadoPrecificacao {
    private List<ItemPedido> itens;
    private BigDecimal subtotal;
    private BigDecimal descontoTotal;
    private BigDecimal valorTotal;
    private boolean possuiHardStop;
    private boolean possuiAlerta;
    private String statusGeral;

    public ResultadoPrecificacao() {}

    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }

    public BigDecimal getSubtotal() { return subtotal != null ? subtotal : BigDecimal.ZERO; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getDescontoTotal() { return descontoTotal != null ? descontoTotal : BigDecimal.ZERO; }
    public void setDescontoTotal(BigDecimal descontoTotal) { this.descontoTotal = descontoTotal; }

    public BigDecimal getValorTotal() { return valorTotal != null ? valorTotal : BigDecimal.ZERO; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public boolean isPossuiHardStop() { return possuiHardStop; }
    public void setPossuiHardStop(boolean possuiHardStop) { this.possuiHardStop = possuiHardStop; }

    public boolean isPossuiAlerta() { return possuiAlerta; }
    public void setPossuiAlerta(boolean possuiAlerta) { this.possuiAlerta = possuiAlerta; }

    public String getStatusGeral() { return statusGeral; }
    public void setStatusGeral(String statusGeral) { this.statusGeral = statusGeral; }
}
