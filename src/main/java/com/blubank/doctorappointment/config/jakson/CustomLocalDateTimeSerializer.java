package com.blubank.doctorappointment.config.jakson;

import com.blubank.doctorappointment.enums.DateFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {


    public CustomLocalDateTimeSerializer() {
        this(null);
    }

    public CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializerProvider)
            throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat.BASIC_DATE_TIME.getKey());
        gen.writeString(localDateTime.format(dateTimeFormatter));
    }
}
