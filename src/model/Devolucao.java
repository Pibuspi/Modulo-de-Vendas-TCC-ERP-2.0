package model;
import java.util.*;
/** Processo de devolução, parecer e ordem de estorno. */
public class Devolucao { 
	private Long id; 
	private String motivo, parecer, ordemEstorno; 
	private final List<ItemDevolucao> itens=new ArrayList<>(); 
	public Long getId(){return id;} 
	public void setId(Long v){id=v;} 
	public String getMotivo(){return motivo;} 
	public void setMotivo(String v){motivo=v;} 
	public String getParecer(){return parecer;} 
	public void setParecer(String v){parecer=v;} 
	public String getOrdemEstorno(){return ordemEstorno;} 
	public void setOrdemEstorno(String v){ordemEstorno=v;} 
	public List<ItemDevolucao> getItens(){return itens;} 
	}