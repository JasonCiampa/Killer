package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS//
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class GameHandler {
    
    // FIELDS //
    private Deck fullDeck;                                                                                                                                                                          // A Deck that will hold all 52 possible Cards
    private ArrayList<Integer> cardsToPlay;                                                                                                                                                         // An ArrayList that will store the indices of Cards that have been selected to be played
    private Deck currentMove;                                                                                                                                                                       // A Deck that will store the current move (which cards have been played most recently onto the table)
    
    private Deck[] hands;                                                                                                                                                                           // An array of Decks that will hold each of the four hands
    private Deck leftHand;                                                                                                                                                                          // A Deck that will serve as the left bot's hand
    private Deck centerHand;                                                                                                                                                                        // A Deck that will serve as the center bot's hand
    private Deck rightHand;                                                                                                                                                                         // A Deck that will serve as the right bot's hand
    private Deck playerHand;                                                                                                                                                                        // A Deck that will serve as the player's hand
    
    private static final int SINGLES = 1;                                                                                                                                                                  // A constant int used to indicate a Singles round
    private static final int DOUBLES = 2;                                                                                                                                                                  // A constant int used to indicate a Doubles round
    private static final int TRIPLES = 3;                                                                                                                                                                  // A constant int used to indicate a Triples round
    private static final int CONSECUTIVES = 4;                                                                                                                                                             // A constant int used to indicate a Consecutives round
    private int roundType;                                                                                                                                                                          // An int used to keep track of the current round type
    
    private int turnToPlay;                                                                                                                                                                         // Keeps track of which hand is currently up to play Cards
    private boolean botPlayingTurn;                                                                                                                                                                 // Keeps track of whether or not the bot is currently playing their turn
    
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR //
    public GameHandler() {
        this.fullDeck = new Deck(false);                                                                                                                                                      // Creates a new Deck with 52 cards
        this.cardsToPlay = new ArrayList<Integer>();
        this.currentMove = new Deck(true);                                                                                                                                                    // Creates a new empty Deck that will store the cards played on the latest turn

        this.hands = this.fullDeck.deal(13, 4);                                                                                                                                     // Creates 4 hands with 13 cards each (all cards come from fullDeck, which is now empty)
        
        this.leftHand = this.hands[0];                                                                                                                                                              // Set the left hand equal to the first hand created
        this.centerHand = this.hands[1];                                                                                                                                                            // Set the center hand equal to the second hand created
        this.rightHand = this.hands[2];                                                                                                                                                             // Set the right hand equal to the third hand created
        this.playerHand = this.hands[3];                                                                                                                                                            // Set the Player's hand equal to the fourth hand created
        
        this.setHand(this.leftHand, Card.LEFT);                                                                                                                                    // Sets up the left hand
        this.setHand(this.centerHand, Card.CENTER);                                                                                                                                // Sets up the center hand
        this.setHand(this.rightHand, Card.RIGHT);                                                                                                                                  // Sets up the right hand
        
        this.setHandAlignments();                                                                                                                                                                   // Sets the position for every Card in each hand
        
        for (Deck hand : this.hands) {                                                                                                                                                              // For each hand created...
            hand.sort();                                                                                                                                                                                // Sort the hand from lowest Card value to highest
            hand.alignCards(0, hand.getX(), hand.getY(), hand.getShiftX(), hand.getShiftY(), hand.getCardWidth(), hand.getCardHeight());        // Align all of the Cards in this hand neatly next to each other
        }                     

//        this.determineFirstTurn();                                                                                                                                                                  // Determine which hand should play their Cards first
        this.turnToPlay = 3;
        this.startNewRound();                                                                                                                                                                       // Start the first round
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // TURN HANDLING METHODS //
    
    
    // Determines which hand is up first to start the game (whichever hand has the three of spades)
    private void determineFirstTurn() {                                                                                                                                                                                                         
        for (int handIndex = 0; handIndex < this.hands.length; handIndex++) {                                                                                                                       // For each hand in the game...
            Card lowestCard = this.hands[handIndex].getCard(0);                                                                                                                                // Set the lowest Card in the hand equal to the first Card in the hand (since each Deck has been sorted, the first Card is the lowest Card)
            
            if (lowestCard.getSuit() == Card.SPADE && lowestCard.getValue() == Card.VALUE_3) {                                                                                                          // If the lowest Card is the three of spades...
                this.turnToPlay = handIndex;                                                                                                                                                                // Set this hand as the Deck that will play first (by storing its index value)
                return;                                                                                                                                                                                     // Return since the first turn has been determined
            }
        }
    }
    
    // Passes the current turn
    private void passTurn() {
        this.hands[this.turnToPlay].setPlayable(false);                                                                                                                                     // Sets the hand's playable status to false so that Cards can't be played
    }
    
    // Sets turnToPlay equal to whichever hand is up to play their cards
    private void setNextTurn() {
        this.turnToPlay = this.turnToPlay + 1;                                                                                                                                                      // Increases the index indicating which hand should play by 1
        
        if (this.turnToPlay > 3) {                                                                                                                                                                  // If the new index refers to a non-existent hand...
            this.turnToPlay = 0;                                                                                                                                                                        // Set the index to 0 so that the first hand in the list is up again
        }
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CARD MOVE VALIDATION METHODS //
    
    // Returns whether or not the given Cards are valid consecutives
    private boolean validateConsecutives(Deck cards) {
        int numCards = cards.getSize();                                                                                                                                                             // Sets the number of Cards in the Deck that was passed in
        
        for (int cardIndex = 1; cardIndex < numCards - 1; cardIndex++) {                                                                                                                            // For every Card in the Deck that was passed in except the first Card...
            Card previousCard = (cards.getCard(cardIndex - 1));                                                                                                                                         // Store a reference to the Card that comes before the current Card
            Card currentCard = (cards.getCard(cardIndex));                                                                                                                                              // Store a reference to the current Card
                
            if (previousCard.getValue() >= currentCard.getValue()) {                                                                                                                                     // If the previous Card has a value the same as or greater than the current Card's value...
                return false;                                                                                                                                                                               // Return false because this isn't a proper consecutive move
            }
        }
        
        if (this.roundType != -1) {                                                                                                                                                                 // If this isn't the first move of the round (meaning that the round type has already been established)...
            if (!(cards.getCard(0).getValue() >= this.currentMove.getCard(0).getValue())) {                                                                                           // If the first Card in this Deck has a value that isn't the same as or greater than the first Card in the currentMove on the table...
                return false;                                                                                                                                                                               // Return false because the first card must have the same value or a greater one than the first Card on the table     
            }
        }
        
        return true;                                                                                                                                                                                // Return true because this is a proper consecutive move
    }
    
    // Returns whether or not the given Cards are valid triples
    private boolean validateTriples(Deck cards) {
        int firstCardValue = cards.getCard(0).getValue();                                                                                                                                  // Stores a reference to the first Card's value in the Deck that was passed in
        int secondCardValue = cards.getCard(1).getValue();                                                                                                                                 // Stores a reference to the second Card's value in the Deck that was passed in
        int thirdCardValue = cards.getCard(2).getValue();                                                                                                                                  // Stores a reference to the third Card's value in the Deck that was passed in

        if ((firstCardValue != secondCardValue) || (firstCardValue != thirdCardValue) || (secondCardValue != thirdCardValue)) {                                                                     // If any of the Card values aren't equal to each other...
            return false;                                                                                                                                                                               // Return false because this isn't a proper triples move
        }
        
        if (this.roundType != -1) {                                                                                                                                                                 // If this isn't the first move of the round (meaning that the round type has already been established)...
            if (firstCardValue < this.currentMove.getCard(0).getValue()) {                                                                                                                     // If the first Card in this Deck has a value that isn't the same as or greater than the first Card in the currentMove on the table...
                return false;                                                                                                                                                                               // Return false because the first card must have the same value or a greater one than the first Card on the table
            }
        }
        
        return true;                                                                                                                                                                                // Return true because this is a proper triples move
    }
    
    // Returns whether or not the given Cards are valid doubles
    private boolean validateDoubles(Deck cards) {
        int firstCardValue = cards.getCard(0).getValue();                                                                                                                                  // Stores a reference to the first Card's value in the Deck that was passed in
        int secondCardValue = cards.getCard(1).getValue();                                                                                                                                 // Stores a reference to the second Card's value in the Deck that was passed in

        if (firstCardValue != secondCardValue) {                                                                                                                                                    // If the two cards don't have the same value...
            return false;                                                                                                                                                                               // Return false because this isn't a proper doubles move
        }
        
        if (this.roundType != -1) {                                                                                                                                                                 // If this isn't the first move of the round (meaning that the round type has already been established)...
            if (firstCardValue < this.currentMove.getCard(0).getValue()) {                                                                                                                     // If the first Card in this Deck has a value that isn't the same as or greater than the first Card in the currentMove on the table...
                return false;                                                                                                                                                                                // Return false because the first card must have the same value or a greater one than the first Card on the table
            }
        }
        
        return true;                                                                                                                                                                                // Return true because this is a proper triples move
    }
    
    // Returns whether or not the given Card is a valid single
    private boolean validateSingles(Card card) {
        if (card.getValue() < this.currentMove.getCard(0).getValue()) {                                                                                                                    // If the Card that was passed in doesn't have the same value or a greater on than the Card on the table currently...
            return false;                                                                                                                                                                               // Return false because this isn't a proper singles move
        }
        
        return true;                                                                                                                                                                                // Return true because this is a proper singles move
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // ROUND HANDLING METHODS //
    
    // Starts a new round
    private void startNewRound() {
        for (Deck hand : this.hands) {                                                                                                                                                              // For every hand in the game...
            hand.setPlayable(true);                                                                                                                                                             // Make the hand playable
        }
        
        this.currentMove.clearCards();                                                                                                                                                              // Clear the table for the start of the new round
        this.roundType = -1;                                                                                                                                                                        // Set the round type to -1 (this will be changed when the first cards are played down)
    }
    
    // Checks how far the round has progressed
    private void checkRoundProgress() {
        ArrayList<Integer> activeHands = new ArrayList<Integer>();                                                                                                                                  // Create an ArrayList that will store the index of each hand that hasn't passed this round
        
        for (int handIndex = 0; handIndex < 4; handIndex++) {                                                                                                                                       // For each hand in the game...
            if (this.hands[handIndex].getPlayable()) {                                                                                                                                                  // If the hand hasn't passed their turn...
                activeHands.add(handIndex);                                                                                                                                                               // Add the hand's index to the activeHands ArrayList
            }
        }
        
        if (activeHands.size() == 1) {                                                                                                                                                              // If there is only one active hand...
            this.turnToPlay = activeHands.get(0);                                                                                                                                                 // Set that active hand to have their turn first next round since they won this round
            this.startNewRound();                                                                                                                                                                       // Start the new round
        }
    }
    
    
    // Determines what the type of round is based on the first move played at the start of the round (singles, doubles, triples, consecutives, or -1 if the first move is invalid)
    private int determineRoundType(Deck startingCards) {
        int numCards = startingCards.getSize();                                                                                                                                                     // Store the number of cards in the Deck that was passed in
//        System.out.println(numCards);

        if (numCards == 1) {
            this.roundType = SINGLES;
        }
        else if (numCards == 2) {                                                                                                                                                                   // If there are two cards...
            if (this.validateDoubles(startingCards)) {                                                                                                                                            // If the cards are a valid double play...
                this.roundType = DOUBLES;                                                                                                                                                               // Set the roundType to be singles
            }
        }  
        else if (numCards == 3) {                                                                                                                                                                   // If there are three cards...
            if (this.validateTriples(startingCards)) {                                                                                                                                            // If the cards are a valid triple play...
                this.roundType = TRIPLES;                                                                                                                                                                   // Set the roundType to be consecutives
            }
            else if (this.validateConsecutives(startingCards)) {                                                                                                                                  // Otherwise, if the cards are a valid consecutive play
                this.roundType = CONSECUTIVES;                                                                                                                                                              // Set the roundType to be consecutives
            } 
        }    
        else if (numCards >= 4) {                                                                                                                                                                   // If there are four or more cards...
            if (this.validateConsecutives(startingCards)) {                                                                                                                                       // If the cards are a valid consecutive play...
                this.roundType = CONSECUTIVES;                                                                                                                                                              // Set the roundType to be consecutives
            }
        }                                                                                                                                                                                    // No check for singles because any single card thrown at the start of the round has to be valid by default
        
        return this.roundType;                                                                                                                                                                      // Return the roundType
    }
    
    // Returns whether or not a move of Cards is valid to play
    private boolean checkMoveValidity(ArrayList<Integer> cardIndices) {
        Deck tempDeck = new Deck(true);                                                                                                                                                       // Creates a temporary empty Deck that will hold all of the Cards to check the validity of
        System.out.println(tempDeck.getSize() + " fart");    

        for (int i = 0; i < cardIndices.size() - 1; i++) {                                                                                                                                          // For every cardIndex in the cardIndices list...
            tempDeck.addCard(this.hands[this.turnToPlay].getCard(cardIndices.get(i)));                                                                                                                     // Use the cardIndex to copy the Card from the current turn hand 
        }

        if (this.roundType == SINGLES) {                                                                                                                                                            // If the roundType is singles...
            return this.validateSingles(tempDeck.getCard(0));                                                                                                                             // Return whether or not the Cards passed in are a valid singles move
        }
        else if (this.roundType == DOUBLES) {                                                                                                                                                       // If the roundType is doubles...
            return this.validateDoubles(tempDeck);                                                                                                                                                // Return whether or not the Cards passed in are a valid doubles move
        }
        else if (this.roundType == TRIPLES) {                                                                                                                                                       // If the roundType is triples...
            return this.validateTriples(tempDeck);                                                                                                                                                // Return whether or not the Cards passed in are a valid triples move
        }
        else if (this.roundType == CONSECUTIVES) {                                                                                                                                                  // If the roundType is consecutives...
            return this.validateConsecutives(tempDeck);                                                                                                                                           // Return whether or not the Cards passed in are a valid consecutives move
                // Also check for Killers and Bombs
        }
        else {                                                                                                                                                                                      // Otherwise, a new round must be starting, so...
            if (this.determineRoundType(tempDeck) != -1) {                                                                                                                                        // Determine what type of round this one will be                                                                                                                                 
                return true;                                                                                                                                                                            // Return true because the round was able to be set, meaning a valid card selection was played
            }
        }
        
        return false;                                                                                                                                                                               // Return false because the cards passed in did not produce a valid move
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // DECK AND CARD STATUS METHODS //
    
    // Sets the alignment values for each Hand
    private void setHandAlignments() {
        this.leftHand.setAlignment((((((float) (Gdx.graphics.getWidth() / 4.2) - ((this.leftHand.getSize() - 1)) * 20)) + 75) / 2), 450, 20, 5, 75, 132);         // Sets the leftHands Card position values 
        this.centerHand.setAlignment((((Gdx.graphics.getWidth() - (((this.centerHand.getSize() - 1) * 25) + 35)) / 2)), 660, 25, 0, 35, 62);                      // Sets the centerHands Card position values
        this.rightHand.setAlignment(((float) (Gdx.graphics.getWidth() / 1.2) + (((this.rightHand.getSize() - 1) * 20) / 2)), 450, -20, 5, 75, 132);               // Sets the rightHands Card position values
        this.playerHand.setAlignment(((float) (Gdx.graphics.getWidth() - (((this.playerHand.getSize() - 1) * 145) + 160)) / 2), 150, 145, 0, 160, 280);           // Sets the playerHands Card position values
        this.currentMove.setAlignment((((float) ((Gdx.graphics.getWidth() / 1.1)) - ((this.currentMove.getSize() - 1) * 38) + 80) / 2), 500, 38, 0, 80, 140);     // Sets the currentMoves Card position values
    }
    
    // Sets the skin for each card in the hand and flips the Card
    private void setHand(Deck hand, int backSkinValue) {                                                                                                                            
        for (Card card : hand.getCards()) {                                                                                                                                                         // For each Card in the hand...
            card.setBackSkin(Card.BACK, backSkinValue);                                                                                                                                // Set the Card's back skin to backSkinValue
            card.flip();                                                                                                                                                                                // Flip the Card over
        }
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    
    // Plays the currently selected cards from the hand or rejects the play if the move isn't valid
    public void playCards() {
        for (int cardIndex = 0; cardIndex < this.hands[this.turnToPlay].getSize(); cardIndex++) {                                                                                                   // For every card in the hand that is playing their turn...                                                                                  
            Card card = this.hands[turnToPlay].getCard(cardIndex);                                                                                                                                      // Create a variable to store the Card so it doesn't need to be fetched more than once                                                                                                                             
            if (card.getSelected()) {                                                                                                                                                                   // If the Card is selected...
                card.setSelected(false);                                                                                                                                                            // Unselect the Card now that it is being played
                this.cardsToPlay.add(cardIndex);                                                                                                                                                             // Add the Card into the cardsToPlay Deck
            }
        }            
        
        if (this.checkMoveValidity(this.cardsToPlay)) {                                                                                                                                             // If the Cards that were selected to be played are a valid move in Killer...
            for (int i = this.cardsToPlay.size() - 1; i > -1; i--) {                                                                                                                                        // For every Card that was selected to be played (starting from the last one)...
                Card card = this.hands[this.turnToPlay].getCard(this.cardsToPlay.get(i));                                                                                                   // Store the Card in a variable once so it doesn't have to be fetched more than once
                this.currentMove.addCard(card);                                                                                                                                                            // Add the Card into the currentMove Deck now that it is being played 
                this.hands[this.turnToPlay].removeCard(this.cardsToPlay.get(i));                                                                                                            // Remove the Card from the hand now that it is being played 
            } 
            
            this.setHandAlignments();
            this.hands[this.turnToPlay].alignCards((float) 0.5, this.hands[this.turnToPlay].getX(), this.hands[this.turnToPlay].getY(), this.hands[this.turnToPlay].getShiftX(), this.hands[this.turnToPlay].getShiftY(), this.hands[this.turnToPlay].getCardWidth(), this.hands[this.turnToPlay].getCardHeight());
            this.currentMove.alignCards((float) 0.5, this.currentMove.getX(), this.currentMove.getY(), this.currentMove.getShiftX(), this.currentMove.getShiftY(), this.currentMove.getCardWidth(), this.currentMove.getCardHeight());
            this.setNextTurn();
        }
        else {
            this.cardsToPlay.clear();
            // Play an error sound or display an error message since the card that are trying to be played aren't valid
            System.out.println("Invalid Cards to Play");
            System.out.println(this.roundType);
        }
    }
    

        
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // STATE HANDLING FUNCTIONS //
    
    // Updates every Card in every hand in the game
    public void update() {
                
        if (!this.hands[this.turnToPlay].getPlayable()) {                                                                                                                                           // If the current hand has passed their turn for the round...
            this.setNextTurn();                                                                                                                                                                         // Move the turn to the next in line
        }
        
        if (this.turnToPlay != 3 && !this.botPlayingTurn) {                                                                                                                                         // If the current turn is not the Player's turn and a bot isn't already in the process of playing their turn...
            // Start bot turn                                                                                                                                                                           // Start the bot's turn
        }
        
        this.currentMove.update();
        
        for (Deck hand : this.hands) {                                                                                                                                                              // For each of the hands in the game...
            hand.update();                                                                                                                                                                              // Update all of the Cards in the hand
        }
        
        for (Card card : this.playerHand.getCards()) {                                                                                                                                              // For every Card in the player's hand...
            card.toggleSelected();                                                                                                                                                                      // Check if the Card has been selected by the player or not
        }
        
        this.checkRoundProgress();                                                                                                                                                                  // Checks how far along the round is and ends the round if necessary
    }
    
    // Draws every Card from every hand in the game
    public void draw(SpriteBatch batch) {               
        this.currentMove.draw(batch);
        
        for (Deck hand : this.hands) {                                                                                                                                                    // For each of the hands in the game...
            hand.draw(batch);                                                                                                                                                           // Draw all of the Cards in the hand
        }
    }
    
}
