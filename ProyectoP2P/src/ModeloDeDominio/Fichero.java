package ModeloDeDominio;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Fichero implements Serializable{

	private String hash;
	private String nombre;
	private String direccion;
	
	// Constructores
	public Fichero(String hash, String nombre, String direccion) {
		this.hash = hash;
		this.nombre = nombre;
		this.direccion = direccion;
	}
	
	public Fichero(String hash, String direccion) {
		this.hash = hash;
		this.direccion = direccion;
	}
	
	// Métodos
	
	
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
	
	// Métodos redefinidos
	// Dos Ficheros son iguales si coincide su hash
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Fichero) {
			Fichero f = (Fichero) obj;
			return this.hash.equals(f.getHash());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "- Hash: " + this.hash + " - Path: " + this.direccion;
	}
	
	// Con esto funciona la busqueda de claves en la tabla
	@Override
	public int hashCode() {
		return 1;
	}
	
	
}
