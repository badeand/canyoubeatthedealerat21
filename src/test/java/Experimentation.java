import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.Test;

public class Experimentation {

    @Test
    public void demoTestMethod() {

        assertEquals(7, cardFromValue("C7").value());
        assertEquals(10, cardFromValue("DJ").value());
        assertEquals(10, cardFromValue("HQ").value());
        assertEquals(10, cardFromValue("SK").value());
        assertEquals(11, cardFromValue("SA").value());

        assertEquals("C7", cardFromValue("C7").formatAsString());

        {
            Player player = new Player("sam");
            player.dealCards(cardsFromValues(new String[]{"C7", "SK"}));
            assertEquals(17, player.sumValue());
            assertEquals("sam: C7, SK", player.formatCards());
        }

        {
            Player player = new Player("sam");
            player.dealCards(cardsFromValues(new String[]{"C7", "SK", "D5"}));
            assertEquals("sam: C7, SK, D5", player.formatCards());
        }

        {
            Player player = new Player("sam");
            player.dealCards(cardsFromValues(new String[]{"H9"}));
            assertEquals("sam: H9", player.formatCards());
        }

        {
            Player player = new Player("ken");
            player.dealCards(cardsFromValues(new String[]{"CA", "HA"}));
            assertEquals(true, player.isBust());
        }
        {
            Player player = new Player("dealer");
            player.dealCards(cardsFromValues(new String[]{"DQ", "HA"}));
            assertEquals(true, player.hasBlackjack());
        }
    }

    private List<Card> cardsFromValues(String[] strings) {
        List<Card> cards = Arrays.stream(strings).map(s -> cardFromValue(s)).collect(Collectors.toList());
        return cards;
    }

    Card cardFromValue(String s) {
        String substring = s.substring(1, s.length());
        return new Card(Suit.valueOf(s.substring(0, 1)), substring);
    }

    enum Suit {
        C, D, H, S
    }

    class Deck {

    }

    class Player {

        private String     name;
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
            String collect = cards.stream().map(card -> card.formatAsString()).collect(Collectors.joining(", "));
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
