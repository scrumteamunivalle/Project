/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author GekkoHKD
 */
public class VistaUsuario extends javax.swing.JFrame {

    /**
     * Creates new form VistaVisitante
     */
    public VistaUsuario() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Renovation - Software");
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Icon.png")).getImage());
        JOptionPane.showMessageDialog(rootPane, "Bienvenido a nuestro sistema de consultas");
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenuIntegrante = new javax.swing.JMenu();
        jMenuItemConsultaIntegrante = new javax.swing.JMenuItem();
        jMenuActividades = new javax.swing.JMenu();
        jMenuItemConsultaActividad = new javax.swing.JMenuItem();
        jMenuZonasVeredales = new javax.swing.JMenu();
        jMenuItemConsultaZona = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );

        jMenuIntegrante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cliente.png"))); // NOI18N
        jMenuIntegrante.setText("Integrante");

        jMenuItemConsultaIntegrante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/consultarCliente.png"))); // NOI18N
        jMenuItemConsultaIntegrante.setText("Consulta");
        jMenuIntegrante.add(jMenuItemConsultaIntegrante);

        jMenuBar2.add(jMenuIntegrante);

        jMenuActividades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/consultar.png"))); // NOI18N
        jMenuActividades.setText("Actividades");

        jMenuItemConsultaActividad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/consultas.png"))); // NOI18N
        jMenuItemConsultaActividad.setText("Consulta");
        jMenuActividades.add(jMenuItemConsultaActividad);

        jMenuBar2.add(jMenuActividades);

        jMenuZonasVeredales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/proveedores.png"))); // NOI18N
        jMenuZonasVeredales.setText("Zonas veredales");

        jMenuItemConsultaZona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/consulta.png"))); // NOI18N
        jMenuItemConsultaZona.setText("Consulta");
        jMenuZonasVeredales.add(jMenuItemConsultaZona);

        jMenuBar2.add(jMenuZonasVeredales);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenuActividades;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenu jMenuIntegrante;
    private javax.swing.JMenuItem jMenuItemConsultaActividad;
    private javax.swing.JMenuItem jMenuItemConsultaIntegrante;
    private javax.swing.JMenuItem jMenuItemConsultaZona;
    private javax.swing.JMenu jMenuZonasVeredales;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
