package com.killer.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class Card {
    
    // FIELDS
    
    // Constants for all valid Card suits (ordered from lowest influence to highest influence in Killer)
    public static final int SPADE = 0;
    public static final int CLUB = 1;
    public static final int DIAMOND = 2;
    public static final int HEART = 3;
    
    // Constants for all valid Card values (ordered from lowest influence to highest influence in Killer)
    public static final int VALUE_3 = 0;
    public static final int VALUE_4 = 1;
    public static final int VALUE_5 = 2;
    public static final int VALUE_6 = 3;
    public static final int VALUE_7 = 4;
    public static final int VALUE_8 = 5;
    public static final int VALUE_9 = 6;
    public static final int VALUE_10 = 7;
    public static final int VALUE_JACK = 8;
    public static final int VALUE_QUEEN = 9;
    public static final int VALUE_KING = 10;
    public static final int VALUE_ACE = 11;
    public static final int VALUE_2 = 12;
   
    // A 2D Array to hold textures (each array represents a suit. each of these arrays holds 13 different skins for the 13 different card values)
    private static final Texture[][] skins = new Texture[5][13];
    
    private int suit;
    private int value;
    
    private Texture skinFront;
    private Texture skinBack;
    private Texture currentSkin;
            
    private int width;
    private int height;
    private int x;
    private int y;
    
    private boolean mouseHovering;
    private boolean selected;       // If the card is clicked on and a part of the user's selected cards
    
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // CONSTRUCTOR
    
    public Card(int x, int y, int suit, int value) {
        
        if (skins[0][0] == null) {                                                                                  // If the Card.skins list hasn't been initialized...                 
            for (int cardValue = 0; cardValue < 13; cardValue++) {                                                      // For every possible card value...
                skins[0][cardValue] = new Texture("images/cards/spades/spades_" + cardValue + ".jpg");                      // Add a spades card with the current cardValue to the spades subarray
                skins[1][cardValue] = new Texture("images/cards//clubs/clubs_" + cardValue + ".jpg");                       // Add a clubs card with the current cardValue to the clubs subarray
                skins[2][cardValue] = new Texture("images/cards/diamonds/diamonds_" + cardValue + ".jpg");                  // Add a diamonds card with the current cardValue to the diamonds subarray
                skins[3][cardValue] = new Texture("images/cards/hearts/hearts_" + cardValue + ".jpg");                      // Add a hearts card with the current cardValue to the hearts subarray
            }
            
            skins[4][0] = new Texture("images/cards/card_back.jpg");
        }
        
        this.suit = suit;
        this.value = value;
        
        this.skinFront = skins[this.suit][this.value];
        this.skinBack = skins[4][0];
        this.currentSkin = skinFront;
        
        this.width = this.currentSkin.getWidth();
        this.height = this.currentSkin.getHeight();
        this.x = x;
        this.y = y;
        
        this.mouseHovering = false;
        this.selected = false;
    }
    
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS
    
    public Texture getSkin() {
        return this.currentSkin;
    }
    
    // Draws the Card onto the screen so it is visible to the user
    public void draw(SpriteBatch batch) {
        if(this.selected) {
            batch.setColor((float) 1, (float) 0.51, (float) 0.59, 1);
        }
        else {
            batch.setColor((float) 1, (float) 1, (float) 1, 1);
        }
        
        batch.draw(this.currentSkin, this.x, this.y);
        batch.setColor(1, 1, 1, 1);
    }
    
    
    // Plays the card
    public void play() {
        
    }
    
    // Flips the Card from its current state (face up or face down)
    public void flip() {
        if (this.currentSkin == this.skinBack) {
            this.currentSkin = this.skinFront;
        }
        else {
            this.currentSkin = this.skinBack;
        }
    }
    
    // Returns the suit of the Card
    public int getSuit() {
        return this.suit;
    }
    
    // Returns the value of the Card
    public int getValue() {
        return this.value;
    }
    
    // Sets the selected state of the card to the opposite of what it was at the time of the function call
    public void toggleSelected() {
        this.mouseHovering = Mouse.checkHover(this.x, this.y, this.width, this.height);
        
        if (this.mouseHovering && Mouse.checkClick()) {
            this.selected = !this.selected;
        }
    }
    
    // Updates the state of the Card
    public void update() {
        this.toggleSelected();
    }
}
