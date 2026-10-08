package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;

/** Pietro: solicitação, análise e ordem comercial de devoluções. */
public class GestaoDevolucoesView extends JPanel {
    private final JTextField idDevolucao = new JTextField(10);
    private final JTextField idPedido = new JTextField(10);
    private final JTextArea motivo = new JTextArea(3, 28);
    private final JTextField dataSolicitacao = new JTextField(LocalDate.now().toString(), 10);
    private final JTextField valorEstorno = new JTextField(Formato.moeda(0), 12);
    private final JComboBox<String> status = new JComboBox<>(new String[]{"SOLICITADO", "ANALISADO", "ORDEM_GERADA", "CANCELADO"});
    private final JTextField caminhoAnexoEvidencia = new JTextField(24);
    private final JTextArea parecerGerencial = new JTextArea(3, 28);
    private final DefaultTableModel itensModel = new DefaultTableModel(new Object[]{"idProduto", "nomeProduto", "quantidade", "elegivel"}, 0) {
        @Override public Class<?> getColumnClass(int coluna) { return coluna == 2 ? Integer.class : coluna == 3 ? Boolean.class : String.class; }
        @Override public boolean isCellEditable(int linha, int coluna) { return coluna == 2 || coluna == 3; }
    };
    private final JTable tabelaItens = new JTable(itensModel);

    public GestaoDevolucoesView() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(criarConsulta(), BorderLayout.NORTH);
        add(criarTabela(), BorderLayout.CENTER);
        add(criarDetalhes(), BorderLayout.SOUTH);
    }

    /** Monta os campos de identificação e consulta do vínculo da devolução. */
    private JPanel criarConsulta() {
        JPanel painel = new JPanel(new GridBagLayout()); painel.setBorder(BorderFactory.createTitledBorder("Identificação"));
        Formulario.adicionarLinha(painel, 0, "ID Devolução:", idDevolucao);
        JPanel pedido = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0)); pedido.add(idPedido);
        JButton buscar = new JButton("Buscar Pedido"); buscar.addActionListener(evento -> buscarPedido()); pedido.add(buscar);
        Formulario.adicionarLinha(painel, 1, "ID Pedido:", pedido);
        Formulario.adicionarLinha(painel, 2, "Data Solicitação:", dataSolicitacao);
        Formulario.adicionarLinha(painel, 3, "Status:", status);
        return painel;
    }

    /** Monta a tabela de itens de devolução com checkbox de elegibilidade. */
    private JScrollPane criarTabela() {
        tabelaItens.setFillsViewportHeight(true); tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(tabelaItens);
    }

    /** Monta motivo, estorno, evidência, parecer e ações da devolução. */
    private JPanel criarDetalhes() {
        JPanel painel = new JPanel(new BorderLayout(6, 6)); painel.setBorder(BorderFactory.createTitledBorder("Detalhes da Devolução"));
        JPanel campos = new JPanel(new GridBagLayout());
        Formulario.adicionarLinha(campos, 0, "Motivo:", new JScrollPane(motivo));
        Formulario.adicionarLinha(campos, 1, "Valor Estorno:", valorEstorno);
        Formulario.adicionarLinha(campos, 2, "Evidência:", criarCampoAnexo());
        Formulario.adicionarLinha(campos, 3, "Parecer Gerencial:", new JScrollPane(parecerGerencial));
        Formulario.fecharFormulario(campos, 4); painel.add(campos, BorderLayout.CENTER);
        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton buscar = new JButton("Buscar Pedido"); buscar.addActionListener(evento -> buscarPedido());
        JButton anexar = new JButton("Anexar Evidência"); anexar.addActionListener(evento -> selecionarAnexo());
        JButton solicitar = new JButton("Solicitar Devolução"); solicitar.addActionListener(evento -> solicitarDevolucao());
        JButton estorno = new JButton("Gerar Ordem de Estorno"); estorno.addActionListener(evento -> gerarOrdemEstorno());
        JButton cancelar = new JButton("Cancelar"); cancelar.addActionListener(evento -> cancelar());
        acoes.add(buscar); acoes.add(anexar); acoes.add(solicitar); acoes.add(estorno); acoes.add(cancelar); painel.add(acoes, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarCampoAnexo() {
        JPanel painel = new JPanel(new BorderLayout(4, 0)); painel.add(caminhoAnexoEvidencia, BorderLayout.CENTER);
        JButton anexar = new JButton("Anexar Evidência"); anexar.addActionListener(evento -> selecionarAnexo()); painel.add(anexar, BorderLayout.EAST); return painel;
    }

    /** Consulta o pedido e prepara as linhas de itens para análise. */
    private void buscarPedido() {
        if (idPedido.getText().isBlank()) { aviso("Informe o ID do pedido."); return; }
        itensModel.setRowCount(0); itensModel.addRow(new Object[]{"-", "Itens do pedido " + idPedido.getText(), 0, Boolean.FALSE});
    }

    private void selecionarAnexo() {
        JFileChooser seletor = new JFileChooser();
        if (seletor.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { File arquivo = seletor.getSelectedFile(); caminhoAnexoEvidencia.setText(arquivo.getAbsolutePath()); }
    }

    private void solicitarDevolucao() {
        if (idPedido.getText().isBlank() || motivo.getText().isBlank()) { aviso("Informe o pedido e o motivo da devolução."); return; }
        status.setSelectedItem("SOLICITADO"); aviso("Solicitação de devolução registrada.");
    }

    private void gerarOrdemEstorno() {
        if (idPedido.getText().isBlank()) { aviso("Informe o ID do pedido."); return; }
        status.setSelectedItem("ORDEM_GERADA"); aviso("Ordem de estorno gerada.");
    }

    private void cancelar() { status.setSelectedItem("CANCELADO"); aviso("Devolução cancelada."); }
    private void aviso(String mensagem) { JOptionPane.showMessageDialog(this, mensagem, "Gestão de Devoluções", JOptionPane.INFORMATION_MESSAGE); }
}
