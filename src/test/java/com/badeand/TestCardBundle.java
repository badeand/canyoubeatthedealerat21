package com.badeand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestCardBundle {

    @Test
    public void popCardBundle() {
        CardBundle cardBundle = new CardBundle(CardBundle.cardsFromStringList("C1,CA,S7,D5"));
        assertEquals("C1, CA, S7, D5", CardBundle.cardsToString(cardBundle.getCards()));

        assertEquals("C1", cardBundle.pop().formatAsString());
        assertEquals("CA", cardBundle.pop().formatAsString());
        assertEquals("S7", cardBundle.pop().formatAsString());
        assertEquals("D5", cardBundle.pop().formatAsString());
        assertEquals(0, cardBundle.getCards().size());
    }


}
