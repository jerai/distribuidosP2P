package Servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

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
		PrintStream os = new PrintStream(this.cliente.getOutputStream());
		
	}

	// Actualiza la tabla con los datos que recibe del cliente
	private void actualizarFicheros() {
		
	}
	
}
