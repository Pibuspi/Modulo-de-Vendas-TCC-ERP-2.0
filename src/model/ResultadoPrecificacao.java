package model;

/** Resultado de uma validação de margem e alçada comercial. */
public class ResultadoPrecificacao {
    public enum StatusAprovacao { LIBERADO, PENDENTE_APROVACAO, HARD_STOP }
    private double precoPraticado, descontoAplicado, margemReal;
    private StatusAprovacao statusAprovacao;
    private String justificativa;
    public ResultadoPrecificacao(double precoPraticado, double descontoAplicado, double margemReal, StatusAprovacao status, String justificativa) {
        this.precoPraticado = precoPraticado; this.descontoAplicado = descontoAplicado; this.margemReal = margemReal;
        this.statusAprovacao = status; this.justificativa = justificativa;
    }
    public double getPrecoPraticado() { return precoPraticado; }
    public double getDescontoAplicado() { return descontoAplicado; }
    public double getMargemReal() { return margemReal; }
    public StatusAprovacao getStatusAprovacao() { return statusAprovacao; }
    public String getJustificativa() { return justificativa; }
}
