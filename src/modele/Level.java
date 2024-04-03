package modele;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Level {

    @JsonProperty("SIZE_X")
    private int sizeX;

    @JsonProperty("SIZE_Y")
    private int sizeY;

    @JsonProperty("numObjectif")
    private int numObjectif;

    @JsonProperty("hero")
    private Coordinate hero;

    @JsonProperty("BlocObjectif")
    private List<Coordinate> listCoordinateBlocObjectif;

    @JsonProperty("CaseObjectif")
    private List<Coordinate> listeCoordinateCaseObjectif;

    @JsonProperty("Bloc")
    private List<Coordinate> listCoordinateBloc;

    @JsonProperty("Wall")
    private List<Coordinate> listCoordinateWall;

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

    public List<Coordinate> getGetListCoordinateWall() {
        return listCoordinateWall;
    }
}