package com.bank.antifraud.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

class ConverterServiceTest {
    /**
     * Method under test: {@link ConverterService#convertToList(List, Function)}
     */
    @Test
    void testConvertToList() {
        assertTrue(
                ConverterService.convertToList(new ArrayList<>(), (Function<Object, Object>) mock(Function.class)).isEmpty());
    }
}

