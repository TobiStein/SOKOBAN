package modele;

public class Bloc extends Entite {


    public Bloc(Jeu _jeu, Case c) {
        super(_jeu, c);
    }

    public boolean pousser(Entite e, Direction d) {
        if (!this.isDesactive() && !(e.getCase() instanceof Glace)) {
            return jeu.deplacerEntite(this, d);
        } else {
            return false;
        }
    }


}
