package modele;

public class Glace extends Case{
    private boolean aPousseDepuisGlace;

    public Glace(Jeu _jeu) {
        super(_jeu);
        aPousseDepuisGlace = false;
    }

    @Override
    public boolean peutEtreParcouru() {
        return e == null;
    }

    public boolean entrerSurLaCase(Entite e, Direction d) {
        super.entrerSurLaCase(e,d);
        jeu.deplacerEntite(e, d);
        return true;
    }

    public boolean aPousseDepuisGlace() {
        return aPousseDepuisGlace;
    }

    public void setaPousseDepuisGlace(boolean aPousseDepuisGlace) {
        this.aPousseDepuisGlace = aPousseDepuisGlace;
    }
}
