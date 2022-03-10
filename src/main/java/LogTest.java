import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

class logTest {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        JSONObject seconds = new JSONObject();
        seconds.put("Encoder","running");
        seconds.put("Arm","running");

        JSONObject Time = new JSONObject();
        Time.put("TimeSensititve",seconds);

        JSONObject encoderValues = new JSONObject();
        encoderValues.put("Time","0");
        encoderValues.put("Value","0");

        JSONObject encoder = new JSONObject();
        encoder.put("Encoder", encoderValues);

        JSONObject NoTime = new JSONObject();
        NoTime.put("NonTimeSensitive",encoder);

        JSONArray fulltime = new JSONArray();
        fulltime.add(Time);
        fulltime.add(NoTime);

        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fulltime);
            FileWriter info = new FileWriter("infotest.json");
            info.write(json);
            info.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
