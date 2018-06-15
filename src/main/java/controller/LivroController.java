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

import model.Livro;

@ManagedBean(name="livroController")
@SessionScoped
public class LivroController {

	private Livro livro = new Livro();
	private List<Livro> livros = new ArrayList<>();;
	private int livroId;
	private Livro livroLista = new Livro();
	private List<Livro> livroPedido = new ArrayList<>();
	private List<Livro> liv = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		livros = findAllLivros();
		System.out.println(livros.size());
	}
	
	public List<Livro> findAllLivros() {
	    Client c = Client.create();
	    WebResource wr = c.resource("http://localhost:8080/livros");
	    String json = wr.get(String.class);
	    Gson gson = new Gson();
	    return gson.fromJson(json, new TypeToken<List<Livro>>(){}.getType());
	  }
	
	public void selecionarLivro(AjaxBehaviorEvent event) {
		 for(int i = 0; i < livros.size(); i++) {
			if(livros.get(i).getId().equals(livroId)) {
				livro.setAnoLancamento(livros.get(i).getAnoLancamento());
				livro.setEditora(livros.get(i).getEditora());
				livro.setNome(livros.get(i).getNome());
				livro.setValor(livros.get(i).getValor());
				livro.setId(livros.get(i).getId());
			}
		}
	}
	
	public void updateItensPedidos() {
		 boolean isEqual = true;
		 	if(livroLista == null) {
		 		String msg="Nenhum Livro Selecionado";
				 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
				 FacesContext context = FacesContext.getCurrentInstance();
				 context.addMessage(null, message);
		 }else {
		 		if(livroPedido == null || livroPedido.isEmpty()) {
		 			livroPedido = new ArrayList<>();
		 			livroLista.setQuantidade(1);
		 			liv.add(livroLista);
		 			livroPedido.add(livroLista);
		 		}else {
		 			for(int i = 0; i < livroPedido.size(); i++) {
		 				if(livroLista.getId() == livroPedido.get(i).getId()) {
		 					isEqual = true;
		 					break;
		 				}else {
		 					isEqual=false;
		 				}
		 			}
		 			if(isEqual == false) {
		 				livroLista.setQuantidade(1);
		 				liv.add(livroLista);
		 				livroPedido.add(livroLista);
		 			}if(isEqual == true) {
		 				for(int i = 0; i < livroPedido.size(); i++){			 				
		 					if(livroLista.getId() == liv.get(i).getId()) {
		 						livroPedido.get(i).setQuantidade(livroPedido.get(i).getQuantidade()+1);
		 						break;
		 					}
		 				}
		 				
		 				
		 				
		 			}
		 		}
		 	}
			 	String msg="Livro Adicionado";
				 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
				 FacesContext context = FacesContext.getCurrentInstance();
				 context.addMessage(null, message);
	 	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public int getLivroId() {
		return livroId;
	}

	public void setLivroId(int livroId) {
		this.livroId = livroId;
	}

	public Livro getLivroLista() {
		return livroLista;
	}

	public void setLivroLista(Livro livroLista) {
		this.livroLista = livroLista;
	}

	public List<Livro> getLivroPedido() {
		return livroPedido;
	}

	public void setLivroPedido(List<Livro> livroPedido) {
		this.livroPedido = livroPedido;
	}
	
	
}
