/*
 * Copyright (c) Forge Development LLC
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.accesstransformer.test;
/* Disabled for now because this fucking project doesn't want to be behave like every other god damn modular project and load things correctly.
import java.lang.module.ModuleFinder;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.Launcher;
import net.minecraftforge.accesstransformer.AccessTransformerEngine;
import net.minecraftforge.accesstransformer.parser.*;
import net.minecraftforge.securemodules.SecureModuleClassLoader;
import net.minecraftforge.unsafe.UnsafeFieldAccess;
import net.minecraftforge.unsafe.UnsafeHacks;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransformationTest {
    private static final String TEST_PACKAGE = "net.minecraftforge.accesstransformer.testjar";


    @BeforeAll
    public static void setup() {
        Configurator.setRootLevel(Level.DEBUG);
    }

    @AfterEach
    public void cleanUp() {
        UnsafeFieldAccess<AccessTransformerEngine, AccessTransformerList> masterList = UnsafeHacks.findField(AccessTransformerEngine.class, "masterList");
        masterList.set(AccessTransformerEngine.INSTANCE, new AccessTransformerList());
    }

    boolean calledback;
    Class<?> transformedClass;
    Class<?> transformedClass2;
    Class<?> transformedClass3;

    @Test
    public void testTestingLaunchHandler() throws Exception {
        System.setProperty("test.harness", "build/classes/java/testJars");
        System.setProperty("test.harness.callable", "net.minecraftforge.accesstransformer.test.TransformationTest$TestCallback");
        calledback = false;
        TestCallback.callable = () -> {
            calledback = true;
            final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            transformedClass = Class.forName(TEST_PACKAGE + ".ATTestClass", true, contextClassLoader);
            transformedClass2 = Class.forName(TEST_PACKAGE + ".DefaultClass", true, contextClassLoader);
            transformedClass3 = Class.forName(TEST_PACKAGE + ".DefaultClass$Inner", true, contextClassLoader);
            return null;
        };
        UnsafeFieldAccess<AccessTransformerEngine, AccessTransformerList> masterList = UnsafeHacks.findField(AccessTransformerEngine.class, "masterList");
        AccessTransformerList list = masterList.get(AccessTransformerEngine.INSTANCE);
        list.loadFromResource("test_at.cfg");

        var cfg = ModuleLayer.boot().configuration().resolveAndBind(ModuleFinder.of(), ModuleFinder.ofSystem(), List.of());
        var cl = new SecureModuleClassLoader("MC-BOOTSTRAP", cfg, List.of(ModuleLayer.boot()));
        var clold = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        try {
            var launcher = Class.forName(Launcher.class.getName(), true, cl);
            var main = launcher.getMethod("main", String[].class);
            main.invoke(null, (Object)new String[]{"--version", "1.0", "--launchTarget", "testharness"});
        } catch (Exception e) {
            sneak(e);
        } finally {
            Thread.currentThread().setContextClassLoader(clold);
        }

        assertTrue(calledback, "We got called back");
        assertAll(
                ()-> assertTrue(Modifier.isPublic(transformedClass2.getModifiers()), "public class"),
                ()-> assertTrue(Modifier.isPublic(transformedClass3.getModifiers()), "public inner class"),
                ()-> assertTrue(Modifier.isProtected(transformedClass.getDeclaredField("privateField").getModifiers()), "public field"),
                ()-> assertTrue(Modifier.isPublic(transformedClass.getDeclaredField("finalPrivateField").getModifiers()), "public field"),
                ()-> assertTrue(!Modifier.isFinal(transformedClass.getDeclaredField("finalPrivateField").getModifiers()), "nonfinal field"),
                ()-> assertTrue(Modifier.isPublic(transformedClass.getDeclaredMethod("privateMethod").getModifiers()), "nonfinal method")
        );
    }

    @SuppressWarnings("unused")
    private String toBinary(int num) {
        return String.format("%16s", Integer.toBinaryString(num)).replace(' ', '0');
    }

    public static class TestCallback {
        private static Callable<Void> callable;
        public static Callable<Void> supplier() {
            return callable;
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable, R> R sneak(Throwable e) throws E {
        throw (E)e;
    }
}

*/
