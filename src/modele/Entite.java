package modele;

/**
 * Entités amenées à bouger
 */
public abstract class Entite extends Obj {
    protected boolean desactive;
    protected Case c;

    public Entite(Jeu _jeu, Case _c) {
        super(_jeu);
        c = _c;
        desactive = false;
        c.setEntite(this);
    }

    public void quitterCase() {
        c = null;
    }

    public Case getCase() {
        return c;
    }

    public void setCase(Case _c) {
        c = _c;
    }

    public void allerSurCase(Case _c) {
        c = _c;
    }

    public boolean pousser(Direction d) {
        return false;
    }


    public boolean avancerDirectionChoisie(Direction d) {

        return jeu.deplacerEntite(this, d);
    }

    public void setDesactive(boolean _desactive) {
        this.desactive = _desactive;
    }

    public boolean estDesactive() {
        return desactive;
    }

    public void glisser(Direction d) { jeu.deplacerEntite(this, d);}
}
