package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** Cabeçalho e acumuladores do pedido de venda. */
public class PedidoVenda {
    public enum Status { ABERTO, BLOQUEADO, PENDENTE_APROVACAO, LIBERADO_PARA_FATURAMENTO, DEVOLVIDO_AO_VENDEDOR, FATURADO, CANCELADO }
    private int idPedido;
    private String numeroPedido;
    private Cliente cliente;
    private Timestamp dataPedido;
    private String vendedor, condicaoPagamento, canalVenda, observacoes;
    private double subtotal, descontoTotal, valorTotal;
    private String status = "ABERTO";
    private final List<ItemPedido> itens = new ArrayList<>();
    public PedidoVenda() {}
    public PedidoVenda(int idPedido, String numeroPedido, Cliente cliente, Timestamp dataPedido, String vendedor,
                       String condicaoPagamento, String canalVenda, String observacoes, double subtotal,
                       double descontoTotal, double valorTotal, String status, List<ItemPedido> itens) {
        this.idPedido=idPedido; this.numeroPedido=numeroPedido; this.cliente=cliente; this.dataPedido=dataPedido;
        this.vendedor=vendedor; this.condicaoPagamento=condicaoPagamento; this.canalVenda=canalVenda; this.observacoes=observacoes;
        this.subtotal=subtotal; this.descontoTotal=descontoTotal; this.valorTotal=valorTotal; this.status=status;
        if(itens!=null)this.itens.addAll(itens);
    }
    public int getIdPedido(){return idPedido;} public void setIdPedido(int v){idPedido=v;}
    public Long getId(){return (long)idPedido;} public void setId(Long v){idPedido=v==null?0:v.intValue();}
    public String getNumeroPedido(){return numeroPedido;} public void setNumeroPedido(String v){numeroPedido=v;}
    public Cliente getCliente(){return cliente;} public void setCliente(Cliente v){cliente=v;}
    public int getIdCliente(){return cliente==null?0:cliente.getIdCliente();}
    public Timestamp getDataPedido(){return dataPedido;} public void setDataPedido(Timestamp v){dataPedido=v;}
    public String getVendedor(){return vendedor;} public void setVendedor(String v){vendedor=v;}
    public String getCondicaoPagamento(){return condicaoPagamento;} public void setCondicaoPagamento(String v){condicaoPagamento=v;}
    public String getCanalVenda(){return canalVenda;} public void setCanalVenda(String v){canalVenda=v;}
    public String getObservacoes(){return observacoes;} public void setObservacoes(String v){observacoes=v;}
    public double getSubtotal(){recalcular();return subtotal;} public void setSubtotal(double v){subtotal=v;}
    public double getDescontoTotal(){recalcular();return descontoTotal;} public void setDescontoTotal(double v){descontoTotal=v;}
    public double getValorTotal(){recalcular();return valorTotal;} public void setValorTotal(double v){valorTotal=v;}
    public double getTotal(){return getValorTotal();}
    public Status getStatus(){try{return Status.valueOf(status);}catch(Exception e){return Status.ABERTO;}}
    public String getStatusTexto(){return status;} public void setStatus(String v){status=v==null?"ABERTO":v;}
    public void setStatus(Status v){status=v==null?"ABERTO":v.name();}
    public List<ItemPedido> getItens(){return itens;}
    public void recalcular(){subtotal=itens.stream().mapToDouble(ItemPedido::getSubtotal).sum(); descontoTotal=itens.stream().mapToDouble(ItemPedido::getDesconto).sum(); valorTotal=subtotal;}
}
