package com.badeand;

public class Card {
    final Suit   suit;
    final String value;

    public Card(Suit suit, String value) {
        this.suit = suit;
        this.value = value;
        calcValue();
    }

    public static Card cardFromValue(String s) {
        return new Card(Suit.valueOf(s.substring(0, 1)), s.substring(1));
    }

    public int calcValue() {
        try {
            int integerValue = Integer.parseInt(value);
            if (integerValue >= 2 && integerValue <= 10) {
                return integerValue;
            } else {
                throw new IllegalArgumentException(String.format("Illegal value: %s", value));
            }
        } catch (NumberFormatException e) {
            if ("J".equals(value) || "Q".equals(value) || "K".equals(value)) {
                return 10;
            }
            if ("A".equals(value)) {
                return 11;
            }
            throw new IllegalArgumentException(String.format("Illegal value: %s ", value));
        }
    }

    public String formatAsString() {
        return suit.name() + value;
    }
}
