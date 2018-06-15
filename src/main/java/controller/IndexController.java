package controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import model.Cliente;
import model.ItemPedido;
import model.LivroDTO;
import model.Pedido;

@ManagedBean(name="indexController")
@SessionScoped
public class IndexController {

	private ClienteController clienteController = new ClienteController();
	private LivroController livroController = new LivroController();
	private PedidoController pedidoController = new PedidoController();
	private Pedido pedido = new Pedido();;
	private LivroDTO livroDTO = new LivroDTO();
	private ItemPedido itemPedido = new ItemPedido();
	
	public void selecionarCliente(AjaxBehaviorEvent event) {
		clienteController.selecionarCliente(event);
	}
	
	@PostConstruct
	public void init() {
		clienteController.setClientes(clienteController.findAllClientes());
		livroController.setLivros(livroController.findAllLivros());
	}
	
	public String enviarPedido() {
		 if(livroController.getLivroPedido().size() == 0 || clienteController.getCliente().getCpf() == "" || clienteController.getCliente().getNome() == "" || clienteController.getCliente().getTelefones().get(1) == null || clienteController.getCliente().getTelefones().get(1).length() < 9) { 	 
	     String msg="Preencha todos os campos corretamente para fazer um pedido!";
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, "Preencha todos os campos");
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.addMessage(null, message);
		 return null;
		 }else {
		 	Client c = Client.create();
		    Gson gson = new Gson();
	        WebResource wr = c.resource("http://localhost:8080/pedidos");
	        //String json = wr.get(String.class);
	        pedido.setId(null);
	        pedido.setCliente(clienteController.getCliente());
	        for(int i = 0; i < livroController.getLivroPedido().size(); i++) {
	        	ItemPedido itemPedido2 = new ItemPedido();
	        	System.out.println("LIVRO CONTROLLER TROLL: "+ livroController.getLivroPedido().get(i).getNome());
	        	itemPedido2.setLivro(livroController.getLivroPedido().get(i));
	        	itemPedido2.setQuantidade(livroController.getLivroPedido().get(i).getQuantidade());
	        	itemPedido2.setPreco(livroDTO.getValor());
	        	pedido.getItens().add(itemPedido2);
	        	System.out.println("PEDIDO TROLL: "+pedido.getItens().get(i).getLivro().getNome());
	        }
	        for(int i = 0; i < pedido.getItens().size(); i++) {
	        	System.out.println("PEDIDO TROLL fora for: "+pedido.getItens().get(i).getLivro().getNome());
	        }
	        System.out.println(gson.toJson(pedido));
	        wr.type("application/json").accept("application/json")
            .post(gson.toJson(pedido));
	        String msg="Pedido enviado com Sucesso";
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "Pedido Enviado");
			 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, message);
			 pedidoController.setPedidos(pedidoController.findAllPedidos());
			 clienteController.setCliente(new Cliente());
			 clienteController.setNovoCliente(new Cliente());
			 livroController.setLivroPedido(null);
			 return "pedidos.xhtml";
		 }
	 }
	
			public void limparLista() {
				livroController.setLivroPedido(null);
			}
			
			public String mostrarPedidos() {
				clienteController.setCliente(new Cliente());
				clienteController.setNovoCliente(new Cliente());
				livroController.setLivroPedido(null);
				return "pedidos.xhtml";
			}

	public ClienteController getClienteController() {
		return clienteController;
	}

	public void setClienteController(ClienteController clienteController) {
		this.clienteController = clienteController;
	}

	public LivroController getLivroController() {
		return livroController;
	}

	public void setLivroController(LivroController livroController) {
		this.livroController = livroController;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public PedidoController getPedidoController() {
		return pedidoController;
	}

	public void setPedidoController(PedidoController pedidoController) {
		this.pedidoController = pedidoController;
	}

	public LivroDTO getLivroDTO() {
		return livroDTO;
	}

	public void setLivroDTO(LivroDTO livroDTO) {
		this.livroDTO = livroDTO;
	}

	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}
	
	
	
	
	
}
