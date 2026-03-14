import java.util.*;

public class TextCleaner {
	
	private Set<String> paroleInutili = new HashSet<>(Arrays.asList(
	        "the", "and", "of", "to", "a", "in", "it", "is", "that", "was", "for",
	        "on", "with","as", "by", "this", "at", "but", "not", "or",
	        "an", "from", "my", "they", "we", "you", "he", "she", "his", "her",
	        "i", "be", "have", "had", "which", "their", "are", "were", "all"));

    public List<String> pulisciTesto(String testoGrezzo) {
        
        List<String> parolePulite = new ArrayList<>();

        if(testoGrezzo == null || testoGrezzo.isEmpty()){
            return parolePulite;
        }

        String testoMinuscolo = testoGrezzo.toLowerCase();
        String testoSenzaPunti = testoMinuscolo.replaceAll("[^a-z0-9\\s]", "");        //regex
        String[] tokens = testoSenzaPunti.split("\\s+");

        for(String parola : tokens){
            if(!parola.trim().isEmpty() && !paroleInutili.contains(parola.trim())){
            	String radice = Stemmer.trovaRadice(parola);   //stemming
                parolePulite.add(radice);
            }
        }

        return parolePulite;
    }
}