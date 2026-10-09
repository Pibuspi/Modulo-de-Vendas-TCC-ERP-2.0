package view;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/** Eúde e Pietro: cockpit com KPIs, filtros, seleção múltipla e consolidação (somente visual). */
public class LiberacaoComercialView extends JPanel {

    // Listas provisórias (wireframe). TODO-INTEGRACAO: trocar pela fonte oficial do Back.
    private static final String[] STATUS = {"TODOS", "ABERTO", "BLOQUEADO", "PENDENTE_APROVACAO",
        "LIBERADO_PARA_FATURAMENTO", "DEVOLVIDO_AO_VENDEDOR"};
    private static final String[] PAGAMENTOS = {"TODOS", "À Vista", "7 dias", "15 dias", "30 dias",
        "30/60 dias"};
    private static final String[] COLUNAS = {"Pedido", "Cliente", "Vendedor", "Pagamento", "Status", "Total"};

    private static final int COLUNA_CLIENTE = 1;
    private static final int COLUNA_PAGAMENTO = 3;
    private static final int COLUNA_STATUS = 4;
    private static final int COLUNA_TOTAL = 5;

    // Filtros
    private final JTextField campoCliente = new JTextField(10);
    private final JTextField campoVendedor = new JTextField(10);
    private final JComboBox<String> campoPagamento = new JComboBox<>(PAGAMENTOS);
    private final JComboBox<String> campoStatus = new JComboBox<>(STATUS);

    // Cards de KPI
    private final JLabel kpiAbertos = new JLabel("0");
    private final JLabel kpiPendentes = new JLabel("0");
    private final JLabel kpiBloqueados = new JLabel("0");
    private final JLabel kpiLiberados = new JLabel("0");

    // Tabela de pedidos
    private final DefaultTableModel modeloPedidos = new DefaultTableModel(COLUNAS, 0) {
        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };
    private final JTable tabelaPedidos = new JTable(modeloPedidos);

    // Painel de consolidação
    private final JLabel rotuloQuantidade = new JLabel("0");
    private final JLabel rotuloClientes = new JLabel("0");
    private final JLabel rotuloPagamentos = new JLabel("0");
    private final JLabel rotuloTotal = new JLabel(Formato.moeda(0));
    private final JLabel alertaIncompatibilidade = new JLabel();
    private final JCheckBox checkCredito = new JCheckBox("Crédito conferido");
    private final JCheckBox checkMargem = new JCheckBox("Margem conferida");
    private final JCheckBox checkAlcada = new JCheckBox("Alçada conferida");

    public LiberacaoComercialView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setPreferredSize(new Dimension(900, 600));

        add(criarTopo(), BorderLayout.NORTH);
        add(criarCorpo(), BorderLayout.CENTER);
        add(criarBotoes(), BorderLayout.SOUTH);

