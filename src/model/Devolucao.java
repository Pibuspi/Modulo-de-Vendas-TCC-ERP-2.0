package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** Solicitação de devolução, evidências, parecer e estorno. */
public class Devolucao {
    private int idDevolucao, idPedido;
    private String motivo;
    private Timestamp dataSolicitacao;
    private double valorEstorno;
    private String status="SOLICITADO", caminhoAnexoEvidencia, parecerGerencial, ordemEstorno;
    private final List<ItemDevolucao> itens=new ArrayList<>();
    public Devolucao() {}
    public Devolucao(int idDevolucao,int idPedido,String motivo,Timestamp dataSolicitacao,double valorEstorno,String status,String caminhoAnexoEvidencia,String parecerGerencial,List<ItemDevolucao> itens){this.idDevolucao=idDevolucao;this.idPedido=idPedido;this.motivo=motivo;this.dataSolicitacao=dataSolicitacao;this.valorEstorno=valorEstorno;this.status=status;this.caminhoAnexoEvidencia=caminhoAnexoEvidencia;this.parecerGerencial=parecerGerencial;if(itens!=null)this.itens.addAll(itens);}
    public int getIdDevolucao(){return idDevolucao;} public void setIdDevolucao(int v){idDevolucao=v;}
    public Long getId(){return (long)idDevolucao;} public void setId(Long v){idDevolucao=v==null?0:v.intValue();}
    public int getIdPedido(){return idPedido;} public void setIdPedido(int v){idPedido=v;}
    public String getMotivo(){return motivo;} public void setMotivo(String v){motivo=v;}
    public Timestamp getDataSolicitacao(){return dataSolicitacao;} public void setDataSolicitacao(Timestamp v){dataSolicitacao=v;}
    public double getValorEstorno(){return valorEstorno;} public void setValorEstorno(double v){valorEstorno=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public String getCaminhoAnexoEvidencia(){return caminhoAnexoEvidencia;} public void setCaminhoAnexoEvidencia(String v){caminhoAnexoEvidencia=v;}
    public String getParecerGerencial(){return parecerGerencial;} public void setParecerGerencial(String v){parecerGerencial=v;}
    public String getParecer(){return parecerGerencial;} public void setParecer(String v){parecerGerencial=v;}
    public String getOrdemEstorno(){return ordemEstorno;} public void setOrdemEstorno(String v){ordemEstorno=v;}
    public List<ItemDevolucao> getItens(){return itens;}
}
