package model;

import java.util.ArrayList;
import java.util.List;
/** Matheus Godoy: conjunto de regras de preço aplicável a produtos. */
public class TabelaPreco {
    private String nome;
    private final List<RegraPreco> regras = new ArrayList<>();
    public String getNome() { return nome; } public void setNome(String v) { nome = v; }
    public List<RegraPreco> getRegras() { return regras; }
}
