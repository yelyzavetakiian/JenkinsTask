package parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public <T> T convertResponseToObject(String response, Class<T> tClass) {
            return mapper.readValue(response, tClass);
    }

    @SneakyThrows
    public <T> List<T> convertResponseToListOfObjects(String response, Class<T> tClass) {
            return mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass));
    }
}
