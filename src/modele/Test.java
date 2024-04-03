package modele;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;


public class Test {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Level level = mapper.readValue(new File("levels/level1.json"), Level.class);
            String prettyLevel = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(level);
            System.out.println(level.getSizeX());
            System.out.println(level.getSizeY());
            System.out.println(level.getNumObjectif());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
