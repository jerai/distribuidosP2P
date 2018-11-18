package Cliente;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;

public class Principal {

	public static void main(String[] args) {
		Scanner sn = new Scanner(System.in);
		String opcion, descarga;
		File directorio;
		int puerto;
		System.out.print("¿Desea introducir un directorio para compartir y descargar ficheros? (Y/N): ");
		opcion = sn.nextLine();
		directorio = seleccionarDirectorio(opcion);
		System.out.print("Selecciona el puerto donde quieres que funcione tu servidor: ");
		puerto = sn.nextInt();
		ServidorCliente sc = new ServidorCliente(puerto);
		try(Socket cliente = new Socket("localhost", 6666);
			DataOutputStream os = new DataOutputStream(cliente.getOutputStream())){
			Timer timer = new Timer();
			do{
				timer.schedule(new ObtenerTabla(directorio, directorio.lastModified(), cliente), 0, TimeUnit.MINUTES.toMinutes(5));
				// Mostrar la tabla obtenida del servidor
				System.out.print("Elige el fichero de la tabla que quieres descargar: ");
				descarga = sn.nextLine();
				ClienteCliente cc = new ClienteCliente(descarga);
				// Una vez hecha la descarga, actualizar la tabla del servidor
				os.writeUTF("SET");
				System.out.print("¿Quieres descargar más ficheros? (Y/N): ");
				opcion = sn.nextLine();
			} while(opcion.equals("Y") | opcion.equals("y"));
			// Desconectar al cliente, quitándole de la tabla del servidor
			os.writeUTF("SET");	
			sn.close();
			timer.cancel();
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
					Files.createDirectory(Paths.get("./Compartidos"));
					System.out.println("Se ha creado un directorio 'Compartidos' en el directorio por defecto");
					return new File("./Compartidos");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
	        }
		} else {
			try {
				Files.createDirectory(Paths.get("./Compartidos"));
				System.out.println("Se ha creado un directorio 'Compartidos' en el directorio por defecto");
				return new File("./Compartidos");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}

}
