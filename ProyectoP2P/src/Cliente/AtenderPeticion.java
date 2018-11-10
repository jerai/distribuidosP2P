package Cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class AtenderPeticion implements Runnable {

	private Socket cliente;

	public AtenderPeticion(Socket cliente) {
		this.cliente = cliente;
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
					// Comprueba que tiene el fichero y responde un OK o un NO según corresponda
					// Si se responde OK:
						// Recibe la petición de que parte del fichero quiere
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
