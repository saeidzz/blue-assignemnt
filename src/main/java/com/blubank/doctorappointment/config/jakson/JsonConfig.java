package com.blubank.doctorappointment.config.jakson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
public class JsonConfig {

    @Autowired
    private CustomLocalDateTimeSerializer customLocalDateTimeSerializer;
    @Autowired
    private CustomLocalDateTimeDeserializer customLocalDateTimeDeserializer;
    @Autowired
    private CustomLocalDateDeserializer customLocalDateDeserializer;
    @Autowired
    private CustomLocalDateSerializer customLocalDateSerializer;
    @Autowired
    private CustomTimestampSerializer customTimestampSerializer;


    @Bean
    public ObjectMapper jsonObjectMapper() {

        SimpleModule module = new SimpleModule();

        module.addDeserializer(LocalDate.class, customLocalDateDeserializer);
        module.addSerializer(LocalDate.class, customLocalDateSerializer);

        module.addDeserializer(LocalDateTime.class, customLocalDateTimeDeserializer);
        module.addSerializer(LocalDateTime.class, customLocalDateTimeSerializer);

        module.addSerializer(Timestamp.class, customTimestampSerializer);

        ArrayList<Module> modules = new ArrayList<>();

        modules.add(module);

        ObjectMapper result = Jackson2ObjectMapperBuilder.json()
                .modules(modules)
                .build();
        JsonMapper.init(result);
        return result;
    }
}
