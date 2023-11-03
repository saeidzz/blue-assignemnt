package com.blubank.doctorappointment.config.jakson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonMapper {

    private static final Logger log = LoggerFactory.getLogger(JsonMapper.class);
    private static ObjectMapper mapper;

    public static void init(ObjectMapper mapperBean) {
        mapper = mapperBean;
    }

    public static String asJsonString(final Object obj) {
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return json;
    }

    public static <T> Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(json, objectClass);
    }
}
