package presentation;

import javax.swing.*;
import controller.DiagramaController;

public class FrmDiagrama extends JFrame {

    private JTextField txtClase;
    private JButton btnAgregar;

    public FrmDiagrama() {

        setTitle("Editor UML - Diagrama de Clases");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtClase = new JTextField();
        txtClase.setBounds(50, 50, 200, 30);
        add(txtClase);

        btnAgregar = new JButton("Agregar Clase");
        btnAgregar.setBounds(270, 50, 150, 30);
        add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            new DiagramaController()
                    .agregarClase(txtClase.getText());
        });

        setVisible(true);
    }
}
