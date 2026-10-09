package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/** Pricing Engine: regras por produto, simulação de preço e status de alçada (somente visual). */
public class PricingEngineView extends JPanel {

    // Faixas de alçada. TODO-INTEGRACAO: os limites oficiais vêm do PricingEngineController.
    private static final double DESCONTO_AUTOMATICO_MAXIMO = 5.0;
    private static final double DESCONTO_GERENTE_MAXIMO = 10.0;

    private static final String[] COLUNAS = {"SKU", "Produto", "Custo", "Preço mínimo", "Preço de tabela",
        "Margem mín.", "Desc. auto.", "Desc. gerente"};

    private static final int FAIXA_LIBERADO = 0;
    private static final int FAIXA_PENDENTE = 1;
    private static final int FAIXA_HARD_STOP = 2;

    // Tabela de regras
    private final DefaultTableModel modeloRegras = new DefaultTableModel(COLUNAS, 0) {
        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };
    private final JTable tabelaRegras = new JTable(modeloRegras);

    // Cards das faixas de alçada (o card aplicado fica em negrito)
    private final JLabel[] statusFaixas = {
        new JLabel("LIBERADO", SwingConstants.CENTER),
        new JLabel("PENDENTE_APROVACAO", SwingConstants.CENTER),
        new JLabel("HARD_STOP", SwingConstants.CENTER)
    };

    // Simulação
    private final JLabel rotuloProduto = new JLabel("Selecione uma regra na tabela.");
    private final JTextField campoCusto = new JTextField("0", 10);
    private final JTextField campoPreco = new JTextField("0", 10);
    private final JTextField campoDesconto = new JTextField("0", 10);

    // Resultado
    private final JLabel rotuloPrecoPraticado = new JLabel("-");
    private final JLabel rotuloMargem = new JLabel("-");
    private final JLabel rotuloFaixa = new JLabel("-");
    private final JLabel badgeStatus = new JLabel("-", SwingConstants.CENTER);
    private final JTextArea areaMotivo = new JTextArea(2, 20);

    public PricingEngineView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setPreferredSize(new Dimension(900, 600));

        add(criarTopo(), BorderLayout.NORTH);
        add(criarTabela(), BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);

