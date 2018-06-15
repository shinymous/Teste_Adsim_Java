package model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

	private Integer id;
	private Cliente cliente;
	private List<ItemPedido> itens = new ArrayList<>();
	private Double valorTotal;
	private String valorTotalCurrency;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<ItemPedido> getItens() {
		return itens;
	}
	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	public Double getValorTotal() {
		for(int i = 0; i < itens.size(); i++) {
			if(i == 0) {
			valorTotal = itens.get(i).getPreco() * itens.get(i).getQuantidade();
			}else {
				valorTotal = valorTotal + (itens.get(i).getPreco() * itens.get(i).getQuantidade());
			}
		}
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getValorTotalCurrency() {
		NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(); 
		valorTotalCurrency = formatoMoeda.format(this.getValorTotal());
		//System.out.println(formatoMoeda.format(this.getValorTotal()));
		return valorTotalCurrency;
	}
	public void setValorTotalCurrency(Double valorTotal) {
		this.valorTotalCurrency = Double.toString(valorTotal);
	}

	
	
}
