package com.qubit.internal;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class ExceptionHandler {

    public static void throwIfFalse(BooleanSupplier condition, RuntimeException exception) {
        if (!condition.getAsBoolean()) {
            throw exception;
        }
    }

    public static void checkUnsupportedType(boolean condition, String message) {
        throwIfFalse(() -> condition, new UnsupportedOperationException(message));
    }

    // Specific method for InvalidArgumentException
    public static void checkInvalidArgument(boolean condition, String message) {
        throwIfFalse(() -> condition, new InvalidParameterException(message));
    }


    public static void checkNullPointerException(Object element, Object key) {
        if (Objects.isNull(element))
            throw new NoSuchElementException(key.toString());
    }

}
