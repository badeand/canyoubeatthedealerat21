import java.util.List;

class Player {

    private String     name;
    //TODO: To deck/ card collection
    private CardBundle cardBundle = new CardBundle();

    public Player(String name) {
        this.name = name;
    }

    public CardBundle getCardBundle() {
        return cardBundle;
    }

    public String getName() {
        return name;
    }

    void deal(Card card) {
        cardBundle.addCard(card);
    }

    void dealCards(List<Card> newCards) {
        newCards.stream().forEach(card -> deal(card));
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
