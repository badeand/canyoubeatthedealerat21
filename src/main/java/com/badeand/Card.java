package com.badeand;

public class Card {
    final Suit   suit;
    final String value;

    public Card(Suit suit, String value) {
        this.suit = suit;
        // TODO: Validate value
        this.value = value;
    }

    public static Card cardFromValue(String s) {
        String substring = s.substring(1);
        return new Card(Suit.valueOf(s.substring(0, 1)), substring);
    }

    public int value() {
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
