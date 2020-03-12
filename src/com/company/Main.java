package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    1.) Есть документ со списком URL:
    https://drive.google.com/open?id=1wVBKKxpTKvWwuCzqY1cVXCQZYCsdCXTl
    Вывести топ 10 доменов которые встречаются чаще всего. В документе могут встречается пустые и недопустимые строки.
    */

public class Main {

    public static void main(String[] args) {
        textParsing("urls.txt");
    }

    private static void textParsing(String fileName) {
        try {
            List<String> streamFromFiles = Files.readAllLines(Paths.get(fileName))
                    .stream()
                    .filter(v -> v.contains("/"))
                    .filter(v -> v.contains("."))
                    .map(v -> v.substring(0, v.indexOf("/")))
                    .map(v -> v.trim())
                    .map(v -> v.replaceFirst("www.", ""))
                    .map(v -> v.replaceFirst("m.", ""))
                    .collect(Collectors.toList());
            Map<String, Long> map = streamFromFiles
                    .stream()
                    .collect(Collectors.groupingBy(v -> v, Collectors.counting()));
            List<Map.Entry<String, Long>> domains = new ArrayList<>(map.entrySet());
            domains.sort((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()));
            System.out.println("Top 10 domains: " + domains.subList(0, 10));
        } catch (IOException e) {
            System.out.println("File " + fileName + " not found");
        }
    }
}