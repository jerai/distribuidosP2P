package Cliente;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;

import ModeloDeDominio.Cliente;
import ModeloDeDominio.Fichero;

public class ClienteCliente implements Runnable{
	
	private int puertoServidor;
	private DataOutputStream os;
	
	public ClienteCliente(int puertoServidor, DataOutputStream os) {
		this.puertoServidor = puertoServidor;
		this.os = os;
	}
	
	public void run() {
		
		Scanner sn = new Scanner(System.in);
		String opcion;
		File directorio/*, ficheroDestino*/;
		Fichero ficheroOrigen;
		System.out.print("¿Desea introducir un directorio para compartir y descargar ficheros? (Y/N): ");
		opcion = sn.nextLine();
		directorio = seleccionarDirectorio(opcion);
		Timer timer = new Timer();
		Map<Fichero, List<Cliente>> TABLA;
		ObtenerTabla obtTabla;
		try {
			do{
				CountDownLatch count = new CountDownLatch(1);
				obtTabla = new ObtenerTabla(count, directorio, directorio.lastModified(), this.puertoServidor);
				timer.schedule(obtTabla, 0, TimeUnit.MINUTES.toMinutes(5));
				count.await();
				TABLA = obtTabla.getTabla();
				System.out.println("TAMAÑO TABLA: " + TABLA.size()); // <--------------------------- prueba
				int i = 1;
				for (Fichero fichero : TABLA.keySet()) {
					System.out.println(i + ". " + fichero.getNombre());
					i++;
				}
				System.out.print("Elige el fichero de la tabla que quieres descargar: ");
				i = sn.nextInt();
				ficheroOrigen = (Fichero) TABLA.keySet().toArray()[i-1];
				String dirDestino = directorio.getAbsolutePath() + "/" + ficheroOrigen.getNombre();
				long tamaño = ficheroOrigen.getTamano();
				//ficheroDestino = new File(ficheroOrigen.getNombre());
				List<Cliente> clientes = TABLA.get(ficheroOrigen);
				count = new CountDownLatch(clientes.size());
				ExecutorService pool = Executors.newFixedThreadPool(clientes.size());
				for (i = 0; i < clientes.size(); i++) {
					pool.execute(new Descargador(tamaño*i/clientes.size(), tamaño*(i+1)/clientes.size(), count, clientes.get(i).getIp(), clientes.get(i).getPuerto(), ficheroOrigen/*, ficheroDestino*/, dirDestino));
				}
				count.await();
				pool.shutdown();
				// Una vez hecha la descarga, actualizar la tabla del servidor
				try {
					os.write("SET\n".getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.print("¿Quieres descargar más ficheros? (Y/N): ");
				opcion = sn.nextLine();
			} while(opcion.equals("Y") | opcion.equals("y"));
			// Desconectar al cliente, quitándole de la tabla del servidor
			try {
				os.write("SET\n".getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			sn.close();
			timer.cancel();
		} catch (InterruptedException e) {
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
