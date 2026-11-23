
package model;

public class Cliente {

    private Long id;
    private String cpfCnpj;
    private String razaoSocial;
    private String nomeFantasia;

    private String inscricaoEstadual;
    private String inscricaoMunicipal;

    private String status = "PENDENTE";
    private String condicaoPagamento;
    private double limiteCredito;

    private boolean bloqueado;
    private boolean bloqueioInadimplencia;
    private String motivoBloqueio;

    private String tabelaPreco;
    private double alçadaDesconto;
    private String rota;
    private Integer prazoEntrega;

    public Cliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(String condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public boolean isBloqueioInadimplencia() {
        return bloqueioInadimplencia;
    }

    public void setBloqueioInadimplencia(boolean bloqueioInadimplencia) {
        this.bloqueioInadimplencia = bloqueioInadimplencia;
    }

    public String getMotivoBloqueio() {
        return motivoBloqueio;
    }

    public void setMotivoBloqueio(String motivoBloqueio) {
        this.motivoBloqueio = motivoBloqueio;
    }

    public String getTabelaPreco() {
        return tabelaPreco;
    }

    public void setTabelaPreco(String tabelaPreco) {
        this.tabelaPreco = tabelaPreco;
    }

    public double getAlcadaDesconto() {
    return alcadaDesconto;
}

public void setAlcadaDesconto(double alcadaDesconto) {
    this.alcadaDesconto = alcadaDesconto;
}

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public Integer getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(Integer prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }
}
