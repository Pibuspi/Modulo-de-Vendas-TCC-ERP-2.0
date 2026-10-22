package model;

/** Matheus Godoy: faixas de governança comercial para descontos. */
public class AlcadaComercial {
    private String nome;
    private double descontoInicial, descontoFinal;
    public AlcadaComercial() {}
    public AlcadaComercial(String nome, double inicial, double final_) { this.nome = nome; descontoInicial = inicial; descontoFinal = final_; }
    public String getNome() { return nome; } public void setNome(String v) { nome = v; }
    public double getDescontoInicial() { return descontoInicial; } public void setDescontoInicial(double v) { descontoInicial = v; }
    public double getDescontoFinal() { return descontoFinal; } public void setDescontoFinal(double v) { descontoFinal = v; }
}
