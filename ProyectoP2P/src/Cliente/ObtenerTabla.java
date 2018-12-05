package Cliente;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import ModeloDeDominio.Cliente;
import ModeloDeDominio.Fichero;

public class ObtenerTabla extends TimerTask {

	private File directorio;
	private long fechaModificacion;
	private Socket cliente;

	private Map<Fichero, List<Cliente>> TABLA;

	public ObtenerTabla(File directorio, long fechaModificacion, Socket cliente) {
		this.directorio = directorio;
		this.fechaModificacion = fechaModificacion;
		this.cliente = cliente;
	}

	public void run() {
		InputStream is = null;
		OutputStream os = null;
		try {
			if (this.directorio.lastModified() != this.fechaModificacion) {
				System.out.println("Actualizando tabla " + (this.directorio.lastModified() - this.fechaModificacion));// <--------------------- prueba
				this.fechaModificacion = this.directorio.lastModified();
				os = cliente.getOutputStream();
				is = cliente.getInputStream();
				os.write("GET\n".getBytes());
				this.TABLA = recibirTabla(is);
				
				os.write("SET\n".getBytes());
				mandarTabla(os);

				os.write("END\n".getBytes());
				
				// prueba
				for (Fichero fichero : TABLA.keySet()) {
					System.out.println(fichero);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Crea un objeto Map<String, Fichero> con los ficheros del directorio
	// compartido y lo manda por el OutputStream os
	private void mandarTabla(OutputStream os) throws IOException {
		Map<Fichero, List<Cliente>> tablita = new HashMap<>();
		Fichero f;
		for (File aux : this.directorio.listFiles()) {
			f = new Fichero(HashSHA256.getHash(aux), aux.getName(), aux.getPath());
			tablita.put(f, new ArrayList<>());
		}
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(tablita);
	}

	// Recibe un objeto Map<String, Fichero> con los ficheros disponibles en la red
	private Map<Fichero, List<Cliente>> recibirTabla(InputStream is) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(is);
		Map<Fichero, List<Cliente>> tabla = (Map<Fichero, List<Cliente>>) ois.readObject();

		return tabla;
	}

}
