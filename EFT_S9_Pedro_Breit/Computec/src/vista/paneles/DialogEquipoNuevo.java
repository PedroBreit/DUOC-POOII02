package vista.paneles;

import dao.EquipoDAO;
import dao.jdbc.EquipoDAOJDBC;
import modelo.Desktop;
import modelo.Laptop;
import vista.util.Mensajes;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Diálogo para agregar un equipo nuevo.
 * - Si es DESKTOP, pide: potencia (W) y placa (ATX, MicroATX, etc.)
 * - Si es LAPTOP, pide: pantalla (pulg), si es touch y puertos USB
 */
public class DialogEquipoNuevo extends JDialog {

    private final JComboBox<String> cbTipo = new JComboBox<>(new String[]{"DESKTOP","LAPTOP"});

    // Campos comunes
    private final JTextField txtModelo = new JTextField(16);
    private final JTextField txtCpu = new JTextField(16);
    private final JTextField txtDisco = new JTextField(8);  
    private final JTextField txtRam = new JTextField(5);    
    private final JTextField txtPrecio = new JTextField(8); 

    // Panel específico Desktop
    private final JTextField txtPotencia = new JTextField(5);
    private final JTextField txtPlaca = new JTextField(10);

    // Panel específico Laptop
    private final JTextField txtPantalla = new JTextField(5);
    private final JCheckBox chkTouch = new JCheckBox("Touch");
    private final JTextField txtUsb = new JTextField(3);

    private final CardLayout card = new CardLayout();
    private final JPanel panelEspecifico = new JPanel(card);

    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final EquipoDAO dao = new EquipoDAOJDBC();
    private final Consumer<Boolean> onClose;

    public DialogEquipoNuevo(Window owner, Consumer<Boolean> onClose) {
        super(owner, "Agregar equipo", ModalityType.APPLICATION_MODAL);
        this.onClose = onClose;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(520, 440);
        setLocationRelativeTo(owner);

        JPanel contenido = new JPanel(new BorderLayout(10,10));
        contenido.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setContentPane(contenido);

        // selector de tipo
        JPanel selTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selTipo.add(new JLabel("Tipo de equipo:"));
        selTipo.add(cbTipo);
        contenido.add(selTipo, BorderLayout.NORTH);

        // formulario común
        JPanel comunes = new JPanel(new GridLayout(3, 4, 6, 6));
        comunes.setBorder(BorderFactory.createTitledBorder("Datos generales"));
        comunes.add(new JLabel("Modelo:")); comunes.add(txtModelo);
        comunes.add(new JLabel("CPU:")); comunes.add(txtCpu);
        comunes.add(new JLabel("Disco (GB):")); comunes.add(txtDisco);
        comunes.add(new JLabel("RAM (GB):")); comunes.add(txtRam);
        comunes.add(new JLabel("Precio:")); comunes.add(txtPrecio);

        // panel Desktop
        JPanel pDesktop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pDesktop.setBorder(BorderFactory.createTitledBorder("Datos Desktop"));
        pDesktop.add(new JLabel("Potencia (W):")); pDesktop.add(txtPotencia);
        pDesktop.add(new JLabel("Placa:")); pDesktop.add(txtPlaca);

        // panel Laptop
        JPanel pLaptop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pLaptop.setBorder(BorderFactory.createTitledBorder("Datos Laptop"));
        pLaptop.add(new JLabel("Pantalla (pulg):")); pLaptop.add(txtPantalla);
        pLaptop.add(new JLabel("Puertos USB:")); pLaptop.add(txtUsb);
        JPanel filaTouch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaTouch.add(chkTouch);

        JPanel centro = new JPanel(new BorderLayout(6,6));
        centro.add(comunes, BorderLayout.NORTH);
        panelEspecifico.add(pDesktop, "DESKTOP");
        JPanel wrapLaptop = new JPanel(new BorderLayout());
        wrapLaptop.add(pLaptop, BorderLayout.NORTH);
        wrapLaptop.add(filaTouch, BorderLayout.CENTER);
        panelEspecifico.add(wrapLaptop, "LAPTOP");
        centro.add(panelEspecifico, BorderLayout.CENTER);
        contenido.add(centro, BorderLayout.CENTER);

        // botones
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sur.add(btnCancelar);
        sur.add(btnGuardar);
        contenido.add(sur, BorderLayout.SOUTH);

        // eventos
        cbTipo.addActionListener(e -> card.show(panelEspecifico, (String) cbTipo.getSelectedItem()));
        btnCancelar.addActionListener(e -> { if (onClose != null) onClose.accept(false); dispose(); });
        btnGuardar.addActionListener(e -> guardar());

        // estado inicial
        card.show(panelEspecifico, "DESKTOP");
    }

    private void guardar() {
        String tipo = (String) cbTipo.getSelectedItem();

        // Validaciones básicas
        if (txtModelo.getText().isBlank() || txtCpu.getText().isBlank() ||
            txtDisco.getText().isBlank() || txtRam.getText().isBlank() || txtPrecio.getText().isBlank()) {
            Mensajes.error("Completa modelo, CPU, disco (GB), RAM (GB) y precio.");
            return;
        }
        try {
            int discoGb = Integer.parseInt(txtDisco.getText().trim());
            int ramGb = Integer.parseInt(txtRam.getText().trim());
            int precio = Integer.parseInt(txtPrecio.getText().trim());

            boolean ok;
            if ("DESKTOP".equals(tipo)) {
                if (txtPotencia.getText().isBlank() || txtPlaca.getText().isBlank()) {
                    Mensajes.error("Completa potencia (W) y placa.");
                    return;
                }
                Desktop d = new Desktop();
                d.setModelo(txtModelo.getText().trim());
                d.setCpu(txtCpu.getText().trim());
                d.setDiscoGb(discoGb);     // <-- GB
                d.setRamGb(ramGb);
                d.setPrecio(precio);
                d.setPotenciaFuenteW(Integer.parseInt(txtPotencia.getText().trim()));
                d.setPlaca(txtPlaca.getText().trim()); // <-- Placa

                ok = dao.crearDesktop(d);

            } else { // LAPTOP
                if (txtPantalla.getText().isBlank() || txtUsb.getText().isBlank()) {
                    Mensajes.error("Completa pantalla (pulg) y puertos USB.");
                    return;
                }
                Laptop l = new Laptop();
                l.setModelo(txtModelo.getText().trim());
                l.setCpu(txtCpu.getText().trim());
                l.setDiscoGb(discoGb);
                l.setRamGb(ramGb);
                l.setPrecio(precio);

                BigDecimal pant = new BigDecimal(txtPantalla.getText().trim().replace(",", "."));
                l.setTamPantallaPulg(pant.doubleValue());

                l.setEsTouch(chkTouch.isSelected());
                l.setPuertosUsb(Integer.parseInt(txtUsb.getText().trim()));

                ok = dao.crearLaptop(l);
            }

            if (ok) {
                Mensajes.info("Equipo guardado correctamente.");
                if (onClose != null) onClose.accept(true);
                dispose();
            } else {
                Mensajes.error("No se pudo guardar el equipo.");
            }

        } catch (NumberFormatException ex) {
            Mensajes.error("Revisa que Disco (GB), RAM (GB), Precio, Potencia (W) y USB sean números válidos.");
        }
    }
}

