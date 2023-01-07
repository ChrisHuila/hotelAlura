package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.HuespedController;
import controller.ReservaController;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloH;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	
	private ReservaController controllerReserva;
	private HuespedController controllerHuesped;

	public static Object editarReserva[];
	public static Object editarHuesped[];
	private JTable tbReserva;
	
	static ArrayList<String> datosBuscados = new ArrayList<String>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
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
	public Busqueda() {
		this.controllerReserva = new ReservaController();
		this.controllerHuesped = new HuespedController();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
//		agrega el termino de busqueda
		setTxtBuscar(txtBuscar);
		
		JLabel lblNewLabel_4 = new JLabel("BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
		
//		Agrega la tabla de reservas
		JScrollPane scrollPane = new JScrollPane();
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scrollPane, null);
		
		tbReserva = new JTable();
		tbReserva.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00B0 Reserva", "Fecha Ingreso", "Fecha de salida", "Valor", "Forma de Pago"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tbReserva.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReserva.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReserva.getModel();
		scrollPane.setViewportView(tbReserva);	

		setModelo(modelo);		
//		recibe la tabla de reservas		
		setTbReservas(tbReserva);
	
		
//		Tabla Huespedes
		JScrollPane scrollPane1 = new JScrollPane();
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scrollPane1, null);
		
		tbHuespedes = new JTable();
		tbHuespedes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00B0 Hu\u00E9sped", "Nombre", "Apellido", "Nacionalidad", "Tel\u00E9fono", "N\u00B0 Reserva", "Fecha de Nacimiento"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloH = (DefaultTableModel) tbHuespedes.getModel();
		scrollPane1.setViewportView(tbHuespedes);
		
