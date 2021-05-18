package pojo;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {

    public static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern(CustomDateDeserializer.pattern)
                    .toFormatter();

    public CustomDateTimeDeserializer(){
        this(null);
    }

    public CustomDateTimeDeserializer(Class<?> c){
        super(c);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        return OffsetDateTime.parse(date, this.formatter);
    }
}