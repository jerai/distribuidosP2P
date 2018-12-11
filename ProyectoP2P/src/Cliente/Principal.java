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
		
		Scanner sn = new Scanner(System.in);
		String opcion;
		File directorio;
		System.out.print("¿Desea introducir un directorio para compartir y descargar ficheros? (Y/N): ");
		opcion = sn.nextLine();
		directorio = seleccionarDirectorio(opcion);
		sn.close();
		try(Socket cliente = new Socket("localhost", 6666);
			//ServerSocket ss = new ServerSocket(0);
			DataOutputStream os = new DataOutputStream(cliente.getOutputStream())){
			
			ServidorCliente sc = new ServidorCliente();
			ClienteCliente cc = new ClienteCliente(directorio, sc.getPuerto(), os);
			
			Thread th1 = new Thread(sc);
			Thread th2 = new Thread(cc);
			
			th1.start();
			th2.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static File seleccionarDirectorio (String opcion) {
		JFileChooser chooser;
		File directorio;
		if (opcion.equals("y") | opcion.equals("Y")) {
	        chooser = new JFileChooser();
	        chooser.setDialogTitle("Selecciona un directorio");
	        chooser.setCurrentDirectory(new File("."));
	        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        chooser.setAcceptAllFileFilterUsed(false);
	        chooser.setVisible(true);
	        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	        	directorio = chooser.getSelectedFile();
		        return directorio;
	        } else {
	        	try {
	        		if (!Files.isDirectory(Paths.get("Compartidos"))) {
	        			Files.createDirectory(Paths.get("Compartidos"));
	        		}
					System.out.println("Se utilizará el directorio 'Compartidos' ubicado en el directorio por defecto");
					return new File("Compartidos");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
	        }
		} else {
			try {
				if (!Files.isDirectory(Paths.get("Compartidos"))) {
        			Files.createDirectory(Paths.get("Compartidos"));
        		}
				System.out.println("Se utilizará el directorio 'Compartidos' ubicado en el directorio por defecto");
				return new File("Compartidos");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}

}
