import com.google.gson.Gson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Data;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        int gson_win_count = 0;
        int jackson_win_count = 0;
        int max = 100;

        for(int i = 0;i<max;i++){
            long jackson_start_time = System.nanoTime();
            jacksonSample();
            long jackson_end_time = System.nanoTime();
            long jackson_time = (jackson_end_time - jackson_start_time);
            System.out.println("Jackson : " + jackson_time + "ナノ秒");

            long gson_start_time = System.nanoTime();
            gsonSample();
            long gson_end_time = System.nanoTime();
            long gson_time = (gson_end_time - gson_start_time);
            System.out.println("Gson : " + gson_time + "ナノ秒");

            if(gson_time > jackson_time){
                System.out.println("Jacksonのほうが「 " + (gson_time - jackson_time) + " 」ナノ秒速いです。\n");
                jackson_win_count++;
            }else {
                System.out.println("Gsonのほうが「 " + (jackson_time - gson_time) + " 」ナノ秒速いです。\n");
                gson_win_count++;
            }
        }

        System.out.println(max + "回中\n" +
                "Jacksonの勝利数:[ " + jackson_win_count +" ]回\n" +
                "Gsonの勝利数:[ " + gson_win_count + " ]回");
    }

    public static void jacksonSample() throws IOException {
//        InputStream is = ClassLoader.getSystemResourceAsStream("table_schema.json");
        ObjectMapper mapper = new ObjectMapper();
//        List<Schema> schemaList = mapper.readValue(is, new TypeReference<List<Schema>>() {});
        List<Schema> schemaList = mapper.readValue(new File("src/main/resources/table_schema.json"), new TypeReference<List<Schema>>() {});
    }

    public static void gsonSample() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/table_schema.json"));
        List<Schema> schemaList = gson.fromJson(reader, new TypeToken<List<Schema>>() {
        }.getType());
    }
}

@Data
class Schema{
    private String mode;
    private String name;
    private String type;
}