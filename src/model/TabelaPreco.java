package model;

import java.util.ArrayList;
import java.util.List;

/** Conjunto de regras de preço aplicável a produtos. */
public class TabelaPreco {
    private String nome; private String status; private final List<RegraPreco> regras=new ArrayList<>();
    public TabelaPreco() {}
    public TabelaPreco(String nome,String status,List<RegraPreco> regras){this.nome=nome;this.status=status;if(regras!=null)this.regras.addAll(regras);}
    public String getNome(){return nome;} public void setNome(String v){nome=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;} public List<RegraPreco> getRegras(){return regras;}
}