//		recibe la tabla de huespedes		
		setTbHuespedes(tbHuespedes);
		setModeloH(modeloH);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				reinicia los campos de reserva y huesped 
				ReservasView.borrarCamposReserva = true;
				RegistroHuesped.borrarCamposHuesped = true;
				
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				reinicia los campos de reserva y huesped 
				ReservasView.borrarCamposReserva = true;
				RegistroHuesped.borrarCamposHuesped = true;
				
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
//		Reinicia el array de busqueda
		datosBuscados.clear();
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
//				Valida que haya escrito termino de busqueda				
				if(getTxtBuscar().getText().length() > 0) {
					
					
					buscarDatos();
					
				}else {
					JOptionPane.showMessageDialog(null, "Escribe un termino de busqueda");
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				editar();			

			}
			
		});
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				eliminarRegistro();		
				
			}
		});
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	public void eliminarRegistro() {
		int filaSeleccionadaREliminar = getTbReservas().getSelectedRow();
		int filaSeleccionadaHEliminar = getTbHuespedes().getSelectedRow();	
		
//		Valida que haya seleccionado la fila de reserva o huespedes y valida el indice de las columnas
		if (filaSeleccionadaREliminar != -1  ) {
			
			filaSeleccionadaHEliminar = filaSeleccionadaREliminar;
		} else if (filaSeleccionadaHEliminar != -1 ) {
			filaSeleccionadaREliminar = filaSeleccionadaHEliminar;
		}	

		try {
			String idReserva = getTbReservas().getValueAt(filaSeleccionadaREliminar, 0).toString();
			String idHuesped = getTbHuespedes().getValueAt(filaSeleccionadaHEliminar, 5).toString();		

			
			if (filaSeleccionadaREliminar ==  filaSeleccionadaHEliminar ) {
				
				System.out.println("muestrame esto " + idHuesped);
				if ((Integer.parseInt(idReserva)) == (Integer.parseInt(idHuesped))) {
					
					int confirmaEliminacion=JOptionPane.showConfirmDialog(null,
							"Quieres eliminar el registro con numero de  reserva: " + idReserva );
				      if (JOptionPane.OK_OPTION == confirmaEliminacion){
				    	  
				    		controllerHuesped.eliminarHuesped(idHuesped);
							controllerReserva.eliminarReserva(idHuesped);
							
							MenuUsuario usuario = new MenuUsuario();
							usuario.setVisible(true);
							dispose();
				 }
			
				}
			
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar registro ( " + e.getMessage() + ")");
		}
		
	}
	
	public void editar(){

		int filaSeleccionadaREditar = getTbReservas().getSelectedRow();
		
		int filaSeleccionadaHEditar = getTbHuespedes().getSelectedRow();
		
		try {
			
//			Valida que haya seleccionado la fila de reserva o huespedes y valida el indice de las columnas
			if (filaSeleccionadaREditar != -1  ) {
				
				filaSeleccionadaHEditar = filaSeleccionadaREditar;
			} else if (filaSeleccionadaHEditar != -1 ) {
				filaSeleccionadaREditar = filaSeleccionadaHEditar;
			}
//			Edita
			if (filaSeleccionadaREditar == filaSeleccionadaHEditar) {
				asignaDatos(filaSeleccionadaREditar, filaSeleccionadaHEditar);
				
//				Permite que los campos de reserva y huesped sean llenados
				ReservasView.borrarCamposReserva = false;
				RegistroHuesped.borrarCamposHuesped = false;
				
//				Carga la tabla de reserva
				ReservasView actualizarR = new ReservasView();
				actualizarR.setVisible(true);		

				dispose();
			}	
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila, error ( " + e.getMessage() + ")");
		}
	
	}
	public void asignaDatos(int filaSeleccionadaREditar, int filaSeleccionadaHEditar ) {
//		Asigna datos de la reserva
		
		editarReserva = new Object[5];
		editarReserva[0]  = (getTbReservas().getValueAt(filaSeleccionadaREditar, 0).toString());
		editarReserva[1]  = (getTbReservas().getValueAt(filaSeleccionadaREditar, 1).toString());
		editarReserva[2]  = (getTbReservas().getValueAt(filaSeleccionadaREditar, 2).toString());
		editarReserva[3]  = (getTbReservas().getValueAt(filaSeleccionadaREditar, 3).toString());
		editarReserva[4]  = (getTbReservas().getValueAt(filaSeleccionadaREditar, 4));
		
//		Asigna datos del huesped
		editarHuesped = new Object[7];
		
		editarHuesped[0] = (getTbHuespedes().getValueAt(filaSeleccionadaHEditar, 0).toString());
		editarHuesped[1] = (getTbHuespedes().getValueAt(filaSeleccionadaHEditar, 1).toString());
		editarHuesped[2] = (getTbHuespedes().getValueAt(filaSeleccionadaHEditar, 2).toString());
		editarHuesped[3] = (getTbHuespedes().getValueAt(filaSeleccionadaHEditar, 3));
		editarHuesped[4] = (getTbHuespedes().getValueAt(filaSeleccionadaHEditar, 4).toString());
		editarHuesped[5] = (getTbHuespedes().getValueAt(filaSeleccionadaHEditar, 5).toString());
		editarHuesped[6] = (getTbHuespedes().getValueAt(filaSeleccionadaHEditar, 6).toString());
	}
	
	public void buscarDatos() {
		int filaInicial = getTbReservas().getRowCount();
		
//		Evita las busquedas repetidas
		boolean palabraBuscada = datosBuscados.contains( getTxtBuscar().getText().trim());
		if(!palabraBuscada) {
//			Valida el ingreso de numeros o datos
			if((getTxtBuscar().getText()).matches("[0-9]*")) {
//				Busca para la tabla reserva
				 controllerReserva.mostrarDatosReserva(getTbReservas(),  getModelo(), getTxtBuscar().getText());
//				Busca para la tabla huesped
				controllerHuesped.mostrarHuesped(getTbHuespedes(), getModeloH(), getTxtBuscar().getText());
				
//				Agrega el dato al array
				datosBuscados.add(getTxtBuscar().getText().trim());
				
			}else {
				controllerReserva.mostrarDatosReservaApellido(getTbReservas(), getModelo(), getTxtBuscar().getText());
				
				controllerHuesped.mostrarDatosHuespedApellido(getTbHuespedes(), getModeloH(), getTxtBuscar().getText());
				
				datosBuscados.add(getTxtBuscar().getText().trim());
			}
		}

		
		int filaFinal = getTbReservas().getRowCount();
		
		if (filaInicial == filaFinal) {
			JOptionPane.showMessageDialog(null, "No se encontro el ID de la reserva, el apellido del huesped o el regisro esta en pantalla");
		}
	
	}
	
	
	
public JTextField getTxtBuscar() {
		return txtBuscar;
	}

	public void setTxtBuscar(JTextField txtBuscar) {
		this.txtBuscar = txtBuscar;
	}

	
	public JTable getTbHuespedes() {
		return tbHuespedes;
	}

	public void setTbHuespedes(JTable tbHuespedes) {
		this.tbHuespedes = tbHuespedes;
	}

	public JTable getTbReservas() {
		return tbReserva;
	}

	public void setTbReservas(JTable tbReserva) {
		this.tbReserva = tbReserva;
	}

	public DefaultTableModel getModelo() {
		return modelo;
	}

	public void setModelo(DefaultTableModel modelo) {
		this.modelo = modelo;
	}

	public DefaultTableModel getModeloH() {
		return modeloH;
	}

	public void setModeloH(DefaultTableModel modeloH) {
		this.modeloH = modeloH;
	}

	//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
