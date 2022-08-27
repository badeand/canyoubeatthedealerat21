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
                String cardsList = new String(Files.readAllBytes(Path.of(deckFilename)));
                deck = CardBundle.parse(cardsList);
                System.out.printf("Loaded file: '%s' containing %s cards%n", deckFilename, deck.getCards().size());
            } catch (Throwable t) {
                System.err.printf("Unable to read file '%s'. Error: %s%n", deckFilename, t);
                System.exit(2);
            }
        } else if (args.length == 0) {
            System.out.println("Using random shuffeled deck");
            deck = CardBundle.shuffeledDeck();
        } else {
            System.err.printf("Illegal arguments. Excpected one or none arguments. Got %s: %s%n", args.length, String.join(" ", args));
            System.exit(1);
        }

        System.out.printf("Deck: %s%n", deck.getCards().stream().map(Card::formatAsString).collect(Collectors.joining(",")));

        Result result = Game.playGame(deck);
        System.out.println(result.formatAsMultilineString());
    }
}
