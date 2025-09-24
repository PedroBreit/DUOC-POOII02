package vista.Dialogo;

import modelo.Pelicula;

import javax.swing.*;
import java.awt.*;


public class DialogoAgregarPelicula extends JDialog {
    private final JTextField txtTitulo = new JTextField(25);
    private final JTextField txtDirector = new JTextField(25);
    private final JTextField txtAnio = new JTextField(8);
    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnLimpiar = new JButton("Limpiar");

    public DialogoAgregarPelicula(Window owner) {
        super(owner, "Agregar película", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout(10,10));

        JPanel form = new JPanel(new GridLayout(3,2,10,10)); 
        form.setBorder(BorderFactory.createEmptyBorder(12, 16, 4, 16));

        txtTitulo.setMargin(new Insets(4, 8, 4, 8));
        txtDirector.setMargin(new Insets(4, 8, 4, 8));
        txtAnio.setMargin(new Insets(4, 8, 4, 8));

        form.add(new JLabel("Título:"));   form.add(txtTitulo);
        form.add(new JLabel("Director:")); form.add(txtDirector);
        form.add(new JLabel("Año:"));      form.add(txtAnio);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10,10));
        acciones.setBorder(BorderFactory.createEmptyBorder(4, 16, 12, 16));
        btnLimpiar.setMargin(new Insets(6, 12, 6, 12));
        btnAgregar.setMargin(new Insets(6, 16, 6, 16));
        acciones.add(btnLimpiar);
        acciones.add(btnAgregar);

        btnLimpiar.addActionListener(e -> limpiar());

        add(form, BorderLayout.CENTER);
        add(acciones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    public Pelicula construirPelicula() {
        return new Pelicula(
                txtTitulo.getText().trim(),
                txtDirector.getText().trim(),
                txtAnio.getText().trim()
        );
    }

    public void limpiar() {
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnio.setText("");
    }

    public JButton getBtnAgregar() { return btnAgregar; }
}
