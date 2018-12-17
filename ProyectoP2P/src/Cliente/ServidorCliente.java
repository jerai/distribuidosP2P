package Cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServidorCliente implements Runnable {

	private ServerSocket ss;
	
	public ServidorCliente() {
		try {
			this.ss = new ServerSocket(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
			System.out.println("PUERTO SERVIDOR CLIENTE: " + this.ss.getLocalPort());	// prueba <-------------------------------------
			Socket cliente;
			ExecutorService pool = Executors.newCachedThreadPool();
			while(true) {
				try {
					cliente = this.ss.accept();
					pool.execute(new AtenderPeticion(cliente, this.ss.getLocalPort()));
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
	}
	
	public int getPuerto() {
		return this.ss.getLocalPort();
	}
	
}


