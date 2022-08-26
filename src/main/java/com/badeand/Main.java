package com.badeand;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        CardBundle deck = null;
        if (args.length == 1) {
            String deckFilename = args[0];
            try {
                byte[] bytes = Files.readAllBytes(Path.of(deckFilename));
                deck = CardBundle.cardsFromStringList(new String(bytes));
                System.out.printf("Loaded file: '%s' containing %s cards%n", deckFilename, deck.getCards().size());
            } catch (Throwable t) {
                System.err.printf("Unable to read file '%s'. Error: %s%n", deckFilename, t.toString());
                System.exit(2);
            }
        } else if (args.length == 0) {
                System.out.println("Using random shuffeled deck");
            deck = CardBundle.shuffeledDeck();
        } else {
            System.err.printf("Illegal arguments. Excpected one or none arguments. Got %s: %s%n", args.length, String.join(" ", args));
            System.exit(1);
        }

        System.out.printf("Deck: %s%n", deck.getCards().stream().map(card -> card.formatAsString()).collect(Collectors.joining(",")));
        Game game = new Game(deck);
        game.playGame();
        System.out.println(game.getWinner() != null ? game.getWinner().getName() : "tie");
        System.out.println(game.sam.formatCards());
        System.out.println(game.dealer.formatCards());
    }
}
