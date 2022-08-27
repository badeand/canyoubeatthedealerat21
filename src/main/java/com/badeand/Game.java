package com.badeand;

import java.util.Optional;

public class Game {

    public static Result playGame(CardBundle deck) {
        final Player sam = new Player("sam");
        final Player dealer = new Player("dealer");

        sam.deal(deck.draw());
        dealer.deal(deck.draw());
        sam.deal(deck.draw());
        dealer.deal(deck.draw());

        if (sam.isBust() && dealer.isBust()) {
            return createResult(sam, dealer, dealer);
        }

        if (sam.hasBlackjack()) {
            return createResult(sam, dealer, sam);
        }

        if (dealer.hasBlackjack()) {
            return createResult(sam, dealer, dealer);
        }

        while (sam.sumValue() < 17 && !sam.isBust()) {
            sam.deal(deck.draw());
        }

        while (dealer.sumValue() <= sam.sumValue() && !dealer.isBust()) {
            dealer.deal(deck.draw());
        }

        if (!sam.isBust() && sam.sumValue() > (!dealer.isBust() ? dealer.sumValue() : -1)) {
            return createResult(sam, dealer, sam);
        } else if (!dealer.isBust() && dealer.sumValue() > (!sam.isBust() ? sam.sumValue() : -1)) {
            return createResult(sam, dealer, dealer);
        } else {
            return new Result(sam, dealer, Optional.empty());
        }
    }

    private static Result createResult(Player sam, Player dealer, Player winner) {
        return new Result(sam, dealer, Optional.of(winner));
    }
}
