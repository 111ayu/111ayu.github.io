package src;

import java.io.*;
import java.nio.file.*;

public class htmlMaker {
    private static final String HTML_DOS = "html";
    private static final String TXT_DOS = "txt";
    private static final String IMAGE_DOS = "../Image";
    private static final String CSS_CHEMIN = "../css/stylePortrait.css";

    public htmlMaker(String titre) throws IOException {
        titreCorrect(titre);
        
        Path htmlDos = Paths.get(HTML_DOS);
        Path txtDos = Paths.get(TXT_DOS);
        Path imageDos = Paths.get(IMAGE_DOS);
        
        Files.createDirectories(htmlDos);
        
        if (!Files.exists(txtDos)) {
            throw new IOException("Le dossier txt n'existe pas.");
        }
        
        Path txtFichier = txtDos.resolve(titre + ".txt");
        if (!Files.exists(txtFichier)) {
            throw new IOException("Le fichier txt n'existe pas.");
        }
        
        Path htmlFichier = htmlDos.resolve(titre + ".html");
        /*if (Files.exists(htmlFichier)) {
            System.out.println("Le fichier html existe déjà.");
            return;
        }*/

        Path imageFichier = imageDos.resolve(titre+".jpg");
        Path imageFichier2 = Paths.get("./Image/").resolve(titre+".jpg");
        if (!Files.exists(imageFichier2)) {
            System.out.println("Le fichier image n'existe pas.");
            System.out.println(imageFichier);
        }
        
        générerHtml(titre, txtFichier, htmlFichier, imageFichier);
    }

    private void titreCorrect(String titre) {
        if (titre == null || titre.isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        if (titre.matches(".*[\\\\/:*?\"<>|].*")) {
            throw new IllegalArgumentException("Caractères invalides dans le titre");
        }
    }

    private void générerHtml(String titre, Path txtFichier, Path htmlFichier, Path imagedir) throws IOException {
        writeHeader(htmlFichier, titre);
        
        try (BufferedReader reader = Files.newBufferedReader(txtFichier)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("# ")) {
                    writeSubtitle(htmlFichier, line.substring(2),imagedir);
                    // writeImage(htmlFichier, titre); // Décommenter si nécessaire
                } else if (line.startsWith("## ")) {
                    writeSubSubtitle(htmlFichier, line.substring(3));
                } else if (line.startsWith("Lien : ")) {
                    String source = line.substring(6);
                    writeSourceImage(htmlFichier, source);
                } else{
                    writeParagraph(htmlFichier, line);
                }
            }
        }
        
        writeFooter(htmlFichier);
    }

    private void writeToFile(Path file, String content, boolean append) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file, 
                StandardOpenOption.CREATE, 
                append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(content);
        }
    }

    private void writeHeader(Path htmlFichier, String titre) throws IOException {
        String header = String.join("\n",
            "<!DOCTYPE html>",
            "<html lang=\"fr\">",
            "<head>",
            "    <meta charset=\"UTF-8\">",
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">",
            "    <link rel=\"stylesheet\" href=\"" + CSS_CHEMIN + "\">",
            "    <title>" + titre + "</title>",
            "</head>",
            "<body>",
            "    <header>",
            "        <div class=\"Logo\">",
            "            <a href=\"accueil.html\">",
            "                <img src=\"../Image/Logo.jpg\" alt=\"Logo\">",
            "            </a>",
            "        </div>",
            "        <h1>Portrait de Femmes</h1>",
            "    </header>",
            "    <main>",
            ""
        );
        writeToFile(htmlFichier, header, false);
    }

    private void writeFooter(Path htmlFichier) throws IOException {
        String footer = String.join("\n",
            "    </main>",
            "    <footer>",
            "        <div class=\"Boite\">",
            "            <ul class=\"Lien\">",
            "                <li><a href=\"index.html\">Accueil</a></li>",
            "            </ul>",
            "            <ul class=\"Lien\">",
            "                <li><a href=\"contact.html\">Crédits</a></li>",
            "            </ul>",
            "        </div>",
            "    </footer>",
            "</body>",
            "</html>"
        );
        writeToFile(htmlFichier, footer, true);
    }
    private void writeSubtitle(Path htmlFichier, String subtitle, Path imageDos) throws IOException {
        String content = "<h2>" + subtitle + "</h2>\n";
        content += "<div class=\"portrait-container\">\n<img src=\"" + imageDos + "\" width=\"300\"\n" +"        height=\"400\" \" alt=\"" + subtitle + "\" class=\"portrait\">\n";
        writeToFile(htmlFichier, content, true);
    }
    private void writeSubSubtitle(Path htmlFichier, String subSubtitle) throws IOException {
        String content = "<h3>" + subSubtitle + "</h3>\n";
        writeToFile(htmlFichier, content, true);
    }
    private void writeParagraph(Path htmlFichier, String paragraph) throws IOException {
        String content = "<p>" + paragraph + "</p>\n";
        writeToFile(htmlFichier, content, true);
    }

    private void writeSourceImage(Path htmlFichier, String source) throws IOException {
        String content = "<div class=\"source\">\n" +
                source + " </div>\n" + 
                "</div>\n";
        writeToFile(htmlFichier, content, true);
    }
    

   
    public static void main(String[] args) throws IOException {
        /*htmlMaker htmlMaker  = new htmlMaker("ClaireVoisin");
        htmlMaker htmlMaker2 = new htmlMaker("MadeleineBres");
        htmlMaker htmlMaker3 = new htmlMaker("RosalindElsieFranklin");
        htmlMaker htmlMaker4 = new htmlMaker("ElizabethBlackwell");
        htmlMaker htmlMaker5 = new htmlMaker("JulieVictoireDaubie");
        htmlMaker htmlMaker6 = new htmlMaker("ClaudieHaignere");
        htmlMaker htmlMaker7 = new htmlMaker("YvonneChoquetBruhat");
        htmlMaker htmlMaker8 = new htmlMaker("Portrait");*/
        }
}