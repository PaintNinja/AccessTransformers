/*
 * Copyright (c) Forge Development LLC
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.accesstransformer.parser;

import java.util.*;

import net.minecraftforge.accesstransformer.*;
import net.minecraftforge.accesstransformer.AccessTransformer.Modifier;

public final class ModifierProcessor {
    private ModifierProcessor() {}
    public static AccessTransformer.Modifier modifier(String modifierString) {
        String modifier = modifierString.toUpperCase(Locale.ROOT);
        String ending = modifier.substring(modifier.length()-2, modifier.length());

        if ("+F".equals(ending) || "-F".equals(ending))
            modifier = modifier.substring(0, modifier.length()-2);

        switch (modifier) {
            case "PUBLIC": return Modifier.PUBLIC;
            case "PROTECTED": return Modifier.PROTECTED;
            case "DEFAULT": return Modifier.DEFAULT;
            case "PRIVATE": return Modifier.PRIVATE;
            default: return null;
        }
    }

    public static AccessTransformer.FinalState finalState(String modifierString) {
        final String modifier = modifierString.toUpperCase(Locale.ROOT);
        final String ending = modifier.substring(modifier.length()-2, modifier.length());
        if ("+F".equals(ending)) {
            return AccessTransformer.FinalState.MAKEFINAL;
        } else if ("-F".equals(ending)) {
            return AccessTransformer.FinalState.REMOVEFINAL;
        } else {
            return AccessTransformer.FinalState.LEAVE;
        }
    }
}
