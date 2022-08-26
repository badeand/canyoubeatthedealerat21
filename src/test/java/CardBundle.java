import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class CardBundle {

    private List<Card> cards;

    public CardBundle(List<Card> cards) {
        this.cards = cards;
    }

    public CardBundle() {
        this.cards = new ArrayList<>();
    }

    static String cardsToString(List<Card> cards1) {
        String collect = cards1.stream().map(card -> card.formatAsString()).collect(Collectors.joining(", "));
        return collect;
    }

    static List<Card> cardsFromStringList(String s) {
        List<String> strings = Arrays.stream(s.split(",")).map(s1 -> s1.trim().toUpperCase(Locale.ROOT)).collect(Collectors.toList());
        List<Card> cards1 = strings.stream().map(s1 -> Card.cardFromValue(s1)).collect(Collectors.toList());
        List<Card> cards = cards1;
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int sumValue() {
        // TODO: Use reduce
        AtomicInteger sum = new AtomicInteger();
        cards.stream().forEach(card -> sum.addAndGet(card.value()));
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
