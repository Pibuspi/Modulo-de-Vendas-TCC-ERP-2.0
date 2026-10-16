package controller;

import dao.Conexao;
import java.sql.*;

/** KPIs executivos consultados diretamente no banco de vendas. */
public class MainController {
    public Kpis carregarKpis() {
        String sql = "SELECT (SELECT COUNT(*) FROM cliente WHERE bloqueado = FALSE), (SELECT COUNT(*) FROM pedido_venda WHERE status_comercial = 'PENDENTE_APROVACAO'), (SELECT COALESCE(SUM(total_liquido),0) FROM pedido_venda WHERE status_comercial NOT IN ('CANCELADO','DEVOLVIDO_AO_VENDEDOR'))";
        try (Connection c = Conexao.abrir(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) { rs.next(); return new Kpis(rs.getLong(1), rs.getLong(2), rs.getDouble(3), null); }
        catch (SQLException e) { return new Kpis(0, 0, 0, "Banco indisponível: " + e.getMessage()); }
    }
    public static final class Kpis { public final long clientesAtivos, pedidosPendentes; public final double faturamento; public final String erro; public Kpis(long c,long p,double f,String e){clientesAtivos=c;pedidosPendentes=p;faturamento=f;erro=e;} }
}
