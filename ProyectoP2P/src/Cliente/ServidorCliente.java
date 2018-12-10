package Cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ModeloDeDominio.Cliente;

public class ServidorCliente {

	public ServidorCliente(ServerSocket ss) {
		System.out.println(ss.getLocalPort());// prueba <-------------------------------------
		Socket cliente;
		ExecutorService pool = Executors.newCachedThreadPool();
		while(true) {
			try {
				cliente = ss.accept();
				pool.execute(new AtenderPeticion(cliente, ss.getLocalPort()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
