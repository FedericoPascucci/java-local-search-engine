
public class Documento {

    private String titolo;
    private String autore;
    private String testo;
    private String nomeFileOriginale; 
    private String percorso; 
    
    public Documento(String titolo, String autore, String contenutoTestuale, String nomeFileOriginale, String percorso) {
        this.titolo = titolo;
        this.autore = autore;
        this.testo = contenutoTestuale;
        this.nomeFileOriginale = nomeFileOriginale;
        this.percorso = percorso;
    }

    public String getTitolo(){
        return titolo;
    }

    public String getAutore(){
        return autore;
    }

    public String getContenutoTestuale(){
        return testo;
    }

    public String getNomeFileOriginale(){
        return nomeFileOriginale;
    }
    
    public String getPercorso(){
        return percorso;
    }
}