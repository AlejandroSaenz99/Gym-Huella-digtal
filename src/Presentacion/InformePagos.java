/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Datos.ConexionBD;
import Puentes.PuenteAltas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author del.sistemas_res
 */
public class InformePagos extends javax.swing.JFrame {
String FECHAI="";
String FECHAF="";
    
Statement sentenciaSQL;
  
  ConexionBD con;
  public static PreparedStatement sentencia_preparada;
  
  public static ResultSet resultado;
  
  public static String url;
  
  public static int Resultado_numero = 0;
/**
     * Creates new form InformePagos
     */
    public InformePagos() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
      Fecha1.setDate(Fechaa());
      Fecha2.setDate(Fechaa());
        MostrarTabla();
        Total.setText(Float.toString(suma()));
    }
void MostrarTabla() {
    (new PuenteAltas()).PuenteMostrarTabla(this.jDatos);
  }
public float suma(){
    int contar=jDatos.getRowCount();
    float suma=0;
    for (int i=0; i < contar;i++){
        suma=suma+Float.parseFloat(jDatos.getValueAt(i, 4).toString());
    }
 return suma;}



     public static Date Fechaa() {
    Date FechaA = new Date();
    SimpleDateFormat FormatoFecha = new SimpleDateFormat("dd/MM/YYYY");
    return FechaA;
  } 
     
     
     
     public void PagosBetween(){
     try { if(Fecha1.getDate()==null&&Fecha2.getDate()==null){
    System.out.println("Valor nulo");
}
else{
try{
Date date=new Date();
       
     Date Variable =Fecha1.getDate(); 
   SimpleDateFormat FormatoFecha = new SimpleDateFormat("YYYY-MM-dd");
       // System.out.println(Variable);
FormatoFecha.format(Variable);
        //System.out.println(FormatoFecha.format(Variable));
        FECHAI="'"+FormatoFecha.format(Variable)+"'";
        System.out.println(FECHAI);
     
        Date date1=new Date();
       
     Date Variable1 =Fecha2.getDate(); 
   SimpleDateFormat FormatoFecha1 = new SimpleDateFormat("YYYY-MM-dd");
       // System.out.println(Variable);
FormatoFecha1.format(Variable1);
        //System.out.println(FormatoFecha.format(Variable));
        FECHAF="'"+FormatoFecha1.format(Variable1)+"'";
        System.out.println(FECHAF);
      
}
catch(Exception e){System.out.println(e);}}   
    // String sql = "SELECT * FROM pagos WHERE  Fecha Between '03-03-2022' AND '12-03-2022' ";
        String sql = "SELECT * FROM pagos WHERE  Fecha Between "+FECHAI+"AND"+ FECHAF+"";
      
      
       
           
   
  
// String sql = "SELECT * FROM asistencia WHERE Fecha=" + Fecha.getText();
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      ResultSetMetaData rsm = rs.getMetaData();
      int col = rsm.getColumnCount();
      DefaultTableModel modelo = new DefaultTableModel();
      for (int i = 1; i <= col; i++)
        modelo.addColumn(rsm.getColumnLabel(i)); 
      while (rs.next()) {
        String[] fila = new String[col];
        for (int j = 0; j < col; j++)
          fila[j] = rs.getString(j + 1); 
        modelo.addRow((Object[])fila);
      } 
      jDatos.setModel(modelo);
      rs.close();
      this.con.CerrarConexion();
       Total.setText(Float.toString(suma()));
    } catch(Exception e){System.out.println(e);}     
    
     }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usua = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDatos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        Total = new javax.swing.JLabel();
        Fecha2 = new com.toedter.calendar.JDateChooser();
        Fecha1 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        xd = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Informe Pagos");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Usuario:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        usua.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        usua.setForeground(new java.awt.Color(255, 255, 255));
        usua.setText("Usuario");
        getContentPane().add(usua, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 210, -1));

        jDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jDatos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 97, 983, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(688, 542, -1, -1));

        Total.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        Total.setForeground(new java.awt.Color(255, 255, 255));
        Total.setText("100");
        getContentPane().add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(758, 542, 98, -1));

        Fecha2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                Fecha2PropertyChange(evt);
            }
        });
        getContentPane().add(Fecha2, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 53, 202, -1));

        Fecha1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                Fecha1PropertyChange(evt);
            }
        });
        getContentPane().add(Fecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(393, 53, 202, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Fecha Inicial:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Fecha final:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(669, 53, -1, -1));

        jButton2.setText("Regresar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 550, -1, -1));

        xd.setText("Exportar");
        getContentPane().add(xd, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 550, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Gym3.jpg"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 1190, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Fecha1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_Fecha1PropertyChange
PagosBetween(); 
    }//GEN-LAST:event_Fecha1PropertyChange

    private void Fecha2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_Fecha2PropertyChange
PagosBetween();    // TODO add your handling code here:
    }//GEN-LAST:event_Fecha2PropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
Sistema lo=new Sistema();
lo.setVisible(true);
dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(InformePagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InformePagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InformePagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InformePagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InformePagos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Fecha1;
    private com.toedter.calendar.JDateChooser Fecha2;
    private javax.swing.JLabel Total;
    private javax.swing.JButton jButton2;
    public javax.swing.JTable jDatos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel usua;
    public javax.swing.JButton xd;
    // End of variables declaration//GEN-END:variables
}
