/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.MedicoDAO;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import modelo.Medico;
import controlador.PacienteDAO;
import modelo.Paciente;


public class Inicio extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Inicio.class.getName());
    
    //iconos instanciados
    private ImageIcon iconEditar; 
    private ImageIcon iconEliminar; 
  
    public Inicio() {
        initComponents();
        
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
        int opcion = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea eliminar este médico?", "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION);

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
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
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
        jButton1.setText("Medicos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(0, 330, 300, 60);

        jButton2.setBackground(new java.awt.Color(91, 123, 140));
        jButton2.setText("Pacientes");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(0, 410, 300, 60);

        jButton3.setBackground(new java.awt.Color(91, 123, 140));
        jButton3.setText("Citas");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(0, 490, 300, 60);

        jButton4.setBackground(new java.awt.Color(91, 123, 140));
        jButton4.setText("Inicio");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(0, 250, 300, 60);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 300, 800);

        jPanel2.setBackground(new java.awt.Color(160, 164, 165));
        jPanel2.setLayout(new java.awt.CardLayout());

        jPanel4.setBackground(new java.awt.Color(20, 40, 51));
        jPanel4.setToolTipText("");
        jPanel4.setLayout(null);
        jPanel2.add(jPanel4, "inicio");

        jPanel3.setBackground(new java.awt.Color(20, 40, 51));
        jPanel3.setToolTipText("");
        jPanel3.setLayout(null);

        jTextField1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jTextField1.setToolTipText("");
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

        jButton7.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jButton7.setText("+");
        jButton7.setAutoscrolls(true);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7);
        jButton7.setBounds(990, 80, 70, 50);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Médicos");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(40, 30, 140, 30);

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

        jButton10.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jButton10.setText("+");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton10);
        jButton10.setBounds(990, 80, 70, 50);

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pacientes");
        jPanel5.add(jLabel2);
        jLabel2.setBounds(40, 30, 140, 30);

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

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField3);
        jTextField3.setBounds(40, 80, 930, 50);

        jButton13.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jButton13.setText("+");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton13);
        jButton13.setBounds(990, 80, 70, 50);

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Citas");
        jPanel6.add(jLabel3);
        jLabel3.setBounds(40, 30, 140, 30);

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
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
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
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
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
