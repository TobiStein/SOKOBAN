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
    private int niveau;

    private Level level;
    private int pas;


    public Jeu() {
        try {
            level = chargerNiveauJson("levels/level4.json");
            SIZE_X = level.getSizeX();
            SIZE_Y = level.getSizeY();
            grilleEntites = new Case[SIZE_X][SIZE_Y];
            niveau = 1;
            pas = 0;
            initialisationNiveau(level);
        } catch (NullPointerException e) {
            System.out.println("Impossible de charger niveau");
        }
    }

    public Jeu(int n) {
        try {
            level = chargerNiveauJson("levels/level"+n+".json");
            SIZE_X = level.getSizeX();
            SIZE_Y = level.getSizeY();
            grilleEntites = new Case[SIZE_X][SIZE_Y];
            niveau = n;
            initialisationNiveau(level);
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

    
    private void initialisationNiveau(Level level) {
        // remplit la grille de vide
        level = level;
        for (int x = 0; x < SIZE_X; x++){
            for (int y = 0; y < SIZE_Y; y++){
                addCase(new Vide(this), x, y);
            }
        }

        // ajout les murs
        List<Level.Coordinate> listeMur = level.getGetListCoordinateMur();
        for (int m = 0; m < listeMur.size(); m++){
            addCase(new Mur(this), listeMur.get(m).getX(), listeMur.get(m).getY());
        }

        listeBlocObjectif = new BlocObjectif[level.getNumObjectif()];
        listeCaseObjectif = new CaseObjectif[level.getNumObjectif()];

        //ajout blocs objectifs
        List<Level.Coordinate> listBlocObjectifCoor = level.getListCoordinateBlocObjectif();
        List<Level.Coordinate> listCaseObjectifCoor = level.getListeCoordinateCaseObjectif();

        for (int i = 0; i < level.getNumObjectif(); i++) {
            listeCaseObjectif[i] = new CaseObjectif(this, listCaseObjectifCoor.get(i).getId());
            addCase(listeCaseObjectif[i], listCaseObjectifCoor.get(i).getX(), listCaseObjectifCoor.get(i).getY());
            listeBlocObjectif[i] = new BlocObjectif(this, grilleEntites[listBlocObjectifCoor.get(i).getX()][listBlocObjectifCoor.get(i).getY()], listBlocObjectifCoor.get(i).getId());
        }

        // ajout des blocs Normaux s'il y en a
        List<Level.Coordinate> listBlocCoor = level.getListCoordinateBloc();

        if (listBlocCoor.size() != 0) {
            for (int i = 0; i < listBlocCoor.size(); i++){
                new Bloc(this, grilleEntites[listBlocCoor.get(i).getX()][listBlocCoor.get(i).getY()]);
            }
        }

        // ajout des blocs Pièges s'il y en a
        List<Level.Coordinate> listPiegeCoor = level.getListCoordinatePiege();

        if (listPiegeCoor.size() != 0) {
            for (int i = 0; i < listPiegeCoor.size(); i++){
                Piege piege = new Piege(this);
                addCase(piege, listPiegeCoor.get(i).getX(), listPiegeCoor.get(i).getY());
            }
        }

        // ajout des blocs Glaces s'il y en a
        List<Level.Coordinate> listGlaceCoor = level.getListCoordinateGlace();

        if (listGlaceCoor.size() != 0) {
            for (int i = 0; i < listGlaceCoor.size(); i++){
                Glace glace = new Glace(this);
                addCase(glace, listGlaceCoor.get(i).getX(), listGlaceCoor.get(i).getY());
            }
        }

        heros = new Heros(this, grilleEntites[level.getHero().getX()][level.getHero().getY()]);
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
        return niveau;
    }

    public int getPasMin(){
        return level.getPasMinimum();
    }

    public int getPas(){
        return pas;
    }

    public void updateNiveau(int n){
        niveau = n+1;
        level = chargerNiveauJson("levels/level"+niveau+".json");
        SIZE_X = level.getSizeX();
        SIZE_Y = level.getSizeY();
        initialisationNiveau(level);
        setChanged();
        notifyObservers();
    }
}
