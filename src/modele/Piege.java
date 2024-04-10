package modele;

public class Piege extends Case{
    private boolean ouvert;
    private boolean deactive;

    public Piege(Jeu _jeu){
        super(_jeu);
        ouvert = false;
        deactive = false;
    }

    @Override
    public boolean peutQuitterLaCase(Direction d){
        if (!ouvert) {
            ouvert = true;
            return true;
        } else if (deactive) {
            return true;
        } else {
            return false;
        }
    }

    public boolean entrerSurLaCase(Entite e, Direction d) {
        if(ouvert && !deactive){
            e.setDesactive(true);
            if (e instanceof Bloc || e instanceof  BlocObjectif) {
                deactive = true;
            }
        }
        super.entrerSurLaCase(e, d);
        return true;
    }

    @Override
    public boolean peutEtreParcouru() {
        return true;
    }

    public boolean isOuvert() {
        return ouvert;
    }

    public boolean isDeactive() {
        return deactive;
    }

    public void setOuvert(boolean ouvert) {
        this.ouvert = ouvert;
    }
}