package Cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Descargador implements Runnable {
	
	private long inicio;
	private long fin;
	private CountDownLatch count;
	private String host;
	private int puerto;

	public Descargador(long inicio, long fin, CountDownLatch count, String host, int puerto) {
		this.inicio = inicio;
		this.fin = fin;
		this.count = count;
		this.host = host;
		this.puerto = puerto;
	}

	public void run() {
		try {
			// indicar desde donde hasta donde
			
			RandomAccessFile raf = new RandomAccessFile(Configuracion.get("directorioCompartido"), "rw");
			raf.seek(this.inicio);
			
			Socket s = new Socket(this.host, this.puerto);
			InputStream is = s.getInputStream();
			
			byte buff[] = new byte[1024];
			int len = is.read(buff);
			while (len != -1) {
				raf.write(buff, 0, len);
				len = is.read(buff);
			}
			
			is.close();
			raf.close();
			count.countDown();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
