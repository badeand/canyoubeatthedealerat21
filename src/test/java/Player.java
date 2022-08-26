import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Player {

    private String     name;
    //TODO: To deck/ card collection
    private List<Card> cards = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void deal(Card card) {
        cards.add(card);
    }

    void dealCards(List<Card> newCards) {
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

    public String formatCards() {
        String collect = CardBundle.cardsToString(cards);
        return String.format("%s: %s", name, collect);
    }
}
