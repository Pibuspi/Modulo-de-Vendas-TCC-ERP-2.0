package model;
import java.util.*;
/** Cabeçalho e itens de venda; o controller deve aplicar as validações comerciais. */
public class PedidoVenda {
    public enum Status { RASCUNHO, PENDENTE_APROVACAO, LIBERADO, BLOQUEADO, FATURADO, CANCELADO }
    private Long id; private Cliente cliente; private final List<ItemPedido> itens = new ArrayList<>(); private Status status=Status.RASCUNHO;
    public Long getId(){return id;} public void setId(Long v){id=v;} public Cliente getCliente(){return cliente;} public void setCliente(Cliente v){cliente=v;}
    public List<ItemPedido> getItens(){return itens;} public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
    public double getTotal(){return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();}
}