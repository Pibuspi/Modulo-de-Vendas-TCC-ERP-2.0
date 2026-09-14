package model;
/** Regra de margem/desconto aplicável a uma tabela ou produto. */
public class RegraPreco { private double margemMinima, descontoMaximo; public double getMargemMinima(){return margemMinima;} public void setMargemMinima(double v){margemMinima=v;} public double getDescontoMaximo(){return descontoMaximo;} public void setDescontoMaximo(double v){descontoMaximo=v;} }