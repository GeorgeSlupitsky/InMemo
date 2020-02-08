package ua.slupitsky.in_memo.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ua.slupitsky.in_memo.constants.DefaultAppConstants;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String stringDate = jsonParser.readValueAs(String.class).replace(DefaultAppConstants.DOT, DefaultAppConstants.PATH_DELIMITER);
        LocalDate date;
        DateTimeFormatter dateTimeFormatter;
        try {
            dateTimeFormatter = DateTimeFormatter.ofPattern(DefaultAppConstants.DATE_TIME_FORMATTER_PATTERN_SLASH);
            date = LocalDate.parse(stringDate, dateTimeFormatter);
        } catch (DateTimeParseException e){
            dateTimeFormatter = DateTimeFormatter.ofPattern(DefaultAppConstants.DATE_TIME_FORMATTER_PATTERN_DASH);
            date = LocalDate.parse(stringDate, dateTimeFormatter);
        }
        return date;
    }

}
