package com.blubank.doctorappointment.config.jakson;

import com.blubank.doctorappointment.enums.DateFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CustomLocalDateDeserializer extends StdDeserializer<LocalDate> {

    public CustomLocalDateDeserializer() {
        this(null);
    }

    public CustomLocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat.BASIC_DATE.getKey());
        return LocalDate.parse(jsonparser.getText(), dateTimeFormatter);
    }
}
