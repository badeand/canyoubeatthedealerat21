package com.badeand;

public class Game {
    final Player     sam    = new Player("sam");
    final Player     dealer = new Player("dealer");
    final CardBundle cardBundle;
    Player winner = null;

    public Game(CardBundle cardBundle) {
        this.cardBundle = cardBundle;
    }

    public Player getWinner() {
        return winner;
    }

    // TODO: Winner is optinoal return
    public void playGame() {
        sam.deal(cardBundle.pop());
        dealer.deal(cardBundle.pop());
        sam.deal(cardBundle.pop());
        dealer.deal(cardBundle.pop());

        if (sam.hasBlackjack()) {
            winner = sam;
            return;
        }

        if (dealer.hasBlackjack()) {
            winner = dealer;
            return;
        }

        while (sam.sumValue() < 17 && !sam.isBust()) {
            sam.deal(cardBundle.pop());
        }

        while (dealer.sumValue() <= sam.sumValue() && !dealer.isBust() && dealer.sumValue() < 21) {
            dealer.deal(cardBundle.pop());
        }

        if (!sam.isBust() && sam.sumValue() > (!dealer.isBust() ? dealer.sumValue() : -1)) {
            winner = sam;
        }

        if (!dealer.isBust() && dealer.sumValue() > (!sam.isBust() ? sam.sumValue() : -1)) {
            winner = dealer;
        }
    }
}