        carregarRegrasExemplo();
    }

    // Topo: título e faixas de alçada
    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout(0, 10));

        JLabel titulo = new JLabel("Motor de Preços e Alçadas");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));

        topo.add(titulo, BorderLayout.NORTH);
        topo.add(criarFaixas(), BorderLayout.CENTER);
        return topo;
    }

    private JPanel criarFaixas() {
        JPanel faixas = new JPanel(new GridLayout(1, 3, 10, 0));
        faixas.add(criarCardFaixa("Até 5% · aprovação automática", FAIXA_LIBERADO));
        faixas.add(criarCardFaixa("5,01% a 10% · parecer do gerente", FAIXA_PENDENTE));
        faixas.add(criarCardFaixa("Acima de 10% · bloqueio imediato", FAIXA_HARD_STOP));
        return faixas;
    }

    private JPanel criarCardFaixa(String titulo, int faixa) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder(titulo));
        card.add(statusFaixas[faixa], BorderLayout.CENTER);
        return card;
    }

    // Centro: regras por produto
    private JPanel criarTabela() {
        tabelaRegras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaRegras.setRowHeight(24);
        tabelaRegras.getTableHeader().setReorderingAllowed(false);
        for (int coluna = 2; coluna <= 4; coluna++) {
            tabelaRegras.getColumnModel().getColumn(coluna).setCellRenderer(new RenderizadorNumero(true));
        }
        for (int coluna = 5; coluna <= 7; coluna++) {
            tabelaRegras.getColumnModel().getColumn(coluna).setCellRenderer(new RenderizadorNumero(false));
        }
        ajustarLarguraColunas();
        tabelaRegras.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherSimulacaoComRegra();
            }
        });

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Regras por produto"));
        painel.add(new JScrollPane(tabelaRegras), BorderLayout.CENTER);
        return painel;
    }

    private void ajustarLarguraColunas() {
        int[] larguras = {80, 170, 85, 100, 105, 90, 90, 100};
        for (int i = 0; i < larguras.length; i++) {
            tabelaRegras.getColumnModel().getColumn(i).setPreferredWidth(larguras[i]);
        }
    }

    // Rodapé: simulação, resultado e ações
    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout(0, 10));
        rodape.add(criarSimulacaoEResultado(), BorderLayout.CENTER);
        rodape.add(criarBotoes(), BorderLayout.SOUTH);
        return rodape;
    }

    private JPanel criarSimulacaoEResultado() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 10, 0));
        painel.add(criarPainelSimulacao());
        painel.add(criarPainelResultado());
        return painel;
    }

    private JPanel criarPainelSimulacao() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Simulação de preço"));
        Formulario.adicionarLinha(painel, 0, "Produto", rotuloProduto);
        Formulario.adicionarLinha(painel, 1, "Custo (R$)", campoCusto);
        Formulario.adicionarLinha(painel, 2, "Preço de tabela (R$)", campoPreco);
        Formulario.adicionarLinha(painel, 3, "Desconto (%)", campoDesconto);
        Formulario.fecharFormulario(painel, 4);
        return painel;
    }

    private JPanel criarPainelResultado() {
        badgeStatus.setFont(badgeStatus.getFont().deriveFont(Font.BOLD, 14f));
        badgeStatus.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        areaMotivo.setEditable(false);
        areaMotivo.setLineWrap(true);
        areaMotivo.setWrapStyleWord(true);
        areaMotivo.setOpaque(false);
        areaMotivo.setFont(rotuloMargem.getFont());

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Resultado"));
        Formulario.adicionarLinha(painel, 0, "Preço praticado", rotuloPrecoPraticado);
        Formulario.adicionarLinha(painel, 1, "Margem real", rotuloMargem);
        Formulario.adicionarLinha(painel, 2, "Faixa aplicada", rotuloFaixa);
        Formulario.adicionarLinha(painel, 3, "Status", badgeStatus);
        Formulario.adicionarLinha(painel, 4, "Motivo", areaMotivo);
        Formulario.fecharFormulario(painel, 5);
        return painel;
    }

    private JPanel criarBotoes() {
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton botaoLimpar = new JButton("Limpar");
        JButton botaoSimular = new JButton("Simular");
        botaoLimpar.addActionListener(e -> limpar());
        botaoSimular.addActionListener(e -> simular());
        botoes.add(botaoLimpar);
        botoes.add(botaoSimular);
        return botoes;
    }

    // Ações (apenas visuais)
    private void simular() {
        double custo;
        double preco;
        double desconto;
        try {
            custo = Formato.lerNumero(campoCusto.getText());
            preco = Formato.lerNumero(campoPreco.getText());
            desconto = Formato.lerNumero(campoDesconto.getText());
        } catch (IllegalArgumentException e) {
            avisar("Informe valores numéricos válidos. Exemplo: 12,50.");
            return;
        }
        if (custo < 0 || preco <= 0 || desconto < 0 || desconto > 100) {
            avisar("Custo e desconto não podem ser negativos, o preço deve ser maior que zero "
                    + "e o desconto deve ser de até 100%.");
            return;
        }

        // TODO-INTEGRACAO: trocar este bloco por controller.validarMargemLucro(custo, preco, desconto).
        double precoPraticado = preco * (1.0 - desconto / 100.0);
        double margem = precoPraticado <= 0 ? -100.0 : (precoPraticado - custo) / precoPraticado * 100.0;
        int faixa;
        String status;
        String motivo;
        if (desconto > DESCONTO_GERENTE_MAXIMO || margem < 0) {
            faixa = FAIXA_HARD_STOP;
            status = "HARD_STOP";
            motivo = "Desconto acima de 10% ou margem real negativa.";
        } else if (desconto > DESCONTO_AUTOMATICO_MAXIMO) {
            faixa = FAIXA_PENDENTE;
            status = "PENDENTE_APROVACAO";
            motivo = "Desconto exige parecer e aprovação gerencial.";
        } else {
            faixa = FAIXA_LIBERADO;
            status = "LIBERADO";
            motivo = "Operação liberada automaticamente.";
        }

        rotuloPrecoPraticado.setText(Formato.moeda(precoPraticado));
        rotuloMargem.setText(Formato.numero(Math.round(margem * 100) / 100.0) + "%");
        rotuloFaixa.setText(statusFaixas[faixa].getText());
        atualizarBadge(status);
        areaMotivo.setText(motivo);
        destacarFaixa(faixa);
    }

    private void limpar() {
        tabelaRegras.clearSelection();
        rotuloProduto.setText("Selecione uma regra na tabela.");
        campoCusto.setText("0");
        campoPreco.setText("0");
        campoDesconto.setText("0");
        rotuloPrecoPraticado.setText("-");
        rotuloMargem.setText("-");
        rotuloFaixa.setText("-");
        atualizarBadge("-");
        areaMotivo.setText("");
        destacarFaixa(-1);
    }

    private void avisar(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Validação", JOptionPane.WARNING_MESSAGE);
    }

    // Atualizações de tela
    private void preencherSimulacaoComRegra() {
        int linha = tabelaRegras.getSelectedRow();
        if (linha < 0) {
            return;
        }
        rotuloProduto.setText(modeloRegras.getValueAt(linha, 0) + " - " + modeloRegras.getValueAt(linha, 1));
        campoCusto.setText(Formato.numero(((Number) modeloRegras.getValueAt(linha, 2)).doubleValue()));
        campoPreco.setText(Formato.numero(((Number) modeloRegras.getValueAt(linha, 4)).doubleValue()));
    }

    /** Mostra o status no badge. TODO-TEMA: colorir o badge com as cores do Tema quando existirem. */
    private void atualizarBadge(String status) {
        badgeStatus.setText(status);
    }

    /** Deixa em negrito o status da faixa aplicada; use -1 para nenhuma. */
    private void destacarFaixa(int faixaAplicada) {
        for (int i = 0; i < statusFaixas.length; i++) {
            float tamanho = statusFaixas[i].getFont().getSize2D();
            int estilo = i == faixaAplicada ? Font.BOLD : Font.PLAIN;
            statusFaixas[i].setFont(statusFaixas[i].getFont().deriveFont(estilo, tamanho));
        }
    }

    // Dados de exemplo. TODO-INTEGRACAO: trocar pela TabelaPreco do Controller.
    private void carregarRegrasExemplo() {
        modeloRegras.setRowCount(0);
        modeloRegras.addRow(new Object[]{"CAB-001", "Cabo HDMI 2 m", 12.00, 16.00, 24.90, 20.0, 5.0, 10.0});
        modeloRegras.addRow(new Object[]{"MOU-002", "Mouse óptico USB", 18.50, 25.00, 39.90, 20.0, 5.0, 10.0});
        modeloRegras.addRow(new Object[]{"TEC-003", "Teclado ABNT2", 42.00, 55.00, 79.90, 18.0, 5.0, 10.0});
        modeloRegras.addRow(new Object[]{"MON-004", "Monitor 24 pol.", 520.00, 640.00, 899.00, 15.0, 5.0, 10.0});
        modeloRegras.addRow(new Object[]{"HDS-005", "Headset USB", 65.00, 85.00, 129.90, 18.0, 5.0, 10.0});
    }

    /** Mostra números da coluna como moeda ou como percentual, alinhados à direita. */
    private static class RenderizadorNumero extends DefaultTableCellRenderer {
        private final boolean moeda;

        RenderizadorNumero(boolean moeda) {
            this.moeda = moeda;
            setHorizontalAlignment(SwingConstants.RIGHT);
        }

        @Override
        protected void setValue(Object valor) {
            if (!(valor instanceof Number)) {
                setText("");
                return;
            }
            double numero = ((Number) valor).doubleValue();
            setText(moeda ? Formato.moeda(numero) : Formato.numero(numero) + "%");
        }
    }
}