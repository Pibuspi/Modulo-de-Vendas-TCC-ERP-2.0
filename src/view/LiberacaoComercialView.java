
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/** Eúde e Pietro: cockpit de liberação comercial. */
public class LiberacaoComercialView extends JPanel {

    private final JTextField campoBusca = new JTextField(18);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{
                "Pedido", "Cliente", "Data", "Valor", "Status"
            }, 0
    ) {
        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };

    private final JTable tabela = new JTable(modelo);

    public LiberacaoComercialView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Liberação Comercial");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));

        JPanel topo = new JPanel(new BorderLayout(8, 8));
        topo.add(titulo, BorderLayout.NORTH);

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.add(new JLabel("Buscar pedido ou cliente:"));
        filtros.add(campoBusca);

        JButton botaoBuscar = new JButton("Buscar");
        botaoBuscar.addActionListener(e -> filtrar());
        filtros.add(botaoBuscar);

        topo.add(filtros, BorderLayout.SOUTH);
        add(topo, BorderLayout.NORTH);

        tabela.setSelectionMode(
                javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton botaoLiberar = new JButton("Liberar selecionados");
        JButton botaoDevolver = new JButton("Devolver ao vendedor");
        JButton botaoAtualizar = new JButton("Atualizar");

        botaoLiberar.addActionListener(e ->
                informarAcao("liberação comercial"));

        botaoDevolver.addActionListener(e ->
                informarAcao("devolução ao vendedor"));

        botaoAtualizar.addActionListener(e ->
                informarAcao("atualização dos pedidos"));

        rodape.add(botaoAtualizar);
        rodape.add(botaoDevolver);
        rodape.add(botaoLiberar);

        add(rodape, BorderLayout.SOUTH);
    }

    private void filtrar() {
        String busca = campoBusca.getText().trim().toLowerCase();

        for (int i = 0; i < tabela.getRowCount(); i++) {
            // A tabela será conectada aos pedidos reais na integração.
        }

        if (busca.isEmpty()) {
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "A busca será aplicada após a integração com os pedidos.",
                "Liberação Comercial",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void informarAcao(String acao) {
        if (tabela.getSelectedRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Selecione ao menos um pedido.",
                    "Liberação Comercial",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "A " + acao + " será executada após a integração com o Controller.",
                "Liberação Comercial",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }
}
