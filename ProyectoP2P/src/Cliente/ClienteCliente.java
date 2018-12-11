package Cliente;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import ModeloDeDominio.Cliente;
import ModeloDeDominio.Fichero;

public class ClienteCliente implements Runnable{
	
	private File directorio;
	private int puertoServidor;
	private DataOutputStream os;
	
	public ClienteCliente(File directorio, int puertoServidor, DataOutputStream os) {
		this.directorio = directorio;
		this.puertoServidor = puertoServidor;
		this.os = os;
	}
	
	public void run() {
		Scanner sn = new Scanner(System.in);
		String descarga, opcion;
		Timer timer = new Timer();
		Map<Fichero, List<Cliente>> TABLA;
		ObtenerTabla obtTabla;
		try {
			do{
				final CountDownLatch count = new CountDownLatch(1);
				obtTabla = new ObtenerTabla(count, this.directorio, this.directorio.lastModified(), this.puertoServidor);
				timer.schedule(obtTabla, 0, TimeUnit.MINUTES.toMinutes(5));
				count.await();

				TABLA = obtTabla.getTabla();
				for (Fichero fichero : TABLA.keySet()) {
					System.out.println(fichero);
				}
				System.out.print("Elige el fichero de la tabla que quieres descargar: ");
				descarga = sn.nextLine();
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
	
	private void descargador() {

		
	}

}
