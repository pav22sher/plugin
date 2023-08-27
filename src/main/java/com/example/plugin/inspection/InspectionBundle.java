package com.example.plugin.inspection;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public final class InspectionBundle extends DynamicBundle {

    private static final InspectionBundle ourInstance = new InspectionBundle();

    @NonNls
    public static final String BUNDLE = "messages.InspectionBundle";

    private InspectionBundle() {
        super(BUNDLE);
    }

    public static @Nls String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object @NotNull ... params) {
        return ourInstance.getMessage(key, params);
    }

}
