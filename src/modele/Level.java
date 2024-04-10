package modele;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Level {

    @JsonProperty("SIZE_X")
    private int sizeX;

    @JsonProperty("SIZE_Y")
    private int sizeY;

    @JsonProperty("numObjectif")
    private int numObjectif;

    @JsonProperty("pasMinimum")
    private int pasMinimum;

    @JsonProperty("hero")
    private Coordonnee hero;

    @JsonProperty("BlocObjectif")
    private List<Coordonnee> listeCoordBlocObjectif;

    @JsonProperty("CaseObjectif")
    private List<Coordonnee> listeCoordCaseObjectif;

    @JsonProperty("Bloc")
    private List<Coordonnee> listeCoordBloc;

    @JsonProperty("Mur")
    private List<Coordonnee> listeCoordMur;

    @JsonProperty("Piege")
    private List<Coordonnee> listeCoordPiege;

    @JsonProperty("Glace")
    private List<Coordonnee> listeCoordGlace;

    @JsonProperty("Rail")
    private List<Coordonnee> listeCoordRails;

    public static class Coordonnee {
        @JsonProperty("x")
        private int x;

        @JsonProperty("y")
        private int y;

        @JsonProperty("id")
        private int id;

        @JsonProperty("type")
        private String type;

        @JsonProperty("ouvert")
        private boolean ouvert;

        // getters and setters

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getId() { // And this method
            return id;
        }

        public String getType() {
            return type;
        }

        public boolean isOuvert() {
            return ouvert;
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getNumObjectif() {
        return numObjectif;
    }

    public int getPasMinimum() {
        return pasMinimum;
    }

    public Coordonnee getHero() {
        return hero;
    }

    public List<Coordonnee> getListeCoordBlocObjectif() {
        return listeCoordBlocObjectif;
    }

    public List<Coordonnee> getListeCoordCaseObjectif() {
        return listeCoordCaseObjectif;
    }

    public List<Coordonnee> getListeCoordBloc() {
        return listeCoordBloc;
    }

    public List<Coordonnee> getListeCoordMur() {
        return listeCoordMur;
    }

    public List<Coordonnee> getListeCoordPiege() {
        return listeCoordPiege;
    }

    public List<Coordonnee> getListeCoordGlace() {
        return listeCoordGlace;
    }

    public List<Coordonnee> getListeCoordRails() {
        return listeCoordRails;
    }
}