package Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Cliente.Descargador;
import ModeloDeDominio.Fichero;

public class TestDescarga {

	public static void main(String[] args) {
		Fichero f = new Fichero("96c9766aa0d2590be49a4693e4919653a72537b662501f56dfa74404d06ccaef");
		
		try {
			final CountDownLatch count = new CountDownLatch(1);
			ExecutorService pool = Executors.newFixedThreadPool(1);
//			File file = new File("aux.txt");
//			boolean asdas = file.createNewFile();
//			String p = file.getAbsolutePath();
//			String pp = file.getPath();
			
			
			Descargador des = new Descargador(0, 15L, count, "127.0.0.1", 50734, f/*, file*/, "../aux.txt");
			pool.execute(des);
			count.await();
			System.out.println("done");
			
			pool.shutdown();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} /*catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
