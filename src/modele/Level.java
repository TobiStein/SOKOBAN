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
    private Coordinate hero;

    @JsonProperty("BlocObjectif")
    private List<Coordinate> listCoordinateBlocObjectif;

    @JsonProperty("CaseObjectif")
    private List<Coordinate> listeCoordinateCaseObjectif;

    @JsonProperty("Bloc")
    private List<Coordinate> listCoordinateBloc;

    @JsonProperty("Mur")
    private List<Coordinate> listCoordinateMur;

    @JsonProperty("Piege")
    private List<Coordinate> listCoordinatePiege;

    @JsonProperty("Glace")
    private List<Coordinate> listCoordinateGlace;

    public static class Coordinate {
        @JsonProperty("x")
        private int x;

        @JsonProperty("y")
        private int y;

        @JsonProperty("id")
        private int id;

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

    public Coordinate getHero() {
        return hero;
    }

    public List<Coordinate> getListCoordinateBlocObjectif() {
        return listCoordinateBlocObjectif;
    }

    public List<Coordinate> getListeCoordinateCaseObjectif() {
        return listeCoordinateCaseObjectif;
    }

    public List<Coordinate> getListCoordinateBloc() {
        return listCoordinateBloc;
    }

    public List<Coordinate> getGetListCoordinateMur() {
        return listCoordinateMur;
    }

    public List<Coordinate> getListCoordinatePiege() {
        return listCoordinatePiege;
    }

    public List<Coordinate> getListCoordinateGlace() {
        return listCoordinateGlace;
    }
}