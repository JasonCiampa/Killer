package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Collections;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class Deck {
    
    // FIELDS //
    
    private ArrayList<Card> cards;                                                                                      // Creates an ArrayList that will hold a full deck of 52 cards (all possible card suits and values)
    private float x;                                                                                                    // Stores the x-coordinate for the Deck (where the first card will be drawn)
    private float y;                                                                                                    // Stores the y-coordinate for the Deck (where the first card will be drawn)
    private float shiftX;                                                                                               // Stores the x-axis shift value for the Deck (how far apart each card in the Deck will be drawn horizontally)                                                                             
    private float shiftY;                                                                                               // Stores the y-axis shift value for the Deck (how far apart each card in the Deck will be drawn vertically)
    private float cardWidth;                                                                                            // Stores the width of each Card in the Deck
    private float cardHeight;                                                                                           // Stores the height of each Card in the Deck
    private boolean playable;                                                                                           // Stores whether or not the Deck's Cards are playable
    
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
    
    // Creates a Deck with the given Cards in their given order
    public Deck(Card[] cards) {
        this.cards = new ArrayList<Card>();                                                                             // Initialize the cards ArrayList so it is prepared to store Cards
        
        for (Card card : cards) {
            this.cards.add(card);
        }
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // METHODS //
    
    // Remove all Cards from the Deck at once
    public void clearCards() {
        this.cards.clear();
    }
    
    // Adds a Card to the deck
    public void addCard(Card card) {
        this.cards.add(card);
    }
    
    // Removes a Card from the deck
    public void removeCard(int cardIndex) {
        this.cards.remove(cardIndex);
    }
    
    // Returns the x-coordinate of the Deck
    public float getX() {
        return this.x;
    }
    
    // Returns the y-coordinate of the Deck
    public float getY() {
        return this.y;
    }
    
    // Returns the x-axis shift value of the Deck
    public float getShiftX() {
        return this.shiftX;
    }
        
    // Returns the y-axis shift value of the Deck
    public float getShiftY() {
        return this.shiftY;
    }
    
    // Returns the width of each Card in the Deck
    public float getCardWidth() {
        return this.cardWidth;
    }
        
    // Returns the Height of each Card in the Deck
    public float getCardHeight() {
        return this.cardHeight;
    }
    
    // Returns whether or not the Deck's Cards can be played
    public boolean getPlayable() {
        return this.playable;
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
    
    // Sets the Deck's x-coordinate
    public void setX(float x) {
        this.x = x;
    }
    
    // Sets the Deck's y-coordinate
    public void setY(float y) {
        this.y = y;
    }
    
    // Sets the x-axis shift value of the Deck
    public void setShiftX(float shiftX) {
        this.shiftX = shiftX;
    }
    
    // Sets the y-axis shift value of the Deck
    public void setShiftY(float shiftY) {
        this.shiftY = shiftY;
    }
    
    // Sets whether or not the Deck's Cards can be played
    public void setPlayable(boolean playable) {
        this.playable = playable;
    }
    
    // Sets the alignment values for a Deck
    public void setAlignment(float x, float y, float shiftX, float shiftY, float cardWidth, float cardHeight) {
        this.setX(x);
        this.setY(y);
        this.setShiftX(shiftX);
        this.setShiftY(shiftY);
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        
        for (Card card : this.cards) {
            card.setWidth(this.cardWidth);
            card.setHeight(this.cardHeight);
        }
    }
    
    // Shuffles the Deck into a random assortment of Cards
    public void shuffle() {                                                             
        Collections.shuffle(this.cards);                                                                           // Shuffles the ArrayList of Cards
    }
    
    // Organize the Deck from lowest Card first to highest Card last (based on number values)
    public void sort() {
        int cardListSize = this.getSize();                                                                           // Stores the number of cards in the Deck
        ArrayList<Card> sortedCards = new ArrayList<Card>();                                                            // Creates an ArrayList that will temporarily store the Cards in a sorted order
        
        while (sortedCards.size() < cardListSize) {                                                                     // While the sortedCards list isn't complete...
            Card smallestCard = this.getCard(0);                                                                    // Store the smallest Card in the list as the first Card by default
            int smallestCardIndex = 0;                                                                                      // Store the smallest Card in the list's index value as the first index by default

            for (int cardIndex = 1; cardIndex < this.getSize(); cardIndex++) {                                                      // For every Card in the Deck (except the first since we already have that Card set as smallestCard) ...
//                if (this.getCard(card).getSuit() < smallestCard.getSuit()) {
//                    if (this.getCard(card).getValue() < smallestCard.getValue()) {                                      // If the Card is smaller than smallestCard (has a lower numerical value)...
//                    smallestCard = this.getCard(card);                                                                  // Set the smallestCard to be the current Card
//                    smallestCardIndex = card;                                                                                   // Set the smallestCardIndex to be the index of the current Card
//                    }                  
//                }
                Card card = this.getCard(cardIndex);
                if (card.getValue() < smallestCard.getValue()) {
                    smallestCard = card;
                    smallestCardIndex = cardIndex;
                }
                
                else if (card.getValue() == smallestCard.getValue()) {
                    if (card.getSuit() < smallestCard.getSuit()) {
                        smallestCard = card;
                        smallestCardIndex = cardIndex; 
                    }
                }
            }
            
            sortedCards.add(smallestCard);                                                                            // Add the smallestCard to the end of the sortedCards ArrayList
            this.removeCard(smallestCardIndex);                                                                // Remove the smallestCard from the Deck so that it isn't duplicated
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
        float x = startX;                                                                                               // Set x equal to the startX value that was passed in
        float y = startY;                                                                                               // Set y equal to the startY value that was passed in
        
        for (Card card : this.cards) {                                                                                  // For every Card in the Deck...
            card.move(timer, x, y, width, height);
            x += shiftDistanceX;                                                                                        // Increment x by the horizontal shift distance between this Card and the next
            y += shiftDistanceY;                                                                                        // Increment y by the vertical shift distance between this Card and the next
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