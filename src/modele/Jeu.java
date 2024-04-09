/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;


public class Jeu extends Observable {

    public static int SIZE_X; // 20
    public static int SIZE_Y;



    private Heros heros;
    private CaseObjectif[] listeCaseObjectif;
    private BlocObjectif[] listeBlocObjectif;

    private HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    private Case[][] grilleEntites; // permet de récupérer une case à partir de ses coordonnées
    private int niv;

    private Level niveau;
    private int pas;


    public Jeu() {
        try {
            niveau = chargerNiveauJson("levels/level4.json");
            SIZE_X = niveau.getSizeX();
            SIZE_Y = niveau.getSizeY();
            grilleEntites = new Case[SIZE_X][SIZE_Y];
            niv = 1;
            pas = 0;
            initialisationNiveau(niveau);
        } catch (NullPointerException e) {
            System.out.println("Impossible de charger niveau");
        }
    }

    public Jeu(int n) {
        try {
            niveau = chargerNiveauJson("levels/level"+n+".json");
            SIZE_X = niveau.getSizeX();
            SIZE_Y = niveau.getSizeY();
            grilleEntites = new Case[SIZE_X][SIZE_Y];
            niv = n;
            initialisationNiveau(niveau);
        } catch (NullPointerException e) {
            System.out.println("Impossible de charger niveau");
        }
    }


    
    public Case[][] getGrille() {
        return grilleEntites;
    }
    
    public Heros getHeros() {
        return heros;
    }

    public void deplacerHeros(Direction d) {
        heros.avancerDirectionChoisie(d);
        pas++;
        System.out.println(pas);
        setChanged(); //
        notifyObservers();
    }

    
    private void initialisationNiveau(Level n) {
        // remplit la grille de vide
        niveau = n;
        for (int x = 0; x < SIZE_X; x++){
            for (int y = 0; y < SIZE_Y; y++){
                addCase(new Vide(this), x, y);
            }
        }

        // ajout les murs
        List<Level.Coordonnee> listeMur = niveau.getListeCoordMur();
        for (int m = 0; m < listeMur.size(); m++){
            addCase(new Mur(this), listeMur.get(m).getX(), listeMur.get(m).getY());
        }

        listeBlocObjectif = new BlocObjectif[niveau.getNumObjectif()];
        listeCaseObjectif = new CaseObjectif[niveau.getNumObjectif()];

        //ajout blocs objectifs
        List<Level.Coordonnee> listeBlocObjectifCoor = niveau.getListeCoordBlocObjectif();
        List<Level.Coordonnee> listeCaseObjectifCoor = niveau.getListeCoordCaseObjectif();

        for (int i = 0; i < niveau.getNumObjectif(); i++) {
            listeCaseObjectif[i] = new CaseObjectif(this, listeCaseObjectifCoor.get(i).getId());
            addCase(listeCaseObjectif[i], listeCaseObjectifCoor.get(i).getX(), listeCaseObjectifCoor.get(i).getY());
            listeBlocObjectif[i] = new BlocObjectif(this, grilleEntites[listeBlocObjectifCoor.get(i).getX()][listeBlocObjectifCoor.get(i).getY()], listeBlocObjectifCoor.get(i).getId());
        }

        // ajout des blocs Normaux s'il y en a
        List<Level.Coordonnee> listeBlocCoor = niveau.getListeCoordBloc();

        if (listeBlocCoor.size() != 0) {
            for (int i = 0; i < listeBlocCoor.size(); i++){
                new Bloc(this, grilleEntites[listeBlocCoor.get(i).getX()][listeBlocCoor.get(i).getY()]);
            }
        }

        // ajout des blocs Pièges s'il y en a
        List<Level.Coordonnee> listePiegeCoor = niveau.getListeCoordPiege();

        if (listePiegeCoor.size() != 0) {
            for (int i = 0; i < listePiegeCoor.size(); i++){
                Piege piege = new Piege(this);
                addCase(piege, listePiegeCoor.get(i).getX(), listePiegeCoor.get(i).getY());
            }
        }

        // ajout des blocs Glaces s'il y en a
        List<Level.Coordonnee> listeGlaceCoor = niveau.getListeCoordGlace();

        if (listeGlaceCoor.size() != 0) {
            for (int i = 0; i < listeGlaceCoor.size(); i++){
                Glace glace = new Glace(this);
                addCase(glace, listeGlaceCoor.get(i).getX(), listeGlaceCoor.get(i).getY());
            }
        }

        heros = new Heros(this, grilleEntites[niveau.getHero().getX()][niveau.getHero().getY()]);
    }

    private void addCase(Case e, int x, int y) {
        grilleEntites[x][y] = e;
        map.put(e, new Point(x, y));
    }
    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = true;
        
        Point pCourant = map.get(e.getCase());
        
        Point pCible = calculerPointCible(pCourant, d);

        if (contenuDansGrille(pCible)) {
            Entite eCible = caseALaPosition(pCible).getEntite();
            if (eCible != null) { //si j'ai un bloc
                // si on vient d'arrivé sur le bloc
                if(e.getCase() instanceof Glace && !((Glace) e.getCase()).aPousseDepuisGlace()){
                    // alors on ne pousse pas le bloc
                    ((Glace) e.getCase()).setaPousseDepuisGlace(true);
                } else {
                    // si on est sur le bloc de glace et que l'on souhaite pousser
                    if(e.getCase() instanceof Glace){
                        ((Glace) e.getCase()).setaPousseDepuisGlace(false);
                    }
                    eCible.pousser(d);
                }
            }

            // si la case est libérée
            if (caseALaPosition(pCible).peutEtreParcouru()) {
                caseALaPosition(pCible).entrerSurLaCase(e, d);
//                if (caseALaPosition(pCible) instanceof Glace){
//                    eCible.glisser(d);
//                    pCible = calculerPointCible(pCible, d);
//                }

            } else {
                retour = false;
            }

        } else {
            retour = false;
        }
        return retour;
    }
    
    
    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
        
        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;     
            
        }
        
        return pCible;
    }
    

    
    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Case caseALaPosition(Point p) {
        Case retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }

    public Level chargerNiveauJson(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), Level.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean finDePartie() {
        for (int i = 0; i < listeCaseObjectif.length; i++) {
            if (!(listeCaseObjectif[i].getEntite() instanceof BlocObjectif) || listeCaseObjectif[i].getBlocObjectif().getId() != listeCaseObjectif[i].getId()) {
                return false;
            }
        }
        return true;
    }

    public int getNiveau(){
        return niv;
    }

    public int getPasMin(){
        return niveau.getPasMinimum();
    }

    public int getPas(){
        return pas;
    }

    public void updateNiveau(int n){
        niv = n+1;
        niveau = chargerNiveauJson("levels/level"+niv+".json");
        SIZE_X = niveau.getSizeX();
        SIZE_Y = niveau.getSizeY();
        initialisationNiveau(niveau);
        setChanged();
        notifyObservers();
    }
}
