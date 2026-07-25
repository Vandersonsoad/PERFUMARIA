package model;

import java.util.Date;

/**
 * Classe que representa  Venda no banco de dados do StockMaster.
 */
public class Venda {
    
    private int id;
    private Date dataVenda;
    private double valorTotal;
    private Usuario usuario; 

    // Construtor Vazio
    public Venda() {
    }
    
    // Construtor Completo
    public Venda(int id, Date dataVenda, double valorTotal, Usuario usuario) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.usuario = usuario;
    }

    //  MÉTODOS GETTERS E SETTERS 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}