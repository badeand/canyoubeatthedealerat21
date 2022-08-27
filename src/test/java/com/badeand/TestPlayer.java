package com.badeand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestPlayer {

    static private void dealCards(Player player, String stringList) {
        CardBundle.parse(stringList).getCards()
                .forEach(player::deal);
    }

    @Test
    public void formatCards() {
        formatCardsForPlayer("C7,SK,D5", "sam: C7, SK, D5");
        formatCardsForPlayer("H9", "sam: H9");
    }

    public void formatCardsForPlayer(String stringList, String expected) {
        Player player = new Player("sam");
        dealCards(player, stringList);
        assertEquals(expected, player.formatCardsAsStringList());
    }

    @Test
    public void playerHasBlackjack() {
        Player player = new Player("dealer");
        dealCards(player, "DQ,HA");
        assertTrue(player.hasBlackjack());
    }

    @Test
    public void playerIsBust() {
        Player player = new Player("ken");
        dealCards(player, "CA,HA");
        assertTrue(player.isBust());
    }

    @Test
    public void calculateCardValues() {
        Player player = new Player("sam");
        dealCards(player, "C7,SK");
        assertEquals(17, player.sumValue());
    }
}
