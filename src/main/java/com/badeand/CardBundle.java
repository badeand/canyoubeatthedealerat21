package com.badeand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CardBundle {

    private final List<Card> cards;

    public CardBundle(List<Card> cards) {
        this.cards = cards;
    }

    public CardBundle() {
        this.cards = new ArrayList<>();
    }

    public static String cardsToString(List<Card> cards1) {
        return cards1.stream()
                .map(Card::formatAsString)
                .collect(Collectors.joining(", "));
    }

    public static List<Card> cardsFromStringList(String s) {
        return Arrays.stream(s.split(","))
                .map(s1 -> s1.trim().toUpperCase(Locale.ROOT))
                .map(Card::cardFromValue)
                .collect(Collectors.toList());
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int sumValue() {
        // TODO: Use reduce
        AtomicInteger sum = new AtomicInteger();
        cards.forEach(card -> sum.addAndGet(card.calcValue()));
        return sum.get();
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

    public String formatAsString() {
        return cardsToString(cards);
    }
}