        carregarPedidosExemplo();
    }

    // Topo: título, KPIs e filtros
    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout(0, 10));

        JLabel titulo = new JLabel("Liberação e Consolidação Comercial");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));

        topo.add(titulo, BorderLayout.NORTH);
        topo.add(criarKpis(), BorderLayout.CENTER);
        topo.add(criarFiltros(), BorderLayout.SOUTH);
        return topo;
    }

    private JPanel criarKpis() {
        JPanel kpis = new JPanel(new GridLayout(1, 4, 10, 0));
        kpis.add(criarCard("Em aberto", kpiAbertos));
        kpis.add(criarCard("Pendentes de aprovação", kpiPendentes));
        kpis.add(criarCard("Bloqueados", kpiBloqueados));
        kpis.add(criarCard("Liberados para faturamento", kpiLiberados));
        return kpis;
    }

    private JPanel criarCard(String titulo, JLabel valor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder(titulo));
        valor.setFont(valor.getFont().deriveFont(Font.BOLD, 22f));
        valor.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valor, BorderLayout.CENTER);
        return card;
    }

    private JPanel criarFiltros() {
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        filtros.add(new JLabel("Cliente:"));
        filtros.add(campoCliente);
        filtros.add(new JLabel("Vendedor:"));
        filtros.add(campoVendedor);
        filtros.add(new JLabel("Pagamento:"));
        filtros.add(campoPagamento);
        filtros.add(new JLabel("Status:"));
        filtros.add(campoStatus);
        return filtros;
    }

    // Corpo: tabela de pedidos e painel lateral de consolidação
    private JPanel criarCorpo() {
        JPanel corpo = new JPanel(new BorderLayout(10, 0));
        corpo.add(criarTabela(), BorderLayout.CENTER);
        corpo.add(criarPainelConsolidacao(), BorderLayout.EAST);
        return corpo;
    }

    private JPanel criarTabela() {
        tabelaPedidos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tabelaPedidos.setRowHeight(24);
        tabelaPedidos.getTableHeader().setReorderingAllowed(false);
        tabelaPedidos.getColumnModel().getColumn(COLUNA_TOTAL).setCellRenderer(new RenderizadorMoeda());
        ajustarLarguraColunas();
        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarConsolidacao();
            }
        });

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Pedidos"));
        painel.add(new JScrollPane(tabelaPedidos), BorderLayout.CENTER);
        return painel;
    }

    private void ajustarLarguraColunas() {
        int[] larguras = {52, 125, 70, 82, 198, 90};
        for (int i = 0; i < larguras.length; i++) {
            tabelaPedidos.getColumnModel().getColumn(i).setPreferredWidth(larguras[i]);
        }
    }

    private JPanel criarPainelConsolidacao() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Consolidação"));
        painel.setPreferredSize(new Dimension(220, 0));

        JPanel resumo = new JPanel(new GridBagLayout());
        Formulario.adicionarLinha(resumo, 0, "Pedidos", rotuloQuantidade);
        Formulario.adicionarLinha(resumo, 1, "Clientes", rotuloClientes);
        Formulario.adicionarLinha(resumo, 2, "Pagamentos", rotuloPagamentos);
        Formulario.adicionarLinha(resumo, 3, "Total", rotuloTotal);

        JPanel checklist = new JPanel(new GridLayout(0, 1));
        checklist.setBorder(BorderFactory.createTitledBorder("Checklist"));
        checklist.add(checkCredito);
        checklist.add(checkMargem);
        checklist.add(checkAlcada);

        alertaIncompatibilidade.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        alertaIncompatibilidade.setVisible(false);

        JPanel inferior = new JPanel(new BorderLayout(0, 10));
        inferior.add(checklist, BorderLayout.NORTH);
        inferior.add(alertaIncompatibilidade, BorderLayout.CENTER);

        painel.add(resumo, BorderLayout.NORTH);
        painel.add(inferior, BorderLayout.CENTER);
        return painel;
    }

    // Rodapé: ações
    private JPanel criarBotoes() {
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton botaoLimpar = new JButton("Limpar Filtros");
        JButton botaoDevolver = new JButton("Devolver ao Vendedor");
        JButton botaoLiberar = new JButton("Liberar");
        botaoLimpar.addActionListener(e -> limparFiltros());
        botaoDevolver.addActionListener(e -> devolver());
        botaoLiberar.addActionListener(e -> liberar());
        botoes.add(botaoLimpar);
        botoes.add(botaoDevolver);
        botoes.add(botaoLiberar);
        return botoes;
    }

    // Ações (apenas visuais)
    private void limparFiltros() {
        campoCliente.setText("");
        campoVendedor.setText("");
        campoPagamento.setSelectedIndex(0);
        campoStatus.setSelectedIndex(0);
    }

    private void liberar() {
        if (tabelaPedidos.getSelectedRowCount() == 0) {
            avisar("Selecione ao menos um pedido para liberar.", "Liberação Comercial");
            return;
        }
        if (!checkCredito.isSelected() || !checkMargem.isSelected() || !checkAlcada.isSelected()) {
            avisar("Confirme o checklist de crédito, margem e alçada.", "Liberação Comercial");
            return;
        }
        // TODO-INTEGRACAO: chamar o LiberacaoComercialController quando existir.
        JOptionPane.showMessageDialog(this, "Liberação validada e pronta para envio ao faturamento.",
                "Liberação Comercial", JOptionPane.INFORMATION_MESSAGE);
    }

    private void devolver() {
        if (tabelaPedidos.getSelectedRowCount() == 0) {
            avisar("Selecione ao menos um pedido para devolver.", "Devolver ao Vendedor");
            return;
        }
        abrirDialogoDevolucao();
    }

    private void avisar(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, JOptionPane.WARNING_MESSAGE);
    }

    private void abrirDialogoDevolucao() {
        JDialog dialogo = new JDialog(JOptionPane.getFrameForComponent(this), "Devolver ao Vendedor", true);

        JTextArea justificativa = new JTextArea(6, 32);
        justificativa.setLineWrap(true);
        justificativa.setWrapStyleWord(true);

        JButton cancelar = new JButton("Cancelar");
        JButton confirmar = new JButton("Confirmar Devolução");
        cancelar.addActionListener(e -> dialogo.dispose());
        confirmar.addActionListener(e -> {
            if (justificativa.getText().isBlank()) {
                avisar("Informe a justificativa da devolução.", "Validação");
                return;
            }
            // TODO-INTEGRACAO: chamar o LiberacaoComercialController quando existir.
            JOptionPane.showMessageDialog(dialogo, "Devolução validada e pronta para envio.",
                    "Devolver ao Vendedor", JOptionPane.INFORMATION_MESSAGE);
            dialogo.dispose();
        });

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.add(cancelar);
        botoes.add(confirmar);

        JPanel conteudo = new JPanel(new BorderLayout(10, 10));
        conteudo.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        conteudo.add(new JLabel("Justificativa da devolução *"), BorderLayout.NORTH);
        conteudo.add(new JScrollPane(justificativa), BorderLayout.CENTER);
        conteudo.add(botoes, BorderLayout.SOUTH);

        dialogo.add(conteudo);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setResizable(false);
        dialogo.setVisible(true);
    }

    // Atualizações de tela
    /** Recalcula o painel lateral a partir das linhas marcadas (a tabela não usa ordenação). */
    private void atualizarConsolidacao() {
        int[] linhas = tabelaPedidos.getSelectedRows();
        Set<Object> clientes = new HashSet<>();
        Set<Object> pagamentos = new HashSet<>();
        double total = 0;

        for (int linha : linhas) {
            clientes.add(modeloPedidos.getValueAt(linha, COLUNA_CLIENTE));
            pagamentos.add(modeloPedidos.getValueAt(linha, COLUNA_PAGAMENTO));
            total += ((Number) modeloPedidos.getValueAt(linha, COLUNA_TOTAL)).doubleValue();
        }

        rotuloQuantidade.setText(String.valueOf(linhas.length));
        rotuloClientes.setText(String.valueOf(clientes.size()));
        rotuloPagamentos.setText(String.valueOf(pagamentos.size()));
        rotuloTotal.setText(Formato.moeda(total));

        boolean incompativel = clientes.size() > 1 || pagamentos.size() > 1;
        if (incompativel) {
            alertaIncompatibilidade.setText("<html><b>Atenção:</b> os pedidos selecionados têm "
                    + "clientes ou condições de pagamento diferentes.</html>");
        }
        alertaIncompatibilidade.setVisible(incompativel);
    }

    private void atualizarKpis() {
        kpiAbertos.setText(String.valueOf(contarPorStatus("ABERTO")));
        kpiPendentes.setText(String.valueOf(contarPorStatus("PENDENTE_APROVACAO")));
        kpiBloqueados.setText(String.valueOf(contarPorStatus("BLOQUEADO")));
        kpiLiberados.setText(String.valueOf(contarPorStatus("LIBERADO_PARA_FATURAMENTO")));
    }

    private int contarPorStatus(String status) {
        int quantidade = 0;
        for (int i = 0; i < modeloPedidos.getRowCount(); i++) {
            if (status.equals(modeloPedidos.getValueAt(i, COLUNA_STATUS))) {
                quantidade++;
            }
        }
        return quantidade;
    }

    // Dados de exemplo. TODO-INTEGRACAO: trocar pela lista de pedidos do Controller.
    private void carregarPedidosExemplo() {
        modeloPedidos.setRowCount(0);
        modeloPedidos.addRow(new Object[]{"1001", "Comercial Alfa Ltda", "Marcos", "30 dias", "PENDENTE_APROVACAO", 12500.00});
        modeloPedidos.addRow(new Object[]{"1002", "Comercial Alfa Ltda", "Marcos", "30 dias", "PENDENTE_APROVACAO", 8340.50});
        modeloPedidos.addRow(new Object[]{"1003", "Distribuidora Beta", "Juliana", "15 dias", "ABERTO", 4720.00});
        modeloPedidos.addRow(new Object[]{"1004", "Mercado Gama", "Juliana", "À Vista", "BLOQUEADO", 1980.90});
        modeloPedidos.addRow(new Object[]{"1005", "Atacado Delta", "Marcos", "30/60 dias", "PENDENTE_APROVACAO", 23600.00});
        modeloPedidos.addRow(new Object[]{"1006", "Distribuidora Beta", "Juliana", "15 dias", "LIBERADO_PARA_FATURAMENTO", 6150.75});
        modeloPedidos.addRow(new Object[]{"1007", "Loja Épsilon", "Marcos", "7 dias", "DEVOLVIDO_AO_VENDEDOR", 3210.00});
        modeloPedidos.addRow(new Object[]{"1008", "Mercado Gama", "Juliana", "À Vista", "ABERTO", 2890.40});
        atualizarKpis();
    }

    /** Mostra valores numéricos da coluna como moeda, alinhados à direita. */
    private static class RenderizadorMoeda extends DefaultTableCellRenderer {
        RenderizadorMoeda() {
            setHorizontalAlignment(SwingConstants.RIGHT);
        }

        @Override
        protected void setValue(Object valor) {
            setText(valor instanceof Number ? Formato.moeda(((Number) valor).doubleValue()) : "");
        }
    }
}
