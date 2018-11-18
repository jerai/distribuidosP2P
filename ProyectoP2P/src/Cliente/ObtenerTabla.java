package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.TimerTask;

public class ObtenerTabla extends TimerTask {
	
	private File directorio;
	private long fechaModificacion;
	private Socket cliente;
	
	public ObtenerTabla(File directorio, long fechaModificacion, Socket cliente) {
		this.directorio = directorio;
		this.fechaModificacion = fechaModificacion;
		this.cliente = cliente;
	}

	public void run() {
		try (DataOutputStream os = new DataOutputStream(cliente.getOutputStream());
				DataInputStream is = new DataInputStream(cliente.getInputStream())) {
			if (this.directorio.lastModified() != this.fechaModificacion) {
				this.fechaModificacion = this.directorio.lastModified();
				// 1º) Mandar la tabla de ficheros del cliente
				for (File aux : this.directorio.listFiles()) {
					// ¿Cómo mandamos la tabla?

					os.writeUTF("SET");
				}
				// 2º) Obtener la tabla actualizada del servidor
				os.writeUTF("GET");
				// ¿Cómo leemos la tabla?
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
