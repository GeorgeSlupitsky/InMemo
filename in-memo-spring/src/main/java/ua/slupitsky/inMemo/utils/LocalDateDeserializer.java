package ua.slupitsky.inMemo.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String stringDate = jsonParser.readValueAs(String.class).replace(".","/");
        LocalDate date;
        DateTimeFormatter dateTimeFormatter;
        try {
            dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            date = LocalDate.parse(stringDate, dateTimeFormatter);
        } catch (DateTimeParseException e){
            dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(stringDate, dateTimeFormatter);
        }
        return date;
    }


}
