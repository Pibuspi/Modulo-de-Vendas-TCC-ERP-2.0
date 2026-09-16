package model;

/** Eduardo Yuri: estado do caixa do balcão/PDV, incluindo abertura e fechamento. */
public class CaixaBalcao {
    public enum Status { FECHADO, ABERTO }
    private Long id;
    private double saldoInicial;
    private double saldoFinal;
    private Status status = Status.FECHADO;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(double saldoInicial) { this.saldoInicial = saldoInicial; }
    public double getSaldoFinal() { return saldoFinal; }
    public void setSaldoFinal(double saldoFinal) { this.saldoFinal = saldoFinal; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
