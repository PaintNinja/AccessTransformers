/*
 * Copyright (c) Forge Development LLC
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.accesstransformer.test;

import net.minecraftforge.accesstransformer.generated.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Vocabulary;
import org.junit.jupiter.api.Test;

public class BatATParseTest {
    @Test
    public void testParseBadAT() throws Exception {
        final CharStream charStream = CharStreams.fromStream(getClass().getClassLoader().getResourceAsStream("bad_at.cfg"));
        final AtLexer lexer = new AtLexer(charStream);
        final Vocabulary vocab = lexer.getVocabulary();

        final List<String> tokens = new ArrayList<>();

        int type;
        while ((type = lexer.nextToken().getType()) != AtLexer.EOF) {
            if (type != AtLexer.WS) {
                tokens.add(vocab.getSymbolicName(type));
            }
        }

        final List<String> expectation = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource("bad_at.cfg.txt").toURI()));
        assertEquals(expectation, tokens);
    }
}
