package Cliente;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import ModeloDeDominio.Cliente;
import ModeloDeDominio.Fichero;

public class ObtenerTabla extends TimerTask {

	private File directorio;
	private long fechaModificacion;
	private int puertoServidor;

	private Map<Fichero, List<Cliente>> TABLA;

	public ObtenerTabla(File directorio, long fechaModificacion, int puertoServidor) {
		this.directorio = directorio;
		this.fechaModificacion = fechaModificacion;
		this.puertoServidor = puertoServidor;
	}

	public void run() {
		InputStream is = null;
		OutputStream os = null;
		String ip = Configuracion.get("ipServidor");
		int puerto = Integer.parseInt(Configuracion.get("puertoServidor"));
		
		System.out.println("----------------------------------------------------------");// <--------------------- prueba
		
		if (this.fechaModificacion != this.directorio.lastModified()) {
			System.out.println("Actualizando tabla " + (this.directorio.lastModified() - this.fechaModificacion));// <--------------------- prueba
			this.fechaModificacion = this.directorio.lastModified();
			
			try (Socket socket1 = new Socket(ip, puerto)){
				mandarTabla(socket1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		try (Socket socket2 = new Socket(ip, puerto);){
			this.TABLA = recibirTabla(socket2);
			
			for (Fichero fichero : TABLA.keySet()) {// <--------------------- prueba
				System.out.println(fichero);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Crea un objeto List<Fichero> con los ficheros del directorio compartido y lo manda por el Socket
	private void mandarTabla(Socket s) throws IOException {
		List<Fichero> listFicheros = new ArrayList<>();
		Fichero f;
		for (File aux : getFicheros(this.directorio)) {
			f = new Fichero(HashSHA256.getHash(aux), aux.getName(), aux.getPath());
			listFicheros.add(f);
		}
		
		OutputStream os = s.getOutputStream();
		System.out.println("SET " + this.puertoServidor + "\n");
		os.write(("SET " + this.puertoServidor + "\n").getBytes());
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(listFicheros);
	}

	// Recibe un objeto Map<String, Fichero> con los ficheros disponibles en la red a trav�s del socket
	private Map<Fichero, List<Cliente>> recibirTabla(Socket s) throws IOException, ClassNotFoundException {
		OutputStream os = s.getOutputStream();
		os.write("GET\n".getBytes());
		InputStream is = s.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		Map<Fichero, List<Cliente>> tabla = (Map<Fichero, List<Cliente>>) ois.readObject();

		return tabla;
	}
	
	// Devuelve una lista con los ficheros que contiene un directorio incluyendo los que est�n en carpetas
	private List<File> getFicheros(File direct) {
		List<File> list = new ArrayList<>();
		for (File file : direct.listFiles()) {
			if(file.isFile()) {
				list.add(file);
			}else {
				list.addAll(getFicheros(file));
			}
		}
		return list;
	}
	
	public Map<Fichero, List<Cliente>> getTabla(){
		return this.TABLA;
	}

}
