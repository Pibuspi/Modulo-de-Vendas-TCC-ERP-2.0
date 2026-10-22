package model;

/** Cliente comercial alinhado ao cadastro e à tabela cliente. Telas: CadastroClienteView, ConsultaClienteView e DigitacaoPedidoView. */
public class Cliente {
    private int idCliente;
    private String cpfCnpj, razaoSocial, nomeFantasia, email, telefone;
    private String inscricaoEstadual, inscricaoMunicipal, status;
    private double limiteCredito;
    private String condicaoPagamentoPadrao, vendedorResponsavel, canalVenda, segmentacao;
    private boolean bloqueadoPorInadimplencia, regraFiscalPendente;
    public Cliente() {
        status = "PENDENTE";
    }

    public Cliente(int idCliente, String cpfCnpj, String razaoSocial, String nomeFantasia, String email, String telefone,
                   String inscricaoEstadual, String inscricaoMunicipal, String status, double limiteCredito,
                   String condicaoPagamentoPadrao, String vendedorResponsavel, String canalVenda, String segmentacao,
                   boolean bloqueadoPorInadimplencia, boolean regraFiscalPendente) {
        this.idCliente=idCliente;
        this.cpfCnpj=cpfCnpj;
        this.razaoSocial=razaoSocial;
        this.nomeFantasia=nomeFantasia;
        this.email=email;
        this.telefone=telefone;
        this.inscricaoEstadual=inscricaoEstadual;
        this.inscricaoMunicipal=inscricaoMunicipal;
        this.status=status;
        this.limiteCredito=limiteCredito;
        this.condicaoPagamentoPadrao=condicaoPagamentoPadrao;
        this.vendedorResponsavel=vendedorResponsavel;
        this.canalVenda=canalVenda;
        this.segmentacao=segmentacao;
        this.bloqueadoPorInadimplencia=bloqueadoPorInadimplencia;
        this.regraFiscalPendente=regraFiscalPendente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int v) {
        idCliente=v;
    }

    public Long getId() {
        return (long)idCliente;
    }

    public void setId(Long v) {
        idCliente=v==null?0:v.intValue();
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String v) {
        cpfCnpj=v;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String v) {
        razaoSocial=v;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String v) {
        nomeFantasia=v;
    }

    public String getNome() {
        return razaoSocial;
    }

    public void setNome(String v) {
        razaoSocial=v;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String v) {
        email=v;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String v) {
        telefone=v;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String v) {
        inscricaoEstadual=v;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String v) {
        inscricaoMunicipal=v;
    }

    public String getStatusTexto() {
        return status;
    }

    public void setStatus(String v) {
        status=v;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double v) {
        limiteCredito=v;
    }

    public String getCondicaoPagamentoPadrao() {
        return condicaoPagamentoPadrao;
    }

    public void setCondicaoPagamentoPadrao(String v) {
        condicaoPagamentoPadrao=v;
    }

    public String getCondicaoPagamento() {
        return condicaoPagamentoPadrao;
    }

    public void setCondicaoPagamento(String v) {
        condicaoPagamentoPadrao=v;
    }

    public String getVendedorResponsavel() {
        return vendedorResponsavel;
    }

    public void setVendedorResponsavel(String v) {
        vendedorResponsavel=v;
    }

    public String getCanalVenda() {
        return canalVenda;
    }

    public void setCanalVenda(String v) {
        canalVenda=v;
    }

    public String getSegmentacao() {
        return segmentacao;
    }

    public void setSegmentacao(String v) {
        segmentacao=v;
    }

    public boolean isBloqueadoPorInadimplencia() {
        return bloqueadoPorInadimplencia;
    }

    public void setBloqueadoPorInadimplencia(boolean v) {
        bloqueadoPorInadimplencia=v;
    }

    public boolean isRegraFiscalPendente() {
        return regraFiscalPendente;
    }

    public void setRegraFiscalPendente(boolean v) {
        regraFiscalPendente=v;
    }

    public boolean isBloqueado() {
        return bloqueadoPorInadimplencia || "BLOQUEADO".equalsIgnoreCase(status);
    }

    public void setBloqueado(boolean v) {
        bloqueadoPorInadimplencia=v;
        if (v) status="BLOQUEADO";
    }

    public String getMotivoBloqueio() {
        return isBloqueado()?"Bloqueado":"";
    }

    public void setMotivoBloqueio(String v) {
        if (v!=null&&!v.isBlank())setBloqueado(true);
    }
}
