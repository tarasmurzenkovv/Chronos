package com.syngenta.digital.lab.kyiv.chronos.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class JsonUtils {

    @SneakyThrows
    public static <T> T readFromJson(String pathToJson, Class<T> tClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        var file = new File(URLDecoder.decode(JsonUtils.class.getResource(pathToJson).getFile(), StandardCharsets.UTF_8));
        return objectMapper.readValue(file, tClass);
    }

    @SneakyThrows
    public static <T> GeneralResponse<T> readFromJson(String pathToJson, TypeReference<GeneralResponse<T>> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        var file = new File(URLDecoder.decode(JsonUtils.class.getResource(pathToJson).getFile(), StandardCharsets.UTF_8));
        return objectMapper.readValue(file, typeReference);
    }
}
