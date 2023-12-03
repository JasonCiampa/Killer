package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Collections;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class Deck {
    
    // FIELDS //
    
    private ArrayList<Card> cards;                                                                                       // Creates an ArrayList that will hold a full deck of 52 cards (all possible card suits and values)
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR //
    
    // Creates a Deck of 52 unique cards and shuffles them into a random order
    public Deck(boolean empty) {
        this.cards = new ArrayList<Card>();                                                                             // Initialize the cards ArrayList so it is prepared to store Cards
        
        if (empty) {
            return;
        }
        
        for (int suit = 0; suit < 4; suit++) {                                                                          // For each possible suit value...
            for (int cardValue = 0; cardValue < 13; cardValue++) {                                                      // For each possible cardValue...
                this.cards.add(new Card(0, 0, suit, cardValue));                                              // Add a new Card to the cards ArrayList with the current suit and cardValue
            }
        }
        
        this.shuffle();                                                                                                 // Shuffle the deck
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // METHODS //
    
    // Adds a Card to the deck
    public void addCard(Card card) {
        this.cards.add(card);
    }
    
    // Removes a Card from the deck
    public void removeCard(int cardIndex) {
        this.cards.remove(cardIndex);
    }
    
    // Returns a Card from the deck
    public Card getCard(int cardIndex) {
        return this.cards.get(cardIndex);
    }
    
    // Returns the size of the Deck
    public int getSize() {
        return this.cards.size();
    }
    
    // Returns a list of Cards in the Deck
    public ArrayList<Card> getCards() {
        return this.cards;
    }
    
    // Shuffles the Deck into a random assortment of Cards
    public void shuffle() {                                                             
        Collections.shuffle(this.cards);                                                                           // Shuffles the ArrayList of Cards
    }
    
    // Organize the Deck from lowest Card first to highest Card last (based on number values)
    public void sort() {
        int cardListSize = this.cards.size();                                                                           // Stores the number of cards in the Deck
        ArrayList<Card> sortedCards = new ArrayList<Card>();                                                            // Creates an ArrayList that will temporarily store the Cards in a sorted order
        
        while (sortedCards.size() < cardListSize) {                                                                     // While the sortedCards list isn't complete...
            Card smallestCard = this.cards.get(0);                                                                    // Store the smallest Card in the list as the first Card by default
            int smallestCardIndex = 0;                                                                                      // Store the smallest Card in the list's index value as the first index by default

            for (int card = 1; card < this.cards.size(); card++) {                                                      // For every Card in the Deck (except the first since we already have that Card set as smallestCard) ...
                if (this.cards.get(card).getValue() < smallestCard.getValue()) {                                      // If the Card is smaller than smallestCard (has a lower numerical value)...
                    smallestCard = this.cards.get(card);                                                                  // Set the smallestCard to be the current Card
                    smallestCardIndex = card;                                                                                   // Set the smallestCardIndex to be the index of the current Card
                }   
            }
            
            sortedCards.add(smallestCard);                                                                            // Add the smallestCard to the end of the sortedCards ArrayList
            this.cards.remove(smallestCardIndex);                                                                 // Remove the smallestCard from the Deck so that it isn't duplicated
        }
        
        this.cards = sortedCards;                                                                                       // Set the Deck to hold the sortedCards
    }
    
    // Deals a specified number of Cards out into a specified number of hands and returns the hands
    public Deck[] deal(int numCards, int numHands) {
        Deck[] hands = new Deck[numHands];                                                                              // Initialize an empty array of Decks that will hold the hands being dealt to
        
        for (int hand = 0; hand < numHands; hand++) {                                                                   // For each hand in the number of hands being dealt to...
            hands[hand] = new Deck(true);                                                                           // Create a new empty Deck to store the hand
        } 
        
        for (int card = 0; card < numCards; card++) {                                                                   // For each Card in the number of cards to be dealt to each hand...
            for (int hand = 0; hand < numHands; hand++) {                                                                   // For each Hand that needs to be dealt to...
                int lastCard = this.cards.size() - 1;                                                                           // Determine the index of the last Card in the Deck
                hands[hand].cards.add(this.cards.get(lastCard));                                                        // Adds the Deck's last Card into the hand
                this.cards.remove(lastCard);                                                                                // Removes the Deck's last Card now that it has been dealt to the hand
            } 
        }
        
        return hands;                                                                                                   // Returns the Deck array containing each hand that was dealt Cards
    }
    
    // Aligns all of the Cards in the Deck
    public void alignCards(float timer, float startX, float startY, float shiftDistanceX, float shiftDistanceY, float width, float height) {                      
        float x = startX;                                                                                                 // Set x equal to the startX value that was passed in
        float y = startY;                                                                                                 // Set y equal to the startY value that was passed in
        
        for (Card card : this.cards) {                                                                                    // For every Card in the Deck...
            card.move(timer, x, y, width, height);
            x += shiftDistanceX;                                                                                            // Increment x by the horizontal shift distance between this Card and the next
            y += shiftDistanceY;                                                                                            // Increment y by the vertical shift distance between this Card and the next
        }
    }
       
    // Updates each Card in the Deck
    public void update() {
        for (Card card : this.cards) {                                                                                  // For every Card in the Deck...
            card.update();                                                                                                  // Update the Card
        }
    }
    
    // Draws each Card in the Deck onto the screen
    public void draw(SpriteBatch batch) {
        for (Card card : this.cards) {                                                                                  // For every Card in the Deck...
            card.draw(batch);                                                                                               // Draw the Card onto the screen
        }
    }
}