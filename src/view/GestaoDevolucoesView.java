package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

/**
 * Pietro: interface de solicitação, análise e ordem comercial de devoluções.
 * Mantém componentes Swing nativos e uma apresentação neutra em tons de cinza.
 */
public class GestaoDevolucoesView extends JPanel {
    private final JTextField idPedido = new JTextField(12);
    private final JTextField motivo = new JTextField(30);
    private final JTextArea parecer = new JTextArea(4, 30);
    private final JTextField anexo = new JTextField(25);
    private final DefaultTableModel itensModel = new DefaultTableModel(
        new Object[] {"Código", "Produto", "Qtd Original", "Qtd a Devolver", "Elegível"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return column == 3; }
        @Override public Class<?> getColumnClass(int column) {
            return column == 2 || column == 3 ? Integer.class : String.class;
        }
    };
    private final JTable tabelaItens = new JTable(itensModel);

    public GestaoDevolucoesView() {
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(SystemColor.control);
        add(criarPainelBusca(), BorderLayout.NORTH);
        add(criarPainelItens(), BorderLayout.CENTER);
        add(criarPainelDetalhes(), BorderLayout.SOUTH);
    }

    /** Cria o painel superior com identificação e busca do pedido. */
    private JPanel criarPainelBusca() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.setBackground(SystemColor.control);
        painel.setBorder(BorderFactory.createTitledBorder("Consulta de Pedido"));
        painel.add(new JLabel("ID do Pedido:"));
        painel.add(idPedido);
        JButton buscar = new JButton("Buscar Pedido");
        buscar.addActionListener(evento -> buscarPedido());
        painel.add(buscar);
        return painel;
    }

    /** Cria a tabela de itens com as colunas operacionais da devolução. */
    private JPanel criarPainelItens() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(SystemColor.control);
        painel.setBorder(BorderFactory.createTitledBorder("Itens do Pedido"));
        tabelaItens.setFillsViewportHeight(true);
        tabelaItens.setGridColor(Color.LIGHT_GRAY);
        tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        painel.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);
        return painel;
    }

    /** Cria o formulário de motivo, parecer, evidência e ações finais. */
    private JPanel criarPainelDetalhes() {
        JPanel painel = new JPanel(new BorderLayout(6, 6));
        painel.setBackground(SystemColor.control);
        painel.setBorder(BorderFactory.createTitledBorder("Detalhes da Devolução"));

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setBackground(SystemColor.control);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3); c.anchor = GridBagConstraints.WEST;
        c.gridx = 0; c.gridy = 0; campos.add(new JLabel("Motivo da Devolução:"), c);
        c.gridx = 1; c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL; campos.add(motivo, c);
        c.gridx = 0; c.gridy = 1; c.weightx = 0; c.fill = GridBagConstraints.NONE; campos.add(new JLabel("Parecer Gerencial:"), c);
        c.gridx = 1; c.weightx = 1; c.fill = GridBagConstraints.BOTH; campos.add(new JScrollPane(parecer), c);
        c.gridx = 0; c.gridy = 2; c.weightx = 0; c.fill = GridBagConstraints.NONE; campos.add(new JLabel("Anexo de Evidência:"), c);
        JPanel anexoPainel = new JPanel(new BorderLayout(4, 0)); anexoPainel.setBackground(SystemColor.control); anexoPainel.add(anexo, BorderLayout.CENTER);
        JButton selecionar = new JButton("Selecionar Arquivo"); selecionar.addActionListener(evento -> selecionarAnexo());
        anexoPainel.add(selecionar, BorderLayout.EAST);
        c.gridx = 1; c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL; campos.add(anexoPainel, c);
        painel.add(campos, BorderLayout.CENTER);

        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        acoes.setBackground(SystemColor.control);
        JButton solicitar = new JButton("Solicitar Devolução"); solicitar.addActionListener(evento -> solicitarDevolucao());
        JButton estorno = new JButton("Gerar Ordem de Estorno"); estorno.addActionListener(evento -> gerarOrdemEstorno());
        JButton cancelar = new JButton("Cancelar"); cancelar.addActionListener(evento -> limparFormulario());
        acoes.add(solicitar); acoes.add(estorno); acoes.add(cancelar);
        painel.add(acoes, BorderLayout.SOUTH);
        return painel;
    }

    /** Busca o pedido e carrega os itens; a integração DAO será adicionada posteriormente. */
    private void buscarPedido() {
        if (idPedido.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o ID do pedido.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }
        itensModel.setRowCount(0);
        itensModel.addRow(new Object[] {"-", "Itens do pedido " + idPedido.getText(), 0, 0, "A consultar"});
    }

    /** Abre o seletor nativo de arquivos e armazena o caminho da evidência. */
    private void selecionarAnexo() {
        JFileChooser seletor = new JFileChooser();
        if (seletor.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arquivo = seletor.getSelectedFile();
            anexo.setText(arquivo.getAbsolutePath());
        }
    }

    /** Valida o mínimo necessário e registra a solicitação na interface. */
    private void solicitarDevolucao() {
        if (idPedido.getText().isBlank() || motivo.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o pedido e o motivo da devolução.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Solicitação de devolução registrada.", "Devolução", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Gera a ordem de estorno após verificar que há um pedido selecionado. */
    private void gerarOrdemEstorno() {
        if (idPedido.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o ID do pedido.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Ordem de estorno preparada para o pedido " + idPedido.getText() + ".", "Estorno", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Limpa os campos e os itens carregados no formulário. */
    private void limparFormulario() {
        idPedido.setText(""); motivo.setText(""); parecer.setText(""); anexo.setText(""); itensModel.setRowCount(0);
    }
}
