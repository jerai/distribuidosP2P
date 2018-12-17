package Cliente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ModeloDeDominio.Fichero;

public class Configuracion {
	
	private static String nombreFichero = "./Resources/config.properties";
	private static List<Fichero> listaFicheros = null;
	private static long ultimaActualización;

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

	public static void set(String key, String value) {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(nombreFichero));
			prop.setProperty(key, value);
			prop.store(new FileOutputStream(nombreFichero), null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Devuelve una lista con los ficheros que contiene un directorio incluyendo los que están en carpetas
	private static List<File> getFicheros(File direct) {
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
	
	// devuelve la lista de Ficheros que tiene el cliente en su carpeta compartida
	public static List<Fichero> getListaFicheros(){
		File directorio = new File(get("directorioCompartido"));
		if(ultimaActualización != directorio.lastModified()) {
			ultimaActualización = directorio.lastModified();
			listaFicheros = new ArrayList<>();
			Fichero f;
			for (File aux : getFicheros(directorio)) {
				f = new Fichero(aux);
				listaFicheros.add(f);
			}
		}
		return listaFicheros;
	}
	
	public static Fichero getFichero(String hash) {
		List<Fichero> lista = getListaFicheros();
		int i = lista.indexOf(new Fichero(hash));
		if(i>-1) {
			return lista.get(i);
		}
		return null;
	}

}
