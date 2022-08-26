package com.badeand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Optional;

import org.junit.Test;

public class TestGame {

    @Test
    public void exampleInText() {
        testGame("CA,D5,H9,HQ,S8", "sam");
    }

    @Test
    public void samWinsIfDealerIsBust() {
        testGame("S5,C10,S5,C5,S9,CQ,C10", "sam");
    }

    @Test
    public void dealerWinsIfSamIsBust() {
        testGame("CA,C10, DA, DA, C2,S2,C2,S4,C3,S2,C2,S2,C2,S2", "dealer");
    }

    @Test
    public void samWinsIfBlackjackOnInitialHand() {
        testGame("CA,C9,C10,C8", "sam");
    }

    @Test
    public void dealerWinsIfBlackjackOnInitialHand() {
        testGame("C9,CA,C8,C10", "dealer");
    }

    @Test
    public void samWinsIfBotHasBlackjackOnInitialHand() {
        testGame("CA,DA,C10,D10", "sam");
    }

    @Test
    public void testPopExceptionOnEmptyDeck() {
        assertThrows(RuntimeException.class, () -> new CardBundle().pop());
    }

    private void testGame(String list, String player) {
        Game game = new Game(CardBundle.parse(list));
        Optional<Player> winner = game.playGame();
        assertEquals(player, winner.orElseThrow(() -> new RuntimeException("no winner")).getName());
    }
}
