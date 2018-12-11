package Cliente;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Principal {

	public static void main(String[] args) {
		
		try(Socket cliente = new Socket("localhost", 6666);
			DataOutputStream os = new DataOutputStream(cliente.getOutputStream())){
			
			ServidorCliente sc = new ServidorCliente();
			ClienteCliente cc = new ClienteCliente(sc.getPuerto(), os);
			
			Thread th1 = new Thread(sc);
			Thread th2 = new Thread(cc);
			
			th1.start();
			th2.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
