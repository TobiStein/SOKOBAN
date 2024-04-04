package modele;

public class Glace extends Case{

    public Glace(Jeu _jeu) {
        super(_jeu);
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

}
