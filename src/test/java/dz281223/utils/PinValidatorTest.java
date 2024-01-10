package dz281223.utils;

import dz281223.exception.IncorrectPinLengthException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


class PinValidatorTest {
    @Test
    void getAccessToTransaction_CorrectPin() {
        PinValidator pinValidator = new PinValidator();
        String input = "0000";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputStream originalInput = System.in;

        try {
            System.setIn(inputStream);
            boolean result = pinValidator.getAccessToTransaction("0000");

            assertTrue(result);
        } finally {
            System.setIn(originalInput);
        }
    }

    @Test
    void getAccessToTransaction_ShortPin() {
        PinValidator pinValidator = new PinValidator();
        String input = "000";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputStream originalInput = System.in;

        try {
            System.setIn(inputStream);
            assertThrows(NoSuchElementException.class,()->{
                pinValidator.getAccessToTransaction("0000");
            });
        } finally {
            System.setIn(originalInput);
        }
    }

    @Test
    void getAccessToTransaction_LongPin() {
        PinValidator pinValidator = new PinValidator();
        String input = "00000";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputStream originalInput = System.in;

        try {
            System.setIn(inputStream);
            assertThrows(NoSuchElementException.class,()->{
                pinValidator.getAccessToTransaction("0000");
            });
        } finally {
            System.setIn(originalInput);
        }
    }

    @Test
    void getAccessToTransaction_IncorrectPin() {
        PinValidator pinValidator = new PinValidator();
        String input = "1000";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputStream originalInput = System.in;

        try {
            System.setIn(inputStream);
            assertThrows(NoSuchElementException.class,()->{
                pinValidator.getAccessToTransaction("0000");
            });
        } finally {
            System.setIn(originalInput);
        }
    }
}