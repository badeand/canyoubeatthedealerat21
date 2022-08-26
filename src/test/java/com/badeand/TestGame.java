package com.badeand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TestGame {

    @Test
    public void randomGame() {
        testGame("C1,CA, S7, D5, C1,S1,C1,S4,C3,S2,C1,S1,C1,S1", "dealer");
    }

    @Test
    public void samWinsIfDealerIsBust() {
        testGame("S5,C10,S5,C5,S9,CQ,C10", "sam");
    }

    @Test
    public void dealerWinsIfSamIsBust() {
        testGame("CA,C10, DA, DA, C1,S1,C1,S4,C3,S2,C1,S1,C1,S1", "dealer");
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

    private void testGame(String list, String player) {
        List<Card> cards = CardBundle.cardsFromStringList(list);
        Game game = new Game(new CardBundle(cards));
        game.playGame();
        assertEquals(player, game.getWinner().getName());
    }

    @Test
    public void demoTestMethod() {

        assertEquals(7, Card.cardFromValue("C7").value());
        assertEquals(10, Card.cardFromValue("D10").value());
        assertEquals(10, Card.cardFromValue("DJ").value());
        assertEquals(10, Card.cardFromValue("HQ").value());
        assertEquals(10, Card.cardFromValue("SK").value());
        assertEquals(11, Card.cardFromValue("SA").value());

        assertEquals("C7", Card.cardFromValue("C7").formatAsString());

        {
            Player player = new Player("sam");
            player.dealCards(CardBundle.cardsFromStringList("C7,SK"));
            assertEquals(17, player.sumValue());
            assertEquals("sam: C7, SK", player.formatCards());
        }

        {
            Player player = new Player("sam");
            player.dealCards(CardBundle.cardsFromStringList("C7,SK,D5"));
            assertEquals("sam: C7, SK, D5", player.formatCards());
        }

        {
            Player player = new Player("sam");
            player.dealCards(CardBundle.cardsFromStringList("H9"));
            assertEquals("sam: H9", player.formatCards());
        }

        {
            Player player = new Player("ken");
            player.dealCards(CardBundle.cardsFromStringList("CA,HA"));
            assertTrue(player.isBust());
        }
        {
            Player player = new Player("dealer");
            player.dealCards(CardBundle.cardsFromStringList("DQ,HA"));
            assertTrue(player.hasBlackjack());
        }

        {
            CardBundle cardBundle = new CardBundle(CardBundle.cardsFromStringList("C1,CA,S7,D5"));
            assertEquals("C1, CA, S7, D5", CardBundle.cardsToString(cardBundle.getCards()));

            assertEquals("C1", cardBundle.pop().formatAsString());
            assertEquals("CA", cardBundle.pop().formatAsString());
            assertEquals("S7", cardBundle.pop().formatAsString());
            assertEquals("D5", cardBundle.pop().formatAsString());
            assertEquals(0, cardBundle.getCards().size());
        }

        {
            CardBundle cardBundle = new CardBundle(CardBundle.cardsFromStringList("C1,CA,S7,D5,C1,S1,C1,S4,C3,S2,C1,S1,C1,S1"));
            Player sam = new Player("sam");
            Player dealer = new Player("dealer");
            sam.deal(cardBundle.pop());
            dealer.deal(cardBundle.pop());
            sam.deal(cardBundle.pop());
            dealer.deal(cardBundle.pop());
            assertEquals(8, sam.sumValue());
            assertEquals(16, dealer.sumValue());

            System.out.printf("sam has: %s%n", sam.sumValue());
            while (sam.sumValue() < 17) {
                Card card = cardBundle.pop();
                sam.deal(card);
                System.out.printf("sam gets card: %s, has: %s%n", card.formatAsString(), sam.sumValue());
            }
            assertEquals(18, sam.sumValue());

            System.out.printf("dealer has: %s%n", dealer.sumValue());
            while (dealer.sumValue() <= sam.sumValue()) {
                Card card = cardBundle.pop();
                dealer.deal(card);
                System.out.printf("dealer gets card: %s, has: %s%n", card.formatAsString(), dealer.sumValue());
            }
            assertEquals(18, sam.sumValue());

            Player winner = null;

            if (sam.sumValue() > dealer.sumValue()) {
                winner = sam;
            }

            if (dealer.sumValue() > sam.sumValue()) {
                winner = dealer;
            }

            System.out.printf("Winner: %s%n", winner != null ? winner.getName() : "tie");
        }
    }
}
