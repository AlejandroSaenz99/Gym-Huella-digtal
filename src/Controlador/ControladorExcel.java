/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import Presentacion.Sistema;
import Modelo.ModeloExcel;
import Presentacion.InformePagos;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ControladorExcel implements ActionListener{
    ModeloExcel modeloE = new ModeloExcel();
    InformePagos vistaE= new InformePagos();
    JFileChooser selecArchivo = new JFileChooser();
    File archivo,Archivo2;
    int contAccion=0;
    
    public ControladorExcel(InformePagos vistaE, ModeloExcel modeloE){
        this.vistaE= vistaE;
        this.modeloE=modeloE;
        //this.vistaE.btnImportar.addActionListener(this);
        this.vistaE.xd.addActionListener(this);
        
    }
    
    public void AgregarFiltro(){
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xls)", "xls"));
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        contAccion++;
        if(contAccion==1)AgregarFiltro();
        
        
        
        if(e.getSource() == vistaE.xd){
            if(selecArchivo.showDialog(null, "Exportar")==JFileChooser.APPROVE_OPTION){
                archivo=selecArchivo.getSelectedFile();
                if(archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx")){

                    JOptionPane.showMessageDialog(null, modeloE.Exportar(archivo, vistaE.jDatos) + "\n Formato ."+ archivo.getName().substring(archivo.getName().lastIndexOf(".")+1));

                }else{
                    JOptionPane.showMessageDialog(null, "add \".xls\" or \".xlsx\" to the end of the file name.");
                }
            }
        }
    }
    
}


