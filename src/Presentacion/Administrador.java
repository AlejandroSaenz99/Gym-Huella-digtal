/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Datos.ConexionBD;
import Datos.DAltas;
import Metodos.Metodos_Altas;
import Metodos.Metodos_Pagos;
import Metodos.Metodos_sql;
import static Presentacion.Asistencias.Fechaa;
import Puentes.PuenteAltas;
import Puentes.PuenteUsuarios;
import java.awt.Image;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AlejandroSaenz
 */
public class Administrador extends javax.swing.JFrame {

    ConexionBD con;
    String FECHA = "";
    String FECHA1 = "";
    public static PreparedStatement sentencia_preparada;

    public static ResultSet resultado;

    public static String url;

    public static int Resultado_numero = 0;
    Statement sentenciaSQL;

    Metodos_sql Metodos = new Metodos_sql();
    Metodos_Altas Metodos4 = new Metodos_Altas();
    Metodos_Pagos Metodos1 = new Metodos_Pagos();
    String nombreimagen = "";

    /**
     * Creates new form Administrador
     */
    public Administrador() {
        initComponents();
        Fecha.setDate(Fechaa());

        this.txtId.setEnabled(false);
        this.txtId1.setEnabled(false);
        this.txtRegistradoPor.setEnabled(false);
        this.txtNombre1.setEnabled(false);
        this.txtApellidos1.setEnabled(false);
        this.txtFecha.setEnabled(false);
        //this.txtA.setEnabled(false);
        this.txtId2.setEnabled(false);
        this.txtF.setEnabled(false);

        setLocationRelativeTo(null);
        MostrarTabla();
        MostrarTabla2();
        MostrarTabla3();
    }
public void MostrarTA(){ if (Fecha.getDate() == null) {
            System.out.println("Valor nulo");
        } else {
            try {
                Date date = new Date();

                Date Variable = Fecha.getDate();
                SimpleDateFormat FormatoFecha = new SimpleDateFormat("dd/MM/YYYY");
                // System.out.println(Variable);
                FormatoFecha.format(Variable);
                //System.out.println(FormatoFecha.format(Variable));
                FECHA = "'" + FormatoFecha.format(Variable) + "'";

            } catch (Exception e) {
                System.out.println(e);
            }
            try {

                String sql = "SELECT * FROM asistencia WHERE Fecha=" + FECHA;

// String sql = "SELECT * FROM asistencia WHERE Fecha=" + Fecha.getText();
                this.con = new ConexionBD();
                this.sentenciaSQL = this.con.Conectarse().createStatement();
                ResultSet rs = this.sentenciaSQL.executeQuery(sql);
                ResultSetMetaData rsm = rs.getMetaData();
                int col = rsm.getColumnCount();
                DefaultTableModel modelo = new DefaultTableModel();
                for (int i = 1; i <= col; i++) {
                    modelo.addColumn(rsm.getColumnLabel(i));
                }
                while (rs.next()) {
                    String[] fila = new String[col];
                    for (int j = 0; j < col; j++) {
                        fila[j] = rs.getString(j + 1);
                    }
                    modelo.addRow((Object[]) fila);
                }
                TablaAsistencia.setModel(modelo);
                rs.close();
                this.con.CerrarConexion();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Clase no encontrada..." + ex);
            } catch (InstantiationException ex) {
                JOptionPane.showMessageDialog(null, "Error de conexion..." + ex);
            } catch (IllegalAccessException ex) {
                JOptionPane.showMessageDialog(null, "Acceso ilegal a la base de datos..." + ex);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error de sentencia..." + ex);
            }
        }}
    public void Limpiar() {
        this.txtId.setText("");
        this.txtNombre.setText("");
        this.txtApellidos.setText("");
        this.txtUsuario.setText("");
        this.txtContrase.setText("");
    }

    void MostrarTabla() {
        (new PuenteUsuarios()).PuenteMostrarTabla(this.TablaDatos2);
    }

    void MostrarTabla2() {
        (new PuenteAltas()).PuenteMostrarTabla2(this.TablaDatos);
    }

