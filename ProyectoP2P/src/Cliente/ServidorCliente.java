package Cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorCliente {

	public ServidorCliente(int puerto) {
		try (ServerSocket ss = new ServerSocket(puerto)){
			Socket cliente;
			ExecutorService pool = Executors.newCachedThreadPool();
			while(true) {
				try {
					cliente = ss.accept();
					pool.execute(new AtenderPeticion(cliente));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
