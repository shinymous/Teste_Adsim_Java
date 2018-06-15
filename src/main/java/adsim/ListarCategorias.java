package adsim;

import java.util.List;

import javax.faces.bean.ManagedBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@ManagedBean(name="listarCategorias")
public class ListarCategorias {
	
		  public String getFilmesEmCartaz() {
		    Client c = Client.create();
		    WebResource wr = c.resource("http://localhost:8080/categorias");
		    return wr.get(String.class);
		  }
		  
		  public List<Categoria> getFilmesEmCartaz2() {
			    Client c = Client.create();
			    WebResource wr = c.resource("http://localhost:8080/categorias");
			    String json = wr.get(String.class);
			    Gson gson = new Gson();
			    return gson.fromJson(json, new TypeToken<List<Categoria>>(){}.getType());
			  }


}
