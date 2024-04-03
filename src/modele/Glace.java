package modele;

public class Glace extends Case{

    public Glace(Jeu _jeu) {
        super(_jeu);
    }

    @Override
    public boolean peutEtreParcouru() {
        return e == null;
    }

//    public boolean glisser(Direction d) {
//        return jeu.deplacerEntite(this.getEntite(), d);
//    }


}
