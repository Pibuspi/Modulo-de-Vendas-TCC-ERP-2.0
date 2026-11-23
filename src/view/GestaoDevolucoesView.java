
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

/** Eúde e Pietro: gestão de devoluções comerciais. */
public class GestaoDevolucoesView extends JPanel {

    private final JTextField campoPedido = new JTextField(12);
    private final JTextField campoCliente = new JTextField(20);
    private final JTextField campoPrazo = new JTextField(10);
    private final JTextArea campoMotivo = new JTextArea(4, 30);
    private final JTextArea campoEvidencias = new JTextArea(4, 30);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Pedido", "Cliente", "Motivo", "Prazo", "Status"}, 0
    ) {
        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };

    private final JTable tabela = new JTable(modelo);

    public GestaoDevolucoesView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Gestão de Devoluções");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Solicitação", criarAbaSolicitacao());
        abas.addTab("Análise Comercial", criarAbaAnalise());
        abas.addTab("Ordem Comercial", criarAbaOrdem());

        add(abas, BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);
    }

    private JPanel criarAbaSolicitacao() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painel.add(new JLabel("Nº do Pedido:"));
        painel.add(campoPedido);

        painel.add(new JLabel("Cliente:"));
        painel.add(campoCliente);

        painel.add(new JLabel("Prazo:"));
        painel.add(campoPrazo);

        painel.add(new JLabel("Motivo da devolução:"));
        campoMotivo.setLineWrap(true);
        campoMotivo.setWrapStyleWord(true);
        painel.add(new JScrollPane(campoMotivo));

        painel.add(new JLabel("Evidências / Observações:"));
        campoEvidencias.setLineWrap(true);
        campoEvidencias.setWrapStyleWord(true);
        painel.add(new JScrollPane(campoEvidencias));

        JButton botaoRegistrar = new JButton("Registrar solicitação");
        botaoRegistrar.addActionListener(e -> registrarSolicitacao());
        painel.add(botaoRegistrar);

        return painel;
    }

    private JPanel criarAbaAnalise() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painel.add(new JLabel(
                "Analise as solicitações e selecione uma para avaliação."
        ), BorderLayout.NORTH);

        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton botaoAprovar = new JButton("Aprovar");
        JButton botaoRejeitar = new JButton("Rejeitar");

        botaoAprovar.addActionListener(e ->
                informarAcao("aprovação da devolução"));

        botaoRejeitar.addActionListener(e ->
                informarAcao("rejeição da devolução"));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(botaoRejeitar);
        botoes.add(botaoAprovar);

        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarAbaOrdem() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painel.add(new JLabel(
                "Ordens comerciais de devolução serão exibidas nesta área."
        ), BorderLayout.NORTH);

        return painel;
    }

    private JPanel criarRodape() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "A atualização será conectada aos dados de devolução.",
                        "Devoluções",
                        JOptionPane.INFORMATION_MESSAGE
                ));

        painel.add(botaoAtualizar);
        return painel;
    }

    private void registrarSolicitacao() {
        if (campoPedido.getText().isBlank()
                || campoCliente.getText().isBlank()
                || campoMotivo.getText().isBlank()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe o pedido, o cliente e o motivo da devolução.",
                    "Devoluções",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        modelo.addRow(new Object[]{
            campoPedido.getText().trim(),
            campoCliente.getText().trim(),
            campoMotivo.getText().trim(),
            campoPrazo.getText().trim(),
            "Solicitada"
        });

        JOptionPane.showMessageDialog(
                this,
                "Solicitação adicionada à tabela.",
                "Devoluções",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void informarAcao(String acao) {
        if (tabela.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma solicitação na tabela.",
                    "Devoluções",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "A " + acao + " será integrada ao Controller.",
                "Devoluções",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
