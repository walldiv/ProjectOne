package com.ex.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class JSONScheduleDeserializer extends StdDeserializer {

    public JSONScheduleDeserializer() {
        this(null);
    }

    public JSONScheduleDeserializer(Class<?> c) {
        super(c);
    }



    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if(node.get("teamOne").asText().isEmpty())
            return null;
        else return new Schedule();
    }
}
