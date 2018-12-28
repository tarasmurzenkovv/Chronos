package com.syngenta.digital.lab.kyiv.chronos.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;

public class JsonUtils {

    @SneakyThrows
    public static <T> T readFromJson(String pathToJson, Class<T> tClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        URL resource = JsonUtils.class.getResource(pathToJson);
        return objectMapper.readValue(new File(resource.getPath()), tClass);
    }

    @SneakyThrows
    public static <T> GeneralResponse<T> readFromJson(String pathToJson, TypeReference<GeneralResponse<T>> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        URL resource = JsonUtils.class.getResource(pathToJson);
        return objectMapper.readValue(new File(resource.getPath()), typeReference);
    }
}
