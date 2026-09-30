package controller;

import model.ResultadoPrecificacao;
import model.ResultadoPrecificacao.StatusAprovacao;

/** Matheus Godoy: motor de margens, alçadas e bloqueios da venda. */
public class PricingEngineController {
    public static final double DESCONTO_AUTOMATICO_MAXIMO = 5.0;
    public static final double DESCONTO_GERENTE_MAXIMO = 10.0;

    /** Calcula margem real: (preço praticado - custo) / preço praticado * 100. */
    public ResultadoPrecificacao validarMargemLucro(double custo, double precoVenda, double desconto) {
        if (custo < 0 || precoVenda <= 0 || desconto < 0) {
            return resultado(precoVenda, desconto, -100.0, StatusAprovacao.HARD_STOP, "Valores de custo, preço ou desconto inválidos.");
        }
        double precoPraticado = precoVenda * (1.0 - desconto / 100.0);
        double margemReal = precoPraticado <= 0 ? -100.0 : ((precoPraticado - custo) / precoPraticado) * 100.0;
        StatusAprovacao status;
        String justificativa;
        if (desconto > DESCONTO_GERENTE_MAXIMO || margemReal < 0) {
            status = StatusAprovacao.HARD_STOP;
            justificativa = "Desconto acima de 10% ou margem real negativa.";
        } else if (desconto > DESCONTO_AUTOMATICO_MAXIMO) {
            status = StatusAprovacao.PENDENTE_APROVACAO;
            justificativa = "Desconto exige parecer e aprovação gerencial.";
        } else {
            status = StatusAprovacao.LIBERADO;
            justificativa = "Operação liberada automaticamente.";
        }
        return resultado(precoPraticado, desconto, margemReal, status, justificativa);
    }

    private ResultadoPrecificacao resultado(double preco, double desconto, double margem, StatusAprovacao status, String justificativa) {
        return new ResultadoPrecificacao(preco, desconto, margem, status, justificativa);
    }
}
