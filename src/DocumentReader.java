import java.io.*;
import java.util.*;
import java.util.zip.*;

public class DocumentReader {
	private List<Documento> documentiTrovati = new ArrayList<>();
    public void esploraCartella(File cartellaAttuale) {	
    	File[] contenuto = cartellaAttuale.listFiles();
    	if(contenuto != null){
            for(File elemento : contenuto){
                if(elemento.isDirectory()){
                    esploraCartella(elemento);
                }else if(elemento.getName().endsWith("-0.zip")){
                        processaZip(elemento); 
                }
            }
        }
    }

    public void processaZip(File fileZip) {
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip))){
            ZipEntry entry;
            
            while((entry = zis.getNextEntry()) != null){
                if(entry.getName().endsWith(".txt")){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zis, "UTF-8"));
                    
                    String riga;
                    String titolo = "Unknown Title";
                    String autore = "Unknown Author";
                    
                    StringBuilder testoLibro = new StringBuilder(); 

                    while((riga = reader.readLine()) != null){

                        if(riga.startsWith("Title: ")){
                            titolo = riga.substring(7).trim(); 
                        } 
                        else if(riga.startsWith("Author: ")){
                            autore = riga.substring(8).trim();
                        }

                        testoLibro.append(riga).append("\n"); 
                    }

                    Documento nuovoLibro = new Documento(titolo, autore, testoLibro.toString(), fileZip.getName(), fileZip.getAbsolutePath());

                    documentiTrovati.add(nuovoLibro);
                    
                    System.out.println("Saved in memory: " + titolo + " (by " + autore + ")");
                }
            }
        }catch(Exception e){
            System.err.println("Unable to read: " + fileZip.getName());
        }
    }
    
    public List<Documento> getDocumentiTrovati(){
        return documentiTrovati;
    }

}