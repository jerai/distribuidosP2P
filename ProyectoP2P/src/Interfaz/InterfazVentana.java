package Interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Choice;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Cliente.ClienteCliente;
import Cliente.Configuracion;
import Cliente.Descargador;
import Cliente.ObtenerTabla;
import Cliente.ServidorCliente;
import ModeloDeDominio.Cliente;
import ModeloDeDominio.Fichero;
import Servidor.Principal;

import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazVentana extends JFrame {

	private JPanel contentPane;
	private JTextField txtServidor;
	private JTable tablaLocal;
	private JTable tablaServidor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazVentana frame = new InterfazVentana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazVentana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 876, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel Cabecera = new JPanel();
		Cabecera.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(Cabecera, BorderLayout.NORTH);
		Cabecera.setLayout(new BoxLayout(Cabecera, BoxLayout.X_AXIS));
		
		JPanel TopLeft = new JPanel();
		FlowLayout flowLayout = (FlowLayout) TopLeft.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		Cabecera.add(TopLeft);
		
		JLabel lblServidor = new JLabel("Servidor");
		TopLeft.add(lblServidor);
		
		txtServidor = new JTextField();
		TopLeft.add(txtServidor);
		txtServidor.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		TopLeft.add(separator);
		
		JLabel lblCarpetaCompartida = new JLabel("Carpeta compartida");
		TopLeft.add(lblCarpetaCompartida);
		
		JButton btnCambiarcarpeta = new JButton("Cambiar");
		btnCambiarcarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser(directorioCompartido);
				f.setDialogTitle("Selecciona el directorio compartido");
		        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		        f.showSaveDialog(null);
		        directorioCompartido = f.getSelectedFile();
		        Configuracion.set("directorioCompartido", directorioCompartido.getAbsolutePath());
				btnCambiarcarpeta.setText(directorioCompartido.getAbsolutePath());
				timersTablas();
			}
		});
		TopLeft.add(btnCambiarcarpeta);
		
		JPanel TopRight = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) TopRight.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		Cabecera.add(TopRight);
		
		JCheckBox chckbxServidor = new JCheckBox("Soy servidor");
		chckbxServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxServidor.setEnabled(false);
				Servidor.Principal s = new Principal();
				Thread th1 = new Thread(s);
				th1.start();
			}
		});
		TopRight.add(chckbxServidor);
		
		JPanel Central = new JPanel();
		contentPane.add(Central, BorderLayout.CENTER);
		GridBagLayout gbl_Central = new GridBagLayout();
		gbl_Central.columnWidths = new int[] {374, 100, 374};
		gbl_Central.rowHeights = new int[]{388, 0};
		gbl_Central.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_Central.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		Central.setLayout(gbl_Central);
		
		JPanel CentIzq = new JPanel();
		GridBagConstraints gbc_CentIzq = new GridBagConstraints();
		gbc_CentIzq.fill = GridBagConstraints.BOTH;
		gbc_CentIzq.insets = new Insets(0, 0, 0, 5);
		gbc_CentIzq.gridx = 0;
		gbc_CentIzq.gridy = 0;
		Central.add(CentIzq, gbc_CentIzq);
		CentIzq.setLayout(new BorderLayout(0, 0));
		
		tablaLocal = new JTable();
		CentIzq.add(new JScrollPane(tablaLocal), BorderLayout.NORTH);
		tablaLocal.setShowVerticalLines(false);
		tablaLocal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaLocal.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Tama\u00F1o"
			}
		));
		
		JPanel CentCent = new JPanel();
		GridBagConstraints gbc_CentCent = new GridBagConstraints();
		gbc_CentCent.fill = GridBagConstraints.BOTH;
		gbc_CentCent.insets = new Insets(0, 0, 0, 5);
		gbc_CentCent.gridx = 1;
		gbc_CentCent.gridy = 0;
		Central.add(CentCent, gbc_CentCent);
		CentCent.setLayout(new GridLayout(3, 1, 0, 0));
		
		JButton btnDescargar = new JButton("Descargar");
		btnDescargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				descargar(tablaServidor.getSelectedRow());
			}
		});
		CentCent.add(btnDescargar);
		
		JButton btnActualizar = new JButton("Actualizar");
		CentCent.add(btnActualizar);
		
		JPanel CentDcha = new JPanel();
		GridBagConstraints gbc_CentDcha = new GridBagConstraints();
		gbc_CentDcha.fill = GridBagConstraints.BOTH;
		gbc_CentDcha.gridx = 2;
		gbc_CentDcha.gridy = 0;
		Central.add(CentDcha, gbc_CentDcha);
		CentDcha.setLayout(new BorderLayout(0, 0));
		
		tablaServidor = new JTable();
		CentDcha.add(new JScrollPane(tablaServidor), BorderLayout.NORTH);
		tablaServidor.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Tama\u00F1o", "Proveedores"
			}
		));
		tablaServidor.setShowVerticalLines(false);
		tablaServidor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JPanel Pie = new JPanel();
		Pie.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(Pie, BorderLayout.SOUTH);
		
		JLabel lblEstado = new JLabel("Estado");
		Pie.add(lblEstado);
		
		/*****************************************************CODIGO**********************************************************************/
		String dirServidor = Configuracion.get("ipServidor");
		puertoServidor = Integer.parseInt(Configuracion.get("puertoServidor"));
		directorioCompartido = new File(Configuracion.get("directorioCompartido"));
		
		// iniciamos el servidor
		try {
			ServidorCliente sc = new ServidorCliente();
			puertoServidor = sc.getPuerto();
			Thread th1 = new Thread(sc);
			th1.start();
		
			btnCambiarcarpeta.setText(directorioCompartido.getAbsolutePath());
			txtServidor.setText(dirServidor);
			
			timersTablas();


			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		/*********************************************************************************************************************************/
	}
	
	private Timer timer;
	private int puertoServidor;
	private File directorioCompartido;
	private ObtenerTabla obt;
	
	private void timersTablas() {
		if(timer!=null)
			timer.cancel();
		timer = new Timer();
		obt = new ObtenerTabla(directorioCompartido, puertoServidor);
		ActualizarInterfaz actualizar = new ActualizarInterfaz(tablaLocal, tablaServidor, obt);
		timer.scheduleAtFixedRate(obt, 0, 10000);
		timer.scheduleAtFixedRate(actualizar, 0, 1000);
	}
	
	private void descargar(int i) {
		try {
			List<Fichero> l = new ArrayList<>(obt.getTabla().keySet());
			Fichero fich = l.get(i);
			List<Cliente> lisClientes = obt.getTabla().get(fich);
//			ExecutorService pool = Executors.newFixedThreadPool(lisClientes.size());
			
			
			
			final CountDownLatch count = new CountDownLatch(1);
			ExecutorService pool = Executors.newFixedThreadPool(1);
			File file = new File(directorioCompartido.getAbsolutePath() + "\\" + fich.getNombre());
			
			
			Descargador des = new Descargador(0, fich.getTamano(), count, Configuracion.get("ipServidor"), puertoServidor, fich, file);
			pool.execute(des);
			count.await();
			System.out.println("done");
			
			pool.shutdown();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
