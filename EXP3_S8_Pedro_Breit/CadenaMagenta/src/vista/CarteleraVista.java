package vista;

import modelo.Cartelera;
import javax.swing.table.TableRowSorter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CarteleraVista extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(CarteleraVista.class.getName());

    // Modelo de la tabla + sorter (orden por columnas, con comparadores numéricos)
    private final CarteleraTableModel modeloTabla = new CarteleraTableModel();
    private final TableRowSorter<CarteleraTableModel> sorter =
            new TableRowSorter<>(modeloTabla);

    public CarteleraVista() {
        initComponents();

        // Ajustes base del frame
        setLocationRelativeTo(null);
        setMinimumSize(new java.awt.Dimension(980, 600));
        getRootPane().setDefaultButton(btnBuscar);
        
        configurarTabla();
        configurarViewportTabla();

        // Menú -> Botones para no duplicar lógica
        jMenuItem1.addActionListener(e -> btnAgregar.doClick());          // Agregar
        miModificarPelicula.addActionListener(e -> btnModificar.doClick());// Modificar
        miAgregarPelicula.addActionListener(e -> btnEliminar.doClick());  // Eliminar (tu var ya se llama así)

        // Instancia del controlador
        try {
            new controlador.CarteleraControlador(this);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error inicializando controlador", ex);
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bgModo = new javax.swing.ButtonGroup();
        pnlSuperior = new javax.swing.JPanel();
        pBusqueda = new javax.swing.JPanel();
        lblCriterio = new javax.swing.JLabel();
        txtCriterio = new javax.swing.JTextField();
        rbId = new javax.swing.JRadioButton();
        rdTitulo = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        pFiltros = new javax.swing.JPanel();
        lblGenero = new javax.swing.JLabel();
        cbGenero = new javax.swing.JComboBox<>();
        lblDesde = new javax.swing.JLabel();
        spAnioDesde = new javax.swing.JSpinner();
        lblHasta = new javax.swing.JLabel();
        spAnioHasta = new javax.swing.JSpinner();
        btnAplicarFiltros = new javax.swing.JButton();
        pnlTabla = new javax.swing.JPanel();
        ScrollTabla = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        pnlAcciones = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        miEliminarPelicula = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        miModificarPelicula = new javax.swing.JMenuItem();
        miAgregarPelicula = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Carteler Cine Magenta - S8");
        setMinimumSize(new java.awt.Dimension(980, 600));

        pnlSuperior.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 4, 12));
        pnlSuperior.setMinimumSize(new java.awt.Dimension(0, 120));
        pnlSuperior.setPreferredSize(new java.awt.Dimension(0, 200));
        pnlSuperior.setLayout(new java.awt.GridBagLayout());

        pBusqueda.setBorder(javax.swing.BorderFactory.createTitledBorder(" Búsqueda "));
        pBusqueda.setLayout(new java.awt.GridBagLayout());

        lblCriterio.setText("ID o Título: ");
        lblCriterio.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 6);
        pBusqueda.add(lblCriterio, gridBagConstraints);

        txtCriterio.setColumns(28);
        txtCriterio.setToolTipText("Escribe ID o parte del título");
        txtCriterio.setMargin(new java.awt.Insets(4, 8, 4, 8));
        txtCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 6);
        pBusqueda.add(txtCriterio, gridBagConstraints);

        bgModo.add(rbId);
        rbId.setText("ID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 6);
        pBusqueda.add(rbId, gridBagConstraints);

        bgModo.add(rdTitulo);
        rdTitulo.setSelected(true);
        rdTitulo.setText("Título");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 6);
        pBusqueda.add(rdTitulo, gridBagConstraints);

        btnBuscar.setText("Buscar");
        btnBuscar.setMargin(new java.awt.Insets(4, 8, 4, 8));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 6);
        pBusqueda.add(btnBuscar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 513;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 18, 0, 18);
        pnlSuperior.add(pBusqueda, gridBagConstraints);

        pFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder(" Filtrar "));
        pFiltros.setLayout(new java.awt.GridBagLayout());

        lblGenero.setText("Género: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 8);
        pFiltros.add(lblGenero, gridBagConstraints);

        cbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Acción", "Comedia", "Drama", "Terror", "Ciencia Ficción", "Animación", "Aventura", "Fantasía", "Romance", "Thriller", "Crimen", "Guerra" }));
        cbGenero.setPreferredSize(new java.awt.Dimension(160, 26));
        cbGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGeneroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 8);
        pFiltros.add(cbGenero, gridBagConstraints);

        lblDesde.setText("Año desde: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 8);
        pFiltros.add(lblDesde, gridBagConstraints);

        spAnioDesde.setModel(new javax.swing.SpinnerNumberModel(2000, 1900, 2026, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 8);
        pFiltros.add(spAnioDesde, gridBagConstraints);

        lblHasta.setText("Año hasta: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 8);
        pFiltros.add(lblHasta, gridBagConstraints);

        spAnioHasta.setModel(new javax.swing.SpinnerNumberModel(2001, 1900, 2026, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 8);
        pFiltros.add(spAnioHasta, gridBagConstraints);

        btnAplicarFiltros.setText("Aplicar filtros");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 8);
        pFiltros.add(btnAplicarFiltros, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 58;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 20, 18);
        pnlSuperior.add(pFiltros, gridBagConstraints);

        getContentPane().add(pnlSuperior, java.awt.BorderLayout.NORTH);

        pnlTabla.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 12, 8, 12));
        pnlTabla.setMinimumSize(new java.awt.Dimension(300, 92));
        pnlTabla.setName(""); // NOI18N
        pnlTabla.setLayout(new java.awt.BorderLayout());

        ScrollTabla.setBorder(null);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla.setGridColor(new java.awt.Color(255, 255, 255));
        tabla.setIntercellSpacing(new java.awt.Dimension(1, 1));
        tabla.setMaximumSize(null);
        tabla.setMinimumSize(null);
        tabla.setName(""); // NOI18N
        tabla.setPreferredSize(null);
        tabla.setRowHeight(22);
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla.setShowGrid(true);
        ScrollTabla.setViewportView(tabla);

        pnlTabla.add(ScrollTabla, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlTabla, java.awt.BorderLayout.CENTER);

        pnlAcciones.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 12, 12, 12));
        pnlAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 14, 10));

        btnAgregar.setText("Agregar película");
        btnAgregar.setMargin(new java.awt.Insets(6, 14, 6, 14));
        pnlAcciones.add(btnAgregar);

        btnModificar.setText("Modificar película");
        btnModificar.setMargin(new java.awt.Insets(6, 14, 6, 14));
        pnlAcciones.add(btnModificar);

        btnEliminar.setText("Eliminar película");
        btnEliminar.setMargin(new java.awt.Insets(6, 14, 6, 14));
        pnlAcciones.add(btnEliminar);

        btnLimpiar.setText("Quitar filtros");
        btnLimpiar.setMargin(new java.awt.Insets(6, 14, 6, 14));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        pnlAcciones.add(btnLimpiar);

        getContentPane().add(pnlAcciones, java.awt.BorderLayout.PAGE_END);

        miEliminarPelicula.setText("Acciones");

        jMenuItem1.setText("Agregar película");
        jMenuItem1.setToolTipText("Agregar una nueva película");
        miEliminarPelicula.add(jMenuItem1);

        miModificarPelicula.setText("Modificar película");
        miModificarPelicula.setToolTipText("Modificar película seleccionada");
        miEliminarPelicula.add(miModificarPelicula);

        miAgregarPelicula.setText("Eliminar película");
        miAgregarPelicula.setToolTipText("Eliminar película seleccionada");
        miEliminarPelicula.add(miAgregarPelicula);

        jMenuBar1.add(miEliminarPelicula);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    private void configurarTabla() {
        // Modelo y sorter
        tabla.setModel(modeloTabla);
        tabla.setRowSorter(sorter);

        // Comparadores numéricos para ID (col 0) y Año (col 3)
        sorter.setComparator(0, (a,b) -> Integer.compare(parse(a), parse(b)));
        sorter.setComparator(3, (a,b) -> Integer.compare(parse(a), parse(b)));

        // Auto-resize OFF para permitir scroll horizontal
        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        // Anchos sugeridos (ID, Título, Director, Año, Duración, Género)
        if (tabla.getColumnModel().getColumnCount() >= 6) {
            var cm = tabla.getColumnModel();
            cm.getColumn(0).setPreferredWidth(55);
            cm.getColumn(1).setPreferredWidth(350);
            cm.getColumn(2).setPreferredWidth(210);
            cm.getColumn(3).setPreferredWidth(80);
            cm.getColumn(4).setPreferredWidth(100);
            cm.getColumn(5).setPreferredWidth(130);
        }

        ScrollTabla.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        ScrollTabla.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ScrollTabla.setWheelScrollingEnabled(true);
        ScrollTabla.getVerticalScrollBar().setUnitIncrement(16);
        ScrollTabla.getVerticalScrollBar().setBlockIncrement(120);
    }

