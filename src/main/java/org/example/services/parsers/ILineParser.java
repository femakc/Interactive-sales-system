package org.example.services.parsers;

public interface ILineParser<T> {
    T parse(String line);
}
