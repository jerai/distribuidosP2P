package Cliente;

import java.net.Socket;

public class AtenderPeticion implements Runnable {

	private Socket cliente;

	public AtenderPeticion(Socket cliente) {
		this.cliente = cliente;
	}

	public void run() {
		/*
		 * Protocolos:
		 * GET <hash> --> 
		 */
	}

}
