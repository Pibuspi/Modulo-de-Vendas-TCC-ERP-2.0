package controller;

import model.PedidoVenda;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaVendasController {
    /** Consulta pedidos por período, cliente, vendedor e status comercial. */
    public List<PedidoVenda> consultar(LocalDate dataInicial, LocalDate dataFinal,
                                       String cliente, String vendedor, String status) { return new ArrayList<>();
    }
    /** Soma o valor líquido dos pedidos retornados no período. */
    public double calcularTotalVendido(List<PedidoVenda> pedidos) {
        return pedidos.stream().mapToDouble(PedidoVenda::getTotal).sum();
    }
    /** Conta pedidos ainda em aberto */
    public long contarPedidosAbertos(List<PedidoVenda> pedidos) {
        return pedidos.stream().filter(p -> p.getStatus() == PedidoVenda.Status.ABERTO).count();
    }
    /** Conta pedidos bloqueados */
    public long contarPedidosBloqueados(List<PedidoVenda> pedidos) {
        return pedidos.stream().filter(p -> p.getStatus() == PedidoVenda.Status.BLOQUEADO).count();
    }
}
