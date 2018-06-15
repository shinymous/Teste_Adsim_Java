package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import model.ItemPedido;
import model.Pedido;

@ManagedBean(name="pedidoController")
public class PedidoController {
		private List<Pedido> pedidos = new ArrayList<>();
		private List<ItemPedido> itens = new ArrayList<>();
		private Pedido pedido = new Pedido();
		private List<Pedido> pedidoDTO = new ArrayList<>();
		private Set<ItemPedido> itemPedido = new HashSet<>();
		private ClienteController clienteController = new ClienteController();
		
		public List<Pedido> findAllPedidos() {
		    Client c = Client.create();
		    WebResource wr = c.resource("http://localhost:8080/pedidos");
		    String json = wr.get(String.class);
		    Gson gson = new Gson();
		    return gson.fromJson(json, new TypeToken<List<Pedido>>(){}.getType());
		  }
		
		@PostConstruct
		public void init() {
			pedidos = findAllPedidos();
			//System.out.println(pedidos.get(1).getValorTotalCurrency());
		}
		
		public void refresh() {
	       pedidos = findAllPedidos();
	    }
		public String voltar() {
			return "index.xhtml?faces-redirect=true";
		}
		
		public List<Pedido> getPedidos() {
			return pedidos;
		}
		public void setPedidos(List<Pedido> pedidos) {
			this.pedidos = pedidos;
		}
		public Pedido getPedido() {
			return pedido;
		}
		public void setPedido(Pedido pedido) {
			this.pedido = pedido;
		}

		public List<ItemPedido> getItens() {
			return itens;
		}

		public void setItens(List<ItemPedido> itens) {
			this.itens = itens;
		}

		public Set<ItemPedido> getItemPedido() {
			return itemPedido;
		}

		public void setItemPedido(Set<ItemPedido> itemPedido) {
			this.itemPedido = itemPedido;
		}

		public List<Pedido> getPedidoDTO() {
			return pedidoDTO;
		}

		public void setPedidoDTO(List<Pedido> pedidoDTO) {
			this.pedidoDTO = pedidoDTO;
		}

		public ClienteController getClienteController() {
			return clienteController;
		}

		public void setClienteController(ClienteController clienteController) {
			this.clienteController = clienteController;
		}
		
		
}
