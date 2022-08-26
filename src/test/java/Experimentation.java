import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class Experimentation {

    @Test
    public void testGame() {
        String s = "C1,CA, S7, D5, C1,S1,C1,S4,C3,S2,C1,S1,C1,S1";
        List<Card> cards = CardBundle.cardsFromStringList(s);
        Game game = new Game(new CardBundle(cards));
        game.playGame();
        assertEquals(game.dealer, game.winner);
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
            assertEquals(true, player.isBust());
        }
        {
            Player player = new Player("dealer");
            player.dealCards(CardBundle.cardsFromStringList("DQ,HA"));
            assertEquals(true, player.hasBlackjack());
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

            System.out.println(String.format("sam has: %s", sam.sumValue()));
            while (sam.sumValue() < 17) {
                Card card = cardBundle.pop();
                sam.deal(card);
                System.out.println(String.format("sam gets card: %s, has: %s", card.formatAsString(), sam.sumValue()));
            }
            assertEquals(18, sam.sumValue());

            System.out.println(String.format("dealer has: %s", dealer.sumValue()));
            while (dealer.sumValue() <= sam.sumValue()) {
                Card card = cardBundle.pop();
                dealer.deal(card);
                System.out.println(String.format("dealer gets card: %s, has: %s", card.formatAsString(), dealer.sumValue()));
            }
            assertEquals(18, sam.sumValue());

            Player winner = null;

            if (sam.sumValue() > dealer.sumValue()) {
                winner = sam;
            }

            if (dealer.sumValue() > sam.sumValue()) {
                winner = dealer;
            }

            System.out.println(String.format("Winner: %s", winner != null ? winner.getName() : "tie"));
        }
    }
}
