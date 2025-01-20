package ru.otus.dataprocessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        var sortedData = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        var iterator = sortedData.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            jsonBuilder.append(entry.getKey()).append(":").append(entry.getValue());
            if (iterator.hasNext()) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("}");

        try {
            Files.writeString(Paths.get(fileName), jsonBuilder.toString());
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
