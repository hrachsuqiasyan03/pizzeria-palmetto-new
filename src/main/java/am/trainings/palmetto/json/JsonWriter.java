package am.trainings.palmetto.json;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
public class JsonWriter {
    private static JsonWriter INSTANCE =  new JsonWriter();
    private final ObjectMapper mapper = new ObjectMapper();

    private JsonWriter() {

    }
    public static JsonWriter getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new JsonWriter ();
        }

        return INSTANCE;
    }

    public void write(Object data, String filePath){

         try{
             mapper.writerWithDefaultPrettyPrinter()
                     .writeValue (new File (filePath), data);
         }catch(IOException e){
             throw new RuntimeException(
                     "Failed to write JSON file: " + filePath, e);
         }

    }

}
