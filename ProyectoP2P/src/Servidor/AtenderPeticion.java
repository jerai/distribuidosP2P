package Servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import ModeloDeDominio.Cliente;

public class AtenderPeticion implements Runnable{

	private Socket cliente;
	
	public AtenderPeticion(Socket cliente){
		this.cliente = cliente;
	}

	public void run() {
		/*
		 * Protocolos:
		 * GET --> Pasar la tabla al cliente
		 * SET --> Actualizar la lista de ficheros de ese cliente
		 */
		try (DataInputStream is = new DataInputStream(this.cliente.getInputStream());){
			String protocolo = is.readLine();
			switch (protocolo) {
				case "GET":
					getTabla();
					break;
				case "SET":
					actualizarFicheros();
					break;
				default:
					break;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	// Devuelve la tabla completa al cliente que la ha solicitado
	private void getTabla() throws IOException {
		TablaXML.mandarTabla(this.cliente.getOutputStream());
	}

	// Actualiza la tabla con los datos que recibe del cliente
	private void actualizarFicheros() throws IOException {
		String ip = this.cliente.getInetAddress().getHostAddress();
		int puerto = this.cliente.getPort();
		
		Cliente c = TablaXML.getCliente(ip, puerto);
		if(c==null)
			c = new Cliente(ip, puerto);
		TablaXML.actualizarTabla(this.cliente.getInputStream(), c);
	}
	
}
