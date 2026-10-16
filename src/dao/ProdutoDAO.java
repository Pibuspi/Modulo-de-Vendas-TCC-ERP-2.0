package dao;

import model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Persistência JDBC dos cinco campos oficiais do catálogo de balcão. */
public class ProdutoDAO {
    private static final String COLUNAS = "p.sku, p.descricao, p.estoque_disponivel, "
        + "COALESCE(SUM(ip.quantidade), 0), p.preco_tabela";

    public List<Produto> buscar(String termo) throws SQLException {
        String filtro = termo == null ? "" : termo.trim();
        String sql = "SELECT " + COLUNAS + " FROM produto p "
            + "LEFT JOIN item_pedido ip ON ip.produto_id = p.id "
            + "WHERE (? = '' OR LOWER(p.sku) LIKE LOWER(?) OR LOWER(p.descricao) LIKE LOWER(?) "
            + "OR CAST(p.preco_tabela AS TEXT) LIKE ?) "
            + "GROUP BY p.id, p.sku, p.descricao, p.estoque_disponivel, p.preco_tabela ORDER BY p.sku";
        List<Produto> produtos = new ArrayList<>();
        try (Connection conexao = Conexao.abrir(); PreparedStatement ps = conexao.prepareStatement(sql)) {
            String busca = "%" + filtro + "%";
            ps.setString(1, filtro); ps.setString(2, busca); ps.setString(3, busca); ps.setString(4, busca);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) produtos.add(mapear(rs));
            }
        }
        return produtos;
    }

    public Produto buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM produto p LEFT JOIN item_pedido ip ON ip.produto_id = p.id "
            + "WHERE LOWER(p.sku) = LOWER(?) GROUP BY p.id, p.sku, p.descricao, p.estoque_disponivel, p.preco_tabela";
        try (Connection conexao = Conexao.abrir(); PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    private Produto mapear(ResultSet rs) throws SQLException {
        return new Produto(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5));
    }

    /** Mantém a compatibilidade com o esqueleto DAO anterior. */
    public void reservarMetodos() { }
}
