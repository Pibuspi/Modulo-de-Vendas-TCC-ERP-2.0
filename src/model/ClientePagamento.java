package model;

/** Eduardo Yuri: consumidor da venda, com os oito campos definidos para pagamento. */
public class ClientePagamento {
    private String cpf, nome, ddd, telefone, cep, endereco, numero;
    private String uf = "DF";
    public String getCpf() { return cpf; } public void setCpf(String v) { cpf = v; }
    public String getNome() { return nome; } public void setNome(String v) { nome = v; }
    public String getDdd() { return ddd; } public void setDdd(String v) { ddd = v; }
    public String getTelefone() { return telefone; } public void setTelefone(String v) { telefone = v; }
    public String getCep() { return cep; } public void setCep(String v) { cep = v; }
    public String getEndereco() { return endereco; } public void setEndereco(String v) { endereco = v; }
    public String getNumero() { return numero; } public void setNumero(String v) { numero = v; }
    public String getUf() { return uf; } public void setUf(String v) { uf = v; }
}
