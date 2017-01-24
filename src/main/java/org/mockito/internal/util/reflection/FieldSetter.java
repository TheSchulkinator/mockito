/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.util.reflection;

import java.lang.reflect.Field;

import static java.lang.String.format;

public class FieldSetter {

    private FieldSetter() {
    }

    public static void setField(Object target, Field field, Object value) {
        AccessibilityChanger changer = new AccessibilityChanger();
        changer.enableAccess(field);
        try {
            field.set(target, value);
        } catch (IllegalAccessException illegalAccess) {
            throw new RuntimeException(format("Access not authorized on field '%s' of object '%s' with value: '%s'",
                                              field,
                                              target,
                                              value),
                                       illegalAccess);
        } catch (IllegalArgumentException illegalArgument) {
            throw new RuntimeException(format("Wrong argument on field '%s' of object '%s' with value: '%s', \nreason : %s",
                                              field,
                                              target,
                                              value,
                                              illegalArgument.getMessage()),
                                       illegalArgument);
        }
        changer.safelyDisableAccess(field);
    }
}
