package dao;

import model.ItemPedido;
import model.PedidoVenda;
import java.sql.*;

/** Rafael: gravação transacional de pedidos com validações comerciais básicas. */
public class PedidoDAO {
    public long salvar(PedidoVenda pedido) throws SQLException {
        if (pedido == null || pedido.getCliente() == null || pedido.getItens().isEmpty()) throw new IllegalArgumentException("Pedido, cliente e itens são obrigatórios.");
        try (Connection c = Conexao.abrir()) {
            c.setAutoCommit(false);
            try {
                if (!new ClienteDAO().existeClienteAtivo(c, pedido.getCliente().getId())) throw new IllegalArgumentException("O cliente deve estar ativo para gravar o pedido.");
                validarEstoque(c, pedido);
                long id;
                String insert = "INSERT INTO pedido_venda(cliente_id, status_comercial, total_bruto, total_desconto, total_liquido) VALUES (?, ?, ?, ?, ?) RETURNING id";
                try (PreparedStatement ps = c.prepareStatement(insert)) {
                    ps.setLong(1, pedido.getCliente().getId()); ps.setString(2, pedido.getStatus().name());
                    ps.setDouble(3, pedido.getTotal()); ps.setDouble(4, 0); ps.setDouble(5, pedido.getTotal());
                    try (ResultSet rs = ps.executeQuery()) { rs.next(); id = rs.getLong(1); }
                }
                String itemSql = "INSERT INTO item_pedido(pedido_id, produto_id, quantidade, preco_unitario, desconto, subtotal) SELECT ?, id, ?, ?, ?, ? FROM produto WHERE sku = ?";
                String estoqueSql = "UPDATE produto SET estoque_disponivel = estoque_disponivel - ? WHERE sku = ?";
                for (ItemPedido item : pedido.getItens()) {
                    try (PreparedStatement ps = c.prepareStatement(itemSql); PreparedStatement estoque = c.prepareStatement(estoqueSql)) {
                        ps.setLong(1, id); ps.setDouble(2, item.getQuantidade()); ps.setDouble(3, item.getPrecoUnitario()); ps.setDouble(4, item.getDesconto()); ps.setDouble(5, item.getSubtotal()); ps.setString(6, item.getProduto().getCodigo());
                        if (ps.executeUpdate() != 1) throw new SQLException("Produto não encontrado: " + item.getProduto().getCodigo());
                        estoque.setDouble(1, item.getQuantidade()); estoque.setString(2, item.getProduto().getCodigo()); estoque.executeUpdate();
                    }
                }
                c.commit(); pedido.setId(id); return id;
            } catch (RuntimeException | SQLException e) { c.rollback(); throw e; }
        }
    }
    private void validarEstoque(Connection c, PedidoVenda pedido) throws SQLException {
        String sql = "SELECT estoque_disponivel FROM produto WHERE sku = ? FOR UPDATE";
        for (ItemPedido item : pedido.getItens()) {
            if (item.getProduto() == null || item.getQuantidade() <= 0 || item.getPrecoUnitario() < 0 || item.getSubtotal() < 0) throw new IllegalArgumentException("Item de pedido inválido.");
            try (PreparedStatement ps = c.prepareStatement(sql)) { ps.setString(1, item.getProduto().getCodigo()); try (ResultSet rs = ps.executeQuery()) { if (!rs.next() || rs.getDouble(1) < item.getQuantidade()) throw new IllegalArgumentException("Estoque insuficiente para " + item.getProduto().getCodigo()); } }
        }
    }
    public void reservarMetodos() { }
}
