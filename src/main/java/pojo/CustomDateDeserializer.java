package pojo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import datautil.PrintUtil;

public class CustomDateDeserializer extends StdDeserializer<Date> {

    public static String pattern = "yyyy-MM-dd'T'HH:mm:ss XXXXX";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public CustomDateDeserializer(){
        this(null);
    }

    public CustomDateDeserializer(Class<?> c){
        super(c);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) throws IOException {
        String date = jsonParser.getText();
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            PrintUtil.printData(CustomDateDeserializer.class.getSimpleName() + " : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}