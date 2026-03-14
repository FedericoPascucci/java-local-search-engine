import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UI extends JFrame {
    
    private InvertedIndex motore;
    private TextCleaner spazzino;
    private List<Map.Entry<Documento, Double>> ultimiRisultati = null;
    
    private JTextField campoRicerca;
    private JTextArea areaRisultati;
    private JButton bottoneCerca;
    private JTextField campoNumeroFile;
    private JButton bottoneApriFile;

    public UI(InvertedIndex motore, TextCleaner spazzino){
        this.motore = motore;
        this.spazzino = spazzino;
        
        setTitle("FP's Search Engine");
        setSize(850, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //nord
        
        JPanel pannelloNord = new JPanel(new BorderLayout());
        campoRicerca = new JTextField();
        campoRicerca.setFont(new Font("Arial",Font.PLAIN, 16));
        bottoneCerca = new JButton("Search");
        pannelloNord.add(new JLabel(" Insert search terms: "),BorderLayout.WEST);
        pannelloNord.add(campoRicerca,BorderLayout.CENTER);
        pannelloNord.add(bottoneCerca,BorderLayout.EAST);
        
        //centro
        
        areaRisultati = new JTextArea();
        areaRisultati.setFont(new Font("Monospaced",Font.PLAIN, 14));
        areaRisultati.setEditable(false); 
        areaRisultati.setText("WELCOME TO FP's SEARCH ENGINE\n\n" +
                              "> Enter your search terms in the bar at the top and press the 'Search' button or the Enter key.\n" +
                              "> To turn off the engine, type 'stop' and press Enter, or click the X at the top.\n" +
                              "> Use the panel below to open and read the book directly!\n" +
                              "> Thx to Project Gutenberg <3\n" +
                              "==============================================================================\n");
        JScrollPane scrollPane = new JScrollPane(areaRisultati);
        
        // sud
        
        JPanel pannelloSud = new JPanel(new FlowLayout());
        campoNumeroFile = new JTextField(5); 
        bottoneApriFile = new JButton("Open Selected File");
        pannelloSud.add(new JLabel("Insert the book's result number here to read it: "));
        pannelloSud.add(campoNumeroFile);
        pannelloSud.add(bottoneApriFile);
        
        add(pannelloNord, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pannelloSud, BorderLayout.SOUTH); 
        
        configuraAzioni();
    }
    
    private void configuraAzioni(){
        ActionListener azioneRicerca = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                eseguiRicerca();
            }
        };
        bottoneCerca.addActionListener(azioneRicerca);
        campoRicerca.addActionListener(azioneRicerca);
        
        bottoneApriFile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                apriFileSelezionato();
            }
        });
    }
    
    private void eseguiRicerca(){
        String query = campoRicerca.getText();
        
        if (query.trim().equalsIgnoreCase("stop")){
            System.exit(0);
        }
        
        if (query.trim().isEmpty()){
            areaRisultati.setText("Please, insert something in the Search Bar.");
            return;
        }
        
        areaRisultati.setText("Searching for: '" + query + "'...\n\n");
        ultimiRisultati = motore.cerca(query, spazzino); 
        
        if (ultimiRisultati.isEmpty()){
            areaRisultati.append("No books found.");
        }else{
            areaRisultati.append(ultimiRisultati.size() + " books found! Here's the first 20:\n");
            areaRisultati.append("------------------------------------------------------------------------------\n");
            
            int count = 0;
            for(Map.Entry<Documento, Double> risultato : ultimiRisultati){
                if (count >= 20) break;
                Documento doc = risultato.getKey();
                String riga = String.format("%2d. [Score: %6.2f] %s (di %s) [File: %s]\n", 
                		(count + 1), risultato.getValue(), doc.getTitolo(), doc.getAutore(), doc.getNomeFileOriginale());
                areaRisultati.append(riga);
                count++;
            }
        }
    }
    
    private void apriFileSelezionato(){
        if(ultimiRisultati == null || ultimiRisultati.isEmpty()){
            JOptionPane.showMessageDialog(this, "You need to search something before opening a file!");
            return;
        }
        
        try{
            int numeroScelto = Integer.parseInt(campoNumeroFile.getText().trim());
            int indiceArray = numeroScelto - 1;
            
            if(indiceArray >= 0 && indiceArray < ultimiRisultati.size() && indiceArray < 20){
                Documento docScelto = ultimiRisultati.get(indiceArray).getKey();
                File fileZipOriginale = new File(docScelto.getPercorso());
                if(Desktop.isDesktopSupported() && fileZipOriginale.exists()){
                    File fileTxtTemporaneo = File.createTempFile("GutenbergBook_", ".txt");
                    fileTxtTemporaneo.deleteOnExit(); 
                    try(ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZipOriginale))){
                        ZipEntry entry;
                        while((entry = zis.getNextEntry()) != null){
                            if(entry.getName().endsWith(".txt")){
                                Files.copy(zis, fileTxtTemporaneo.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                break;
                            }
                        }
                    }
                    Desktop.getDesktop().open(fileTxtTemporaneo);
                }else{
                    JOptionPane.showMessageDialog(this, "Unable to open the file / File not found");
                }  
            }else{
                JOptionPane.showMessageDialog(this, "Invalid number. Insert a value between 1 and " + Math.min(20, ultimiRisultati.size()));
            }
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Please, insert a valid number.");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Unexpected error while opening: " + ex.getMessage());
        }
    }
}