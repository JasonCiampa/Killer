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
        
        this.leftHand = this.hands[0];                                                                                                                                              // Set the left hand equal to the first hand created
        this.centerHand = this.hands[1];                                                                                                                                            // Set the center hand equal to the second hand created
        this.rightHand = this.hands[2];                                                                                                                                             // Set the right hand equal to the third hand created
        this.playerHand = this.hands[3];                                                                                                                                            // Set the Player's hand equal to the fourth hand created
        
        setHand(this.leftHand, Card.LEFT);                                                                                                                         // Sets up the left hand
        setHand(this.centerHand, Card.CENTER);                                                                                                                     // Sets up the center hand
        setHand(this.rightHand, Card.RIGHT);                                                                                                                       // Sets up the right hand
        
        this.setHandAlignments();
        
        for (Deck hand : this.hands) {                                                                                                                                              // For each hand created...
            hand.sort();                                                                                                                                                                // Sort the hand from lowest Card value to highest
            hand.alignCards(0, hand.getX(), hand.getY(), hand.getShiftX(), hand.getShiftY(), hand.getCardWidth(), hand.getCardHeight());
        }                     

        this.turnToPlay = determineFirstTurn();
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS //
    
    private Deck determineFirstTurn() {     
        Deck firstTurn = this.playerHand;                                                                                                                                                               // Create a new Deck variable that will hold whichever hand plays first
        
        for (Deck hand : this.hands) {                                                                                                                                                                  // For each hand...
            Card lowestCard = hand.getCard(0);                                                                                                                                                      // Set the lowest Card equal to the first Card in the hand (since it has been sorted, this is the lowest Card)
            
            if (lowestCard.getSuit() == Card.SPADE && lowestCard.getValue() == Card.VALUE_3) {                                                                                                               // If the lowest Card is the three of spades...
                firstTurn = hand;                                                                                                                                                                               // Store this hand as the Deck that will play first
            }
        }
        
        return firstTurn;                                                                                                                                                                               // Return the hand that will play first
    }
    
    // Sets the alignment values for each Hand
    private void setHandAlignments() {
        this.leftHand.setAlignment((((((float) (Gdx.graphics.getWidth() / 4.2) - ((this.leftHand.getSize() - 1)) * 20)) + 75) / 2), 450, 20, 5, 75, 132);
        this.centerHand.setAlignment((((Gdx.graphics.getWidth() - (((this.centerHand.getSize() - 1) * 25) + 35)) / 2)), 660, 25, 0, 35, 62);
        this.rightHand.setAlignment(((float) (Gdx.graphics.getWidth() / 1.2) + (((this.rightHand.getSize() - 1) * 20) / 2)), 450, -20, 5, 75, 132);
        this.playerHand.setAlignment(((float) (Gdx.graphics.getWidth() - (((this.playerHand.getSize() - 1) * 145) + 160)) / 2), 150, 145, 0, 160, 280);
        
        // 920 is center solo card
        this.cardsToPlay.setAlignment((((float) ((Gdx.graphics.getWidth() / 1.1)) - ((this.cardsToPlay.getSize() - 1) * 38) + 80) / 2), 500, 38, 0, 80, 140);
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
        this.cardsToPlay.clearCards();
        
        for (int cardIndex = 0; cardIndex < this.turnToPlay.getSize(); cardIndex++) {                                                                                               // For every Card in the Deck (except the first since we already have that Card set as smallestCard) ...
            Card card = this.turnToPlay.getCard(cardIndex);    
            if (card.getSelected()) {                                                                                                                                   // If the Card is selected...
                card.setSelected(false);                                                                                                                            // Unselect the Card
                this.cardsToPlay.addCard(card);                                                                                                                             // Add the Card into the cardsToPlay Deck
                this.turnToPlay.removeCard(cardIndex);
                cardIndex--;
            }
        }                                                                                                                            
            
        this.setHandAlignments();
        this.turnToPlay.alignCards((float) 0.5, this.turnToPlay.getX(), this.turnToPlay.getY(), this.turnToPlay.getShiftX(), this.turnToPlay.getShiftY(), this.turnToPlay.getCardWidth(), this.turnToPlay.getCardHeight());
        this.cardsToPlay.alignCards((float) 0.5, this.cardsToPlay.getX(), this.cardsToPlay.getY(), this.cardsToPlay.getShiftX(), this.cardsToPlay.getShiftY(), this.cardsToPlay.getCardWidth(), this.cardsToPlay.getCardHeight());
    }
        
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // STATE HANDLING FUNCTIONS //
    
    // Updates every Card in every hand in the game
    public void update() {
        
        cardsToPlay.update();
        
        for (Deck hand : this.hands) {                                                                                                                                               // For each of the hands in the game...
            hand.update();                                                                                                                                                              // Update all of the Cards in the hand
        }
        
        for (Card card : playerHand.getCards()) {
            card.toggleSelected();
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
