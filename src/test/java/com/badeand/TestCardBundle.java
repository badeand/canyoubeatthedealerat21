package com.badeand;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCardBundle {

    @Test
    public void popCardBundle() {
        CardBundle cardBundle = CardBundle.parse("C2,CA,S7,D5");
        assertEquals("C2, CA, S7, D5", CardBundle.cardsToString(cardBundle.getCards()));
        assertEquals("C2", cardBundle.draw().formatAsString());
        assertEquals("CA, S7, D5", CardBundle.cardsToString(cardBundle.getCards()));
        cardBundle.draw();
        assertEquals("S7, D5", CardBundle.cardsToString(cardBundle.getCards()));
        cardBundle.draw();
        assertEquals("D5", CardBundle.cardsToString(cardBundle.getCards()));
        cardBundle.draw();
        assertEquals(0, cardBundle.getCards().size());
    }

    @Test
    public void shuffledDeck() {
        CardBundle cardBundle = CardBundle.shuffledDeck();
        assertEquals(52, cardBundle.getCards().size());
        assertEquals(380, cardBundle.sumValue());
    }
}
