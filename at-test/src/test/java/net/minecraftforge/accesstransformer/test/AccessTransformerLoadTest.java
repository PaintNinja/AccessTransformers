/*
 * Copyright (c) Forge Development LLC
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.accesstransformer.test;

import net.minecraftforge.accesstransformer.AccessTransformer;
import net.minecraftforge.accesstransformer.AccessTransformerEngine;
import net.minecraftforge.accesstransformer.parser.*;
import net.minecraftforge.accesstransformer.service.AccessTransformerService;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.minecraftforge.unsafe.UnsafeFieldAccess;
import net.minecraftforge.unsafe.UnsafeHacks;

public class AccessTransformerLoadTest {
    @AfterEach
    public void cleanUp() {
        UnsafeFieldAccess<AccessTransformerEngine, AccessTransformerList> masterList = UnsafeHacks.findField(AccessTransformerEngine.class, "masterList");
        masterList.set(AccessTransformerEngine.INSTANCE, new AccessTransformerList());
    }

    @Test
    public void testLoadForgeAT() throws Exception {
        final AccessTransformerList atLoader = new AccessTransformerList();
        atLoader.loadFromResource("forge_at.cfg");
        final Map<String, List<AccessTransformer>> accessTransformers = atLoader.getAccessTransformers();
        testText(accessTransformers);
    }

    @Test
    public void testLoadATFromJar() throws Exception {
        final AccessTransformerService mls = new AccessTransformerService();
        try (final FileSystem jarFS = FileSystems.newFileSystem(FileSystems.getDefault().getPath("src","test","resources","testatmod.jar"), getClass().getClassLoader())) {
            final Path atPath = jarFS.getPath("META-INF", "forge_at.cfg");
            mls.offerResource(atPath,"forge_at.cfg");

            UnsafeFieldAccess<AccessTransformerEngine, AccessTransformerList> masterList = UnsafeHacks.findField(AccessTransformerEngine.class, "masterList");
            final AccessTransformerList list = masterList.get(AccessTransformerEngine.INSTANCE);
            final Map<String, List<AccessTransformer>> accessTransformers = list.getAccessTransformers();
            testText(accessTransformers);
        }
    }

    private static void testText(final Map<String, List<AccessTransformer>> accessTransformers) throws Exception {
        accessTransformers.forEach((k,v) -> System.out.printf("Got %d ATs for %s:\n\t%s\n", v.size(), k, v.stream().map(Object::toString).collect(Collectors.joining("\n\t"))));

        final TreeMap<String, List<String>> testOutput = accessTransformers.entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().map(AccessTransformer::toString).sorted().collect(Collectors.toList()),
                        (l1, l2) -> { throw new RuntimeException("duplicate keys"); },
                        TreeMap::new
                )
        );

        final String text = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemClassLoader().getResource("forge_at.cfg.json").toURI())), StandardCharsets.UTF_8);
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        final TreeMap<String, List<String>> expectation = GSON.fromJson(text, new TypeToken<TreeMap<String, List<String>>>() {}.getType());

        final String output = GSON.toJson(testOutput);
        final String expect = GSON.toJson(expectation);

        assertEquals(expect, output);
    }
}
