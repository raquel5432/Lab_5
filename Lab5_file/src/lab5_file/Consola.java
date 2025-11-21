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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.*;

public class Consola extends JFrame {

    private JTextArea consoleArea;
    private JTextField inputField;
    private boolean writingMode = false;
    private File fileBeingWrittenTo = null;
    private StringBuilder writeBuffer = new StringBuilder();

    private String currentPath = System.getProperty("user.dir");

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

    private void printPrompt() {
        consoleArea.append(currentPath + "> ");
    }

    private void processInput() {
        String command = inputField.getText();
        inputField.setText("");

        // MODO ESCRITURA
        if (writingMode) {
            consoleArea.append(command + "\n");

            if (command.equals("<FIN>")) {
                // Guardar en archivo
                try {
                    Files.writeString(fileBeingWrittenTo.toPath(), writeBuffer.toString());
                    consoleArea.append("Archivo escrito correctamente.\n");
                } catch (IOException e) {
                    consoleArea.append("Error escribiendo archivo.\n");
                }

                // Salir de modo escritura
                writingMode = false;
                fileBeingWrittenTo = null;
                writeBuffer = new StringBuilder();

                printPrompt();
                return;
            }

            // Acumular texto
            writeBuffer.append(command).append(System.lineSeparator());
            return;
        }

        consoleArea.append(command + "\n");

        String output = executor.execute(command, currentPath);

        // Activar modo escritura si el comando lo pide
        if (output.startsWith("WR_START:")) {
            String filePath = output.replace("WR_START:", "");
            fileBeingWrittenTo = new File(filePath);
            writingMode = true;

            consoleArea.append("Ingrese texto (termine con <FIN>):\n");
            return;
        }

        if (output.startsWith("PATH_CHANGE:")) {
            currentPath = output.replace("PATH_CHANGE:", "");
        } else {
            consoleArea.append(output);
        }

        printPrompt();
        consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Consola().setVisible(true);
        });
    }
}

