package com.blubank.doctorappointment.config.jakson;

import com.blubank.doctorappointment.enums.DateFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CustomLocalDateSerializer extends StdSerializer<LocalDate> {

    public CustomLocalDateSerializer() {
        this(null);
    }

    public CustomLocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }


    @Override
    public void serialize(
            LocalDate localDate,
            JsonGenerator gen,
            SerializerProvider arg2)
            throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat.BASIC_DATE.getKey());
        String formattedDate = localDate.format(dateTimeFormatter);
        gen.writeString(formattedDate);
    }
}
