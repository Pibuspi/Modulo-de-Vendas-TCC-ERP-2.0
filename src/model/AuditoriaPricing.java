package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** Registro de auditoria das simulações e decisões do motor de preços. */
public class AuditoriaPricing {
    private int idAuditoria, idRegra, idPedido, idUsuario;
    private Timestamp dataAuditoria;
    private String acao, statusResultado, justificativa;
    private BigDecimal descontoPercentual, precoPraticado, margemReal;
    public AuditoriaPricing() {}
    public AuditoriaPricing(int idAuditoria,int idRegra,int idPedido,int idUsuario,Timestamp dataAuditoria,String acao,BigDecimal descontoPercentual,BigDecimal precoPraticado,BigDecimal margemReal,String statusResultado,String justificativa){this.idAuditoria=idAuditoria;this.idRegra=idRegra;this.idPedido=idPedido;this.idUsuario=idUsuario;this.dataAuditoria=dataAuditoria;this.acao=acao;this.descontoPercentual=descontoPercentual;this.precoPraticado=precoPraticado;this.margemReal=margemReal;this.statusResultado=statusResultado;this.justificativa=justificativa;}
    public int getIdAuditoria(){return idAuditoria;} public void setIdAuditoria(int v){idAuditoria=v;} public int getIdRegra(){return idRegra;} public void setIdRegra(int v){idRegra=v;}
    public int getIdPedido(){return idPedido;} public void setIdPedido(int v){idPedido=v;} public int getIdUsuario(){return idUsuario;} public void setIdUsuario(int v){idUsuario=v;}
    public Timestamp getDataAuditoria(){return dataAuditoria;} public void setDataAuditoria(Timestamp v){dataAuditoria=v;} public String getAcao(){return acao;} public void setAcao(String v){acao=v;}
    public BigDecimal getDescontoPercentual(){return descontoPercentual;} public void setDescontoPercentual(BigDecimal v){descontoPercentual=v;} public BigDecimal getPrecoPraticado(){return precoPraticado;} public void setPrecoPraticado(BigDecimal v){precoPraticado=v;}
    public BigDecimal getMargemReal(){return margemReal;} public void setMargemReal(BigDecimal v){margemReal=v;} public String getStatusResultado(){return statusResultado;} public void setStatusResultado(String v){statusResultado=v;}
    public String getJustificativa(){return justificativa;} public void setJustificativa(String v){justificativa=v;}
}
