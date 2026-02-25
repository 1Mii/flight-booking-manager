package system.validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileValidator {

    public static boolean isValid(String filename) throws IOException {

        try {
            List<String> linhas = Files.readAllLines(Paths.get(filename));
            if (linhas.isEmpty()) {
                return false;
            }

            String primeiraLinha = linhas.get(0).trim();
            if (!primeiraLinha.startsWith(">")) {
                return false;
            }

            String[] partes = primeiraLinha.substring(1).trim().split(" ");
            if (partes.length < 2) {
                return false;
            }

            String codigoVoo = partes[0];
            if (!codigoVoo.matches("[A-Z]{2}\\d{3,4}")) {
                return false;
            }

            for (int i = 1; i < partes.length; i++) {
                if (!partes[i].matches("\\d+x\\d+")) {
                    return false;
                }
            }

            for (int i = 1; i < linhas.size(); i++) {
                String linha = linhas.get(i).trim();
                if (!linha.matches("[ET] \\d+")) {
                    return false;
                }
            }

            return true;
        } catch (IOException e) {
            throw new IOException("File not found for path '" + filename + "': " + e.getMessage(), e);
        }
    }
}