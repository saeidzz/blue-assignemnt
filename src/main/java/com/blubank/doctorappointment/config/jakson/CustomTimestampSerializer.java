package com.blubank.doctorappointment.config.jakson;

import com.blubank.doctorappointment.enums.DateFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

@Component
public class CustomTimestampSerializer extends StdSerializer<Timestamp> {

    public CustomTimestampSerializer() {
        this(null);
    }

    public CustomTimestampSerializer(Class<Timestamp> t) {
        super(t);
    }


    @Override
    public void serialize(
            Timestamp timestamp,
            JsonGenerator gen,
            SerializerProvider arg2)
            throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat.BASIC_DATE.getKey());
        gen.writeString(timestamp.toLocalDateTime().format(dateTimeFormatter));
    }
}
