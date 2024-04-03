package modele;

public class Piege extends Case{
    private int passage = 1;

    public Piege(Jeu _jeu){
        super(_jeu);
    }
    @Override
    public void quitterLaCase() {
        e = e;
    }

    @Override
    public boolean peutEtreParcouru() {
        return e == null;
    }
}
