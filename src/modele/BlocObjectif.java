package modele;

public class BlocObjectif extends Bloc{
    private int id;

    public BlocObjectif(Jeu _jeu, Case c,  int _id) {
        super(_jeu, c);
        id = _id;
    }

    public int getId() {
        return id;
    }
}
