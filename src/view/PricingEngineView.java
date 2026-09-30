package view;

import controller.PricingEngineController;
import model.ResultadoPrecificacao;
import javax.swing.*;
import java.awt.*;

/** Matheus Godoy: regras de produto, simulação de preço e status de alçada. */
public class PricingEngineView extends JPanel {
    private final PricingEngineController controller = new PricingEngineController();
    private final JTextField custo = new JTextField("0", 8), preco = new JTextField("0", 8), desconto = new JTextField("0", 8);
    private final JLabel margem = new JLabel("Margem real: -"), status = new JLabel("STATUS: -");
    public PricingEngineView() { setLayout(new BorderLayout(10, 10)); setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); add(regras(), BorderLayout.NORTH); add(simulacao(), BorderLayout.CENTER); }
    private JPanel regras() { JPanel p = new JPanel(new GridLayout(1, 3)); p.setBorder(BorderFactory.createTitledBorder("Alçadas comerciais")); p.add(new JLabel("Até 5%: LIBERADO", SwingConstants.CENTER)); p.add(new JLabel("5,01% a 10%: PENDENTE", SwingConstants.CENTER)); p.add(new JLabel("Acima de 10%: HARD_STOP", SwingConstants.CENTER)); return p; }
    private JPanel simulacao() { JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT)); p.setBorder(BorderFactory.createTitledBorder("Simulação de preço")); p.add(new JLabel("Custo:")); p.add(custo); p.add(new JLabel("Preço tabela:")); p.add(preco); p.add(new JLabel("Desconto %:")); p.add(desconto); JButton validar = new JButton("Validar margem"); validar.addActionListener(e -> validar()); p.add(validar); p.add(margem); p.add(status); return p; }
    private void validar() { try { ResultadoPrecificacao r = controller.validarMargemLucro(Double.parseDouble(custo.getText()), Double.parseDouble(preco.getText()), Double.parseDouble(desconto.getText())); margem.setText(String.format("Margem real: %.2f%%", r.getMargemReal())); status.setText("STATUS: " + r.getStatusAprovacao()); } catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Informe valores numéricos."); } }
}
