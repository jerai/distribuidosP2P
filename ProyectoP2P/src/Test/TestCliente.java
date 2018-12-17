package Test;

import java.io.IOException;
import java.net.ServerSocket;

import Cliente.ServidorCliente;

public class TestCliente {

	public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(0)){
			ServidorCliente sc = new ServidorCliente();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
