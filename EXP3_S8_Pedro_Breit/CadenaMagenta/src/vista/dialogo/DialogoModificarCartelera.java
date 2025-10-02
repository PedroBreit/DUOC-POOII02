package vista.dialogo;

public class DialogoModificarCartelera extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DialogoModificarCartelera.class.getName());

    public DialogoModificarCartelera(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public void cargar(modelo.Cartelera c) {
        txtId.setText(String.valueOf(c.getId()));
        txtTitulo.setText(nvl(c.getTitulo()));
        txtDirector.setText(nvl(c.getDirector()));
        txtAnio.setText(nvl(c.getAnio()));
        cbGenero.setSelectedItem(nvl(c.getGenero()));
        txtDuracion.setText(nvl(c.getDuracion()));
    }
    
    public modelo.Cartelera construirEditada() {
        modelo.Cartelera e = new modelo.Cartelera();
        e.setId(Integer.parseInt(txtId.getText().trim()));
        e.setTitulo(txtTitulo.getText().trim());
        e.setDirector(txtDirector.getText().trim());
        e.setAnio(txtAnio.getText().trim());

        Object g = cbGenero.getSelectedItem();
        e.setGenero(g == null ? null : g.toString().trim().isBlank() ? null : g.toString().trim());
        e.setDuracion(txtDuracion.getText().trim());

        return e;
    }
    
    public javax.swing.JButton getBtnModificar() { return btnModificar; }
    private String nvl(String s) { return s == null ? "" : s; }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelFormulario = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        lblDirector = new javax.swing.JLabel();
        txtDirector = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAnio = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblGenero = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDuracion = new javax.swing.JLabel();
        cbGenero = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        txtDuracion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        PanelAccionesAgregar = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modificar película");
        setModal(true);
        setName("dlgModificar"); // NOI18N
        setResizable(false);

        panelFormulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 16, 4, 16));
        panelFormulario.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setText("Título: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        panelFormulario.add(lblTitulo, gridBagConstraints);

        txtTitulo.setColumns(25);
        txtTitulo.setMargin(new java.awt.Insets(4, 8, 4, 8));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        panelFormulario.add(txtTitulo, gridBagConstraints);

        lblDirector.setText("Director: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        panelFormulario.add(lblDirector, gridBagConstraints);

        txtDirector.setColumns(25);
        txtDirector.setMargin(new java.awt.Insets(4, 8, 4, 8));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        panelFormulario.add(txtDirector, gridBagConstraints);

        jLabel1.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        panelFormulario.add(jLabel1, gridBagConstraints);

        jLabel2.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 6;
        panelFormulario.add(jLabel2, gridBagConstraints);

        txtAnio.setText("Año: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 7;
        panelFormulario.add(txtAnio, gridBagConstraints);

        jLabel3.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 8;
        panelFormulario.add(jLabel3, gridBagConstraints);

        lblGenero.setText("Género: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 9;
        panelFormulario.add(lblGenero, gridBagConstraints);

        jLabel4.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 10;
        panelFormulario.add(jLabel4, gridBagConstraints);

        lblDuracion.setText("Duración: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 11;
        panelFormulario.add(lblDuracion, gridBagConstraints);

        cbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Acción", "Comedia", "Drama", "Terror", "Ciencia Ficción", "Animación", "Aventura", "Fantasía", "Romance", "Thriller", "Crimen", "Guerra" }));
        cbGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGeneroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelFormulario.add(cbGenero, gridBagConstraints);

        jTextField1.setColumns(25);
        jTextField1.setMargin(new java.awt.Insets(4, 8, 4, 8));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelFormulario.add(jTextField1, gridBagConstraints);

        txtDuracion.setColumns(25);
        txtDuracion.setMargin(new java.awt.Insets(4, 8, 4, 8));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 11;
        panelFormulario.add(txtDuracion, gridBagConstraints);

        jLabel5.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        panelFormulario.add(jLabel5, gridBagConstraints);

        jLabel6.setText("ID: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        panelFormulario.add(jLabel6, gridBagConstraints);

        txtId.setColumns(6);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelFormulario.add(txtId, gridBagConstraints);

        getContentPane().add(panelFormulario, java.awt.BorderLayout.CENTER);

        PanelAccionesAgregar.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 16, 12, 16));

        btnLimpiar.setText("Limpiar campos");
        btnLimpiar.setMargin(new java.awt.Insets(4, 8, 4, 8));
        PanelAccionesAgregar.add(btnLimpiar);

        btnModificar.setText("Modificar película");
        btnModificar.setMargin(new java.awt.Insets(4, 8, 4, 8));
        PanelAccionesAgregar.add(btnModificar);

        getContentPane().add(PanelAccionesAgregar, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGeneroActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DialogoModificarCartelera dialog = new DialogoModificarCartelera(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAccionesAgregar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cbGenero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblDirector;
    private javax.swing.JLabel lblDuracion;
    private javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JLabel txtAnio;
    private javax.swing.JTextField txtDirector;
    private javax.swing.JTextField txtDuracion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
