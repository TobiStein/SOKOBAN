package modele;
import java.util.ArrayList;
import java.util.List;

public class Rail extends Case {
    private String type;
    private List<Direction> entree;
    private List<Direction> sortie;

    public Rail(Jeu _jeu, String _type) {
        super(_jeu);
        type = _type;
        entree = new ArrayList<>();
        sortie = new ArrayList<>();
        switch (_type) {
            case "horizontal" :
                entree.add(Direction.droite);
                entree.add(Direction.gauche);
                sortie.add(Direction.droite);
                sortie.add(Direction.gauche);
                break;
            case "vertical":
                entree.add(Direction.haut);
                entree.add(Direction.bas);
                sortie.add(Direction.haut);
                sortie.add(Direction.bas);
                break;
            case "angle_bas_droit":
                entree.add(Direction.droite);
                entree.add(Direction.bas);
                sortie.add(Direction.haut);
                sortie.add(Direction.gauche);
                break;
            case "angle_bas_gauche":
                entree.add(Direction.gauche);
                entree.add(Direction.bas);
                sortie.add(Direction.haut);
                sortie.add(Direction.droite);
                break;
            case "angle_haut_droit":
                sortie.add(Direction.gauche);
                sortie.add(Direction.bas);
                entree.add(Direction.haut);
                entree.add(Direction.droite);
                break;
            case "angle_haut_gauche":
                sortie.add(Direction.droite);
                sortie.add(Direction.bas);
                entree.add(Direction.haut);
                entree.add(Direction.gauche);
                break;
        }
    }

    @Override
    public boolean entrerSurLaCase(Entite e, Direction d) {
        if (e instanceof Bloc) {
            for (int i = 0; i < entree.size(); i++){
                if(entree.get(i) == d){
                    return super.entrerSurLaCase(e, d);
                }
            }
            return false;
        }
        return super.entrerSurLaCase(e, d);
    }

    @Override
    public boolean peutQuitterLaCase(Direction d) {
        if (this.e instanceof Bloc){
            for (int i = 0; i < sortie.size(); i++){
                if(sortie.get(i) == d){
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public boolean peutEtreParcouru() {
        return e == null;
    }

    public String getType() {
        return type;
    }
}