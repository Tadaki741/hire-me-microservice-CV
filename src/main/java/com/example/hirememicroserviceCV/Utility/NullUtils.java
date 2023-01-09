package com.example.hirememicroserviceCV.Utility;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class NullUtils {


    public static <T> void updateIfPresent(Consumer<T> consumer, T value) {
        if (value != null){
            consumer.accept(value);
        }
    }

    public static <CV> void updateIfChanged(Consumer<CV> consumer, CV value, Supplier<CV> supplier) {
        Predicate<CV> predicate = input -> !input.equals(value);
        if (value != null && predicate.test(supplier.get())) {
            consumer.accept(value);
        }
    }


}
