package modele;

public class BlocObjectif extends Bloc{
    private int id;

    public BlocObjectif(Jeu _jeu, Case c,  int _id) {
        super(_jeu, c);
        id = _id;
    }

    public boolean pousser(Entite e, Direction d) {
        if (!this.isDesactive() && !(e.getCase() instanceof Glace)) {
            return jeu.deplacerEntite(this, d);
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }
}
