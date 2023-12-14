package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS//
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class GameHandler {
    
    // FIELDS //
    private Deck fullDeck;                                                                                                                                                                          // A Deck that will hold all 52 possible Cards
    private ArrayList<Integer> cardsToPlay;                                                                                                                                                         // An ArrayList that will store the indices of Cards that have been selected to be played
    private Deck currentMove;                                                                                                                                                                       // A Deck that will store the current move (which cards have been played most recently onto the table)
    private Deck previousMove;                                                                                                                                                                      // A Deck that will store the Cards that have been played this round but aren't a part of the most recent move
    
    private Deck[] hands;                                                                                                                                                                           // An array of Decks that will hold each of the four hands
    private Deck leftHand;                                                                                                                                                                          // A Deck that will serve as the left bot's hand
    private Deck centerHand;                                                                                                                                                                        // A Deck that will serve as the center bot's hand
    private Deck rightHand;                                                                                                                                                                         // A Deck that will serve as the right bot's hand
    private Deck playerHand;                                                                                                                                                                        // A Deck that will serve as the player's hand
    
    private Bot bot;                                                                                                                                                                                // A Bot that will play the three non-player hands

    private boolean playerInputAllowed;
    
    public static final int UNDEFINED = -1;
    public static final int SINGLES = 1;                                                                                                                                                           // A constant int used to indicate a Singles round
    public static final int DOUBLES = 2;                                                                                                                                                           // A constant int used to indicate a Doubles round
    public static final int TRIPLES = 3;                                                                                                                                                           // A constant int used to indicate a Triples round
    public static final int CONSECUTIVES = 4;                                                                                                                                                      // A constant int used to indicate a Consecutives round
    
    private int roundType;                                                                                                                                                                          // An int used to keep track of the current round type
    private boolean roundEnding;                                                                                                                                                                    // A boolean used to keep track of whether ot not the round is in the process of ending
    private final Texture roundOver = new Texture(Gdx.files.internal("images/user_interface/round_over.png"));                                                                                  // An image of the "round over" text
    
    private int turnToPlay;                                                                                                                                                                         // Keeps track of which hand is currently up to play Cards
    private final Texture turnArrow = new Texture(Gdx.files.internal("images/user_interface/arrow/arrow_down.png"));                                                                                                                                                                      // An arrow that is drawn on the screen to indicate whose turn it is
    private final Texture passed = new Texture (Gdx.files.internal("images/user_interface/passed.png"));
    private boolean botPlayingTurn;                                                                                                                                                                 // Keeps track of whether or not the bot is currently playing their turn
    private float timer = 0;
    
    private boolean gameOver = false;                                                                                                                                                               // Keeps track of whether or not the game has ended
    private Texture gameOutcome;                                                                                                                                                                    // An image that will display "You Win!" or "You Lose!" at the end of a game

    private Sound error;                                                                                                                                                                            // A Sound that will store the "Error" SFX for the game
    
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR //
    public GameHandler() {
        this.error = Gdx.audio.newSound(Gdx.files.internal("sfx/error.mp3"));                                                                                                              // Creates the "Error" SFX
        this.fullDeck = new Deck(false);                                                                                                                                                      // Creates a new Deck with 52 cards
        this.cardsToPlay = new ArrayList<Integer>();                                                                                                                                                // Creates an ArrayList of integers used to keep track of the indexes of Cards that might be played
        this.currentMove = new Deck(true);                                                                                                                                                    // Creates a new empty Deck that will store the cards played on the latest turn
        this.previousMove = new Deck(true);                                                                                                                                                   // Creates a new empty Deck that will store the Cards that have been played this round but are no longer part of the most recent move
        
        this.hands = this.fullDeck.deal(13, 4);                                                                                                                                     // Creates 4 hands with 13 cards each (all cards come from fullDeck, which is now empty)
        this.leftHand = this.hands[0];                                                                                                                                                              // Set the left hand equal to the first hand created
        this.centerHand = this.hands[1];                                                                                                                                                            // Set the center hand equal to the second hand created
        this.rightHand = this.hands[2];                                                                                                                                                             // Set the right hand equal to the third hand created
        this.playerHand = this.hands[3];                                                                                                                                                            // Set the Player's hand equal to the fourth hand created
        
        this.setHand(this.leftHand, Card.LEFT);                                                                                                                                    // Sets up the left hand so that the backs of the Cards are displayed and angled leftward
        this.setHand(this.centerHand, Card.CENTER);                                                                                                                                // Sets up the center hand so that the backs of the Cards are displayed
        this.setHand(this.rightHand, Card.RIGHT);                                                                                                                                  // Sets up the right hand so that the backs of the Cards are displayed and angled rightward
        
        this.setHandAlignments();                                                                                                                                                                   // Sets the position for every Card in each hand
        
        for (Deck hand : this.hands) {                                                                                                                                                              // For each hand created...
            hand.sort();                                                                                                                                                                                // Sort the hand from lowest Card value to highest
            hand.alignCards(0, hand.getX(), hand.getY(), hand.getShiftX(), hand.getShiftY(), hand.getCardWidth(), hand.getCardHeight());        // Align all of the Cards in this hand neatly next to each other
        }         
        
        this.determineFirstTurn();                                                                                                                                                                  // Determine which hand should play their Cards first
        
        this.bot = new Bot(this, this.hands[this.turnToPlay]);
        this.bot.setHand(this.hands[this.turnToPlay]);
        
        this.startNewRound();                                                                                                                                                                       // Start the first round
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // Returns the Deck representing the current move on the table
    public Deck getCurrentMove() {
        return this.currentMove;
    }
    
    // Returns whether or not input (button presses) from the Player are currently allowed
    public boolean getPlayerInputAllowed() {
        return this.playerInputAllowed;
    }
    
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
    
    // Sets turnToPlay equal to whichever hand is up to play their cards
    private void setNextTurn() {
        this.turnToPlay = this.turnToPlay + 1;                                                                                                                                                      // Increases the index indicating which hand should play by 1
        
        if (this.turnToPlay > 3) {                                                                                                                                                                  // If the new index refers to a non-existent hand...
            this.turnToPlay = 0;                                                                                                                                                                        // Set the index to 0 so that the first hand in the list is up again
        }
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CARD MOVE VALIDATION METHODS //
    
    // Returns whether or not the given Card is a valid single
    public boolean validateSingles(Card card) {
        if (this.roundType != UNDEFINED) {                                                                                                                                                          // If this is the first move of the round...
            if (card.getValue() < this.currentMove.getCard(0).getValue()) {                                                                                                                    // If the Card that was passed in doesn't have the same value or a greater on than the Card on the table currently...
                return false;                                                                                                                                                                               // Return false because this isn't a proper singles move
            }
        
            if (card.getValue() > Card.VALUE_10 && (card.getValue() == this.currentMove.getCard(0).getValue()) ) {                                                                             // If the Card has a value that is greater than 10 and is equal to the current move Card...
                if (card.getSuit() < this.currentMove.getCard(0).getSuit()) {                                                                                                                      // If the Card's suit is lower than the Card on the table currently...
                    return false;                                                                                                                                                                           // Return false because face cards and higher consider suits, and the Card's suit was not high enough
                }
            } 
        }
        
        return true;                                                                                                                                                                                // Return true because this is a proper singles move
    }
    
        // Returns whether or not the given Cards are valid doubles
    public boolean validateDoubles(Deck cards) {
        if (cards.getSize() != 2) {                                                                                                                                                               // If there are not exactly two Cards...
            return false;                                                                                                                                                                             // Return false because this isn't a proper doubles move
        }
        
        Card firstCard = cards.getCard(0);                                                                                                                                                // Stores a reference to the first Card in the Deck that was passed in
        Card secondCard = cards.getCard(1);                                                                                                                                               // Stores a reference to the second Card in the Deck that was passed in

        if (firstCard.getValue() != secondCard.getValue()) {                                                                                                                                      // If the two cards don't have the same value...
            return false;                                                                                                                                                                             // Return false because this isn't a proper doubles move
        }
        
        if (this.roundType != UNDEFINED) {                                                                                                                                                         // If this isn't the first move of the round (meaning that the round type has already been established)...
            if (secondCard.getValue() < this.currentMove.getCard(0).getValue()) {                                                                                                             // If the second Card in this Deck has a value that is less than the first Card in the currentMove on the table...
                return false;                                                                                                                                                                          // Return false because the first card must have the same value or a greater one than the first Card on the table
            }
            
            if (secondCard.getValue() > Card.VALUE_10 && (secondCard.getValue() == this.currentMove.getCard(0).getValue())) {                                                              // If the Card has a value that is greater than 10 and is equal to the current move Card...
                if (secondCard.getSuit() < this.currentMove.getCard(1).getSuit()) {                                                                                                           // If the first Card's suit is lower than the first Card's suit in the current move...
                    return false;                                                                                                                                                                         // Return false because the first Card doesn't have a high enough suit, and suits are considered with face cards and higher
                }
            }
        }
        
        return true;                                                                                                                                                                                // Return true because this is a proper doubles move
    }
    
    // Returns whether or not the given Cards are valid triples
    public boolean validateTriples(Deck cards) {
        if (cards.getSize() != 3) {                                                                                                                                                             // If there are not exactly three cards...
            return false;                                                                                                                                                                           // Return false because this isn't a proper triples move
        }
        
        Card firstCard = cards.getCard(0);                                                                                                                                              // Stores a reference to the first Card in the Deck that was passed in
        Card secondCard = cards.getCard(1);                                                                                                                                             // Stores a reference to the second Card in the Deck that was passed in
        Card thirdCard = cards.getCard(2);                                                                                                                                              // Stores a reference to the third Card in the Deck that was passed in

        if ((firstCard.getValue() != secondCard.getValue()) || (firstCard.getValue() != thirdCard.getValue()) || (secondCard.getValue() != thirdCard.getValue())) {                              // If any of the Card values aren't equal to each other...
            return false;                                                                                                                                                                            // Return false because this isn't a proper triples move
        }
        
        if (this.roundType != UNDEFINED) {                                                                                                                                                              // If this isn't the first move of the round (meaning that the round type has already been established)...
            if (firstCard.getValue() < this.currentMove.getCard(0).getValue()) {                                                                                                            // If the first Card in this Deck has a value that isn't the same as or greater than the first Card in the currentMove on the table...
                return false;                                                                                                                                                                            // Return false because the first card must have the same value or a greater one than the first Card on the table
            }
        }
        
        return true;                                                                                                                                                                                // Return true because this is a proper triples move
    }
    
    // Returns whether or not the given Cards are valid consecutives
    public boolean validateConsecutives(Deck cards) {     
        if (cards.getSize() < 3) {                                                                                                                                                              // If there are not at least 3 Cards...
            return false;                                                                                                                                                                           // Return false because this is not a valid consecutives move
        }
        
        for (int cardIndex = 1; cardIndex < cards.getSize(); cardIndex++) {                                                                                                                     // For every Card in the Deck that was passed in except the first Card...
            Card previousCard = (cards.getCard(cardIndex - 1));                                                                                                                                         // Store a reference to the Card that comes before the current Card
            Card currentCard = (cards.getCard(cardIndex));                                                                                                                                              // Store a reference to the current Card
                
            if ((previousCard.getValue() + 1) != currentCard.getValue()) {                                                                                                                              // If the previous Card's value isn't one less than the current Card's value...
                return false;                                                                                                                                                                              // Return false because this isn't a proper consecutive move
            }
        }
        
        if (this.roundType != UNDEFINED) {                                                                                                                                                                 // If this isn't the first move of the round (meaning that the round type has already been established)...
            if ((cards.getSize() != this.currentMove.getSize())) {                                                                                                                                      // If the number of Cards selected for this consecutive play doesn't match the number of Cards on the table...
                return false;                                                                                                                                                                               // Return false because each play in a consecutives round must have the same number of Cards
            }
            
            if ((cards.getCard(0).getValue() < this.currentMove.getCard(0).getValue())) {                                                                                           // If the first Card in this Deck has a value that is less than the first Card in the currentMove on the table...
                return false;                                                                                                                                                                             // Return false because the first card must have the same value or a greater one than the first Card on the table     
            }
            
            Card lastCard = cards.getCard(cards.getSize() - 1);
            if (lastCard.getValue() > Card.VALUE_10 && (lastCard.getValue() == this.currentMove.getCard(this.currentMove.getSize() - 1).getValue())) {                                                              // If the Card has a value that is greater than 10 and is equal to the current move Card...
                if (lastCard.getSuit() < this.currentMove.getCard(this.currentMove.getSize() - 1).getSuit()) {                                                                                                           // If the first Card's suit is lower than the first Card's suit in the current move...
                    return false;                                                                                                                                                                         // Return false because the first Card doesn't have a high enough suit, and suits are considered with face cards and higher
                }
            }
        }
        
        return true;                                                                                                                                                                                // Return true because this is a proper consecutive move
    }
    
    
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // ROUND HANDLING METHODS //
    
    // Returns the current round type
    public int getRoundType() {
        return this.roundType;
    }
    
    // Starts a new round
    private void startNewRound() {
        for (Deck hand : this.hands) {                                                                                                                                                              // For every hand in the game...
            hand.setPlayable(true);                                                                                                                                                             // Make the hand playable
        }
        
        this.previousMove.clearCards();
        this.currentMove.clearCards();                                                                                                                                                              // Clear the table for the start of the new round
        this.roundType = UNDEFINED;                                                                                                                                                                 // Set the round type to undefined (this will be changed when the first cards are played down)
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
            this.timer = 2;                                                                                                                                                                            // Set a two second timer
            this.roundEnding = true;                                                                                                                                                                   // Set the roundEnding status to true
            this.startNewRound();                                                                                                                                                                      // Start the new round
        }
    }
    
    private void checkGameProgress() {
        for (Deck hand : this.hands) {                                                                                                                                                              // For every hand in the game...
            if (hand.getSize() == 0) {                                                                                                                                                                  // If there are no Cards left in the hand...
                this.gameOver = true;                                                                                                                                                                   // Set the gameOver status to true
                this.timer = 2;                                                                                                                                                                         // Set a two second timer
                if (hand == this.hands[3]) {                                                                                                                                                            // If the hand with no Cards is the Player's hand...
                    this.gameOutcome = new Texture(Gdx.files.internal("images/user_interface/you_win.png"));                                                                                     // Set the gameOutcome to be "You Win!"
                }   
                else {                                                                                                                                                                                  // Otherwise...
                    this.gameOutcome = new Texture(Gdx.files.internal("images/user_interface/you_lose.png"));                                                                                     // Set the gameOutcome to be "You Lose!"
                }
            }
        }
    }
    
    // Determines what the type of round is based on the first move played at the start of the round (singles, doubles, triples, consecutives, or -1 if the first move is invalid)
    private int determineRoundType(Deck startingCards) {
        int numCards = startingCards.getSize();                                                                                                                                                     // Store the number of cards in the Deck that was passed in

        if (numCards == 1) {                                                                                                                                                                        // If there is one Card...
            this.roundType = SINGLES;                                                                                                                                                                   // Set the roundType to singles
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
        }                                                                                                                                                                                    
        
        return this.roundType;                                                                                                                                                                      // Return the roundType
    }
    
    // Returns whether or not a move of Cards is valid to play
    private boolean checkMoveValidity(ArrayList<Integer> cardIndices) {
        Deck tempDeck = new Deck(true);                                                                                                                                                       // Creates a temporary empty Deck that will hold all of the Cards to check the validity of

        for (int i = 0; i < cardIndices.size(); i++) {                                                                                                                                             // For every cardIndex in the cardIndices list...
            tempDeck.addCard(this.hands[this.turnToPlay].getCard(cardIndices.get(i)));                                                                                                // Use the cardIndex to copy the Card from the current turn hand 
        }

        if (this.roundType == SINGLES) {                                                                                                                                                            // If the roundType is singles...
            if (tempDeck.getSize() == 1) {                                                                                                                                                              // If there is only one Card in the move...
                return this.validateSingles(tempDeck.getCard(0));                                                                                                                             // Return whether or not the Card passed in is a valid singles move
            }
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
        this.previousMove.setAlignment((((float) ((Gdx.graphics.getWidth() / 1.15)) - ((this.currentMove.getSize() - 1) * 38) + 80) / 2), 475, 38, 0, 80, 140);         // Sets the previousMoves Card position values
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
            Card card = this.hands[this.turnToPlay].getCard(cardIndex);                                                                                                                                          // Create a variable to store the Card so it doesn't need to be fetched more than once                                                                                                                             
            
            if (card.getSelected()) {                                                                                                                                                                       // If the Card is selected...
                card.setDrawHighlighted(false);                                                                                                                                                   // Ensure that the Card is not drawn as highlighted
                card.setSelected(false);                                                                                                                                                                // Unselect the Card now that it is being played
                this.cardsToPlay.add(cardIndex);                                                                                                                                                              // Add the Card's index into the cardsToPlay Deck
            }
        }    
        
        if(this.cardsToPlay.size() == 0) {                                                                                                                                                         // If there are no cards to play...
            System.out.println("No cards selected.");  
            this.error.setVolume(this.error.play(), (float) 1.5);
            return;
        }
        
        if (this.checkMoveValidity(this.cardsToPlay)) {                                                                                                                                  // If the Cards that were selected to be played are a valid move in Killer...
            this.previousMove.clearCards();
            for (Card card : this.currentMove.getCards()) {                                                                                                                                             // For every Card that is part of the current move...
                this.previousMove.addCard(card);                                                                                                                                                                 // Add the Card to the previousMove Deck so that it remains on the table even after the current move is reset
            }
            
            this.currentMove.clearCards();                                                                                                                                                             // Clear the current cards on the table                                                                                                                    
            for (int i = 0; i < this.cardsToPlay.size(); i++) {                                                                                                                                        // For every Card that needs to be played...
                Card card = this.hands[this.turnToPlay].getCard(this.cardsToPlay.get(i));                                                                                                   // Store the Card in a variable once so it doesn't have to be fetched more than once
                this.currentMove.addCard(card);                                                                                                                                                            // Add the Card into the currentMove Deck now that it is being played 
            }
            
            for (int i = this.cardsToPlay.size() - 1; i > -1; i--) {                                                                                                                                        // For every Card that was selected to be played (starting from the last one)...
                Card card = this.hands[this.turnToPlay].getCard(this.cardsToPlay.get(i));                                                                                                   // Store the Card in a variable once so it doesn't have to be fetched more than once
                this.hands[this.turnToPlay].removeCard(this.cardsToPlay.get(i));                                                                                                            // Remove the Card from the hand now that it is being played 
            } 
            
            if (this.turnToPlay != 3) {                                                                                                                                                             // If the current turn is a bot's turn...
                for (Card card : this.currentMove.getCards()) {
                    card.flip();
                }
            }
            
            this.setHandAlignments();
            this.hands[this.turnToPlay].alignCards((float) 0.5, this.hands[this.turnToPlay].getX(), this.hands[this.turnToPlay].getY(), this.hands[this.turnToPlay].getShiftX(), this.hands[this.turnToPlay].getShiftY(), this.hands[this.turnToPlay].getCardWidth(), this.hands[this.turnToPlay].getCardHeight());
            this.previousMove.alignCards((float) 0.5, this.previousMove.getX(), this.previousMove.getY(), this.previousMove.getShiftX(), this.previousMove.getShiftY(), this.previousMove.getCardWidth(), this.previousMove.getCardHeight());
            this.currentMove.alignCards((float) 0.5, this.currentMove.getX(), this.currentMove.getY(), this.currentMove.getShiftX(), this.currentMove.getShiftY(), this.currentMove.getCardWidth(), this.currentMove.getCardHeight());
            this.setNextTurn();
        }
        else {
            // Play an error sound or display an error message since the card that are trying to be played aren't valid
            for (Card card : this.hands[this.turnToPlay].getCards()) {
                if (card.getY() > (this.hands[this.turnToPlay].getY() + 5)) {
                    card.moveDown();
                }
            }
            
            System.out.println("Invalid Cards to Play");
            System.out.println("Round Type: " + this.roundType);
            this.error.setVolume(this.error.play(), (float) 1.5);

        }
        
        this.cardsToPlay.clear();
    }
    
    // Passes the current turn
    public void passTurn() {
        this.hands[this.turnToPlay].setPlayable(false);                                                                                                                                     // Sets the hand's playable status to false so that Cards can't be played
        this.setNextTurn();
    }
        
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // STATE HANDLING FUNCTIONS //
    
    // Updates every Card in every hand in the game
    public void update() {
        if (this.gameOver) {
            this.timer -= Gdx.graphics.getDeltaTime();
            
            if (this.timer < 0) {
                this.timer = 0;
            }
            else if (this.timer == 0) {
                return;
            }
        }
        
        this.bot.setHand(this.hands[this.turnToPlay]);
        this.checkGameProgress();
        
        if (this.roundEnding) {
            if (this.timer > 0) {                                                                                                                                                                       // If the timer is still counting down to 0...
                this.timer -= Gdx.graphics.getDeltaTime();                                                                                                                                                  // Reduce the timer by the amount of time that has passed since the last update
                return;
            } 
            else {                                                                                                                                                                                      // Otherwise...
                this.timer = 0;                                                                                                                                                                             // Set the timer back to 0
                this.roundEnding = false;
            }
        }
        
        if (!this.hands[this.turnToPlay].getPlayable()) {                                                                                                                                           // If the current hand has passed their turn for the round...
            this.setNextTurn();                                                                                                                                                                         // Move the turn to the next in line
        }
        
        if (this.turnToPlay != 3) {                                                                                                                                                                 // If the current turn is not the Player's turn...
            this.playerInputAllowed = false;                                                                                                                                                            // Disable the player's ability to play cards or pass their turn since it isn't their turn
            
            if(!this.bot.getPlayingTurn()) {                                                                                                                                                                  // If the bot isn't already in the process of playing a turn...
                this.timer = 2;                                                                                                                                                                             // Start a 2 second timer
                this.bot.setPlayingTurn(true);                                                                                                                                                                 // Set the botPlayingTurn value to true since the bot has commenced their turn
            }
        }
        else {                                                                                                                                                                                      // Otherwise...
            this.playerInputAllowed = true;                                                                                                                                                            // Enable the player's ability to play cards or pass their turn since it is their turn
        }
        
        if (this.bot.getPlayingTurn()) {                                                                                                                                                                  // If the bot is currently playing their turn...
            if (this.timer > 0) {                                                                                                                                                                       // If the timer is still counting down to 0...
                this.timer -= Gdx.graphics.getDeltaTime();                                                                                                                                                  // Reduce the timer by the amount of time that has passed since the last update
            }
            else {                                                                                                                                                                                      // Otherwise...
                this.timer = 0;                                                                                                                                                                             // Set the timer back to 0
                this.bot.playTurn();
                this.bot.setPlayingTurn(false);                                                                                                                                                                // Set the botPlayingTurn value to false now that the bot has finished their turn
            }
        }
        
        this.previousMove.update();
        this.currentMove.update();                                                                                                                                                                  // Update the Card(s) on the table
        
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
        for (Card card : this.previousMove.getCards()) {
            batch.setColor((float) 0.5, (float) 0.5, (float) 0.5, 1);
            card.draw(batch);
        }
        this.currentMove.draw(batch);
        
        for (Deck hand : this.hands) {                                                                                                                                                    // For each of the hands in the game...
            hand.draw(batch);                                                                                                                                                           // Draw all of the Cards in the hand
        }
        
        if (this.turnToPlay == 0 && this.leftHand.getPlayable()) {
            batch.draw(this.turnArrow, 192, 800, 60, 100);
        }
        else if (this.turnToPlay == 1 && this.centerHand.getPlayable()) {
            batch.draw(this.turnArrow, 927, 900, 60, 100);
        }
        else if (this.turnToPlay == 2 && this.rightHand.getPlayable()) {
            batch.draw(this.turnArrow, 1665, 800, 60, 100);
        }
        
        if (!this.leftHand.getPlayable()) {
            batch.draw(this.passed, 113, 825);
        }
        if (!this.centerHand.getPlayable()) {
            batch.draw(this.passed, 854, 900);
        }
        if (!this.rightHand.getPlayable()) {
            batch.draw(this.passed, 1600, 825);
        }
        
        if (this.roundEnding) {
            batch.draw(this.roundOver, (float) 672.5, 600);
            return;
        }
        
        if (this.gameOver) {
            batch.draw(this.gameOutcome, (float) 672.5, 600);
        }
    }
    
}
