package model;
/** Item elegível para devolução, com quantidade e motivo específico. */
public class ItemDevolucao {
	private Produto produto; 
	private double quantidade; 
	private String motivo; 
	public Produto getProduto(){return produto;} 
	public void setProduto(Produto v){produto=v;} 
	public double getQuantidade(){return quantidade;} 
	public void setQuantidade(double v){quantidade=v;} 
	public String getMotivo(){return motivo;} 
	public void setMotivo(String v){motivo=v;} 
	}