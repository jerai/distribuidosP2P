package Interfaz;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Cliente.Configuracion;
import Cliente.ObtenerTabla;
import ModeloDeDominio.Cliente;
import ModeloDeDominio.Fichero;

public class ActualizarInterfaz extends TimerTask{
	
	private JTable tablaLocal;
	private JTable tablaServidor;
	private ObtenerTabla obt;
	private Map<Fichero, List<Cliente>> tabla;
	
	public ActualizarInterfaz(JTable tablaLocal, JTable tablaServidor, ObtenerTabla obt) {
		this.tablaLocal = tablaLocal;
		this.tablaServidor = tablaServidor;
		this.obt = obt;
	}

	public void run() {
		
		Map<Fichero, List<Cliente>> nuevaTabla = this.obt.getTabla();
		List<Fichero> listaLocal = Configuracion.getListaFicheros();
		
		
		DefaultTableModel def = new DefaultTableModel(0, 2);
		def.setColumnIdentifiers(new String[] {"Nombre", "Tama\u00F1o"});
		for (Fichero fichero : listaLocal) {
			def.addRow(new Object[] {fichero.getNombre(), fichero.getTamano()});
		}
		this.tablaLocal.setModel(def);
		
		
		if(this.tabla==null || !this.tabla.equals(nuevaTabla)) {
			this.tabla = nuevaTabla;
			def = new DefaultTableModel(0, 3);
			def.setColumnIdentifiers(new String[] {"Nombre", "Tama\u00F1o", "Proveedores"});
			for (Fichero fichero : tabla.keySet()) {
				def.addRow(new Object[] {fichero.getNombre(), getTamano(fichero.getTamano()), tabla.get(fichero).size()});
			}
			this.tablaServidor.setModel(def);
		}
		
		
	}
	
	private String getTamano(long lon) {
		if(lon<1000)
			return lon + " bytes";
		if(lon<1000000)
			return (lon/1000) + " KB";
		if(lon<1000000000)
			return (lon/1000000) + " MB";
		return (lon/1000000000) + " GB";
	}

}
