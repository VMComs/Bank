package com.bank.authorization.util;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ErrBindingResultTest {

    @Test
    void shouldCreateBindingResult() {
        BindingResult bindingResult = mock(BindingResult.class);
        ErrBindingResult errBindingResult = new ErrBindingResult();
        List<FieldError> errorList =  List.of(new FieldError("Error1", "Error2", "Error3"),
                new FieldError("Error4", "Error5", "Error6"));
        String expectedResult = "Error3; Error6";

        when(bindingResult.getFieldErrors()).thenReturn(errorList);
        String actualResult = errBindingResult.getErrorsFromBindingResult(bindingResult);

        verify(bindingResult, times(1)).getFieldErrors();
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
