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
		 * SET <puerto> --> Actualizar la lista de ficheros de ese cliente. <puerto> es el puerto por el que el cliente atiende peticiones
		 */
		try (DataInputStream is = new DataInputStream(this.cliente.getInputStream());){
			String linea = is.readLine();
//			String[] mensaje = null;
//			String protocolo = "";
//			if (linea != null) {
				String[] mensaje = linea.split(" ");
				String protocolo = mensaje[0];
//			}
			switch (protocolo) {
				case "GET":
					getTabla();
					break;
				case "SET":
					if(mensaje.length>1) {
						actualizarFicheros(Integer.parseInt(mensaje[1]));
					}
					break;
				default:
					break;
			}
			
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
		
	}

	// Devuelve la tabla completa al cliente que la ha solicitado
	private void getTabla() throws IOException {
		TablaXML.mandarTabla(this.cliente.getOutputStream());
	}

	// Actualiza la tabla con los datos que recibe del cliente
	private void actualizarFicheros(int puerto) throws IOException {
		String ip = this.cliente.getInetAddress().getHostAddress();
		
		Cliente c = TablaXML.getCliente(ip, puerto);
		if(c==null)
			c = new Cliente(ip, puerto);
		TablaXML.actualizarTabla(this.cliente.getInputStream(), c);
	}
	
}
