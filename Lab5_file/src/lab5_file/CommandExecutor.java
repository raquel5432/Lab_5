/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;

/**
 *
 * @author gaat1
 */
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CommandExecutor {

    public String execute(String command, String currentPath) {

        if (command.trim().isEmpty()) {
            return "";
        }

        String[] parts = command.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        String arg = (parts.length > 1 ? parts[1] : "");

        File currentDir = new File(currentPath);

        switch (cmd) {

            case "mkdir":
                if (arg.isEmpty()) return "Debe ingresar un nombre.\n";
                File newFolder = new File(currentDir, arg);
                if (newFolder.exists()) return "La carpeta ya existe.\n";
                return newFolder.mkdir() ? "Carpeta creada.\n" : "No se pudo crear la carpeta.\n";

            case "mfile":
                if (arg.isEmpty()) return "Debe ingresar el nombre del archivo.\n";
                File newFile = new File(currentDir, arg);
                if (newFile.exists()) return "El archivo ya existe.\n";
                try {
                    return newFile.createNewFile() ? 
                        "Archivo creado.\n" : "No se pudo crear el archivo.\n";
                } catch (IOException e) {
                    return "Error: " + e.getMessage() + "\n";
                }

            case "rm":
                if (arg.isEmpty()) return "Debe ingresar un nombre.\n";
                File target = new File(currentDir, arg);
                if (!target.exists()) return "No existe.\n";
                deleteRecursive(target);
                return "Eliminado correctamente.\n";

            case "cd":
                if (arg.isEmpty())
                    return "Debe ingresar una carpeta.\n";

                if (arg.equals("...")) {
                    File parent = currentDir.getParentFile();
                    if (parent == null)
                        return "Ya está en la raíz.\n";
                    return "PATH_CHANGE:" + parent.getAbsolutePath();
                }
 
                if (arg.equals(".")) {
                    return ""; // no cambia de carpeta
                }
                if (arg.equals("..")){
                    return "Ruta inválida.\n";
                }
                // Prohibir múltiples puntos como "..."
                if (arg.matches("\\.{3,}")) {
                    return "Ruta inválida.\n";
                }

                // Cambiar a carpeta normal
                File newPath = new File(currentDir, arg);

                if (!newPath.exists())
                    return "La carpeta no existe.\n";

                if (!newPath.isDirectory())
                    return "No es una carpeta.\n";

                return "PATH_CHANGE:" + newPath.getAbsolutePath();


   
            case "dir":
                File[] list = currentDir.listFiles();
                if (list == null) return "Error al listar archivos.\n";

                StringBuilder sb = new StringBuilder();
                for (File f : list) {
                    sb.append(f.isDirectory() ? "[DIR]  " : "       ");
                    sb.append(f.getName()).append("\n");
                }
                return sb.toString();

       
            case "date":
                return LocalDate.now().toString() + "\n";

            case "time":
                return LocalTime.now().withNano(0).toString() + "\n";

            case "wr":
                if (arg.isEmpty()) return "Debe indicar un archivo.\n";

                File writeFile = new File(currentDir, arg);
                if (!writeFile.exists()) return "El archivo no existe.\n";

                return "WR_START:" + writeFile.getAbsolutePath();

            case "rd":
                if (arg.isEmpty()) return "Debe indicar archivo.\n";

                File fileRead = new File(currentDir, arg);
                if (!fileRead.exists()) return "El archivo no existe.\n";

                try {
                    return Files.readString(fileRead.toPath()) + "\n";
                } catch (IOException e) {
                    return "Error leyendo archivo.\n";
                }

          
            default:
                return "Comando no reconocido.\n";
        }
    }

   
    private void deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File sub : file.listFiles()) {
                deleteRecursive(sub);
            }
        }
        file.delete();
    }

   
    private String writeToFile(File file) {
        try {
            System.out.println("\n-- MODO ESCRITURA --");
            System.out.println("Escriba su texto. Finalice con una línea que diga: <FIN>");
            System.out.print("> ");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals("<FIN>")) break;
                sb.append(line).append(System.lineSeparator());
            }

            Files.writeString(file.toPath(), sb.toString());
            return "Archivo escrito correctamente.\n";

        } catch (Exception e) {
            return "Error escribiendo archivo.\n";
        }
    }
}


