package modele;

public class CaseObjectif extends Vide{
    private int id;

    public CaseObjectif(Jeu _jeu, int _id) {
        super(_jeu);
        id = _id;
    }

    public int getId() {
        return id;
    }

    public BlocObjectif getBlocObjectif() {
        return (BlocObjectif)e;
    }
}
