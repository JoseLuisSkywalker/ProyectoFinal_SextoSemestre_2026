/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import Reportes.ReportePacientesPDF;
import conexion.ConexionBD;
import controlador.MedicoDAO;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import modelo.Medico;
import controlador.PacienteDAO;
import modelo.Paciente;
import controlador.CitaDAO;
import controlador.EstadisticaDAO;
import java.sql.ResultSet;
import modelo.Cita;
import modelo.EstadisticaCita;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;





public class Inicio extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Inicio.class.getName());
    
    //iconos instanciados
    private ImageIcon iconEditar; 
    private ImageIcon iconEliminar; 
    
    public Inicio() {
        initComponents();
        setTitle("Wellmeadows Hospital"); 
        
        
        //cambios estéticos
        iconEditar = new ImageIcon(getClass().getResource("/iconos/editar.png"));

        iconEliminar = new ImageIcon(getClass().getResource("/iconos/eliminar.png"));
        
        iconEditar = escalarIcono(new ImageIcon(getClass().getResource("/iconos/editar.png")),45, 45);

        iconEliminar = escalarIcono(new ImageIcon(getClass().getResource("/iconos/eliminar.png")),45, 45);
        
        //para la ventana
        setSize(1400, 800); 
        setResizable(false); 
        setLocationRelativeTo(null);
             
        //para la tabla 
        
        cargarTablaMedicos();
        cargarTablaPacientes();
        cargarTablaCitas();
        
        setVisible(true);
        
    }
/*
    ===========================================≠≠≠≠≠≠≠≠≠≠≠≠≠=============================================
    ----------------------------------- METODOS PARA MEDICOS --------------------------------------------
    =====================================================================================================
    */
    private void cargarTablaMedicos() {

        DefaultTableModel modelo = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int row, int column) {

            return column == 6 || column == 7;
        }
    };

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Departamento");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("M"); 
        modelo.addColumn("E"); 

        MedicoDAO medicoDAO = new MedicoDAO();

        List<Medico> listaMedicos = medicoDAO.obtenerTodos();

        for (Medico medico : listaMedicos) {

            modelo.addRow(new Object[]{
                medico.getIdMedico(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getNumeroDepartamento(),
                medico.getDireccion(),
                medico.getTelefono(),
                "M", 
                "E"
            });
        }
        
        medicoDAO.cerrarConexion();
        jTable1.setModel(modelo);
        configurarTabla(); 
       
        
    }
    
    private void buscarMedicos() {

        String texto = jTextField1.getText().trim();

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Departamento");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("M");
        modelo.addColumn("E");

        MedicoDAO medicoDAO = new MedicoDAO();

        List<Medico> lista = medicoDAO.buscar(texto);

        for (Medico medico : lista) {

            modelo.addRow(new Object[]{
                medico.getIdMedico(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getNumeroDepartamento(),
                medico.getDireccion(),
                medico.getTelefono(),
                "M",
                "E"
            });
        }
        
        medicoDAO.cerrarConexion();
        jTable1.setModel(modelo);
        
        // esto es para los botones que estan en la tabla
        configurarTabla();
        
       
    }
    
    // para eliminar el médico utilizando id_medico
    public void eliminarMedico(int fila) {
        int opcion = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea eliminar este médico?, Haciendo esto elmina a sus pacientes y sus citas asignadas en cascada", "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION);

        if (opcion != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        int idMedico = Integer.parseInt(
                jTable1.getValueAt(fila, 0).toString()
        );

        MedicoDAO dao = new MedicoDAO();
        dao.eliminar(idMedico);
        dao.cerrarConexion();

        cargarTablaMedicos();
    }
    
    
    public void modificarMedico(int fila) {

        int idMedico = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());

        String nombre = jTable1.getValueAt(fila, 1).toString();

        String apellido = jTable1.getValueAt(fila, 2).toString();

        int departamento = Integer.parseInt(jTable1.getValueAt(fila, 3).toString());

        String direccion = jTable1.getValueAt(fila, 4).toString();

        String telefono = jTable1.getValueAt(fila, 5).toString();

        Medico medico = new Medico(
                idMedico,
                nombre,
                apellido,
                departamento,
                direccion,
                telefono
        );

        DialogModificarMedico dialog = new DialogModificarMedico(this, true, medico);

        dialog.setVisible(true);

        cargarTablaMedicos();
    }
    
    
    //métodos estéticos: 
    private ImageIcon escalarIcono(ImageIcon icono, int ancho, int alto) {

    java.awt.Image imagen = icono.getImage();

    java.awt.Image imagenEscalada = imagen.getScaledInstance(
            ancho,
            alto,
            java.awt.Image.SCALE_SMOOTH
    );

    return new ImageIcon(imagenEscalada);
    }
    
    
    private void configurarTabla() {

        jTable1.setRowHeight(30);

        jTable1.getColumn("M").setCellRenderer(new ButtonRenderer(iconEditar, new Color(52, 152, 219)));

        jTable1.getColumn("E").setCellRenderer(new ButtonRenderer(iconEliminar, new Color(231, 76, 60)));

        jTable1.getColumn("M").setMaxWidth(35);
        jTable1.getColumn("E").setMaxWidth(35);

        jTable1.getColumn("M").setMinWidth(35);
        jTable1.getColumn("E").setMinWidth(35);
        
        
        jTable1.getColumn("M").setCellEditor(new ButtonEditor(iconEditar,new Color(52, 152, 219),e -> {
            int fila = jTable1.getSelectedRow();
            modificarMedico(fila);
        }));

        jTable1.getColumn("E").setCellEditor(new ButtonEditor(iconEliminar,new Color(231, 76, 60),e -> {
            int fila = jTable1.getSelectedRow();
            eliminarMedico(fila);
        }));
       
    }
    
