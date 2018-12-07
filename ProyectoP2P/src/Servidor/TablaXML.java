package Servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import ModeloDeDominio.Cliente;
import ModeloDeDominio.Fichero;

public final class TablaXML {
	
	private static ConcurrentHashMap<Fichero, List<Cliente>> TABLA = new ConcurrentHashMap<>();;
	private static List<Cliente> listaClientes = new ArrayList<>();

    // Recibe un objeto ConcurrentHashMap<String, Fichero> por el InputStream y actualiza la tabla con los registros de ficheros que tiene el cliente cli en su directorio compartido
    public static void actualizarTabla(InputStream is, Cliente cli) {
		try {
			if(!listaClientes.contains(cli))
				listaClientes.add(cli);
			
			ObjectInputStream ois = new ObjectInputStream(is);
			Object aux = ois.readObject();
			HashMap<Fichero, List<Cliente>> tablita = (HashMap<Fichero, List<Cliente>>) aux;

			for (Fichero f : tablita.keySet()) {
				if(TABLA.containsKey(f)) {// si ya tenemos el fichero
					if(!TABLA.get(f).contains(cli)) {// si no teniamos ese cliente asociado al fichero
						TABLA.get(f).add(cli);
					}
				}else {// si el fichero es nuevo
					List<Cliente> l = new ArrayList<>();
					l.add(cli);
					TABLA.put(f, l);
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
    
    // Devuelve el objeto Cliente que corresponde a la ip y puerto indicados
    // si no lo encuentra devuelve null
    public static Cliente getCliente(String ip, int puerto) {
    	Cliente c = null;
    	int i=0;
    	for(i=0 ; i<listaClientes.size() && c==null ; i++);
    	if(i<listaClientes.size())
    		c = listaClientes.get(i);
    	return c;
    }
    
}
