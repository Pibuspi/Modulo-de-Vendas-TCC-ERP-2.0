package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Controller responsável pelas Regras de Negócio do Motor de Preços (Pricing Engine).
 * Foco em processamento de dados, cálculo de margem real, validação de descontos e travas de Hard Stop.
 */
public class PricingEngineController {

    /**
     * DTO interno para transportar o resultado da simulação de preço de forma limpa.
     */
    public static class ResultadoSimulacao {
        private BigDecimal precoPraticado;
        private BigDecimal margemReal;
        private int faixa; // 0: LIBERADO, 1: PENDENTE_APROVACAO, 2: HARD_STOP
        private String status;
        private String motivo;

        public ResultadoSimulacao(BigDecimal precoPraticado, BigDecimal margemReal, int faixa, String status, String motivo) {
            this.precoPraticado = precoPraticado;
            this.margemReal = margemReal;
            this.faixa = faixa;
            this.status = status;
            this.motivo = motivo;
        }

        public BigDecimal getPrecoPraticado() { return precoPraticado; }
        public BigDecimal getMargemReal() { return margemReal; }
        public int getFaixa() { return faixa; }
        public String getStatus() { return status; }
        public String getMotivo() { return motivo; }
    }

    /**
     * Valida a margem de lucro, calcula o preço praticado e define a faixa de alçada.
     */
    public ResultadoSimulacao validarMargemLucro(BigDecimal custo, BigDecimal precoTabela, BigDecimal descontoPercentual, BigDecimal margemMinimaPermitida) {
        if (custo == null) custo = BigDecimal.ZERO;
        if (precoTabela == null) precoTabela = BigDecimal.ZERO;
        if (descontoPercentual == null) descontoPercentual = BigDecimal.ZERO;
        if (margemMinimaPermitida == null) margemMinimaPermitida = new BigDecimal("18.00");

        // Preço Praticado = Preço Tabela * (1 - Desconto / 100)
        BigDecimal cem = new BigDecimal("100.00");
        BigDecimal fatorDesconto = BigDecimal.ONE.subtract(descontoPercentual.divide(cem, 4, RoundingMode.HALF_UP));
        BigDecimal precoPraticado = precoTabela.multiply(fatorDesconto).setScale(2, RoundingMode.HALF_UP);

        // Margem Real = ((Preço Praticado - Custo) / Preço Praticado) * 100
        BigDecimal margemReal = BigDecimal.ZERO;
        if (precoPraticado.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal lucro = precoPraticado.subtract(custo);
            margemReal = lucro.divide(precoPraticado, 4, RoundingMode.HALF_UP)
                    .multiply(cem)
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            margemReal = new BigDecimal("-100.00");
        }

        int faixa;
        String status;
        String motivo;

        BigDecimal limiteGerente = new BigDecimal("10.00");
        BigDecimal limiteAuto = new BigDecimal("5.00");

        if (descontoPercentual.compareTo(limiteGerente) > 0 || margemReal.compareTo(margemMinimaPermitida) < 0 || margemReal.compareTo(BigDecimal.ZERO) < 0) {
            faixa = 2; // FAIXA_HARD_STOP
            status = "HARD_STOP";
            motivo = "Desconto acima de 10% ou margem real inferior ao mínimo permitido (" + margemMinimaPermitida + "%).";
        } else if (descontoPercentual.compareTo(limiteAuto) > 0) {
            faixa = 1; // FAIXA_PENDENTE
            status = "PENDENTE_APROVACAO";
            motivo = "Desconto entre 5,01% e 10% exige parecer e aprovação gerencial.";
        } else {
            faixa = 0; // FAIXA_LIBERADO
            status = "LIBERADO";
            motivo = "Operação dentro da alçada padrão, liberada automaticamente.";
        }

        return new ResultadoSimulacao(precoPraticado, margemReal, faixa, status, motivo);
    }
}