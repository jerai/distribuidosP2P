package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Principal implements Runnable{

	public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(6666);){
			Socket cliente;
			ExecutorService pool = Executors.newCachedThreadPool();// Hay que probar cuantos hilos soporta para ponerlo fijo
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

	public void run() {
		try (ServerSocket ss = new ServerSocket(6666);){
			Socket cliente;
			ExecutorService pool = Executors.newCachedThreadPool();// Hay que probar cuantos hilos soporta para ponerlo fijo
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
