package model;

/** Consumidor do checkout de balcão; o ID permite múltiplas compras. */
public class ClientePagamento {
    private int idClientePagamento;
    private String cpf,nome,ddd,telefone,cep,endereco,numero,uf="DF";
    public ClientePagamento() {}
    public ClientePagamento(int idClientePagamento,String cpf,String nome,String ddd,String telefone,String cep,String endereco,String numero,String uf){this.idClientePagamento=idClientePagamento;this.cpf=cpf;this.nome=nome;this.ddd=ddd;this.telefone=telefone;this.cep=cep;this.endereco=endereco;this.numero=numero;this.uf=uf;}
    public int getIdClientePagamento(){return idClientePagamento;} public void setIdClientePagamento(int v){idClientePagamento=v;}
    public String getCpf(){return cpf;} public void setCpf(String v){cpf=v;} public String getNome(){return nome;} public void setNome(String v){nome=v;}
    public String getDdd(){return ddd;} public void setDdd(String v){ddd=v;} public String getTelefone(){return telefone;} public void setTelefone(String v){telefone=v;}
    public String getCep(){return cep;} public void setCep(String v){cep=v;} public String getEndereco(){return endereco;} public void setEndereco(String v){endereco=v;}
    public String getNumero(){return numero;} public void setNumero(String v){numero=v;} public String getUf(){return uf;} public void setUf(String v){uf=v;}
}
