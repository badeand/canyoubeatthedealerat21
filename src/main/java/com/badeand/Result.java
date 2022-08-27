package com.badeand;

import java.util.Optional;

public class Result {

    private final Player           sam;
    private final Player           dealer;
    private final Optional<Player> winner;

    public Result(Player sam, Player dealer, Optional<Player> winner) {
        this.sam = sam;
        this.dealer = dealer;
        this.winner = winner;
    }

    public Optional<Player> getWinner() {
        return winner;
    }

    public String formatAsMultilineString() {
        return String.format("%s\n%s\n%s",
                winner.map(Player::getName).orElse("tie"),
                sam.formatCardsAsStringList(),
                dealer.formatCardsAsStringList()
        );
    }
}
