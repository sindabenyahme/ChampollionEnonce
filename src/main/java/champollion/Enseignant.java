package champollion;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
public class Enseignant extends Personne {
    private Set<TypeIntervention> interventions = new HashSet<>();
    private Map<UE, ServicePrevu> enseignements = new HashMap<>();

    // TODO : rajouter les autres méthodes présentes dans le diagramme UML

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**+
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        float result = 0f;
        for (UE ue : enseignements.keySet()) {
            result += Math.round(heuresPrevuesPourUE(ue));
        }
        return Math.round(result);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        if (enseignements.containsKey(ue)) {
            SuperPrevu superPrevu = enseignements.get(ue);
            int heuresCM = ServicePrevu.getVolumeCM();
            int heuresTD = ServicePrevu.getVolumeTD();
            int heuresTP = ServicePrevu.getVolumeTP();

            int heuresEquivalentTD = (int)(heuresCM * 1.5f + heuresTD + heuresTP * 0.75f);
            return heuresEquivalentTD;
        } else {
            throw new IllegalArgumentException("L'UE spécifiée n'a pas été trouvée parmi les enseignements de cet enseignant.");
        }
    }



    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        if (volumeCM < 0 || volumeTD < 0 || volumeTP < 0) {
            throw new IllegalArgumentException("Les volumes d'heures ne peuvent pas être négatifs.");
        }

        if (!enseignements.containsKey(ue)) {
            enseignements.put(ue, new SuperPrevu());
        }

        SuperPrevu ServicePrevu = enseignements.get(ue);
        superPrevu.ajouteEnseignement(volumeCM, volumeTD, volumeTP);
    }

    public void ajouteIntervention(TypeIntervention intervention) {
        interventions.add(intervention);
    }

    public boolean estEnSousService() {
        return heuresPrevues() < 192;
    }



}
