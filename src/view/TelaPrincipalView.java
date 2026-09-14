package view;
import javax.swing.*;
import java.awt.*;
/** Eúde e Pietro: dashboard/menu com navegação para todas as views do módulo. */
public class TelaPrincipalView extends JFrame {
 public TelaPrincipalView(){ setTitle("TCC ERP 2.0 - Módulo de Vendas"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(800,500); setLocationRelativeTo(null);
  JPanel p=new JPanel(new GridLayout(0,2,10,10)); p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
  add(new JLabel("Módulo de Vendas", SwingConstants.CENTER), BorderLayout.NORTH);
  for(String item: new String[]{"Cadastro de Cliente","Digitação de Pedido","Pricing Engine","Liberação Comercial","Gestão de Devoluções","Tela Balcão / PDV"}){ JButton b=new JButton(item); b.addActionListener(e -> JOptionPane.showMessageDialog(this, item+" — esqueleto inicial")); p.add(b); } add(p); }
}
