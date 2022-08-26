import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Experimentation {

    @Test
    public void demoTestMethod() {
        assertTrue(true);

        Card card = new Card(Suit.C, "7");

        System.out.println(card);
    }

    enum Suit {
        C, D, H, S
    }

    class Card {
        Suit   suit;
        String value;

        public Card(Suit suit, String value) {
            this.suit = suit;
            // TODO: Validate value
            this.value = value;
        }

        @Override
        public String toString() {
            return "Card{" + "suit=" + suit + ", value='" + value + '\'' + '}';
        }
    }
}
