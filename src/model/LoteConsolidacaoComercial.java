package model;

import java.util.ArrayList;
import java.util.List;

/** Agrupa pedidos para uma rodada de liberação comercial. */
public class LoteConsolidacaoComercial {
    private int idLote; private String status; private final List<PedidoVenda> pedidos=new ArrayList<>();
    public LoteConsolidacaoComercial() {}
    public LoteConsolidacaoComercial(int idLote,String status,List<PedidoVenda> pedidos){this.idLote=idLote;this.status=status;if(pedidos!=null)this.pedidos.addAll(pedidos);}
    public int getIdLote(){return idLote;} public void setIdLote(int v){idLote=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;} public List<PedidoVenda> getPedidos(){return pedidos;}
}
