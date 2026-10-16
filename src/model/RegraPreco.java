package model;

import java.math.BigDecimal;
import java.util.Date;

public class RegraPreco {
    private int idRegra;
    private int idProduto;
    private String sku;
    private BigDecimal precoCusto;
    private BigDecimal precoMinimo;
    private BigDecimal margemMinimaPercentual;
    private BigDecimal descontoMaximoAutomatico;
    private BigDecimal descontoMaximoGerente;
    private String nomeTabela;
    private String regiao;
    private String canalVenda;
    private Date vigenciaInicial;
    private Date vigenciaFinal;
    private String statusTabela;

    public RegraPreco() {}

    public RegraPreco(BigDecimal precoCusto, BigDecimal margemMinimaPercentual) {
        this.precoCusto = precoCusto != null ? precoCusto : BigDecimal.ZERO;
        this.margemMinimaPercentual = margemMinimaPercentual != null ? margemMinimaPercentual : BigDecimal.ZERO;
    }

    public int getIdRegra() { return idRegra; }
    public void setIdRegra(int idRegra) { this.idRegra = idRegra; }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public BigDecimal getPrecoCusto() { return precoCusto != null ? precoCusto : BigDecimal.ZERO; }
    public void setPrecoCusto(BigDecimal precoCusto) { this.precoCusto = precoCusto; }

    public BigDecimal getPrecoMinimo() { return precoMinimo; }
    public void setPrecoMinimo(BigDecimal precoMinimo) { this.precoMinimo = precoMinimo; }

    public BigDecimal getMargemMinimaPercentual() { return margemMinimaPercentual != null ? margemMinimaPercentual : BigDecimal.ZERO; }
    public void setMargemMinimaPercentual(BigDecimal margemMinimaPercentual) { this.margemMinimaPercentual = margemMinimaPercentual; }

    public BigDecimal getDescontoMaximoAutomatico() { return descontoMaximoAutomatico; }
    public void setDescontoMaximoAutomatico(BigDecimal descontoMaximoAutomatico) { this.descontoMaximoAutomatico = descontoMaximoAutomatico; }

    public BigDecimal getDescontoMaximoGerente() { return descontoMaximoGerente; }
    public void setDescontoMaximoGerente(BigDecimal descontoMaximoGerente) { this.descontoMaximoGerente = descontoMaximoGerente; }

    public String getNomeTabela() { return nomeTabela; }
    public void setNomeTabela(String nomeTabela) { this.nomeTabela = nomeTabela; }

    public String getRegiao() { return regiao; }
    public void setRegiao(String regiao) { this.regiao = regiao; }

    public String getCanalVenda() { return canalVenda; }
    public void setCanalVenda(String canalVenda) { this.canalVenda = canalVenda; }

    public Date getVigenciaInicial() { return vigenciaInicial; }
    public void setVigenciaInicial(Date vigenciaInicial) { this.vigenciaInicial = vigenciaInicial; }

    public Date getVigenciaFinal() { return vigenciaFinal; }
    public void setVigenciaFinal(Date vigenciaFinal) { this.vigenciaFinal = vigenciaFinal; }

    public String getStatusTabela() { return statusTabela; }
    public void setStatusTabela(String statusTabela) { this.statusTabela = statusTabela; }
}