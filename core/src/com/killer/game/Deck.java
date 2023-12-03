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
    public Deck() {
        this.cards = new ArrayList<Card>();                                                                             // Initialize the cards ArrayList so it is prepared to store Cards
        
        for (int suit = 0; suit < 4; suit++) {                                                                          // For each possible suit value...
            for (int cardValue = 0; cardValue < 13; cardValue++) {                                                      // For each possible cardValue...
                this.cards.add(new Card(0, 0, suit, cardValue));                                              // Add a new Card to the cards ArrayList with the current suit and cardValue
            }
        }
        
        this.shuffle();                                                                                                 // Shuffle the deck
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // METHODS //
    
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
            hands[hand] = new Deck();                                                                                       // Create a new Deck to store the hand
            hands[hand].cards.clear();                                                                                      // Clear the Deck in preparation for new Card insertion
        } 
        
        for (int card = 0; card < numCards; card++) {                                                                   // For each Card in the number of cards to be dealt to each hand...
            for (int hand = 0; hand < numHands; hand++) {                                                                   // For each Hand that needs to be dealt to...
                int lastCard = this.cards.size() - 1;                                                                           // Determine the index of the last card in the Deck
                hands[hand].cards.add(this.cards.get(lastCard));                                                        // Adds the Deck's last card into the hand
                this.cards.remove(lastCard);                                                                                // Removes the Deck's last card now that it has been dealt to the hand
            } 
        }
        
        return hands;                                                                                                   // Returns the Deck array containing each hand that was dealt Cards
    }
    
    // Draws each Card in the Deck onto the screen
    public void draw(SpriteBatch batch) {
        for (int card = 0; card < this.cards.size(); card++) {                                                          // For every Card in the Deck...
            this.cards.get(card).draw(batch);                                                                         // Draw the Card onto the screen
        }
    }
}