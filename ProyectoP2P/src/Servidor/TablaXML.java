package Servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import ModeloDeDominio.Cliente;
import ModeloDeDominio.Fichero;

public final class TablaXML {
	
	private static ConcurrentHashMap<String, Fichero> TABLA;

    // Recibe un objeto ConcurrentHashMap<String, Fichero> por el InputStream y actualiza la tabla con los registros de ficheros que tiene el cliente cli en su directorio compartido
    public static void actualizarTabla(InputStream is, Cliente cli) {
		try {
			ObjectInputStream ois = new ObjectInputStream(is);
			Object aux = ois.readObject();
			ConcurrentHashMap<String, Fichero> tablita = (ConcurrentHashMap<String, Fichero>) aux;

			for (Enumeration<Fichero> e = tablita.elements(); e.hasMoreElements();) {
				Fichero f = e.nextElement();
				String clave = f.getHash();
				if(TABLA.containsKey(clave)) {
					TABLA.get(clave).addPropietario(cli);
				}else {
					f.addPropietario(cli);
					TABLA.put(clave, f);
				}
			}
			 
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
    }
    
    // Manda la tabla en forma de objeto por el OutputStream que se le pasa
    public static void mandarTabla(OutputStream os) throws IOException {
    	ObjectOutputStream oos = new ObjectOutputStream(os);
    	oos.writeObject(TABLA);
    }
    
}
