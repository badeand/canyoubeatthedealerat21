package com.badeand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestPlayer {

    @Test
    public void formatCards() {
        formatCardsForPlayer("C7,SK,D5", "sam: C7, SK, D5");
        formatCardsForPlayer("H9", "sam: H9");
    }

    public void formatCardsForPlayer(String s, String expected) {
        Player player = new Player("sam");
        player.dealCards(CardBundle.parse(s));
        assertEquals(expected, player.formatCardsAsStringList());
    }

    @Test
    public void playerHasBlackjack() {
        Player player = new Player("dealer");
        player.dealCards(CardBundle.parse("DQ,HA"));
        assertTrue(player.hasBlackjack());
    }

    @Test
    public void playerIsBust() {
        Player player = new Player("ken");
        player.dealCards(CardBundle.parse("CA,HA"));
        assertTrue(player.isBust());
    }

    @Test
    public void calculateCardValues() {
        Player player = new Player("sam");
        player.dealCards(CardBundle.parse("C7,SK"));
        assertEquals(17, player.sumValue());
    }
}
