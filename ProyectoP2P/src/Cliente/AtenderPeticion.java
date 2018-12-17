package Cliente;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import ModeloDeDominio.Fichero;

public class AtenderPeticion implements Runnable {

	private Socket cliente;
	//private int puertoServidor; <----------------------- pa' qué?

	public AtenderPeticion(Socket cliente/*, int puertoServidor*/) {
		this.cliente = cliente;
		//this.puertoServidor = puertoServidor;
	}

	public void run() {
		/*
		 * Protocolos:
		 * GET <hash> --> Quiere descargar un archivo con el hash <hash>
		 */
		try (DataInputStream is = new DataInputStream(this.cliente.getInputStream());){
			String mensaje[] = is.readLine().split(" ");
			String protocolo = mensaje[0];
			switch (protocolo) {
				case "GET":
					if(mensaje.length==2) {
						// Comprueba que tiene el fichero y responde un OK o un NO según corresponda
						comprobarFichero(new Fichero(mensaje[1]));
					}else if(mensaje.length==4) {
						mandarFichero(Configuracion.getFichero(mensaje[1]), Long.parseLong(mensaje[2]), Long.parseLong(mensaje[3]));
					}
					break;
	
				default:
					break;
			}
			
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	private void comprobarFichero(Fichero f) throws IOException {
		OutputStream os = this.cliente.getOutputStream();
		if(Configuracion.getListaFicheros().contains(f)) {
			os.write("OK\n".getBytes());
		}else {
			os.write("NO\n".getBytes());
		}
	}
	
	private void mandarFichero(Fichero f, long inicio, long fin) throws IOException {// puede fallar todo <------------------------------------
		RandomAccessFile raf = new RandomAccessFile(new File(f.getDireccion()), "rw");
		raf.seek(inicio);
		
		OutputStream os = this.cliente.getOutputStream();
		
		long restante = fin-inicio;
		byte buff[] = new byte[1024];
		int len;
//		if(restante<1024) {
//			len = raf.read(buff, 0, Math.toIntExact(restante));
//		}else {
//			len = raf.read(buff);
//		}
		while (restante != 0) {
			System.out.println(buff);
			if(restante<1024) {
				len = raf.read(buff, 0, Math.toIntExact(restante));
			}else {
				len = raf.read(buff);
			}
			os.write(buff, 0, len);
			restante -= len;
		}
		raf.close();
	}

}
