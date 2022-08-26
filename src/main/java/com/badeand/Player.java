package com.badeand;

import java.util.List;

public class Player {

    private final String name;

    private final CardBundle cardBundle = new CardBundle();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void deal(Card card) {
        cardBundle.addCard(card);
    }

    public void dealCards(List<Card> newCards) {
        newCards.forEach(this::deal);
    }

    public boolean isBust() {
        return cardBundle.sumValue() > 21;
    }

    public boolean hasBlackjack() {
        return cardBundle.sumValue() == 21;
    }

    public String formatCards() {
        String collect = cardBundle.formatAsString();
        return String.format("%s: %s", name, collect);
    }

    public int sumValue() {
        return cardBundle.sumValue();
    }
}
