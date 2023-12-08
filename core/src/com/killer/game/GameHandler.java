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
    
    private int turnToPlay;                                                                                                                                                                         // Keeps track of which hand is currently up to play Cards

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

        this.determineFirstTurn();
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS //
    
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
        this.turnToPlay = this.turnToPlay + 1;                                                                                                                                                      // Increases the index for which hand should play by 1
        
        if (this.turnToPlay > 3) {                                                                                                                                                                  // If the new index refers to a non-existent hand...
            this.turnToPlay = 0;                                                                                                                                                                        // Set the index to 0 so that the first hand in the list is up again
        }
    }
    
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
    
    // Returns whether or not a move of Cards is valid to play
    private boolean checkMoveValidity(ArrayList<Integer> cardIndices) {
        return true;
    }
    
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
        }
            

    }
        
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // STATE HANDLING FUNCTIONS //
    
    // Updates every Card in every hand in the game
    public void update() {
        
        this.currentMove.update();
        
        for (Deck hand : this.hands) {                                                                                                                                               // For each of the hands in the game...
            hand.update();                                                                                                                                                              // Update all of the Cards in the hand
        }
        
        for (Card card : this.playerHand.getCards()) {
            card.toggleSelected();
        }
    }
    
    // Draws every Card from every hand in the game
    public void draw(SpriteBatch batch) {               
        this.currentMove.draw(batch);
        
        for (Deck hand : this.hands) {                                                                                                                                                    // For each of the hands in the game...
            hand.draw(batch);                                                                                                                                                           // Draw all of the Cards in the hand
        }
    }
    
}
