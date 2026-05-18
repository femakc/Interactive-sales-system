package org.example.services.parsers;

public interface LineParser<T> {
    T parse(String line, String delimiter);
}
