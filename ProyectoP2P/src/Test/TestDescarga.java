package Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Cliente.Descargador;
import ModeloDeDominio.Fichero;

public class TestDescarga {

	public static void main(String[] args) {
		// - Hash: ab8103117ad8be7e3384704738536f418ba14a32c42067858c1a736f30cac247 - Path: C:\Users\Jerai\Desktop\Compartido\ffff.txt - Tamaño: 16
		Fichero f = new Fichero("ab8103117ad8be7e3384704738536f418ba14a32c42067858c1a736f30cac247"); 
		try {
			final CountDownLatch count = new CountDownLatch(1);
			ExecutorService pool = Executors.newFixedThreadPool(1);
			File file = new File("C:\\Users\\Jerai\\Desktop\\descargados\\auxsdfhusyidbfyuasdg.txt");
			boolean asdas = file.createNewFile();
			String p = file.getAbsolutePath();
			String pp = file.getPath();
			
			
			Descargador des = new Descargador(0, 15L, count, "127.0.0.1", 55816, f, file);
			pool.execute(des);
			count.await();
			System.out.println("done");
			
			pool.shutdown();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
