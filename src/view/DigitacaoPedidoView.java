package view;

import controller.PedidoController;
import model.Cliente;
import model.ItemPedido;
import model.PedidoVenda;
import model.Produto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/** Digitação de pedidos com seleção de cliente, itens e totalização. */
public class DigitacaoPedidoView extends JPanel {
    private final PedidoController controller=new PedidoController(); private PedidoVenda pedido=new PedidoVenda(); private Cliente cliente;
    private final JTextField idCliente=new JTextField(5), cpf=new JTextField(14), nome=new JTextField(22), limite=new JTextField(10), status=new JTextField(10);
    private final JTextField codigo=new JTextField(8), descricao=new JTextField(16), quantidade=new JTextField(6), preco=new JTextField(8), desconto=new JTextField("0",8); private final JLabel total=new JLabel();
    private final DefaultTableModel itens=new DefaultTableModel(new Object[]{"Código","Produto","Qtd","Preço","Desconto","Subtotal"},0){public boolean isCellEditable(int r,int c){return false;}}; private final JTable tabela=new JTable(itens);
    public DigitacaoPedidoView(){ Tema.aplicar(); setLayout(new BorderLayout(8,8)); setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); add(cabecalho(),BorderLayout.NORTH); add(new JScrollPane(tabela),BorderLayout.CENTER); add(rodape(),BorderLayout.SOUTH); recarregar(); }
    private JPanel cabecalho(){ JPanel p=new JPanel(); p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS)); JPanel c=new JPanel(new FlowLayout(FlowLayout.LEFT)); c.setBorder(BorderFactory.createTitledBorder("Cliente")); c.add(new JLabel("ID:")); c.add(idCliente); c.add(new JLabel("CPF/CNPJ:")); c.add(cpf); c.add(new JLabel("Nome:")); c.add(nome); c.add(new JLabel("Limite:")); limite.setEditable(false); c.add(limite); c.add(new JLabel("Status:")); status.setEditable(false); c.add(status); JButton buscar=new JButton("Buscar Cliente"); buscar.addActionListener(e->ConsultaClienteView.abrirDialogo(this,this::selecionarCliente)); c.add(buscar); p.add(c); JPanel i=new JPanel(new FlowLayout(FlowLayout.LEFT)); i.setBorder(BorderFactory.createTitledBorder("Novo item")); i.add(new JLabel("Código:"));i.add(codigo);i.add(new JLabel("Produto:"));i.add(descricao);i.add(new JLabel("Qtd:"));i.add(quantidade);i.add(new JLabel("Preço:"));i.add(preco);i.add(new JLabel("Desconto:"));i.add(desconto); JButton add=new JButton("Adicionar");add.addActionListener(e->adicionar());i.add(add);p.add(i);return p; }
    private JPanel rodape(){ JPanel p=new JPanel(new BorderLayout()); total.setFont(total.getFont().deriveFont(Font.BOLD,16f));p.add(total,BorderLayout.WEST);JPanel b=new JPanel();JButton rem=new JButton("Remover item");rem.addActionListener(e->remover());JButton limpar=new JButton("Limpar");limpar.addActionListener(e->limpar());JButton salvar=new JButton("Salvar pedido");salvar.addActionListener(e->salvar());b.add(rem);b.add(limpar);b.add(salvar);p.add(b,BorderLayout.EAST);return p; }
    private void selecionarCliente(Cliente c){cliente=c;idCliente.setText(String.valueOf(c.getId()));cpf.setText(c.getCpfCnpj());nome.setText(c.getRazaoSocial());limite.setText(Formato.moeda(c.getLimiteCredito()));status.setText(c.isBloqueado()?"BLOQUEADO":"ATIVO");pedido.setCliente(c);}
    private void adicionar(){try{ItemPedido i=new ItemPedido();i.setProduto(new Produto(codigo.getText().trim(),descricao.getText().trim(),0,0,Formato.lerNumero(preco.getText())));i.setQuantidade(Formato.lerNumero(quantidade.getText()));i.setPrecoUnitario(Formato.lerNumero(preco.getText()));i.setDesconto(desconto.getText().isBlank()?0:Formato.lerNumero(desconto.getText()));controller.adicionarItem(pedido,i);recarregar();codigo.setText("");descricao.setText("");quantidade.setText("");preco.setText("");}catch(Exception e){erro(e);}}
    private void remover(){int r=tabela.getSelectedRow();if(r>=0){controller.removerItem(pedido,pedido.getItens().get(tabela.convertRowIndexToModel(r)));recarregar();}else erro(new IllegalArgumentException("Selecione um item."));}
    private void salvar(){if(cliente==null||cliente.isBloqueado()){erro(new IllegalArgumentException("Selecione um cliente ativo."));return;}if(!controller.validar(pedido)){erro(new IllegalArgumentException("Pedido inválido."));return;}JOptionPane.showMessageDialog(this,"Pedido validado para gravação. Total: "+Formato.moeda(controller.calcularTotal(pedido)));}
    private void limpar(){pedido=new PedidoVenda();cliente=null;idCliente.setText("");cpf.setText("");nome.setText("");limite.setText("");status.setText("");recarregar();}
    private void recarregar(){itens.setRowCount(0);for(ItemPedido i:pedido.getItens())itens.addRow(new Object[]{i.getProduto().getCodigo(),i.getProduto().getProduto(),Formato.numero(i.getQuantidade()),Formato.moeda(i.getPrecoUnitario()),Formato.moeda(i.getDesconto()),Formato.moeda(i.getSubtotal())});total.setText("Total: "+Formato.moeda(pedido.getTotal()));}
    private void erro(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Pedido",JOptionPane.WARNING_MESSAGE);}
}
