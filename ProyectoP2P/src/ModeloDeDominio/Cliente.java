package ModeloDeDominio;

import java.io.Serializable;

public class Cliente implements Serializable{

	private String ip;
	private int puerto;// puerto de su servidor
	
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
	
	// Métodos redefinidos
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Cliente) {
			Cliente c = (Cliente) obj;
			return (this.ip.equals(c.getIp()) && this.puerto==c.getPuerto());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "- Ip: " + this.ip + " - Puerto: " + this.puerto;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
}
