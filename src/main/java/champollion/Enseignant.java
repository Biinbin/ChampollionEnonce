package champollion;

import java.util.ArrayList;

public class Enseignant extends Personne {

    public ArrayList<ServicePrevu> SP;
    public ArrayList<Intervention> interventions;

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures
     * équivalent TD" Pour le calcul : 1 heure de cours magistral vaut 1,5 h
     * "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut
     * 0,75h "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet
     * enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        double equivalentTD = 0;
        for (ServicePrevu s : SP) {
            equivalentTD = s.getVolumeCM() * 1.5 + s.getVolumeTD() + s.getVolumeTP() * 0.75;
        }
        return (int) equivalentTD;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE
     * spécifiée en "heures équivalent TD" Pour le calcul : 1 heure de cours
     * magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent
     * TD" 1 heure de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet
     * enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        double equivalentTD = 0;
        for (ServicePrevu s : SP) {
            if (ue == s.getue()) {
                equivalentTD = s.getVolumeCM() * 1.5 + s.getVolumeTD() + s.getVolumeTP() * 0.75;
            }
        }
        return (int) equivalentTD;
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magitral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        ServicePrevu SPrevu = new ServicePrevu(volumeCM, volumeTD, volumeTP, ue);
        SP.add(SPrevu);
    }

    public boolean enSousService() {
        return heuresPrevues() < 192;
    }

    public void ajouteIntervention(Intervention inter) {
        interventions.add(inter);
    }

    public int resteAPlanifier(UE ue, TypeIntervention type) {
        int sommePlanifiee = 0;

        for (Intervention inter : interventions) {
            if (inter.getType().equals(type) && inter.getUe().equals(ue)) {
                sommePlanifiee += inter.getDuree();
            }
        }

        for (ServicePrevu s : SP) {
            if (s.getue().equals(ue)) {
                switch (type) {
                    case TP:
                        sommePlanifiee -= s.getVolumeTP();
                    case TD:
                        sommePlanifiee -= s.getVolumeTD();
                    case CM:
                        sommePlanifiee -= s.getVolumeCM();
                        break;
                }
            }

        }
        return (int) sommePlanifiee;

    }

    }
