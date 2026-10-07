package controller;

import model.PedidoVenda;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Pimenta, em parceria com Lucas de Lima: consulta filtrada e indicadores
 * para o histórico e dashboard do ciclo de vendas.
 */
public class ConsultaVendasController {
    /** Consulta pedidos por período, cliente, vendedor e status comercial. */
    public List<PedidoVenda> consultar(LocalDate dataInicial, LocalDate dataFinal,
                                       String cliente, String vendedor, String status) {
        // TODO: delegar a consulta parametrizada ao PedidoDAO e aplicar os filtros.
        return new ArrayList<>();
    }

    /** Soma o valor líquido dos pedidos retornados no período. */
    public double calcularTotalVendido(List<PedidoVenda> pedidos) {
        return pedidos.stream().mapToDouble(PedidoVenda::getTotal).sum();
    }

    /** Conta pedidos ainda em aberto para alimentar o card de KPI. */
    public long contarPedidosAbertos(List<PedidoVenda> pedidos) {
        return pedidos.stream().filter(p -> p.getStatus() == PedidoVenda.Status.ABERTO).count();
    }

    /** Conta pedidos bloqueados para alimentar o card de KPI. */
    public long contarPedidosBloqueados(List<PedidoVenda> pedidos) {
        return pedidos.stream().filter(p -> p.getStatus() == PedidoVenda.Status.BLOQUEADO).count();
    }
}
