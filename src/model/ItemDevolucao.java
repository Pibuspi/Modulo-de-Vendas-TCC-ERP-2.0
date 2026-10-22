package model;

/** Item de devolução e sua elegibilidade pela política de 30 dias. Tela: GestaoDevolucoesView. */
public class ItemDevolucao {
    private int idItemDevolucao, idDevolucao, idProduto;
    private Produto produto;
    private double quantidade;
    private boolean elegivel;
    private String motivo;
    public ItemDevolucao() {}

    public ItemDevolucao(int idItemDevolucao,int idDevolucao,int idProduto,double quantidade,boolean elegivel) {
        this.idItemDevolucao=idItemDevolucao;
        this.idDevolucao=idDevolucao;
        this.idProduto=idProduto;
        this.quantidade=quantidade;
        this.elegivel=elegivel;
    }

    public int getIdItemDevolucao() {
        return idItemDevolucao;
    }

    public void setIdItemDevolucao(int v) {
        idItemDevolucao=v;
    }

    public int getIdDevolucao() {
        return idDevolucao;
    }

    public void setIdDevolucao(int v) {
        idDevolucao=v;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int v) {
        idProduto=v;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto v) {
        produto=v;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double v) {
        quantidade=v;
    }

    public boolean isElegivel() {
        return elegivel;
    }

    public boolean getElegivel() {
        return elegivel;
    }

    public void setElegivel(boolean v) {
        elegivel=v;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String v) {
        motivo=v;
    }
}
