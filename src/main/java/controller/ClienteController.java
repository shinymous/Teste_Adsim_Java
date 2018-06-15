package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import model.Cliente;

@ManagedBean(name="clienteController")
@SessionScoped
public class ClienteController {

	private Cliente cliente = new Cliente();
	private List<Cliente> clientes = new ArrayList<>();
	private int clienteId;
	private Cliente novoCliente = new Cliente();
	private String telefone1;
	private String telefone2;
	
	
	@PostConstruct
	public void init() {
		clientes = findAllClientes();
	}
	
	public void alterarCliente(Cliente cliente) {
	 	Client c = Client.create();
	    Gson gson = new Gson();
        WebResource wr = c.resource("http://localhost:8080/clientes/"+cliente.getId());
	 	//String json = wr.get(String.class);
        if(cliente.getId() == null || cliente.getId() == 0) {
        	cliente.setId(null);
            wr.type("application/json").accept("application/json")
              .post(gson.toJson(cliente));
        } else {
            wr.type("application/json").accept("application/json")
              .put(gson.toJson(cliente));
        }
    }
	
	public void selecionarCliente(AjaxBehaviorEvent event) {
		for(int i = 0; i < clientes.size(); i++) {
			if(clientes.get(i).getId().equals(clienteId)) {
				cliente.setCpf(clientes.get(i).getCpf());
				cliente.setId(clientes.get(i).getId());
				cliente.setNome(clientes.get(i).getNome());
				List<String> telefones = new ArrayList<String>(clientes.get(i).getTelefones());
				cliente.setTelefones(telefones);
			}if(clienteId == 0) {
				cliente = new Cliente();
			}
		}
	}
	
	
	 public void salvarCliente() {
		 if(novoCliente.getNome() == "" || novoCliente.getCpf() == "" || telefone1 == null || telefone1 == "" || telefone1.length() < 9) {
			 String msg="Cliente com dados em branco ou incompleto!";
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, "Preencha todos os campos");
			 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, message);
		 }else {
		 System.out.println("NomeCliente:"+ novoCliente.getNome()+"..");
		 Client c = Client.create();
		    Gson gson = new Gson();
	        WebResource wr = c.resource("http://localhost:8080/clientes");
	        novoCliente.setId(null);
	        novoCliente.setCpf(novoCliente.getCpf());
	        novoCliente.getTelefones().add(telefone1);
	        novoCliente.getTelefones().add(telefone2);
	        System.out.println(telefone2);
	        wr.type("application/json").accept("application/json")
              .post(gson.toJson(novoCliente));
	        clientes = findAllClientes();
			cliente = novoCliente;
	        telefone1 = null;
	        telefone2 = null;
	        novoCliente = new Cliente();
		 }
	 }
	 
	 public List<Cliente> findAllClientes() {
		    Client c = Client.create();
		    WebResource wr = c.resource("http://localhost:8080/clientes");
		    String json = wr.get(String.class);
		    Gson gson = new Gson();
		    return gson.fromJson(json, new TypeToken<List<Cliente>>(){}.getType());
		  }

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public Cliente getNovoCliente() {
		return novoCliente;
	}

	public void setNovoCliente(Cliente novoCliente) {
		this.novoCliente = novoCliente;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	
	 
	 
}
