/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab5_file;

/**
 *
 * @author ALISSONRAQUELMARTINE
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Consola extends JFrame {

    private JTextArea consoleArea;
    private JTextField inputField;

    // Ruta inicial
    private String currentPath = System.getProperty("user.dir");

    // Aquí se conectará tu intérprete más adelante:
    private CommandExecutor executor = new CommandExecutor();

    public Consola() {
        setTitle("Java CMD");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        printPrompt();
    }

    private void initComponents() {
        consoleArea = new JTextArea();
        consoleArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        consoleArea.setEditable(false);
        consoleArea.setBackground(Color.black);
        consoleArea.setForeground(Color.green);

        JScrollPane scroll = new JScrollPane(consoleArea);

        inputField = new JTextField();
        inputField.setFont(new Font("Consolas", Font.PLAIN, 14));

        // Evento ENTER
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
    }

    // Mostrar el prompt
    private void printPrompt() {
        consoleArea.append(currentPath + "> ");
    }

    // Procesar lo que el usuario escribe
    private void processInput() {
        String command = inputField.getText().trim();
        inputField.setText("");

        // Mostrar lo que escribió el usuario
        consoleArea.append(command + "\n");

        // Ejecutar comando (esto llamará tu lógica)
        String output = executor.execute(command, currentPath);

        // Actualizar ruta si el comando lo cambió
        if (output.startsWith("PATH_CHANGE:")) {
            currentPath = output.replace("PATH_CHANGE:", "");
        } else {
            consoleArea.append(output);
        }

        printPrompt();

        // Auto-scroll
        consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Consola().setVisible(true);
        });
    }
}