    void MostrarTabla3() {
        (new PuenteAltas()).PuenteMostrarTabla(this.TablaDatosP);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaDatos2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtContrase = new javax.swing.JPasswordField();
        txtApellidos = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        txtFecha = new javax.swing.JTextField();
        txtRegistradoPor = new javax.swing.JTextField();
        txtApellidos1 = new javax.swing.JTextField();
        txtNombre1 = new javax.swing.JTextField();
        txtId1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbImagen = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaDatosP = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        txtId2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtF = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaAsistencia = new javax.swing.JTable();
        Fecha = new com.toedter.calendar.JDateChooser();
        LID = new javax.swing.JLabel();
        eliminar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar1.setRollover(true);

        jLabel1.setText("Administrador");
        jToolBar1.add(jLabel1);
        jToolBar1.add(jSeparator1);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Salir_16.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jToolBar1.add(jLabel2);

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1429, 25));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaDatos2.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaDatos2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDatos2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaDatos2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 895, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Id:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 37, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nombre:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 77, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Apellidos:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 123, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Usuario:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 173, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Contraseña:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 226, -1, -1));
        jPanel1.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 39, 209, -1));
        jPanel1.add(txtContrase, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 228, 209, -1));
        jPanel1.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 125, 209, -1));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 92, 209, -1));
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 175, 209, -1));

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jButton1.setText("Actualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 292, -1, -1));

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 361, 95, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Gym3.jpg"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 600));

        jTabbedPane1.addTab("Usuarios", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDatosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablaDatos);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 690, -1));
        jPanel2.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 139, 363, -1));
        jPanel2.add(txtRegistradoPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 187, 363, -1));
        jPanel2.add(txtApellidos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 99, 363, -1));
        jPanel2.add(txtNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 58, 363, -1));
        jPanel2.add(txtId1, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 20, 363, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Id:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 23, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nombre:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 61, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Apellidos:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 102, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Fecha de ingreso:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 142, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Dado de alta por:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 190, -1, -1));

        lbImagen.setBorder(javax.swing.BorderFactory.createTitledBorder("Fotografia"));
        jPanel2.add(lbImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 342, 185));

        jButton3.setText("Actualizar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 301, 89, -1));

        jButton4.setText("Cargar foto");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 251, -1, -1));

        jButton5.setText("Eliminar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 351, 89, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Gym3.jpg"))); // NOI18N
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 600));

        jTabbedPane1.addTab("Clientes", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaDatosP.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaDatosP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDatosPMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TablaDatosP);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 28, 841, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Id:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        txtId2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtId2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtId2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Folio:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        txtF.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtF.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, -1, -1));

        jButton6.setText("Eliminar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 28, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo2.jpg"))); // NOI18N
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 600));

        jTabbedPane1.addTab("Pagos", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaAsistencia.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaAsistencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaAsistenciaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaAsistencia);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(462, 28, 801, -1));

        Fecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FechaPropertyChange(evt);
            }
        });
        jPanel4.add(Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 28, 158, -1));

        LID.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        LID.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(LID, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 28, -1, -1));

        eliminar.setText("Eliminar TODAS las asistencias");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        jPanel4.add(eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 460, -1, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Gym3.jpg"))); // NOI18N
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1350, 600));

        jTabbedPane1.addTab("Asistencias", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 31, 1398, 627));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        Login lo = new Login();
        lo.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void TablaDatos2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDatos2MouseClicked
        if (evt.getButton() == 1) {
            int fila = this.TablaDatos2.getSelectedRow();
            this.txtId.setText(this.TablaDatos2.getValueAt(fila, 0).toString());
            this.txtNombre.setText(this.TablaDatos2.getValueAt(fila, 1).toString());
            this.txtApellidos.setText(this.TablaDatos2.getValueAt(fila, 2).toString());
            this.txtUsuario.setText(this.TablaDatos2.getValueAt(fila, 3).toString());
            // this.txtContrase.setText(this.TablaDatos2.getValueAt(fila, 4).toString());
        }         // TODO add your handling code here:
    }//GEN-LAST:event_TablaDatos2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (this.txtId.getText().isEmpty() || this.txtNombre.getText().isEmpty() || this.txtApellidos.getText().isEmpty() || this.txtUsuario.getText().isEmpty() || this.txtContrase.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "llenar todos los datos", "falta de datos", 1);
        } else {
            this.Metodos.Actualizar(Integer.parseInt(this.txtId.getText()), this.txtNombre.getText(), this.txtApellidos.getText(), this.txtUsuario.getText(), this.txtContrase.getText());
            MostrarTabla();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (this.txtId.getText().isEmpty() || this.txtNombre.getText().isEmpty() || this.txtApellidos.getText().isEmpty() || this.txtUsuario.getText().isEmpty() || this.txtContrase.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "llenar todos los datos", "falta de datos", 1);
        } else {
            this.Metodos.Eliminar(Integer.parseInt(this.txtId.getText()), this.txtNombre.getText(), this.txtApellidos.getText(), this.txtUsuario.getText(), this.txtContrase.getText());
            Limpiar();
            MostrarTabla();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void TablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDatosMouseClicked
        if (evt.getButton() == 1) {
            int fila = this.TablaDatos.getSelectedRow();
            this.txtId1.setText(this.TablaDatos.getValueAt(fila, 0).toString());
            this.txtNombre1.setText(this.TablaDatos.getValueAt(fila, 1).toString());
            this.txtApellidos1.setText(this.TablaDatos.getValueAt(fila, 2).toString());
            this.txtFecha.setText(this.TablaDatos.getValueAt(fila, 3).toString());
            this.txtRegistradoPor.setText(this.TablaDatos.getValueAt(fila, 4).toString());
            int clv = Integer.parseInt(this.txtId1.getText());
            ImageIcon img = (new PuenteAltas()).puenteObtenerImagen(clv);
            Icon icono = new ImageIcon(img.getImage().getScaledInstance(this.lbImagen.getWidth(), this.lbImagen.getHeight(), 1));
            if (img != null) {
                this.lbImagen.setIcon(icono);
            }
        }          // TODO add your handling code here:
    }//GEN-LAST:event_TablaDatosMouseClicked

    private void TablaDatosPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDatosPMouseClicked
        if (evt.getButton() == 1) {
            int fila = this.TablaDatosP.getSelectedRow();
            this.txtId2.setText(this.TablaDatosP.getValueAt(fila, 0).toString());
            this.txtF.setText(this.TablaDatosP.getValueAt(fila, 1).toString());
        }
    }//GEN-LAST:event_TablaDatosPMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
