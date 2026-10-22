package model;

/** Estado do caixa do balcão/PDV. */
public class CaixaBalcao {
    public enum Status { FECHADO, ABERTO }
    private Long id; private double saldoInicial, saldoFinal; private Status status=Status.FECHADO;
    public CaixaBalcao() {}
    public CaixaBalcao(Long id,double saldoInicial,double saldoFinal,Status status){this.id=id;this.saldoInicial=saldoInicial;this.saldoFinal=saldoFinal;this.status=status;}
    public Long getId(){return id;} public void setId(Long v){id=v;} public double getSaldoInicial(){return saldoInicial;} public void setSaldoInicial(double v){saldoInicial=v;} public double getSaldoFinal(){return saldoFinal;} public void setSaldoFinal(double v){saldoFinal=v;} public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
}
