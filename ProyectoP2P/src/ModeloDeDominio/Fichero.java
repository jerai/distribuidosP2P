package ModeloDeDominio;

import java.io.File;
import java.io.Serializable;

import Cliente.HashSHA256;

public class Fichero implements Serializable{

	private String hash;
	private String nombre;
	private String direccion;
	private long tamano;
	
	// Constructores
	public Fichero(String hash, String nombre, String direccion, long tamano) {
		this.hash = hash;
		this.nombre = nombre;
		this.direccion = direccion;
		this.tamano = tamano;
	}
	
	public Fichero(File f) {
		this.hash = HashSHA256.getHash(f);
		this.nombre = f.getName();
		this.direccion = f.getAbsolutePath();
		this.tamano = f.length();
	}
	
	public Fichero(String hash) {
		this.hash = hash;
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
	
	public long getTamano() {
		return tamano;
	}

	public void setTamano(long tamano) {
		this.tamano = tamano;
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
		return "- Hash: " + this.hash + " - Path: " + this.direccion + " - Tamaño: " + this.tamano;
	}
	
	// Con esto funciona la busqueda de claves en la tabla
	@Override
	public int hashCode() {
		return 1;
	}
	
	
}
