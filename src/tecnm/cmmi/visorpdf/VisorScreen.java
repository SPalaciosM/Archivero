/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnm.cmmi.visorpdf;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.sun.pdfview.*;
import javax.swing.*;
/**
 *
 * @author jesusgtz
 */
public class VisorScreen extends javax.swing.JFrame {
    
    PagePanel panelpdf;
    JFileChooser selector;
    PDFFile pdffile;
    int indice=0;

    /**
     * Creates new form VisorScreen
     */
    public VisorScreen() {
        super("Visualizacion de PDFs");
        panelpdf=new PagePanel();
        JMenuBar barra=new JMenuBar();
        JMenu archivo=new JMenu("Archivo");
        JMenuItem ver=new JMenuItem("Buscar Archivo");
        
        ver.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                indice=0;
                selector=new JFileChooser();
                int op=selector.showOpenDialog(VisorScreen.this);
                if(op==JFileChooser.APPROVE_OPTION){
                    try{
                        File file = selector.getSelectedFile();
                        RandomAccessFile raf = new RandomAccessFile(file, "r");
                        FileChannel channel = raf.getChannel();
                        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,0, channel.size());
                        pdffile = new PDFFile(buf);
                        PDFPage page = pdffile.getPage(indice);
                        panelpdf.showPage(page);
                        repaint();
                    } catch(IOException ioe) {
                        JOptionPane.showMessageDialog(null, ioe.getMessage());
                    }
                }
            }		
        });
        
        JPanel pabajo=new JPanel();
        JButton bsiguiente=new JButton("Siguiente");
        bsiguiente.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                indice++;
                PDFPage page = pdffile.getPage(indice);
                panelpdf.showPage(page);
            }
	});
		
        JButton banterior=new JButton("Anterior");
        banterior.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                indice--;
                PDFPage page = pdffile.getPage(indice);
                panelpdf.showPage(page);
            }
        });
		
        pabajo.add(banterior);
        pabajo.add(bsiguiente);
        archivo.add(ver);
        barra.add(archivo);
        setJMenuBar(barra);
        add(panelpdf);
        add(pabajo,BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(0, 0, 500, 500);
        setLocationRelativeTo(null);
    }
}
