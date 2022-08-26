package com.badeand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class TestCard {

    @Test
    public void formatString() {
        assertEquals("C7", Card.cardFromValue("C7").formatAsString());
    }

    @Test
    public void unknownSuit() {
        assertThrows(IllegalArgumentException.class, () -> Card.cardFromValue("X7"));
    }

    @Test
    public void tooLowValue() {
        assertThrows(IllegalArgumentException.class, () -> Card.cardFromValue("D0"));
    }

    @Test
    public void tooHighValue1() {
        assertThrows(IllegalArgumentException.class, () -> Card.cardFromValue("D11"));
    }

    @Test
    public void tooHighValue2() {
        assertThrows(IllegalArgumentException.class, () -> Card.cardFromValue("D99999"));
    }

    @Test
    public void IllegalValue() {
        assertThrows(IllegalArgumentException.class, () -> Card.cardFromValue("DX"));
    }

    @Test
    public void test1() {
        assertEquals(7, Card.cardFromValue("C7").calcValue());
        assertEquals(10, Card.cardFromValue("D10").calcValue());
        assertEquals(10, Card.cardFromValue("DJ").calcValue());
        assertEquals(10, Card.cardFromValue("HQ").calcValue());
        assertEquals(10, Card.cardFromValue("SK").calcValue());
        assertEquals(11, Card.cardFromValue("SA").calcValue());
    }
}
