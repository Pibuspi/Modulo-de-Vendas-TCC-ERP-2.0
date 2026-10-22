package model;

import java.math.BigDecimal;

import java.util.ArrayList;

import java.util.List;

/** Resultado de uma simulação do Pricing Engine. Tela: PricingEngineView. */
public class ResultadoPrecificacao {
    public enum StatusAprovacao { LIBERADO, PENDENTE_APROVACAO, HARD_STOP }
    private List<ItemPedido> itens=new ArrayList<>();
    private BigDecimal subtotal,descontoTotal,valorTotal;
    private boolean possuiHardStop,possuiAlerta;
    private String statusGeral;
    private double margemReal;
    private StatusAprovacao statusAprovacao;
    public ResultadoPrecificacao() {}

    public ResultadoPrecificacao(List<ItemPedido> itens,BigDecimal subtotal,BigDecimal descontoTotal,BigDecimal valorTotal,boolean possuiHardStop,boolean possuiAlerta,String statusGeral) {
        if (itens!=null)this.itens=new ArrayList<>(itens);
        this.subtotal=subtotal;
        this.descontoTotal=descontoTotal;
        this.valorTotal=valorTotal;
        this.possuiHardStop=possuiHardStop;
        this.possuiAlerta=possuiAlerta;
        this.statusGeral=statusGeral;
    }

    public ResultadoPrecificacao(double precoPraticado,double desconto,double margem,StatusAprovacao status,String justificativa) {
        this.valorTotal=BigDecimal.valueOf(precoPraticado);
        this.descontoTotal=BigDecimal.valueOf(desconto);
        this.margemReal=margem;
        this.statusAprovacao=status;
        this.statusGeral=status==null?null:status.name();
        this.possuiHardStop=status==StatusAprovacao.HARD_STOP;
        this.possuiAlerta=status==StatusAprovacao.PENDENTE_APROVACAO;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> v) {
        itens=v==null?new ArrayList<>():v;
    }

    public BigDecimal getSubtotal() {
        return subtotal==null?BigDecimal.ZERO:subtotal;
    }

    public void setSubtotal(BigDecimal v) {
        subtotal=v;
    }

    public BigDecimal getDescontoTotal() {
        return descontoTotal==null?BigDecimal.ZERO:descontoTotal;
    }

    public void setDescontoTotal(BigDecimal v) {
        descontoTotal=v;
    }

    public BigDecimal getValorTotal() {
        return valorTotal==null?BigDecimal.ZERO:valorTotal;
    }

    public void setValorTotal(BigDecimal v) {
        valorTotal=v;
    }

    public boolean isPossuiHardStop() {
        return possuiHardStop;
    }

    public void setPossuiHardStop(boolean v) {
        possuiHardStop=v;
    }

    public boolean isPossuiAlerta() {
        return possuiAlerta;
    }

    public void setPossuiAlerta(boolean v) {
        possuiAlerta=v;
    }

    public String getStatusGeral() {
        return statusGeral;
    }

    public void setStatusGeral(String v) {
        statusGeral=v;
    }

    public double getMargemReal() {
        return margemReal;
    }

    public void setMargemReal(double v) {
        margemReal=v;
    }

    public StatusAprovacao getStatusAprovacao() {
        return statusAprovacao;
    }

    public void setStatusAprovacao(StatusAprovacao v) {
        statusAprovacao=v;
    }
}
