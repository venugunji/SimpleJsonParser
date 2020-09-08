package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jackson.model.Car;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class StringParser {

   final  ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void stringToObject() throws IOException {

        String jsonString ="{\n" +
                "    \"brand\":\"toyota\",\n" +
                "    \"doors\":5\n" +
                "}";


        //String
        long startTime = System.nanoTime();
        Car car = objectMapper.readValue(jsonString,Car.class);
        System.out.println(car);
        long endTime = System.nanoTime();
        System.out.println("Time Taken: "+(endTime - startTime)/1000000 +"ms");

    }

    @Test
    public void stringReaderToObject() throws IOException {

        String jsonString ="{\n" +
                "    \"brand\":\"toyota\",\n" +
                "    \"doors\":5\n" +
                "}";
        Reader reader = new StringReader(jsonString);
        Car car = objectMapper.readValue(reader,Car.class);
        System.out.println(car);
    }


    @Test
    public void fileReaderToObject() throws IOException {
        FileReader fileReader = new FileReader("src/test/resources/input.json");
        Car car = objectMapper.readValue(fileReader,Car.class);
        System.out.println(car);
    }
    @Test
    public void fileToObject() throws IOException {
        File file = new File("src/test/resources/input.json");
        Car car = objectMapper.readValue(file,Car.class);
        System.out.println(car);
    }
    @Test
    public void URLToObject_localfile() throws IOException {
        URL file = new URL("file:src/test/resources/input.json");
        Car car = objectMapper.readValue(file,Car.class);
        System.out.println(car);
    }

    @Disabled
    @Test
    public void URLToObject_remotefile() throws IOException {
        URL file = new URL("http://venu.com/some-data.json");
        Car car = objectMapper.readValue(file,Car.class);
        System.out.println(car);
    }

    @Test
    public void inputStreamToObject() throws IOException {
        InputStream stream = new FileInputStream("src/test/resources/input.json");
        Car car = objectMapper.readValue(stream,Car.class);
        System.out.println(car);
    }
    @Test
    public void byteArrayToObject() throws IOException {
        String jsonString ="{\n" +
                "    \"brand\":\"toyota\",\n" +
                "    \"doors\":5\n" +
                "}";

        byte[] bytes = jsonString.getBytes();
        Car car = objectMapper.readValue(bytes,Car.class);
        System.out.println(car);
    }
    @Test
    public void jsonArrayToObject() throws IOException {
        String jsonString ="[{\n" +
                "    \"brand\":\"toyota\",\n" +
                "    \"doors\":5\n" +
                "}]";

        Car[] cars = objectMapper.readValue(jsonString,new TypeReference<Car[]>(){});
        System.out.println(cars[0]);
    }
    @Test
    public void jsonArrayToArrayList() throws IOException {
        String jsonString ="[{\n" +
                "    \"brand\":\"toyota\",\n" +
                "    \"doors\":5\n" +
                "}]";

        List<Car> cars= objectMapper.readValue(jsonString,new TypeReference<List<Car>>(){});
        System.out.println(cars);
    }
    @Test
    public void jsonStringToMap() throws IOException {
        String jsonString ="{\n" +
                "    \"brand\":\"toyota\",\n" +
                "    \"doors\":5\n" +
                "}";

       Map<String,Object> stringObjectMap= objectMapper.readValue(jsonString,new TypeReference<Map<String,Object>>(){});
        System.out.println(stringObjectMap);
    }


    @Test
    public void UnknownFields() throws JsonProcessingException {

        String jsonString ="{\n" +
                "    \"brand\":\"toyota\",\n" +
                "    \"doors\":5,\n" +
                "    \"price\":\"30k\"\n" +
                "}";
        Assertions.assertThrows(UnrecognizedPropertyException.class,()->{
            Car car =objectMapper.readValue(jsonString,Car.class);
            System.out.println(car);
        });
    }
    @Test
    public void IgnoreUnknownFields() throws JsonProcessingException {

        String jsonString ="{\n" +
                "    \"brand\":\"toyota\",\n" +
                "    \"doors\":5,\n" +
                "    \"price\":\"30k\"\n" +
                "}";
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        Car car =objectMapper.readValue(jsonString,Car.class);
        System.out.println(car);
    }
    @Test
    public void failOnNullForPrimitives() throws JsonProcessingException {

        String jsonString ="{\n" +
                "    \"brand\":\"toyota\",\n" +
                "    \"doors\":null,\n" +
                "    \"price\":\"30k\"\n" +
                "}";
       Assertions.assertThrows(MismatchedInputException.class,()-> {

           objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
           Car car = objectMapper.readValue(jsonString, Car.class);
           System.out.println(car);
           });
    }

//    @Test
//    public void test(){
//        Supplier<Double> doubleValue = StringParser::+"`getDoubleValue`";
//        System.out.println(doubleValue.get());
//    }
    private static Double getDoubleValue() {
        return 50.0;
    }
}
