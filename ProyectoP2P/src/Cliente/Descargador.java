package Cliente;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import ModeloDeDominio.Fichero;

public class Descargador implements Runnable {
	
	private long inicio;
	private long fin;
	private CountDownLatch count;
	private String host;
	private int puerto;
	private File ficheroDestino;
	private Fichero ficheroOrigen;
	private String path;

	public Descargador(long inicio, long fin, CountDownLatch count, String host, int puerto, Fichero ficheroOrigen, File ficheroDestino, String path) {
		this.inicio = inicio;
		this.fin = fin;
		this.count = count;
		this.host = host;
		this.puerto = puerto;
		this.ficheroOrigen = ficheroOrigen;
		this.ficheroDestino = ficheroDestino;
		this.path = path;
	}

	public void run() {
		try {
			// indicar desde donde hasta donde
			
			RandomAccessFile raf = new RandomAccessFile(this.path, "rw");
			raf.seek(this.inicio);
			
			Socket s = new Socket(this.host, this.puerto);
			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();
			os.write(("GET " + this.ficheroOrigen.getHash() + " " + this.inicio + " " + this.fin + "\n").getBytes());
			os.flush();
			
			byte buff[] = new byte[1024];
			int len = is.read(buff);
			while (len != -1) {
				String str = new String(buff, StandardCharsets.UTF_8);
				System.out.println(str);
				raf.write(buff, 0, len);
				len = is.read(buff);
			}
			
			raf.close();
			count.countDown();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