try{
        DAltas prod = new DAltas();
        prod.setId(Integer.parseInt(this.txtId1.getText()));
        prod.setNombre(this.txtNombre1.getText());
        prod.setApellidos(this.txtApellidos1.getText());
        prod.setFecha(this.txtFecha.getText());
        prod.setRegistrado_Por(this.txtRegistradoPor.getText());
        prod.setFecha(this.txtFecha.getText());
        prod.setImagen(this.nombreimagen);
        (new PuenteAltas()).PuenteActualizar(prod);
        //Limpiar2();
        MostrarTabla2();}catch(Exception e){System.out.println(e);}
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG, PNG & GIF Images", "jpg", "gif", "png");
        JFileChooser archivo = new JFileChooser();
        archivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        archivo.setFileFilter(filtro);
        archivo.setDialogTitle("Abrir archivo de imagen");
        File ruta = new File("C:/Imagenes Clientes/");
        archivo.setCurrentDirectory(ruta);
        int resultado = archivo.showOpenDialog(this);
        if (resultado != JFileChooser.CANCEL_OPTION) {
            File nombreArchivo = archivo.getSelectedFile();
            nombreimagen = String.valueOf(nombreArchivo);
            Image foto = getToolkit().getImage(nombreimagen);
            foto = foto.getScaledInstance(lbImagen.getWidth(), lbImagen.getHeight(), Image.SCALE_DEFAULT);
            lbImagen.setIcon(new ImageIcon(foto));
        }          // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (this.txtId1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro");
        } else {
            this.Metodos4.Eliminar(Integer.parseInt(this.txtId1.getText()));
            this.Metodos4.EliminarHuella(Integer.parseInt(this.txtId1.getText()));
            MostrarTabla2();
            txtId1.setText("");
            txtNombre1.setText("");
            txtApellidos1.setText("");
            txtFecha.setText("");
            txtRegistradoPor.setText("");
            lbImagen.setIcon(null);

        }         // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (this.txtId2.getText().isEmpty() || this.txtF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro");
        } else {
            this.Metodos1.Eliminar(Integer.parseInt(this.txtId2.getText()), Integer.parseInt(this.txtF.getText()));
            MostrarTabla3();
            txtId2.setText("");
            txtF.setText("");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void FechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FechaPropertyChange
        if (Fecha.getDate() == null) {
            System.out.println("Valor nulo");
        } else {
            try {
                Date date = new Date();

                Date Variable = Fecha.getDate();
                SimpleDateFormat FormatoFecha = new SimpleDateFormat("dd/MM/YYYY");
                // System.out.println(Variable);
                FormatoFecha.format(Variable);
                //System.out.println(FormatoFecha.format(Variable));
                FECHA = "'" + FormatoFecha.format(Variable) + "'";

            } catch (Exception e) {
                System.out.println(e);
            }
            try {

                String sql = "SELECT * FROM asistencia WHERE Fecha=" + FECHA;

// String sql = "SELECT * FROM asistencia WHERE Fecha=" + Fecha.getText();
                this.con = new ConexionBD();
                this.sentenciaSQL = this.con.Conectarse().createStatement();
                ResultSet rs = this.sentenciaSQL.executeQuery(sql);
                ResultSetMetaData rsm = rs.getMetaData();
                int col = rsm.getColumnCount();
                DefaultTableModel modelo = new DefaultTableModel();
                for (int i = 1; i <= col; i++) {
                    modelo.addColumn(rsm.getColumnLabel(i));
                }
                while (rs.next()) {
                    String[] fila = new String[col];
                    for (int j = 0; j < col; j++) {
                        fila[j] = rs.getString(j + 1);
                    }
                    modelo.addRow((Object[]) fila);
                }
                TablaAsistencia.setModel(modelo);
                rs.close();
                this.con.CerrarConexion();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Clase no encontrada..." + ex);
            } catch (InstantiationException ex) {
                JOptionPane.showMessageDialog(null, "Error de conexion..." + ex);
            } catch (IllegalAccessException ex) {
                JOptionPane.showMessageDialog(null, "Acceso ilegal a la base de datos..." + ex);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error de sentencia..." + ex);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_FechaPropertyChange

    private void TablaAsistenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaAsistenciaMouseClicked
        if (evt.getButton() == 1) {
            int fila = TablaAsistencia.getSelectedRow();

            this.LID.setText(TablaAsistencia.getValueAt(fila, 1).toString());

        }         // TODO add your handling code here:
    }//GEN-LAST:event_TablaAsistenciaMouseClicked

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed

            try {
                String sql = "DELETE FROM Asistencia";
                this.con = new ConexionBD();
                this.sentenciaSQL = this.con.Conectarse().createStatement();
                int n = this.sentenciaSQL.executeUpdate(sql);
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado: ", "Confimaci", 1);
                }
                this.con.CerrarConexion();
                this.sentenciaSQL.close();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error 1: " + ex);
            } catch (InstantiationException ex) {
                JOptionPane.showMessageDialog(null, "Error 2: " + ex);
            } catch (IllegalAccessException ex) {
                JOptionPane.showMessageDialog(null, "Error 3: " + ex);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error 4: " + ex);
            }
            MostrarTA();
        
        // TODO add your handling code here:
    }//GEN-LAST:event_eliminarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Administrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Fecha;
    private javax.swing.JLabel LID;
    private javax.swing.JTable TablaAsistencia;
    private javax.swing.JTable TablaDatos;
    private javax.swing.JTable TablaDatos2;
    private javax.swing.JTable TablaDatosP;
    private javax.swing.JButton eliminar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbImagen;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtApellidos1;
    private javax.swing.JPasswordField txtContrase;
    private javax.swing.JLabel txtF;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtId1;
    private javax.swing.JLabel txtId2;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtRegistradoPor;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
