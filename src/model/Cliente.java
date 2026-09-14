package model;

/** Entidade comercial de cliente; Eduardo Yuri mantém os campos e invariantes do domínio. */
public class Cliente {
    private Long id; private String cpfCnpj, razaoSocial, nomeFantasia, condicaoPagamento, motivoBloqueio;
    private double limiteCredito; private boolean bloqueado;
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getCpfCnpj(){return cpfCnpj;} public void setCpfCnpj(String v){cpfCnpj=v;}
    public String getRazaoSocial(){return razaoSocial;} public void setRazaoSocial(String v){razaoSocial=v;}
    public String getNomeFantasia(){return nomeFantasia;} public void setNomeFantasia(String v){nomeFantasia=v;}
    public String getCondicaoPagamento(){return condicaoPagamento;} public void setCondicaoPagamento(String v){condicaoPagamento=v;}
    public String getMotivoBloqueio(){return motivoBloqueio;} public void setMotivoBloqueio(String v){motivoBloqueio=v;}
    public double getLimiteCredito(){return limiteCredito;} public void setLimiteCredito(double v){limiteCredito=v;}
    public boolean isBloqueado(){return bloqueado;} public void setBloqueado(boolean v){bloqueado=v;}
}