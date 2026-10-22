package model;

import java.math.BigDecimal;
import java.util.Date;

/** Regra de preço alinhada à tabela regra_preco e ao Pricing Engine. */
public class RegraPreco {
    private int idRegra, idProduto; private String sku;
    private BigDecimal precoCusto, precoMinimo, margemMinimaPercentual, descontoMaximoAutomatico, descontoMaximoGerente;
    private String nomeTabela, regiao, canalVenda, statusTabela; private Date vigenciaInicial, vigenciaFinal;
    public RegraPreco() {}
    public RegraPreco(BigDecimal precoCusto, BigDecimal margemMinimaPercentual) { this.precoCusto=precoCusto; this.margemMinimaPercentual=margemMinimaPercentual; }
    public RegraPreco(int idRegra,int idProduto,String sku,BigDecimal precoCusto,BigDecimal precoMinimo,BigDecimal margemMinimaPercentual,BigDecimal descontoMaximoAutomatico,BigDecimal descontoMaximoGerente,String nomeTabela,String regiao,String canalVenda,Date vigenciaInicial,Date vigenciaFinal,String statusTabela){this.idRegra=idRegra;this.idProduto=idProduto;this.sku=sku;this.precoCusto=precoCusto;this.precoMinimo=precoMinimo;this.margemMinimaPercentual=margemMinimaPercentual;this.descontoMaximoAutomatico=descontoMaximoAutomatico;this.descontoMaximoGerente=descontoMaximoGerente;this.nomeTabela=nomeTabela;this.regiao=regiao;this.canalVenda=canalVenda;this.vigenciaInicial=vigenciaInicial;this.vigenciaFinal=vigenciaFinal;this.statusTabela=statusTabela;}
    public int getIdRegra(){return idRegra;} public void setIdRegra(int v){idRegra=v;} public int getIdProduto(){return idProduto;} public void setIdProduto(int v){idProduto=v;}
    public String getSku(){return sku;} public void setSku(String v){sku=v;} public BigDecimal getPrecoCusto(){return precoCusto==null?BigDecimal.ZERO:precoCusto;} public void setPrecoCusto(BigDecimal v){precoCusto=v;}
    public BigDecimal getPrecoMinimo(){return precoMinimo;} public void setPrecoMinimo(BigDecimal v){precoMinimo=v;} public BigDecimal getMargemMinimaPercentual(){return margemMinimaPercentual==null?BigDecimal.ZERO:margemMinimaPercentual;} public void setMargemMinimaPercentual(BigDecimal v){margemMinimaPercentual=v;}
    public BigDecimal getDescontoMaximoAutomatico(){return descontoMaximoAutomatico;} public void setDescontoMaximoAutomatico(BigDecimal v){descontoMaximoAutomatico=v;} public BigDecimal getDescontoMaximoGerente(){return descontoMaximoGerente;} public void setDescontoMaximoGerente(BigDecimal v){descontoMaximoGerente=v;}
    public String getNomeTabela(){return nomeTabela;} public void setNomeTabela(String v){nomeTabela=v;} public String getRegiao(){return regiao;} public void setRegiao(String v){regiao=v;} public String getCanalVenda(){return canalVenda;} public void setCanalVenda(String v){canalVenda=v;}
    public Date getVigenciaInicial(){return vigenciaInicial;} public void setVigenciaInicial(Date v){vigenciaInicial=v;} public Date getVigenciaFinal(){return vigenciaFinal;} public void setVigenciaFinal(Date v){vigenciaFinal=v;} public String getStatusTabela(){return statusTabela;} public void setStatusTabela(String v){statusTabela=v;}
}
