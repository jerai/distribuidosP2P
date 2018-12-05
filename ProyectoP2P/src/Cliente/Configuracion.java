package Cliente;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {
	
	private static String nombreFichero = "./Resources/config.properties";

	public static String get(String key) {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(nombreFichero));
			return prop.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
