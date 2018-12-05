package ModeloDeDominio;

import java.io.Serializable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Cliente implements Serializable{

	private String ip;
	private int puerto;
	private int id;
	
	// Constructores
	public Cliente(String ip, int puerto, int id) {
		this.ip = ip;
		this.puerto = puerto;
		this.id = id;
	}

	// Métodos
	public Element getDOM(Document doc) {
		Element cliente = doc.createElement("cliente");
		cliente.setAttribute("id", Integer.toString(this.id));
		
		Element ip = doc.createElement(this.ip);
		cliente.appendChild(ip);
		Element puerto = doc.createElement(Integer.toString(this.puerto));
		cliente.appendChild(puerto);
		
		return cliente;
	}
	
	public Element getDOMRef(Document doc) {
		Element cliente = doc.createElement("cliente");
		cliente.setAttribute("id", Integer.toString(this.id));
		return cliente;
	}
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
