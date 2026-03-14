import java.io.*;
import java.util.*;

public class main {
    public static void main(String[] args) {
        DocumentReader lettore = new DocumentReader();
        String percorsoCartella = "C:\\Users\\feder\\Documenti\\Creazioni\\RobaBrowser\\aleph.gutenberg.org";
        File cartellaPrincipale = new File(percorsoCartella);
        
        System.out.println("Inizio esplorazione del disco...");
        lettore.esploraCartella(cartellaPrincipale);
        List<Documento> tuttiLibri = lettore.getDocumentiTrovati();
        
        System.out.println("\nResearch Compleated!");
        System.out.println("Total number of books saved in RAM (excluding duplicates): " + tuttiLibri.size());
        
        System.out.println("\nBuilding Inverted Index. This might take a bit...");
        
        InvertedIndex motoreDiRicerca = new InvertedIndex();
        TextCleaner cleaner = new TextCleaner();

        for(Documento libro : tuttiLibri){
            motoreDiRicerca.aggiungiDocumento(libro, cleaner);
        }
        
        System.out.println("Index succesfully built!");
        System.out.println("Starting UI...");
        javax.swing.SwingUtilities.invokeLater(() -> {
            UI finestra = new UI(motoreDiRicerca, cleaner);
            finestra.setVisible(true); 
        });
        
    }
}
