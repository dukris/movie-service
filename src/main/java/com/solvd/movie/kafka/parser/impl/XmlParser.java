package com.solvd.movie.kafka.parser.impl;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.solvd.movie.domain.exception.ParsingException;
import com.solvd.movie.kafka.parser.Parser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class XmlParser implements Parser {

    @Override
    public String getValue(final String filename,
                           final String node) {
        try {
            XML xml = new XMLDocument(Objects.requireNonNull(
                    XmlParser.class
                            .getClassLoader()
                            .getResource(filename))
            );
            return xml.nodes("//" + node)
                    .get(0)
                    .xpath("text()")
                    .get(0);
        } catch (IOException ex) {
            throw new ParsingException(ex.getMessage());
        }
    }

}
