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

public final class Tabla {
	
	private static ConcurrentHashMap<Fichero, List<Cliente>> TABLA = new ConcurrentHashMap<>();;
	private static List<Cliente> listaClientes = new ArrayList<>();

    // Recibe un objeto List<Fichero> por el InputStream y actualiza la TABLA con los registros de ficheros que tiene el cliente cli en su directorio compartido
    public static void actualizarTabla(InputStream is, Cliente cli) {
		try {
			if(!listaClientes.contains(cli))
				listaClientes.add(cli);
			
			ObjectInputStream ois = new ObjectInputStream(is);
			List<Fichero> listFicheros = (List<Fichero>) ois.readObject();

			for (Fichero f : listFicheros) {
				if(TABLA.containsKey(f)) {// si ya tenemos el fichero
					if(!TABLA.get(f).contains(cli)) {// si no teniamos ese cliente asociado al fichero
						TABLA.get(f).add(cli);
					}
				}else {// si el fichero es nuevo
					List<Cliente> l = new ArrayList<>();
					l.add(cli);
					TABLA.put(f, l);
				}
				
				// Para borrar los que ya no están disponibles
				for(Fichero ff : TABLA.keySet()) {
					if(!listFicheros.contains(ff)) {
						List<Cliente> aux = TABLA.get(ff);
						if(aux.contains(cli)) {
							if(aux.size()==1) {
								TABLA.remove(ff);
							}else {
								aux.remove(cli);
							}
						}
					}
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

	public static void desconectarUsuario(String ip, int puerto) {
		Cliente cli = new Cliente(ip, puerto);
		for(Fichero f : TABLA.keySet()) {
			List<Cliente> aux = TABLA.get(f);
			if(aux.contains(cli)) {
				if(aux.size()==1) {
					TABLA.remove(f);
				}else {
					aux.remove(cli);
				}
			}
		}
	}
    
}
