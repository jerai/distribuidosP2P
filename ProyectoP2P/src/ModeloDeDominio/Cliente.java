package ModeloDeDominio;

import java.io.Serializable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Cliente implements Serializable{

	private String ip;
	private int puerto;
	
	// Constructores
	public Cliente(String ip, int puerto) {
		this.ip = ip;
		this.puerto = puerto;
	}

	// Métodos
	
	
	// Getters and Setters
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	
}
