package model;
import java.util.*;
/** Tabela de preço comercial. */
public class TabelaPreco { private Long id; private String nome; private final List<RegraPreco> regras=new ArrayList<>(); public Long getId(){return id;} public void setId(Long v){id=v;} public String getNome(){return nome;} public void setNome(String v){nome=v;} public List<RegraPreco> getRegras(){return regras;} }