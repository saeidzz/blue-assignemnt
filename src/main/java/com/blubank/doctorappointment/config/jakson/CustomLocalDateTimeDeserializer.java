package com.blubank.doctorappointment.config.jakson;

import com.blubank.doctorappointment.enums.DateFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    public CustomLocalDateTimeDeserializer() {
        this(null);
    }

    public CustomLocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat.BASIC_DATE_TIME.getKey());
        return LocalDateTime.parse(jsonparser.getText(), dateTimeFormatter);
    }
}
