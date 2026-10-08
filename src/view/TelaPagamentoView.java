package view;

import controller.TelaPagamentoController;
import model.ClientePagamento;
import javax.swing.*;
import java.awt.*;

/** Checkout de venda virtual: consumidor, pagamento e confirmação do pedido. */
public class TelaPagamentoView extends JPanel {
    private final TelaPagamentoController controller = new TelaPagamentoController();
    private final double valorTotal;
    private final JTextField cpf = new JTextField(14), nome = new JTextField(20), ddd = new JTextField(4), telefone = new JTextField(10);
    private final JTextField cep = new JTextField(9), endereco = new JTextField(22), numero = new JTextField(7), uf = new JTextField("DF", 4);
    private final JComboBox<TelaPagamentoController.Metodo> formaPagamento = new JComboBox<>(TelaPagamentoController.Metodo.values());
    private final JTextField valorPago = new JTextField(12);
    private final JLabel troco = new JLabel(Formato.moeda(0));

    public TelaPagamentoView(double valorTotal) {
        this.valorTotal = valorTotal;
        Tema.aplicar();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(criarFormularioConsumidor(), BorderLayout.CENTER);
        add(criarResumoPagamento(), BorderLayout.SOUTH);
    }

    /** Monta os dados do consumidor usando os campos de ClientePagamento. */
    private JPanel criarFormularioConsumidor() {
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Dados do Consumidor"));
        Formulario.adicionarLinha(formulario, 0, "CPF:", cpf);
        Formulario.adicionarLinha(formulario, 1, "Nome:", nome);
        Formulario.adicionarLinha(formulario, 2, "DDD:", ddd);
        Formulario.adicionarLinha(formulario, 3, "Telefone:", telefone);
        Formulario.adicionarLinha(formulario, 4, "CEP:", cep);
        Formulario.adicionarLinha(formulario, 5, "Endereço:", endereco);
        Formulario.adicionarLinha(formulario, 6, "Número:", numero);
        Formulario.adicionarLinha(formulario, 7, "UF:", uf);
        Formulario.fecharFormulario(formulario, 8);
        return formulario;
    }

    /** Monta o resumo da compra, meio de pagamento, valor pago e troco. */
    private JPanel criarResumoPagamento() {
        JPanel resumo = new JPanel(new GridBagLayout());
        resumo.setBorder(BorderFactory.createTitledBorder("Resumo da Venda e Pagamento"));
        Formulario.adicionarLinha(resumo, 0, "Valor total:", new JLabel(Formato.moeda(valorTotal)));
        Formulario.adicionarLinha(resumo, 1, "Forma de pagamento:", formaPagamento);
        Formulario.adicionarLinha(resumo, 2, "Valor pago:", valorPago);
        Formulario.adicionarLinha(resumo, 3, "Troco:", troco);
        JButton atualizar = new JButton("Calcular Troco");
        atualizar.addActionListener(evento -> atualizarTroco());
        JButton confirmar = new JButton("Finalizar Pagamento / Confirmar Pedido");
        confirmar.addActionListener(evento -> confirmarPedido());
        JButton voltar = new JButton("Voltar ao Carrinho");
        voltar.addActionListener(evento -> voltarAoCarrinho());
        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        acoes.add(atualizar); acoes.add(voltar); acoes.add(confirmar);
        GridBagConstraints c = new GridBagConstraints(); c.gridx = 0; c.gridy = 4; c.gridwidth = 2; c.anchor = GridBagConstraints.EAST; resumo.add(acoes, c);
        return resumo;
    }

    /** Atualiza o recebimento e exibe o troco quando a forma permitir. */
    private void atualizarTroco() {
        try {
            double pago = Formato.lerNumero(valorPago.getText());
            controller.registrarRecebimento((TelaPagamentoController.Metodo) formaPagamento.getSelectedItem(), pago);
            troco.setText(Formato.moeda(controller.calcularTroco(valorTotal)));
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Pagamento", JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Valida consumidor e recebimento antes da confirmação final do pedido. */
    private void confirmarPedido() {
        try {
            ClientePagamento cliente = new ClientePagamento();
            cliente.setCpf(cpf.getText()); cliente.setNome(nome.getText()); cliente.setDdd(ddd.getText()); cliente.setTelefone(telefone.getText());
            cliente.setCep(cep.getText()); cliente.setEndereco(endereco.getText()); cliente.setNumero(numero.getText()); cliente.setUf(uf.getText());
            controller.validarCliente(cliente);
            atualizarTroco();
            if (!controller.podeConfirmar(valorTotal)) throw new IllegalArgumentException("O valor pago é insuficiente.");
            JOptionPane.showMessageDialog(this, "Pagamento confirmado e pedido finalizado.", "Checkout", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Retorna ao fluxo do carrinho sem caixa físico ou abertura de operador. */
    private void voltarAoCarrinho() {
        JOptionPane.showMessageDialog(this, "Retornando ao carrinho.", "Checkout", JOptionPane.INFORMATION_MESSAGE);
    }
}
