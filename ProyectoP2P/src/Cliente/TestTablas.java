package Cliente;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;

public class TestTablas {

	public static void main(String[] args) {
		try {
			Timer timer = new Timer();
			File directorio = new File(Configuracion.get("directorioCompartido"));
			
			ObtenerTabla obt = new ObtenerTabla(directorio, 0);
			timer.scheduleAtFixedRate(obt, 0, 10000);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

}
