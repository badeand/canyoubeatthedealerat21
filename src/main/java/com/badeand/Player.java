package com.badeand;

public class Player {

    private final String name;

    private final CardBundle hand = new CardBundle();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void deal(Card card) {
        hand.addCard(card);
    }

    public void dealCards(CardBundle cardBundle) {
        cardBundle.getCards().forEach(this::deal);
    }

    public boolean isBust() {
        return hand.sumValue() > 21;
    }

    public boolean hasBlackjack() {
        return hand.sumValue() == 21;
    }

    public String formatCardsAsStringList() {
        return String.format("%s: %s", name, hand.formatAsString());
    }

    public int sumValue() {
        return hand.sumValue();
    }
}
