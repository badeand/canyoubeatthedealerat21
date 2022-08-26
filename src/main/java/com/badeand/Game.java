package com.badeand;

import java.util.Optional;

public class Game {
    private final Player sam    = new Player("sam");
    private final Player dealer = new Player("dealer");
    private final CardBundle cardBundle;

    public Game(CardBundle cardBundle) {
        this.cardBundle = cardBundle;
    }

    public Player getSam() {
        return sam;
    }

    public Player getDealer() {
        return dealer;
    }

    public Optional<Player> playGame() {
        sam.deal(cardBundle.pop());
        dealer.deal(cardBundle.pop());
        sam.deal(cardBundle.pop());
        dealer.deal(cardBundle.pop());

        if (sam.hasBlackjack()) {
            return Optional.of(sam);
        }

        if (dealer.hasBlackjack()) {
            return Optional.of(dealer);
        }

        while (sam.sumValue() < 17 && !sam.isBust()) {
            sam.deal(cardBundle.pop());
        }

        while (dealer.sumValue() <= sam.sumValue() && !dealer.isBust() && dealer.sumValue() < 21) {
            dealer.deal(cardBundle.pop());
        }

        if (!sam.isBust() && sam.sumValue() > (!dealer.isBust() ? dealer.sumValue() : -1)) {
            return Optional.of(sam);
        }

        if (!dealer.isBust() && dealer.sumValue() > (!sam.isBust() ? sam.sumValue() : -1)) {
            return Optional.of(dealer);
        }

        return Optional.empty();
    }
}
