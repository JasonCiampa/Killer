package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS//
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class GameHandler {
    
    // FIELDS //
    private Deck fullDeck;                                                                                                                                                          // A Deck that will hold all 52 possible Cards
    private Deck cardsToPlay;                                                                                                                                                       // A Deck that will store the player's selected cards

    private Deck[] hands;                                                                                                                                                           // An array of Decks that will hold each of the four hands
    private Deck leftHand;                                                                                                                                                          // A Deck that will serve as the left bot's hand
    private Deck centerHand;                                                                                                                                                        // A Deck that will serve as the center bot's hand
    private Deck rightHand;                                                                                                                                                         // A Deck that will serve as the right bot's hand
    private Deck playerHand;                                                                                                                                                        // A Deck that will serve as the player's hand
    
    private Deck turnToPlay;                                                                                                                                                        // Keeps track of whose turn it is to play Cards

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR //
    public GameHandler() {
        this.fullDeck = new Deck(false);                                                                                                                                      // Creates a new Deck with 52 cards
        this.cardsToPlay = new Deck(true);

        this.hands = this.fullDeck.deal(13, 4);                                                                                                                     // Creates 4 hands with 13 cards each (all cards come from fullDeck, which is now empty)
        
        for (Deck hand : this.hands) {                                                                                                                                              // For each hand created...
            hand.sort();                                                                                                                                                                // Sort the hand from lowest Card value to highest
        }            
        
        determineFirstTurn();

        this.leftHand = this.hands[0];                                                                                                                                              // Set the left hand equal to the first hand created
        this.centerHand = this.hands[1];                                                                                                                                            // Set the center hand equal to the second hand created
        this.rightHand = this.hands[2];                                                                                                                                             // Set the right hand equal to the third hand created
        this.playerHand = this.hands[3];                                                                                                                                            // Set the Player's hand equal to the fourth hand created
        
        setHand(this.leftHand, Card.LEFT);                                                                                                                         // Sets up the left hand
        setHand(this.centerHand, Card.CENTER);                                                                                                                     // Sets up the center hand
        setHand(this.rightHand, Card.RIGHT);                                                                                                                       // Sets up the right hand

        alignLeftCards(0);                                                                                                                                                           // Sets the position for each Card in leftHand
        alignCenterCards(0);                                                                                                                                                         // Sets the position for each Card in centerHand
        alignRightCards(0);                                                                                                                                                          // Sets the position for each Card in rightHand
        alignPlayerCards(0);                                                                                                                                                         // Sets the position for each Card in playerHand
        
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS //
    
    private void determineFirstTurn() {                                                                                                                                                                             
        for (Deck hand : this.hands) {
            Card lowestCard = hand.getCard(0);
            if (lowestCard.getSuit() == Card.SPADE && lowestCard.getValue() == Card.VALUE_3) {
                this.turnToPlay = hand;
                return;
            }
        }
    }
    
    // Sets the skin for each card in the hand and flips the Card
    private void setHand(Deck hand, int backSkinValue) {                                                                                                                            
        for (Card card : hand.getCards()) {                                                                                                                                          // For each Card in the hand...
            card.setBackSkin(Card.BACK, backSkinValue);                                                                                                                 // Set the Card's back skin to backSkinValue
            card.flip();                                                                                                                                                                 // Flip the Card over
        }
    }
    
    // Plays the currently selected cards from the hand
    public void playCards() {
        for (int card = 1; card < this.turnToPlay.getSize(); card++) {                                                                                                                            // For every Card in the Deck (except the first since we already have that Card set as smallestCard) ...
            if (this.turnToPlay.getCard(card).getSelected()) {                                                                                                                                   // If the Card is selected...
                this.cardsToPlay.addCard(this.turnToPlay.getCard(card));                                                                                                                         // Add the Card into the cardsToPlay Deck
                this.turnToPlay.removeCard(card);                                                                                                                                                // Removes the Card from the hand since it is being played
                
                if (this.turnToPlay == this.leftHand) {
                    alignLeftCards((float) 0.5);
                }
                else if (this.turnToPlay == this.centerHand) {
                    alignCenterCards((float) 0.5);
                }
                else if (this.turnToPlay == this.rightHand) {
                    alignRightCards((float) 0.5);
                }
                else {
                    alignPlayerCards((float) 0.5);
                }
            }   
        }
        
        this.cardsToPlay.alignCards((float) 0.5, 700, 500, 50, 0, 80, 140);
    }
    
    private void alignLeftCards(float timer) {
        this.leftHand.alignCards(timer, ((float) ((((Gdx.graphics.getWidth() / 3.5) - ((this.leftHand.getSize() - 1)) * 20)) + 75) / 2), 450, 20, 5, 75, 132);    // Sets the position for each Card in leftHand
    }
    
    private void alignCenterCards(float timer) {
        this.centerHand.alignCards(timer, ((float) (Gdx.graphics.getWidth() - (((this.centerHand.getSize() - 1) * 25) + 35)) / 2), 660, 25, 0, 35, 62);              // Sets the position for each Card in centerHand
    }
    
    private void alignRightCards(float timer) {
        this.rightHand.alignCards(timer, (1608 + (((this.rightHand.getSize() - 1) * 20) / 2)), 450, -20, 5, 75, 132);                                           // Sets the position for each Card in rightHand
    }

    private void alignPlayerCards(float timer) {
        this.playerHand.alignCards(timer, ((float) (Gdx.graphics.getWidth() - (((this.playerHand.getSize() - 1) * 145) + 160)) / 2), 150, 145, 0, 160, 280);           // Sets the position for each Card in playerHand
    }
    
    // Updates every Card in every hand in the game
    public void update() {
        cardsToPlay.update();
        for (Deck hand : this.hands) {                                                                                                                                               // For each of the hands in the game...
            hand.update();                                                                                                                                                              // Update all of the Cards in the hand
        }
    }
    
    // Draws every Card from every hand in the game
    public void draw(SpriteBatch batch) {               
        this.cardsToPlay.draw(batch);
        
        for (Deck hand : hands) {                                                                                                                                                    // For each of the hands in the game...
            hand.draw(batch);                                                                                                                                                           // Draw all of the Cards in the hand
        }
        
    }
    
}