/** Define la “ventana” del viewport para forzar barra vertical con muchas filas */
    private void configurarViewportTabla() {
        int altoFila = Math.max(22, tabla.getRowHeight());
        int filasVisibles = 14;
        int altoViewport = filasVisibles * altoFila + 6;
        int anchoViewport = Math.max(tabla.getPreferredSize().width, 800);

        tabla.setPreferredScrollableViewportSize(
            new java.awt.Dimension(anchoViewport, altoViewport)
        );
    }

    private int parse(Object o) {
        try { return Integer.parseInt(String.valueOf(o)); }
        catch (Exception e) { return 0; }
    }

    public void setPeliculas(java.util.List<Cartelera> list) {
        modeloTabla.setDatos(list);
        sorter.setSortKeys(null); 
    }

    public Cartelera getSeleccion() {
        int viewIndex = tabla.getSelectedRow();
        if (viewIndex < 0) return null;
        int modelIndex = tabla.convertRowIndexToModel(viewIndex);
        return modeloTabla.getPeliculaAt(modelIndex);
    }

    // --- Búsqueda ---
    public javax.swing.JTextField getTxtCriterio() { return txtCriterio; }
    public javax.swing.JRadioButton getRbId() { return rbId; }
    public javax.swing.JRadioButton getRbTitulo() { return rdTitulo; }
    public javax.swing.JButton getBtnBuscar() { return btnBuscar; }

    // --- Filtros ---
    public javax.swing.JButton getBtnAplicarFiltros() { return btnAplicarFiltros; }
    public String getGeneroSeleccionado() {
        Object v = cbGenero.getSelectedItem();
        String s = (v == null) ? "" : v.toString().trim();
        return s.isBlank() ? null : s;
    }
    public Integer getAnioDesde() { return (Integer) spAnioDesde.getValue(); }
    public Integer getAnioHasta() { return (Integer) spAnioHasta.getValue(); }
    public void setGeneroSeleccionado(String genero) { cbGenero.setSelectedItem(genero == null ? " " : genero); }
    public void setAnioDesde(Integer v) { if (v != null) spAnioDesde.setValue(v); }
    public void setAnioHasta(Integer v) { if (v != null) spAnioHasta.setValue(v); }

    // --- Botones CRUD ---
    public javax.swing.JButton getBtnAgregar() { return btnAgregar; }
    public javax.swing.JButton getBtnModificar() { return btnModificar; }
    public javax.swing.JButton getBtnEliminar() { return btnEliminar; }
    public javax.swing.JButton getBtnLimpiar() { return btnLimpiar; }
    public javax.swing.JTable getTabla() { return tabla; }

    // --- main ---
    public static void main(String args[]) {
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
        java.awt.EventQueue.invokeLater(() -> new CarteleraVista().setVisible(true));
    }










    private void txtCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCriterioActionPerformed
    private void cbGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGeneroActionPerformed
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollTabla;
    private javax.swing.ButtonGroup bgModo;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAplicarFiltros;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cbGenero;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel lblCriterio;
    private javax.swing.JLabel lblDesde;
    private javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblHasta;
    private javax.swing.JMenuItem miAgregarPelicula;
    private javax.swing.JMenu miEliminarPelicula;
    private javax.swing.JMenuItem miModificarPelicula;
    private javax.swing.JPanel pBusqueda;
    private javax.swing.JPanel pFiltros;
    private javax.swing.JPanel pnlAcciones;
    private javax.swing.JPanel pnlSuperior;
    private javax.swing.JPanel pnlTabla;
    private javax.swing.JRadioButton rbId;
    private javax.swing.JRadioButton rdTitulo;
    private javax.swing.JSpinner spAnioDesde;
    private javax.swing.JSpinner spAnioHasta;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtCriterio;
    // End of variables declaration//GEN-END:variables
}
