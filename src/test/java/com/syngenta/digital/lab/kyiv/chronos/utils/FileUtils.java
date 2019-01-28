package com.syngenta.digital.lab.kyiv.chronos.utils;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public final class FileUtils {

    private FileUtils() {}

    @SneakyThrows
    public static List<String> parseCsvFile(String pathToCsvFile) {
        var file = new File(URLDecoder.decode(JsonUtils.class.getResource(pathToCsvFile).getFile(), StandardCharsets.UTF_8));
        return Files.newBufferedReader(file.toPath()).lines().collect(Collectors.toList());
    }

    @SneakyThrows
    public static Workbook readXlsxFile(byte[] bytes) {
        return WorkbookFactory.create(new ByteArrayInputStream(bytes));
    }
}
