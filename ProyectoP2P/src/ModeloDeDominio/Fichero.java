package ModeloDeDominio;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Fichero implements Serializable{

	private String hash;
	private String nombre;
	private String direccion;
	private List<Cliente> listaPropietarios;
	
	// Constructores
	public Fichero(String hash, String nombre, String direccion, List<Cliente> listaPropietarios) {
		this.hash = hash;
		this.nombre = nombre;
		this.direccion = direccion;
		this.listaPropietarios = listaPropietarios;
	}
	
	public Fichero(String hash, String direccion) {
		this.hash = hash;
		this.direccion = direccion;
	}
	
	// Métodos
	// Devuelve el elemento dom correspondiente a los datos del fichero
	public Element getDOM(Document doc) {
		Element fichero = doc.createElement("fichero");
		
		Element hash = doc.createElement("hash");
		hash.appendChild(doc.createTextNode(this.hash));
		fichero.appendChild(hash);
		Element nombre = doc.createElement("nombre");
		nombre.appendChild(doc.createTextNode(this.nombre));
		fichero.appendChild(nombre);
		
		Element cli;
		for (Cliente cliente : listaPropietarios) {
			cli = cliente.getDOMRef(doc);
			fichero.appendChild(cli);
		}
		
		return fichero;
	}
	
	
	// Getters and Setters
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<Cliente> getListaPropietarios() {
		return listaPropietarios;
	}

	// añande el cliente cli a la lista de propietarios .
	// si cli ya es propietario no lo añade
	public void addPropietario(Cliente cli) {
		if(!this.listaPropietarios.contains(cli))
			this.listaPropietarios.add(cli);
	}
	
	// Métodos redefinidos
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Fichero) {
			Fichero f = (Fichero) obj;
			return this.hash==f.getHash();
		}
		return false;
	}
	
	
}
