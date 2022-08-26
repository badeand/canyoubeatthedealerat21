package com.badeand;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCardBundle {

    @Test
    public void popCardBundle() {
        CardBundle cardBundle = CardBundle.parse("C2,CA,S7,D5");
        assertEquals("C2, CA, S7, D5", CardBundle.cardsToString(cardBundle.getCards()));
        assertEquals("C2", cardBundle.pop().formatAsString());
        assertEquals("CA, S7, D5", CardBundle.cardsToString(cardBundle.getCards()));
        cardBundle.pop();
        assertEquals("S7, D5", CardBundle.cardsToString(cardBundle.getCards()));
        cardBundle.pop();
        assertEquals("D5", CardBundle.cardsToString(cardBundle.getCards()));
        cardBundle.pop();
        assertEquals(0, cardBundle.getCards().size());
    }

    @Test
    public void shuffeledDeck() {
        CardBundle cardBundle = CardBundle.shuffeledDeck();
        assertEquals(52, cardBundle.getCards().size());
        assertEquals(380, cardBundle.sumValue());
    }
}
