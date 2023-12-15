package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS//
import java.util.ArrayList;
import java.util.Random;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class Bot extends ArrayList{
    
    // FIELDS //
    private GameHandler game;                                                                                                                                           // The Game that the Bot is a part of
    private Deck hand;                                                                                                                                                  // Hand of Cards for the Bot to work with
    private Random RNG;                                                                                                                                                 // A Random object that will help generate "random" numbers
    private boolean playingTurn;                                                                                                                                        // A boolean to keep track of whether or not the Bot is playing a turn

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR //
    public Bot(GameHandler game, Deck hand) {
        this.game = game;                                                                                                                                               // Set the Game that the Bot is a part of
        this.hand = hand;                                                                                                                                               // Set the hand of Cards that the Bot is working with currently
        this.RNG = new Random();                                                                                                                                        // Create the Random object that will help generate "random" numbers
        this.playingTurn = false;
    }
    
 // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS //
        
    // Sets the hand for the Bot to work with
    public void setHand(Deck hand) {                                                                                                                    
        this.hand = hand;
    }
    
    // Returns whether or not the Bot is currently playing a turn
    public boolean getPlayingTurn() {
        return this.playingTurn;
    }
    
    // Sets whether or not the Bot is currently playing a turn
    public void setPlayingTurn(boolean playingTurn) {
        this.playingTurn = playingTurn;
    }
    
    // Returns all possible valid singles moves that can be played this turn
    private ArrayList<Deck> getSinglesMoves() {
        ArrayList<Deck> singlesMoveOptions = new ArrayList<Deck>();                                                                                                     // Create an ArrayList of Decks that will each hold a valid singles play

        for (Card card : this.hand.getCards()) {                                                                                                                        // For every Card in the bot's hand...
            if (this.game.validateSingles(card)) {                                                                                                                          // If the Card is a valid singles play...
                singlesMoveOptions.add(new Deck(new Card[] {card}));                                                                                                        // Add the Card into a Deck and put it into the singlesMoveOptions ArrayList
            }                                                                                                                                                                               
        }     
        
        return singlesMoveOptions;                                                                                                                                      // Returns the ArrayList of valid singles move options                                                                                                              
    }
    
    // Returns all possible valid doubles moves that can be played this turn
    private ArrayList<Deck> getDoublesMoves() {
        ArrayList<Deck> doublesMoveOptions = new ArrayList<Deck>();                                                                                                     // Create an ArrayList of Decks that will each hold a valid doubles play
        Deck doublesMove = new Deck(true);                                                                                                                              // Create a Deck to store the valid doubles move in

        for (int cardIndex = 1; cardIndex < this.hand.getSize(); cardIndex++) {                                                                                         // For every Card in the bot's hand EXCEPT the first Card...
            doublesMove.addCard(this.hand.getCard(cardIndex - 1));                                                                                                      // Add the previous Card to the potential doublesMove Deck
            doublesMove.addCard(this.hand.getCard(cardIndex));                                                                                                          // Add the current Card to the potential doublesMove Deck

            if (this.game.validateDoubles(doublesMove)) {                                                                                                           // If the Cards make up a valid doubles play...
                doublesMoveOptions.add(new Deck(new Card[] {doublesMove.getCard(0), doublesMove.getCard(1)}));                                              // Add the Cards from the doublesMove Deck into a new Deck and put it into the doublesMoveOptions ArrayList
            }
            
            doublesMove.clearCards();                                                                                                                                   // Clear the doublesMove Deck so that is is ready to handle a new doubles move
        }     
        
        return doublesMoveOptions;                                                                                                                                      // Returns the ArrayList of valid doubles move options            
    }
    
    // Returns all possible valid triples moves that can be played this turn
    private ArrayList<Deck> getTriplesMoves() {
        ArrayList<Deck> triplesMoveOptions = new ArrayList<Deck>();                                                                                                     // Create an ArrayList of Decks that will each hold a valid triples play
        Deck triplesMove = new Deck(true);                                                                                                                              // Create a Deck to store the valid triples move in

        for (int cardIndex = 2; cardIndex < this.hand.getSize(); cardIndex++) {                                                                                         // For every Card in the bot's hand EXCEPT the first two Cards...
            triplesMove.addCard(this.hand.getCard(cardIndex - 2));                                                                                                      // Add the previous previous Card to the potential triplesMove Deck
            triplesMove.addCard(this.hand.getCard(cardIndex - 1));                                                                                                      // Add the previous Card to the potential triplesMove Deck
            triplesMove.addCard(this.hand.getCard(cardIndex));                                                                                                          // Add the current Card to the potential triplesMove Deck

            if (this.game.validateTriples(triplesMove)) {                                                                                                           // If the Cards make up a valid triples play...
                triplesMoveOptions.add(new Deck(new Card[] {triplesMove.getCard(0), triplesMove.getCard(1), triplesMove.getCard(2)}));                 // Add the Cards from the triplesMove Deck into a new Deck and put it into the triplesMoveOptions ArrayList                                                                                                               // Add the triplesMove Deck into the triplesOptions ArrayList
            }
            
            triplesMove.clearCards();                                                                                                                                   // Clear the triplesMove Deck so that is is ready to handle a new triples move
        }     
        
        return triplesMoveOptions;                                                                                                                                      // Returns the ArrayList of valid triples move options             
    }
    
    // Returns all possible valid consecutives moves that can be played this turn
    private ArrayList<Deck> getConsecutivesMoves() {
        ArrayList<Deck> consecutivesMoveOptions = new ArrayList<Deck>();                                                                                                // Create an ArrayList of Decks that will each hold a valid consecutives play
        Deck consecutivesMove = new Deck(true);                                                                                                                     // Create a Deck to store the valid consecutives move in
        Deck currentMove = this.game.getCurrentMove();                                                                                                                  // Store a reference to the current move on the table in the game

        for (int i = 0; i < this.hand.getSize() - 1; i++) {                                                                                                             // For every Card in the Bot's hand...
            Card currentCard = this.hand.getCard(i);                                                                                                                // Store a reference to the Card that is being analyzed
            consecutivesMove.addCard(this.hand.getCard(i));                                                                                                     // Add the currentCard to the consecutivesMove Deck

            for (int j = (i + 1); j < this.hand.getSize(); j++) {                                                                                                          // For every Card that comes after the currentCard...
                if (currentCard.getValue() + 1 == this.hand.getCard(j).getValue()) {                                                                                    // If the Card has a value that is one higher than the currentCard...
                    currentCard = this.hand.getCard(j);                                                                                                                     // Set the Card to be the new currentCard
                    consecutivesMove.addCard(this.hand.getCard(j));                                                                                                     // Add the currentCard to the consecutivesMove Deck
                }
            }

            if (this.game.validateConsecutives(consecutivesMove)) {                                                                                                 // If the Cards make up a valid consecutives play...
                consecutivesMoveOptions.add(new Deck(true));                                                                                                            // Add a new empty Deck to the list of valid consecutives moves
                for (Card card : consecutivesMove.getCards()) {                                                                                                             // For every Card in this consecutive move...
                    consecutivesMoveOptions.get(consecutivesMoveOptions.size() - 1).addCard(card);                                                                              // Add the Card to the newly created Deck at the end of consecutivesMoveOptions
                }
            }

            consecutivesMove.clearCards();                                                                                                                              // Clear the consecutivesMove Deck so that it is ready to handle a new consecutives move
        }
            
        return consecutivesMoveOptions;                                                                                                                                 // Return the ArrayList of valid consecutives move options
    }

    
    // Returns all of the possible valid moves that could be played this turn
    public ArrayList<Deck> getAllMoves() {
        ArrayList<Deck> allValidMoves = new ArrayList<Deck>();                                                                                                          // Create an ArrayList of Decks that will hold all possible valid moves
        ArrayList<ArrayList<Deck>> categorizedMoveOptions = new ArrayList<ArrayList<Deck>>();                                                                           // Create an ArrayList of ArrayLists of Decks that will hold the move options for each round type
        
        categorizedMoveOptions.add(this.getSinglesMoves());                                                                                                           // Add all of the valid singles moves into the categorizedMoveOptions ArrayList
        categorizedMoveOptions.add(this.getDoublesMoves());                                                                                                           // Add all of the valid doubles moves into the categorizedMoveOptions ArrayList
        categorizedMoveOptions.add(this.getTriplesMoves());                                                                                                           // Add all of the valid triples moves into the categorizedMoveOptions ArrayList
        categorizedMoveOptions.add(this.getConsecutivesMoves());                                                                                                      // Add all of the valid consecutives moves into the categorizedMoveOptions ArrayList
        
        for (ArrayList<Deck> moveOptions : categorizedMoveOptions) {                                                                                                    // For each of the lists holding the move options for a specific round type...
            for (Deck move : moveOptions) {                                                                                                                                 // For each of the moves in the list of valid moves for a specific round type...
                allValidMoves.add(move);                                                                                                                                    // Add the move to the allValidMoves ArrayList
            }
        }
        
        return allValidMoves;                                                                                                                                           // Return all valid moves
    }
    
    // Selects a move to play at random
    private void playRandomMove(ArrayList<Deck> moveOptions) {
        Deck moveToPlay = moveOptions.get(this.RNG.nextInt(moveOptions.size()));                                                                                // Store a random move from the moveOptions ArrayList as a Deck of Cards
        int cardIndexToPlay = 0;                                                                                                                                        // Set the index value of the next Card to play as 0 (the first Card in the move)

        for (int cardIndex = 0; cardIndex < this.hand.getSize(); cardIndex++) {                                                                                         // For every Card in the Bot's hand...
            if (this.hand.getCard(cardIndex) == moveToPlay.getCard(cardIndexToPlay)) {                                                                              // If the Card in the Bot's hand is the same as the next Card to play from the moveToPlay Deck...
                this.hand.getCard(cardIndex).setSelected(true);                                                                                                         // Set the Card in the Bot's hand to be selected for play
                cardIndexToPlay++;                                                                                                                                            // Move onto the index of the next Card that needs to be played
                
                if (cardIndexToPlay == moveToPlay.getSize()) {                                                                                                          // If all the Cards that needed to be selected have been selected...
                    this.game.playCards();                                                                                                                                  // Play the Cards now that every Card has been selected for play
                    return;                                                                                                                                                 // Return here now that the Cards have been played
                }
            }
        }
    }
    
    // The Bot performs their turn
    public void playTurn() {
        ArrayList<Deck> moveOptions = new ArrayList<Deck>();                                                                                                            // Create an ArrayList of Decks that will store all possible moves that can be played
        
        if (this.game.getRoundType() == GameHandler.SINGLES) {                                                                                                          // If this is a singles round...
            moveOptions = this.getSinglesMoves();                                                                                                                           // Store all possible singles moves into the moveOptions ArrayList
        }
        else if (this.game.getRoundType() == GameHandler.DOUBLES) {                                                                                                     // If this is a doubles round...
            moveOptions = this.getDoublesMoves();                                                                                                                           // Store all possible doubles moves into the moveOptions ArrayList
        }
        else if (this.game.getRoundType() == GameHandler.TRIPLES) {                                                                                                     // If this is a triples round...
            moveOptions = this.getTriplesMoves();                                                                                                                           // Store all possible triples moves into the moveOptions ArrayList
        }
        else if (this.game.getRoundType() == GameHandler.CONSECUTIVES) {                                                                                                // If this is a consecutives round...
            moveOptions = this.getConsecutivesMoves();                                                                                                                      // Store all possible consecutives moves into the moveOptions ArrayList
        }
        else if (this.game.getRoundType() == GameHandler.UNDEFINED) {                                                                                                   // If this is the start of a new round...
            moveOptions = this.getAllMoves();                                                                                                                               // Store all possible moves of every type into the moveOptions ArrayList
        }
        if (moveOptions.size() == 0) {                                                                                                                                  // If the Bot is unable to play any moves...
            this.game.passTurn();                                                                                                                                               // Pass the turn to the next hand
        }
        else {                                                                                                                                                          // Otherwise...
            this.playRandomMove(moveOptions);                                                                                                                               // Play a random move from the Bot's list of moves
        }
    }
}
