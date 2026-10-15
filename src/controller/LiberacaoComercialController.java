package controller;

import model.PedidoVenda;
import java.util.ArrayList;
import java.util.List;


 
public class LiberacaoComercialController {

    private List<PedidoVenda> repositorioCache = new ArrayList<>();

    public LiberacaoComercialController() {
        inicializarDadosMock();
    }

    public List<PedidoVenda> getTodosPedidos() {
        return repositorioCache;
    }

    public List<PedidoVenda> filtrarPedidos(String filtroCliente, String filtroVendedor, String filtroPagamento, String filtroStatus) {
        List<PedidoVenda> filtrados = new ArrayList<>();

        for (PedidoVenda p : repositorioCache) {
            String cliente = obterNomeCliente(p);
            String vendedor = "Marcos";
            String pagamento = "30 dias";
            String status = String.valueOf(p.getStatus());

            boolean matchCliente = filtroCliente == null || filtroCliente.trim().isEmpty() || cliente.toLowerCase().contains(filtroCliente.toLowerCase());
            boolean matchVendedor = filtroVendedor == null || filtroVendedor.trim().isEmpty() || vendedor.toLowerCase().contains(filtroVendedor.toLowerCase());
            boolean matchPagamento = filtroPagamento == null || "TODOS".equalsIgnoreCase(filtroPagamento) || pagamento.equalsIgnoreCase(filtroPagamento);
            boolean matchStatus = filtroStatus == null || "TODOS".equalsIgnoreCase(filtroStatus) || status.equalsIgnoreCase(filtroStatus);

            if (matchCliente && matchVendedor && matchPagamento && matchStatus) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    public void validarConsolidacaoLote(List<PedidoVenda> selecionados, boolean credito, boolean margem, boolean alcada) {
        if (selecionados == null || selecionados.isEmpty()) {
            throw new IllegalArgumentException("Nenhum pedido selecionado para consolidação.");
        }
        if (!credito || !margem || !alcada) {
            throw new IllegalStateException("O checklist de governança (Crédito, Margem e Alçada) deve estar totalmente conferido.");
        }
    }

    public void liberarLoteParaFaturamento(List<PedidoVenda> selecionados) {
        for (PedidoVenda p : selecionados) {
            p.setStatus(PedidoVenda.Status.LIBERADO_PARA_FATURAMENTO);
        }
    }

    public void devolverAoVendedor(List<PedidoVenda> selecionados, String justificativa) {
        if (justificativa == null || justificativa.trim().length() < 5) {
            throw new IllegalArgumentException("A justificativa de devolução é obrigatória (mínimo de 5 caracteres).");
        }
        for (PedidoVenda p : selecionados) {
            p.setStatus(PedidoVenda.Status.DEVOLVIDO_AO_VENDEDOR);
        }
    }

    private void inicializarDadosMock() {
        criarPedido(1001L, PedidoVenda.Status.PENDENTE_APROVACAO);
        criarPedido(1002L, PedidoVenda.Status.PENDENTE_APROVACAO);
        criarPedido(1003L, PedidoVenda.Status.ABERTO);
        criarPedido(1004L, PedidoVenda.Status.BLOQUEADO);
        criarPedido(1005L, PedidoVenda.Status.PENDENTE_APROVACAO);
        criarPedido(1006L, PedidoVenda.Status.LIBERADO_PARA_FATURAMENTO);
        criarPedido(1007L, PedidoVenda.Status.DEVOLVIDO_AO_VENDEDOR);
        criarPedido(1008L, PedidoVenda.Status.ABERTO);
    }

    private void criarPedido(Long id, PedidoVenda.Status status) {
        PedidoVenda p = new PedidoVenda();
        p.setId(id);
        p.setStatus(status);
        repositorioCache.add(p);
    }

    private String obterNomeCliente(PedidoVenda p) {
        try {
            if (p.getCliente() != null) {
                java.lang.reflect.Method m = p.getCliente().getClass().getMethod("getNome");
                Object val = m.invoke(p.getCliente());
                if (val != null) return val.toString();
            }
        } catch (Exception e) {

        }
        return "Comercial Alfa Ltda";
    }
}