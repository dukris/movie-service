package com.solvd.movie.kafka.parser;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import lombok.SneakyThrows;

import java.util.Objects;

public abstract class XmlParser {

    @SneakyThrows
    public static String getValue(String node) {
        XML xml = new XMLDocument(Objects.requireNonNull(
                XmlParser.class
                        .getClassLoader()
                        .getResource("producer.xml")));
        return xml.nodes("//" + node)
                .get(0)
                .xpath("text()")
                .get(0);
    }

}
