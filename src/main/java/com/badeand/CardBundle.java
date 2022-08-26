package com.badeand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CardBundle {

    private final List<Card> cards;

    public CardBundle(List<Card> cards) {
        this.cards = cards;
    }

    public CardBundle() {
        this.cards = new ArrayList<>();
    }

    public static String cardsToString(List<Card> newCards) {
        return newCards.stream().map(Card::formatAsString).collect(Collectors.joining(", "));
    }

    public static List<Card> cardsFromStringList(String stringList) {
        return Arrays.stream(stringList.split(",")).map(s -> s.trim().toUpperCase(Locale.ROOT)).map(Card::cardFromValue).collect(Collectors.toList());
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int sumValue() {
        return cards.stream().map(Card::calcValue).reduce(Integer::sum).orElse(0);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card pop() {
        Card topCard = cards.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to pop. Deck is empty"));
        cards.remove(topCard);
        return topCard;
    }

    public String formatAsString() {
        return cardsToString(cards);
    }
}
