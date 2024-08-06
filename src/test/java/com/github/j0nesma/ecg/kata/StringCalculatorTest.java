package com.github.j0nesma.ecg.kata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringCalculatorTest {

    StringCalculator stringCalculator = new StringCalculator();
    
    @Test
    void test_add(){
        assertEquals(0, stringCalculator.add(""));
        assertEquals(1, stringCalculator.add("1"));
        assertEquals(2, stringCalculator.add("2"));
        assertEquals(4, stringCalculator.add("2,1,1"));
        assertEquals(6, stringCalculator.add("2,1,1\n2"));
        assertEquals(6, stringCalculator.add("//[||]\n2||1||1||2"));
        assertEquals(6, stringCalculator.add("//[||]\n2||1||1||2||-2||-6"));
        assertEquals(1006, stringCalculator.add("//[||]\n2||1||1||2||-2||-6||1000"));
        assertEquals(7, stringCalculator.add("//[||][*]\n2||1||1||2||-2||-6||10001*1"));
    }
}
