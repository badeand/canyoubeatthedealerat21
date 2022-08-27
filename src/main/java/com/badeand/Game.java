package com.badeand;

import java.util.Optional;

public class Game {

    public static Result playGame(CardBundle cardBundle) {
        final Player sam = new Player("sam");
        final Player dealer = new Player("dealer");

        sam.deal(cardBundle.pop());
        dealer.deal(cardBundle.pop());
        sam.deal(cardBundle.pop());
        dealer.deal(cardBundle.pop());

        if (sam.isBust() && dealer.isBust()) {
            return cerateResult(sam, dealer, dealer);
        }

        if (sam.hasBlackjack()) {
            return cerateResult(sam, dealer, sam);
        }

        if (dealer.hasBlackjack()) {
            return cerateResult(sam, dealer, dealer);
        }

        while (sam.sumValue() < 17 && !sam.isBust()) {
            sam.deal(cardBundle.pop());
        }

        while (dealer.sumValue() <= sam.sumValue() && !dealer.isBust()) {
            dealer.deal(cardBundle.pop());
        }

        if (!sam.isBust() && sam.sumValue() > (!dealer.isBust() ? dealer.sumValue() : -1)) {
            return cerateResult(sam, dealer, sam);
        } else if (!dealer.isBust() && dealer.sumValue() > (!sam.isBust() ? sam.sumValue() : -1)) {
            return cerateResult(sam, dealer, dealer);
        } else {
            return new Result(sam, dealer, Optional.empty());
        }
    }

    private static Result cerateResult(Player sam, Player dealer, Player winner) {
        return new Result(sam, dealer, Optional.of(winner));
    }
}
