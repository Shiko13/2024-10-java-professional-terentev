package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try {
            String json;
            try (var inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
                if (inputStream == null) {
                    throw new FileProcessException("Файл не найден: " + fileName);
                }

                json = new String(inputStream.readAllBytes()).trim();
            }

            if (!json.startsWith("[") || !json.endsWith("]")) {
                throw new FileProcessException("Некорректный формат JSON, ожидается массив");
            }

            String trimmedJson = json.substring(1, json.length() - 1);
            if (trimmedJson.isEmpty()) {
                return Collections.emptyList();
            }

            List<Measurement> measurements = new ArrayList<>();

            for (String entry : trimmedJson.split("},\\{")) {
                String cleanedEntry = entry.replace("{", "").replace("}", "");
                Map<String, String> keyValuePairs = Arrays.stream(cleanedEntry.split(","))
                        .map(pair -> pair.split(":"))
                        .collect(Collectors.toMap(
                                pair -> pair[0].trim().replaceAll("\"", ""),
                                pair -> pair[1].trim()
                        ));

                String name = keyValuePairs.get("name");
                double value = Double.parseDouble(keyValuePairs.get("value"));
                measurements.add(new Measurement(name, value));
            }

            return measurements;
        } catch (IOException | NumberFormatException e) {
            throw new FileProcessException(e);
        }
    }
}
