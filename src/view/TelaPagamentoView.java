package view;

import controller.TelaPagamentoController;
import model.ClientePagamento;
import javax.swing.*;
import java.awt.*;

/** Eduardo Yuri: cadastro do consumidor, recebimento múltiplo e confirmação da venda. */
public class TelaPagamentoView extends JPanel {
    private final TelaPagamentoController controller = new TelaPagamentoController();
    private final double total;
    private final JTextField nome = new JTextField(12), cpf = new JTextField(12), ddd = new JTextField(4), telefone = new JTextField(9), cep = new JTextField(8), endereco = new JTextField(14), numero = new JTextField(5), uf = new JTextField("DF", 3);
    private final JTextField dinheiro = new JTextField("0", 8), pix = new JTextField("0", 8), cartao = new JTextField("0", 8);
    private final JLabel pago = new JLabel("R$ 0,00"), troco = new JLabel("R$ 0,00");
    public TelaPagamentoView(double total) { this.total = total; setLayout(new BorderLayout(8, 8)); add(formulario(), BorderLayout.CENTER); add(recebimento(), BorderLayout.SOUTH); }
    private JPanel formulario() { JPanel p = new JPanel(new GridLayout(4, 4, 5, 5)); p.setBorder(BorderFactory.createTitledBorder("Dados do consumidor")); campo(p,"Nome",nome); campo(p,"CPF",cpf); campo(p,"DDD",ddd); campo(p,"Telefone",telefone); campo(p,"CEP",cep); campo(p,"Endereço",endereco); campo(p,"Número",numero); campo(p,"UF",uf); return p; }
    private void campo(JPanel p, String titulo, JTextField campo) { p.add(new JLabel(titulo)); p.add(campo); }
    private JPanel recebimento() { JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT)); p.setBorder(BorderFactory.createTitledBorder("Pagamento")); p.add(new JLabel(String.format("Total: R$ %.2f", total))); p.add(new JLabel("Dinheiro")); p.add(dinheiro); p.add(new JLabel("PIX")); p.add(pix); p.add(new JLabel("Cartão")); p.add(cartao); JButton atualizar = new JButton("Atualizar"); atualizar.addActionListener(e -> atualizar()); p.add(atualizar); p.add(new JLabel("Pago:")); p.add(pago); p.add(new JLabel("Troco:")); p.add(troco); JButton confirmar = new JButton("Confirmar venda"); confirmar.addActionListener(e -> confirmar()); p.add(confirmar); return p; }

private double lerValor(JTextField campo) {
    String texto = campo.getText().trim().replace(",", ".");

    double valor = Double.parseDouble(texto);

    if (!Double.isFinite(valor) || valor < 0) {
        throw new IllegalArgumentException(
            "Os valores devem ser números positivos ou zero."
        );
    }

    return valor;
}

private void atualizar() {
    try {
        // Primeiro, lê e valida todos os campos.
        double valorDinheiro = lerValor(dinheiro);
        double valorPix = lerValor(pix);
        double valorCartao = lerValor(cartao);

        // Só registra os valores depois que todos forem válidos.
        controller.registrarRecebimento(
            TelaPagamentoController.Metodo.DINHEIRO,
            valorDinheiro
        );

        controller.registrarRecebimento(
            TelaPagamentoController.Metodo.PIX,
            valorPix
        );

        controller.registrarRecebimento(
            TelaPagamentoController.Metodo.CARTAO,
            valorCartao
        );

        pago.setText(String.format(
            "R$ %.2f", controller.totalPago()
        ));

        troco.setText(String.format(
            "R$ %.2f", controller.calcularTroco(total)
        ));

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
            this,
            "Digite valores válidos. Exemplo: 10,50.",
            "Pagamento",
            JOptionPane.WARNING_MESSAGE
        );

    } catch (RuntimeException e) {
        JOptionPane.showMessageDialog(
            this,
            e.getMessage(),
            "Pagamento",
            JOptionPane.WARNING_MESSAGE
        );
    }
}
    private void confirmar() { try { ClientePagamento c = new ClientePagamento(); c.setNome(nome.getText()); c.setCpf(cpf.getText()); c.setDdd(ddd.getText()); c.setTelefone(telefone.getText()); c.setCep(cep.getText()); c.setEndereco(endereco.getText()); c.setNumero(numero.getText()); c.setUf(uf.getText()); controller.validarCliente(c); atualizar(); if (!controller.podeConfirmar(total)) throw new IllegalArgumentException("Valor recebido insuficiente."); JOptionPane.showMessageDialog(this, "Venda confirmada."); } catch (RuntimeException e) { JOptionPane.showMessageDialog(this, e.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE); } }
}
