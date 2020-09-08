package jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jackson.model.Car;
import jackson.util.CarBasicDeserializer;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;

public class CustomDeserializer {

    @Test
    public void testCustomDeserializer() throws IOException {
        SimpleModule module = new SimpleModule("CarDeserializer");
        module.addDeserializer(Car.class, new CarBasicDeserializer(Car.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);

        FileReader fileReader = new FileReader("src/test/resources/input.json");

        Car car = objectMapper.readValue(fileReader,Car.class);
        System.out.println(car);
    }
}
