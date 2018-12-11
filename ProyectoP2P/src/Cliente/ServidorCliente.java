package Cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ModeloDeDominio.Cliente;

public class ServidorCliente implements Runnable{
	
	private int puertoServidor;

	public void run() {
		try (ServerSocket ss = new ServerSocket(0)){
			this.puertoServidor = ss.getLocalPort();
			Socket cliente;
			ExecutorService pool = Executors.newCachedThreadPool();
			while(true) {
				try {
					cliente = ss.accept();
					pool.execute(new AtenderPeticion(cliente, this.puertoServidor));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPuerto() {
		return puertoServidor;
	}

}
