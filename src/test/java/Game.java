class Game {
    Player sam    = new Player("sam");
    Player dealer = new Player("dealer");
    Player     winner = null;
    CardBundle cardBundle;

    Game(CardBundle cardBundle) {
        this.cardBundle = cardBundle;
    }

    void playGame() {
        sam.deal(cardBundle.pop());
        dealer.deal(cardBundle.pop());
        sam.deal(cardBundle.pop());
        dealer.deal(cardBundle.pop());

        while (sam.sumValue() < 17) {
            Card card = cardBundle.pop();
            sam.deal(card);
        }

        while (dealer.sumValue() <= sam.sumValue()) {
            Card card = cardBundle.pop();
            dealer.deal(card);
        }

        if (sam.sumValue() > dealer.sumValue()) {
            winner = sam;
        }

        if (dealer.sumValue() > sam.sumValue()) {
            winner = dealer;
        }
    }
}
