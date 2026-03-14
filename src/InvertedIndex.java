import java.util.*;

public class InvertedIndex {
	
	private int totaleDocumenti=0;   //documents counter

    private Map<String, Map<Documento, Integer>> indice = new HashMap<>();

    public void aggiungiDocumento(Documento doc, TextCleaner cleaner) {
    	totaleDocumenti++;
        List<String> parolePulite = cleaner.pulisciTesto(doc.getContenutoTestuale());
        
        for(String parola : parolePulite){
            if(!indice.containsKey(parola)){
                indice.put(parola, new HashMap<>());
            }
            Map<Documento, Integer> frequenzeDocumento = indice.get(parola);
            int conteggioAttuale = frequenzeDocumento.getOrDefault(doc, 0);
            frequenzeDocumento.put(doc, conteggioAttuale + 1);
        }
    }

    public List<Map.Entry<Documento, Double>> cerca(String parolaCercata, TextCleaner cleaner) {
        List<String> paroleRicerca = cleaner.pulisciTesto(parolaCercata);

        if(paroleRicerca.isEmpty() || totaleDocumenti==0){
            return new ArrayList<>();
        }
        String primaParola = paroleRicerca.get(0);
        if(!indice.containsKey(primaParola)){
            return new ArrayList<>(); 
        }

        Set<Documento> documentiSopravvissuti = new HashSet<>(indice.get(primaParola).keySet());
        
        for(int i = 1; i < paroleRicerca.size(); i++){
            String parolaCorrente = paroleRicerca.get(i);
            if (!indice.containsKey(parolaCorrente)) return new ArrayList<>();
            documentiSopravvissuti.retainAll(indice.get(parolaCorrente).keySet()); //intersezione
        }
        
        //TF-IDF   :    W = TF * log10 (N/DF)
        
        Map<Documento, Double> punteggiFinali = new HashMap<>();
        
        for(Documento doc : documentiSopravvissuti){
            double punteggioTotale = 0.0;
            for(String parola : paroleRicerca){
                Map<Documento, Integer> mappaFrequenze = indice.get(parola);
                
                int tf = mappaFrequenze.get(doc); //quante volte nel documento  ,  term frequency
                int df = mappaFrequenze.size();   //in quanti documento compare  ,  document frequency
                double idf = Math.log10((double) totaleDocumenti / df);
                double tfIdf = tf * idf;
                
                punteggioTotale += tfIdf;
            }
            punteggiFinali.put(doc, punteggioTotale);
        }
        
        List<Map.Entry<Documento, Double>> listaOrdinata = new ArrayList<>(punteggiFinali.entrySet());
        listaOrdinata.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        return listaOrdinata;
    }
}