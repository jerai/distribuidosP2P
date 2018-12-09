package Cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class AtenderPeticion implements Runnable {

	private Socket cliente;
	private int puertoServidor;

	public AtenderPeticion(Socket cliente, int puertoServidor) {
		this.cliente = cliente;
		this.puertoServidor = puertoServidor;
	}

	public void run() {
		/*
		 * Protocolos:
		 * GET <hash> --> Quiere descargar un archivo con el hash <hash>
		 */
		try (DataInputStream is = new DataInputStream(this.cliente.getInputStream());){
			String mensaje = is.readLine();
			String protocolo = mensaje.split(" ")[0];
			switch (protocolo) {
				case "GET":
					// Comprueba que tiene el fichero y responde un OK o un NO seg�n corresponda
					// Si se responde OK:
						// Recibe la petici�n de que parte del fichero quiere
						// Se manda lo solicitado
					break;
	
				default:
					break;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