/*
    ================================================================================================================
    ------------------------------------------ METODOS PARA PACIENTES ----------------------------------------------
    ================================================================================================================
    */
    
    private void cargarTablaPacientes() {

        DefaultTableModel modelo = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {

                return column == 8 || column == 9;
            }

        };

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Nacimiento");
        modelo.addColumn("Sexo");
        modelo.addColumn("Estado Civil");
        modelo.addColumn("ID Médico");
        modelo.addColumn("M");
        modelo.addColumn("E");

        PacienteDAO pacienteDAO = new PacienteDAO();
        List<Paciente> listaPacientes = pacienteDAO.obtenerTodos();
        for (Paciente paciente : listaPacientes) {

            modelo.addRow(new Object[]{
                paciente.getIdPaciente(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getTelefono(),
                paciente.getFechaNacimiento(),
                paciente.getSexo(),
                paciente.getEstadoCivil(),
                paciente.getIdMedico(),
                "M",
                "E"
            });

        }

        pacienteDAO.cerrarConexion();
        jTable2.setModel(modelo);
        configurarTablaPacientes();

    }
    
    private void buscarPacientes() {

        String texto = jTextField2.getText().trim();

        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Nacimiento");
        modelo.addColumn("Sexo");
        modelo.addColumn("Estado Civil");
        modelo.addColumn("ID Médico");
        modelo.addColumn("M");
        modelo.addColumn("E");

        PacienteDAO pacienteDAO = new PacienteDAO();

        List<Paciente> lista = pacienteDAO.buscar(texto);

        for (Paciente paciente : lista) {
            modelo.addRow(new Object[] {
                
                paciente.getIdPaciente(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getTelefono(),
                paciente.getFechaNacimiento(),
                paciente.getSexo(),
                paciente.getEstadoCivil(),
                paciente.getIdMedico(),
                "M",
                "E"

            });

        }

        pacienteDAO.cerrarConexion();

        jTable2.setModel(modelo);

        configurarTablaPacientes();

    }
    
    public void eliminarPaciente(int fila) {
        int opcion = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea eliminar este paciente?", "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION);

        if (opcion != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        int idPaciente = Integer.parseInt(jTable2.getValueAt(fila, 0).toString());

        PacienteDAO dao = new PacienteDAO();
        dao.eliminar(idPaciente);
        dao.cerrarConexion();
        cargarTablaPacientes();
        
    }
    
    public void modificarPaciente(int fila) {

        int idPaciente = Integer.parseInt(jTable2.getValueAt(fila, 0).toString());
        PacienteDAO dao = new PacienteDAO();
        Paciente paciente = dao.buscarPorId(idPaciente);
        dao.cerrarConexion();

        if (paciente != null) {

            DialogModificarPaciente dialog = new DialogModificarPaciente(this, true, paciente);

            dialog.setVisible(true);

            cargarTablaPacientes();
        }
    }
    
    
    // diseño y forma de la tabla para los botonsitos 
    private void configurarTablaPacientes() {

        jTable2.setRowHeight(30);
        jTable2.getColumn("M").setCellRenderer(new ButtonRenderer(iconEditar, new Color(52, 152, 219)));
        jTable2.getColumn("E").setCellRenderer(new ButtonRenderer(iconEliminar, new Color(231, 76, 60)));
        jTable2.getColumn("M").setMaxWidth(35);
        jTable2.getColumn("E").setMaxWidth(35);
        jTable2.getColumn("M").setMinWidth(35);
        jTable2.getColumn("E").setMinWidth(35);
        jTable2.getColumn("M").setCellEditor(new ButtonEditor(iconEditar, new Color(52, 152, 219), e -> {
            int fila = jTable2.getSelectedRow();
            modificarPaciente(fila);
        }));

        jTable2.getColumn("E").setCellEditor(new ButtonEditor(iconEliminar, new Color(231, 76, 60), e -> {
            int fila = jTable2.getSelectedRow();
            eliminarPaciente(fila);
        }));

    }
    
/*
    ===========================================≠≠≠≠≠≠≠≠≠≠≠≠≠=============================================
    ----------------------------------- METODOS PARA MEDICOS --------------------------------------------
    =====================================================================================================
    */    
    
    private void cargarTablaCitas() {

        DefaultTableModel modelo = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                    return column == 6 || column == 7;

                }

            };

            modelo.addColumn("No.");
            modelo.addColumn("ID Paciente");
            modelo.addColumn("ID Médico");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            modelo.addColumn("Habitación");
            modelo.addColumn("M");
            modelo.addColumn("E");

            CitaDAO citaDAO = new CitaDAO();

            List<Cita> listaCitas = citaDAO.obtenerTodos();

            for (Cita cita : listaCitas) {

                modelo.addRow(new Object[]{

                    cita.getNumCita(),
                    cita.getIdPaciente(),
                    cita.getIdMedico(),
                    cita.getFechaCita(),
                    cita.getHoraCita(),
                    cita.getNumHabitacion(),
                    "M",
                    "E"

                });
        
            }

            citaDAO.cerrarConexion();

            jTable3.setModel(modelo);

            configurarTablaCitas();

    }
    
    private void buscarCitas() {

        String texto = jTextField3.getText().trim();

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("No.");
        modelo.addColumn("ID Paciente");
        modelo.addColumn("ID Médico");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora");
        modelo.addColumn("Habitación");
        modelo.addColumn("M");
        modelo.addColumn("E");

        CitaDAO citaDAO = new CitaDAO();

        List<Cita> lista = citaDAO.buscar(texto);
        
        for (Cita cita : lista) {

            modelo.addRow(new Object[]{

                cita.getNumCita(),
                cita.getIdPaciente(),
                cita.getIdMedico(),
                cita.getFechaCita(),
                cita.getHoraCita(),
                cita.getNumHabitacion(),
                "M",
                "E"

            });

        }

        citaDAO.cerrarConexion();

        jTable3.setModel(modelo);

        configurarTablaCitas();

    }
    
    public void eliminarCita(int fila) {

        int opcion = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar esta cita?",
                "Confirmar eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (opcion != javax.swing.JOptionPane.YES_OPTION) {

            return;
        }

        int numCita = Integer.parseInt(
                jTable3.getValueAt(fila, 0).toString()
        );

        CitaDAO dao = new CitaDAO();
        dao.eliminar(numCita);
        dao.cerrarConexion();
        cargarTablaCitas();

    }
    
    public void modificarCita(int fila) {

        int numCita = Integer.parseInt(
            jTable3.getValueAt(fila, 0).toString()
        );

        CitaDAO dao = new CitaDAO();

        Cita cita = dao.buscarPorId(numCita);
        
        dao.cerrarConexion();

        if (cita != null) {

            DialogModificarCita dialog = new DialogModificarCita(this, true, cita);

            dialog.setVisible(true);

            cargarTablaCitas();
        }

    }
    
    
    private void configurarTablaCitas() {

        jTable3.setRowHeight(30);

        jTable3.getColumn("M").setCellRenderer(new ButtonRenderer(iconEditar, new Color(52, 152, 219)));

        jTable3.getColumn("E").setCellRenderer(new ButtonRenderer(iconEliminar, new Color(231, 76, 60)));

        jTable3.getColumn("M").setMaxWidth(35);
        jTable3.getColumn("E").setMaxWidth(35);

        jTable3.getColumn("M").setMinWidth(35);
        jTable3.getColumn("E").setMinWidth(35);

        jTable3.getColumn("M").setCellEditor(
            new ButtonEditor(iconEditar, new Color(52, 152, 219), e -> {
                int fila = jTable3.getSelectedRow();
                modificarCita(fila);
            }));

        jTable3.getColumn("E").setCellEditor(
            new ButtonEditor(iconEliminar, new Color(231, 76, 60), (var e) -> {
                int fila = jTable3.getSelectedRow();
                eliminarCita(fila);
            }));

    }
    
    
    /*
    ================================================================================================================
    ------------------------------------------ METODOS PARA GRAFICAS DASHBOARD -------------------------------------
    ================================================================================================================
    */
    private void mostrarGrafica() {

        EstadisticaDAO dao = new EstadisticaDAO();

        List<EstadisticaCita> lista =
                dao.obtenerCitasPorMes();

        DefaultCategoryDataset dataset =
            new DefaultCategoryDataset();

        for (EstadisticaCita e : lista) {

            String etiqueta =
                    e.getMes() + "/" + e.getAnio();

            dataset.addValue(
                e.getTotalCitas(),
                    "Citas",
                    etiqueta
            );
        }

        JFreeChart chart =
                ChartFactory.createBarChart(
                        "Cantidad de Citas por Mes",
                        "Mes",
                        "Número de Citas",
                        dataset
                );

        ChartFrame frame =
                new ChartFrame(
                        "Estadísticas de Citas",
                        chart
                );

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(91, 140, 165));
        setBounds(new java.awt.Rectangle(0, 0, 1400, 800));
        setResizable(false);
        setSize(new java.awt.Dimension(1400, 800));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(35, 71, 89));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 800));
        jPanel1.setLayout(null);

        jButton1.setBackground(new java.awt.Color(91, 123, 140));
        jButton1.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 36)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Medico.png"))); // NOI18N
        jButton1.setText("   Medicos");
        jButton1.setBorder(null);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(0, 390, 300, 60);

        jButton2.setBackground(new java.awt.Color(91, 123, 140));
        jButton2.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 36)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Paciente.png"))); // NOI18N
        jButton2.setText("   Pacientes");
        jButton2.setToolTipText("");
        jButton2.setBorder(null);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(0, 470, 300, 60);

        jButton3.setBackground(new java.awt.Color(91, 123, 140));
        jButton3.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 36)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cita.png"))); // NOI18N
        jButton3.setText("   Citas");
        jButton3.setBorder(null);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(0, 550, 300, 60);

        jButton4.setBackground(new java.awt.Color(91, 123, 140));
        jButton4.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 36)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Dashboard.png"))); // NOI18N
        jButton4.setText("   Inicio");
        jButton4.setBorder(null);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(0, 310, 300, 60);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png"))); // NOI18N
        jLabel4.setToolTipText("");
        jLabel4.setMaximumSize(new java.awt.Dimension(300, 300));
        jLabel4.setMinimumSize(new java.awt.Dimension(300, 300));
        jPanel1.add(jLabel4);
        jLabel4.setBounds(50, 20, 230, 220);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 300, 800);

        jPanel2.setBackground(new java.awt.Color(160, 164, 165));
        jPanel2.setLayout(new java.awt.CardLayout());

        jPanel4.setBackground(new java.awt.Color(20, 40, 51));
        jPanel4.setToolTipText("");
        jPanel4.setLayout(null);

        jPanel7.setBackground(new java.awt.Color(6, 27, 36));
        jPanel7.setLayout(null);

        jButton5.setBackground(new java.awt.Color(91, 123, 140));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Grafica.png"))); // NOI18N
        jButton5.setToolTipText("Generar Gráfica");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 71, 89), 25));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton5);
        jButton5.setBounds(20, 20, 440, 290);

        jButton6.setBackground(new java.awt.Color(91, 123, 140));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Report.png"))); // NOI18N
        jButton6.setToolTipText("Generar Reporte");
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 71, 89), 25));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton6);
        jButton6.setBounds(480, 20, 440, 290);

        jButton8.setBackground(new java.awt.Color(91, 123, 140));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ConsultaRapida.png"))); // NOI18N
        jButton8.setToolTipText("Busqueda Con Índices");
        jButton8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 71, 89), 25));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton8);
        jButton8.setBounds(20, 330, 660, 290);

        jButton9.setBackground(new java.awt.Color(91, 123, 140));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Calendario.png"))); // NOI18N
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 71, 89), 25));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton9);
        jButton9.setBounds(700, 330, 220, 290);

        jPanel4.add(jPanel7);
        jPanel7.setBounds(80, 80, 940, 640);

        jLabel5.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 24)); // NOI18N
        jLabel5.setText("Dashboard");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(470, 20, 141, 40);

        jPanel2.add(jPanel4, "inicio");

        jPanel3.setBackground(new java.awt.Color(20, 40, 51));
        jPanel3.setToolTipText("");
        jPanel3.setLayout(null);

        jTextField1.setBackground(new java.awt.Color(10, 20, 25));
        jTextField1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jTextField1.setToolTipText("");
        jTextField1.setBorder(null);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel3.add(jTextField1);
        jTextField1.setBounds(40, 80, 930, 50);

        jButton7.setBackground(new java.awt.Color(10, 20, 25));
        jButton7.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jButton7.setText("+");
        jButton7.setAutoscrolls(true);
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7);
        jButton7.setBounds(990, 80, 70, 50);

        jLabel1.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Médicos");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(480, 20, 110, 40);

        jTable1.setBackground(new java.awt.Color(10, 20, 25));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(40, 160, 1020, 590);

        jPanel2.add(jPanel3, "medicos");

        jPanel5.setBackground(new java.awt.Color(20, 40, 51));
        jPanel5.setToolTipText("Buscar Pacientes");
        jPanel5.setLayout(null);

        jTextField2.setBackground(new java.awt.Color(10, 20, 25));
        jTextField2.setBorder(null);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jPanel5.add(jTextField2);
        jTextField2.setBounds(40, 80, 930, 50);

        jButton10.setBackground(new java.awt.Color(10, 20, 25));
        jButton10.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jButton10.setText("+");
        jButton10.setBorder(null);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton10);
        jButton10.setBounds(990, 80, 70, 50);

        jLabel2.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pacientes");
        jPanel5.add(jLabel2);
        jLabel2.setBounds(470, 20, 130, 40);

        jTable2.setBackground(new java.awt.Color(10, 20, 25));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel5.add(jScrollPane2);
        jScrollPane2.setBounds(40, 160, 1020, 590);

        jPanel2.add(jPanel5, "pacientes");

        jPanel6.setBackground(new java.awt.Color(20, 40, 51));
        jPanel6.setToolTipText("Buscar Citas");
        jPanel6.setLayout(null);

        jTextField3.setBackground(new java.awt.Color(10, 20, 25));
        jTextField3.setBorder(null);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });
        jPanel6.add(jTextField3);
        jTextField3.setBounds(40, 80, 930, 50);

        jButton13.setBackground(new java.awt.Color(10, 20, 25));
        jButton13.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jButton13.setText("+");
        jButton13.setBorder(null);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton13);
        jButton13.setBounds(990, 80, 70, 50);

        jLabel3.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Citas");
        jPanel6.add(jLabel3);
        jLabel3.setBounds(500, 20, 70, 40);

        jTable3.setBackground(new java.awt.Color(10, 20, 25));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jPanel6.add(jScrollPane3);
        jScrollPane3.setBounds(40, 160, 1020, 590);

        jPanel2.add(jPanel6, "citas");

        getContentPane().add(jPanel2);
        jPanel2.setBounds(300, 0, 1110, 800);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, "medicos");
        System.out.println("Switched to medicos");
        
        cargarTablaMedicos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, "pacientes");
        System.out.println("Switched to pacientes");
        
        cargarTablaPacientes();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, "citas");
        System.out.println("Switched to citas");
        
        cargarTablaCitas();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        DialogAnadirMedico dialog = new DialogAnadirMedico(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true); 
      
        cargarTablaMedicos();
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        
        DialogAnadirPaciente dialog = new DialogAnadirPaciente(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        cargarTablaPacientes();
        
        cargarTablaCitas();
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
        buscarCitas();
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        DialogAnadirCita dialog = new DialogAnadirCita(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        cargarTablaCitas();
        
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        buscarMedicos(); 
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) jPanel2.getLayout();
        System.out.println(cl);
        cl.show(jPanel2, "inicio");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        buscarPacientes();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //botton para la gráfica
        mostrarGrafica();
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here
        //este es el boton para el view con joins y con uso de indices!!!!

        DialogVistaCitas dialog = new DialogVistaCitas(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        ReportePacientesPDF.generarReporte();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        DialogVistaParticion dialog = new DialogVistaParticion(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(rootPaneCheckingEnabled);
        
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        buscarCitas();
    }//GEN-LAST:event_jTextField3KeyReleased

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
