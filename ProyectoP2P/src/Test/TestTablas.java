package Test;

import java.io.File;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;

import Cliente.Configuracion;
import Cliente.ObtenerTabla;

public class TestTablas {

	public static void main(String[] args) {
		try {
			Timer timer = new Timer();
			File directorio = new File(Configuracion.get("directorioCompartido"));
			CountDownLatch count = new CountDownLatch(1);
			ObtenerTabla obt = new ObtenerTabla(count, directorio, 0, 0);
			timer.scheduleAtFixedRate(obt, 0, 10000);
			count.await();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
