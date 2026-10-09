package view;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/** Formatação e leitura de números e moeda no padrão brasileiro. */
public final class Formato {
    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");
    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(PT_BR);
    private static final NumberFormat NUMERO = NumberFormat.getNumberInstance(PT_BR);
    private Formato() { }
    public static String moeda(double valor) { return MOEDA.format(valor); }
    public static String numero(double valor) { return NUMERO.format(valor); }
    public static double lerNumero(String texto) {
        String limpo = texto.replace("R$", "").trim();
        ParsePosition posicao = new ParsePosition(0);
        Number valor = NUMERO.parse(limpo, posicao);
        if (valor == null || posicao.getIndex() != limpo.length()) {
            throw new IllegalArgumentException("Valor numérico inválido: " + texto);
        }
        return valor.doubleValue();
    }
}
