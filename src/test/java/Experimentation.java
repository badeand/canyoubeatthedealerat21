import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
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

        {
            Player player = new Player();
            player.dealCards(cardsFromValues(new String[]{"C7", "SK"}));
            assertEquals(17, player.sumValue());
        }

        {
            Player player = new Player();
            player.dealCards(cardsFromValues(new String[]{"CA", "HA"}));
            assertEquals(true, player.isBust());
        }
        {
            Player player = new Player();
            player.dealCards(cardsFromValues(new String[]{"DQ", "HA"}));
            assertEquals(true, player.hasBlackjack());
        }
    }

    private Set<Card> cardsFromValues(String[] strings) {
        Set<Card> cards = Arrays.stream(strings).map(s -> cardFromValue(s)).collect(Collectors.toSet());
        return cards;
    }

    Card cardFromValue(String s) {
        String substring = s.substring(1, s.length());
        return new Card(Suit.valueOf(s.substring(0, 1)), substring);
    }

    enum Suit {
        C, D, H, S
    }

    class Player {
        private Set<Card> cards = new HashSet<>();

        void deal(Card card) {
            cards.add(card);
        }

        void dealCards(Set<Card> newCards) {
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
    }
}
