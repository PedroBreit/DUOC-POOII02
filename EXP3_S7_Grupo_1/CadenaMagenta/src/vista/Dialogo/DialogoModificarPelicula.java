package vista.Dialogo;

import modelo.Pelicula;

import javax.swing.*;
import java.awt.*;

public class DialogoModificarPelicula extends JDialog {
    private final JTextField txtId = new JTextField(6);
    private final JTextField txtTitulo = new JTextField(25);
    private final JTextField txtDirector = new JTextField(25);
    private final JTextField txtAnio = new JTextField(8);
    private final JButton btnModificar = new JButton("Modificar");
    private final JButton btnLimpiar = new JButton("Limpiar");

    public DialogoModificarPelicula(Window owner) {
        super(owner, "Modificar película", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout(10,10));

        JPanel form = new JPanel(new GridLayout(4,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(12, 16, 4, 16));

        txtId.setEnabled(false);
        txtTitulo.setMargin(new Insets(4,8,4,8));
        txtDirector.setMargin(new Insets(4,8,4,8));
        txtAnio.setMargin(new Insets(4,8,4,8));

        form.add(new JLabel("ID:"));       form.add(txtId);
        form.add(new JLabel("Título:"));   form.add(txtTitulo);
        form.add(new JLabel("Director:")); form.add(txtDirector);
        form.add(new JLabel("Año:"));      form.add(txtAnio);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10,10));
        acciones.setBorder(BorderFactory.createEmptyBorder(4, 16, 12, 16));
        btnLimpiar.setMargin(new Insets(6,12,6,12));
        btnModificar.setMargin(new Insets(6,16,6,16));
        acciones.add(btnLimpiar);
        acciones.add(btnModificar);

        btnLimpiar.addActionListener(e -> limpiar());

        add(form, BorderLayout.CENTER);
        add(acciones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    public void cargar(Pelicula p) {
        txtId.setText(String.valueOf(p.getId()));
        txtTitulo.setText(p.getTitulo());
        txtDirector.setText(p.getDirector());
        txtAnio.setText(p.getAnio());
    }

    public Pelicula construirEditada() {
        return new Pelicula(
                Integer.parseInt(txtId.getText().trim()),
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

    public JButton getBtnModificar() { return btnModificar; }
}
