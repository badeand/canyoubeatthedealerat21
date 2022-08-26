package com.badeand;

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

    public void dealCards(CardBundle cardBundle) {
        cardBundle.getCards().forEach(this::deal);
    }

    public boolean isBust() {
        return cardBundle.sumValue() > 21;
    }

    public boolean hasBlackjack() {
        return cardBundle.sumValue() == 21;
    }

    public String formatCardsAsStringList() {
        return String.format("%s: %s", name, cardBundle.formatAsString());
    }

    public int sumValue() {
        return cardBundle.sumValue();
    }
}
