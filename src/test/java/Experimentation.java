import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.Test;

public class Experimentation {

    List<Card> cardsFromStringList(String s) {
        List<String> strings = Arrays.stream(s.split(",")).map(s1 -> s1.trim().toUpperCase(Locale.ROOT)).collect(Collectors.toList());
        List<Card> cards = cardsFromValues(strings);
        return cards;
    }

    @Test
    public void testGame() {
        String s = "C1,CA, S7, D5, C1,S1,C1,S4,C3,S2,C1,S1,C1,S1";
        List<Card> cards = cardsFromStringList(s);
        Game game = new Game(new Deck(cards));
        game.playGame();
        assertEquals(game.dealer, game.winner);
    }

    @Test
    public void demoTestMethod() {

        assertEquals(7, cardFromValue("C7").value());
        assertEquals(10, cardFromValue("D10").value());
        assertEquals(10, cardFromValue("DJ").value());
        assertEquals(10, cardFromValue("HQ").value());
        assertEquals(10, cardFromValue("SK").value());
        assertEquals(11, cardFromValue("SA").value());

        assertEquals("C7", cardFromValue("C7").formatAsString());

        {
            Player player = new Player("sam");
            player.dealCards(cardsFromStringList("C7,SK"));
            assertEquals(17, player.sumValue());
            assertEquals("sam: C7, SK", player.formatCards());
        }

        {
            Player player = new Player("sam");
            player.dealCards(cardsFromStringList("C7,SK,D5"));
            assertEquals("sam: C7, SK, D5", player.formatCards());
        }

        {
            Player player = new Player("sam");
            player.dealCards(cardsFromStringList("H9"));
            assertEquals("sam: H9", player.formatCards());
        }

        {
            Player player = new Player("ken");
            player.dealCards(cardsFromStringList("CA,HA"));
            assertEquals(true, player.isBust());
        }
        {
            Player player = new Player("dealer");
            player.dealCards(cardsFromStringList("DQ,HA"));
            assertEquals(true, player.hasBlackjack());
        }

        {
            Deck deck = new Deck(cardsFromStringList("C1,CA,S7,D5"));
            assertEquals("C1, CA, S7, D5", cardsToString(deck.getCards()));

            assertEquals("C1", deck.pop().formatAsString());
            assertEquals("CA", deck.pop().formatAsString());
            assertEquals("S7", deck.pop().formatAsString());
            assertEquals("D5", deck.pop().formatAsString());
            assertEquals(0, deck.getCards().size());
        }

        {
            Deck deck = new Deck(cardsFromStringList("C1,CA,S7,D5,C1,S1,C1,S4,C3,S2,C1,S1,C1,S1"));
            Player sam = new Player("sam");
            Player dealer = new Player("dealer");
            sam.deal(deck.pop());
            dealer.deal(deck.pop());
            sam.deal(deck.pop());
            dealer.deal(deck.pop());
            assertEquals(8, sam.sumValue());
            assertEquals(16, dealer.sumValue());

            System.out.println(String.format("sam has: %s", sam.sumValue()));
            while (sam.sumValue() < 17) {
                Card card = deck.pop();
                sam.deal(card);
                System.out.println(String.format("sam gets card: %s, has: %s", card.formatAsString(), sam.sumValue()));
            }
            assertEquals(18, sam.sumValue());

            System.out.println(String.format("dealer has: %s", dealer.sumValue()));
            while (dealer.sumValue() <= sam.sumValue()) {
                Card card = deck.pop();
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

            System.out.println(String.format("Winner: %s", winner != null ? winner.name : "tie"));
        }
    }

    private List<Card> cardsFromValues(List<String> strings) {
        List<Card> cards = strings.stream().map(s -> cardFromValue(s)).collect(Collectors.toList());
        return cards;
    }

    Card cardFromValue(String s) {
        String substring = s.substring(1, s.length());
        return new Card(Suit.valueOf(s.substring(0, 1)), substring);
    }

    String cardsToString(List<Card> cards1) {
        String collect = cards1.stream().map(card -> card.formatAsString()).collect(Collectors.joining(", "));
        return collect;
    }

    enum Suit {
        C, D, H, S
    }

    class Game {
        Player sam    = new Player("sam");
        Player dealer = new Player("dealer");
        Player winner = null;
        Deck   deck;

        Game(Deck deck) {
            this.deck = deck;
        }

        void playGame() {
            sam.deal(deck.pop());
            dealer.deal(deck.pop());
            sam.deal(deck.pop());
            dealer.deal(deck.pop());

            while (sam.sumValue() < 17) {
                Card card = deck.pop();
                sam.deal(card);
            }

            while (dealer.sumValue() <= sam.sumValue()) {
                Card card = deck.pop();
                dealer.deal(card);
            }

            if (sam.sumValue() > dealer.sumValue()) {
                winner = sam;
            }

            if (dealer.sumValue() > sam.sumValue()) {
                winner = dealer;
            }
        }
    }

    class Deck {

        private List<Card> cards;

        public Deck(List<Card> cards) {
            this.cards = cards;
        }

        public List<Card> getCards() {
            return cards;
        }

        public Card pop() {
            if (cards.size() > 0) {
                Card poppedCard = cards.get(0);
                cards.remove(poppedCard);
                return poppedCard;
            } else {
                // TODO: Assert
                throw new RuntimeException("Unable to pop. Deck is empty");
            }
        }
    }

    class Player {

        private String     name;
        //TODO: To deck/ card collection
        private List<Card> cards = new ArrayList<>();

        public Player(String name) {
            this.name = name;
        }

        void deal(Card card) {
            cards.add(card);
        }

        void dealCards(List<Card> newCards) {
            newCards.stream().forEach(card -> deal(card));
        }

        public int sumValue() {
            // TODO: Use reduce
            AtomicInteger sum = new AtomicInteger();
            cards.stream().forEach(card -> sum.addAndGet(card.value()));
            return sum.get();
        }

        public boolean isBust() {
            return sumValue() > 21;
        }

        public boolean hasBlackjack() {
            return sumValue() == 21;
        }

        public String formatCards() {
            String collect = cardsToString(cards);
            return String.format("%s: %s", name, collect);
        }
    }

    class Card {
        Suit   suit;
        String value;

        public Card(Suit suit, String value) {
            this.suit = suit;
            // TODO: Validate value
            this.value = value;
        }

        int value() {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                if ("J".equals(value) || "Q".equals(value) || "K".equals(value)) {
                    return 10;
                }
                if ("A".equals(value)) {
                    return 11;
                }
                throw new RuntimeException(String.format("Illega value: %s ", value));
            }
        }

        public String formatAsString() {
            return suit.name() + value;
        }
    }
}
