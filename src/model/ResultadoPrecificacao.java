package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Resultado de uma simulação do Pricing Engine. */
public class ResultadoPrecificacao {
    private List<ItemPedido> itens=new ArrayList<>(); private BigDecimal subtotal,descontoTotal,valorTotal; private boolean possuiHardStop,possuiAlerta; private String statusGeral;
    public ResultadoPrecificacao() {}
    public ResultadoPrecificacao(List<ItemPedido> itens,BigDecimal subtotal,BigDecimal descontoTotal,BigDecimal valorTotal,boolean possuiHardStop,boolean possuiAlerta,String statusGeral){if(itens!=null)this.itens=new ArrayList<>(itens);this.subtotal=subtotal;this.descontoTotal=descontoTotal;this.valorTotal=valorTotal;this.possuiHardStop=possuiHardStop;this.possuiAlerta=possuiAlerta;this.statusGeral=statusGeral;}
    public List<ItemPedido> getItens(){return itens;} public void setItens(List<ItemPedido> v){itens=v==null?new ArrayList<>():v;} public BigDecimal getSubtotal(){return subtotal==null?BigDecimal.ZERO:subtotal;} public void setSubtotal(BigDecimal v){subtotal=v;} public BigDecimal getDescontoTotal(){return descontoTotal==null?BigDecimal.ZERO:descontoTotal;} public void setDescontoTotal(BigDecimal v){descontoTotal=v;} public BigDecimal getValorTotal(){return valorTotal==null?BigDecimal.ZERO:valorTotal;} public void setValorTotal(BigDecimal v){valorTotal=v;} public boolean isPossuiHardStop(){return possuiHardStop;} public void setPossuiHardStop(boolean v){possuiHardStop=v;} public boolean isPossuiAlerta(){return possuiAlerta;} public void setPossuiAlerta(boolean v){possuiAlerta=v;} public String getStatusGeral(){return statusGeral;} public void setStatusGeral(String v){statusGeral=v;}
}
