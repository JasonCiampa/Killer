package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class Card {
    
    // FIELDS // 
    
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
    
    // Constants for Card back skins
    public static final int BACK = 4;
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;

    // A 2D Array to hold textures (Each subarray represents a suit and hold 13 skins representing different card values)
    private static final Texture[][] skins = new Texture[5][13];
    
    private int suit;                                                                                                               // Stores the suit of the card
    private int value;                                                                                                              // Stores the numerical/face value of the card
        
    private Texture skinFront;                                                                                                      // Stores the Card's front skin
    private Texture skinBack;                                                                                                       // Stores the Card's back skin
    private Texture currentSkin;                                                                                                    // Stores the Card's currently active skin
            
    private float width;                                                                                                              // Stores the Card's width
    private float height;                                                                                                             // Stores the Card's height
    private float x;                                                                                                                  // Stores the Card's x-coordinate
    private float y;                                                                                                                  // Stores the Card's y-coordinate
    
    private boolean mouseHovering;                                                                                                  // Stores whether or not the mouse is hovering over the Card
    private boolean selected;                                                                                                       // Stores whether or not the Card has been clicked on for selection
    private boolean inMotion;                                                                                                       // Stores whether or not the Card is currently moving
    
    private float timer;                                                                                                            // Keeps track of how much time has passed for the Card
    private float dx;                                                                                                               // Stores the movement speed of the Card along the x-axis
    private float dy;                                                                                                               // Stores the movement speed of the Card along the y-axis
    private float dw;                                                                                                               // Stores the scaling speed of the Card along the x-axis
    private float dh;                                                                                                               // Stores the scaling speed of the Card along the y-axis
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // CONSTRUCTOR //
    public Card(int x, int y, int suit, int value) {
        
        if (skins[0][0] == null) {                                                                                                  // If the Card.skins list hasn't been initialized...                 
            for (int cardValue = 0; cardValue < 13; cardValue++) {                                                                      // For every possible card value...
                skins[SPADE][cardValue] = new Texture("images/cards/spades/spades_" + cardValue + ".jpg");                                  // Add a spades card with the current cardValue to the spades subarray
                skins[CLUB][cardValue] = new Texture("images/cards//clubs/clubs_" + cardValue + ".jpg");                                    // Add a clubs card with the current cardValue to the clubs subarray
                skins[DIAMOND][cardValue] = new Texture("images/cards/diamonds/diamonds_" + cardValue + ".jpg");                            // Add a diamonds card with the current cardValue to the diamonds subarray
                skins[HEART][cardValue] = new Texture("images/cards/hearts/hearts_" + cardValue + ".jpg");                                  // Add a hearts card with the current cardValue to the hearts subarray
            }
            
            skins[BACK][LEFT] = new Texture("images/cards/backs/card_back_left.png");                                   // Set the back skin for the left side of the table's cards
            skins[BACK][CENTER] = new Texture("images/cards/backs/card_back_center.jpg");                               // Set the back skin for the center edge of the table's cards
            skins[BACK][RIGHT] = new Texture("images/cards/backs/card_back_right.png");                                 // Set the back skin for the right side of the table's cards
        }
        
        this.suit = suit;                                                                                                           // Set the suit of the Card
        this.value = value;                                                                                                         // Set the value of the Card
        
        this.skinFront = skins[this.suit][this.value];                                                                              // Set the front skin of the Card based on the suit and value
        this.skinBack = skins[BACK][CENTER];                                                                                        // Set the back skin of the Card to the center edge's back side by default
        this.currentSkin = skinFront;                                                                                               // Set the currently in use skin to the frontSkin
        
        this.width = this.currentSkin.getWidth();                                                                                   // Set the width equal to the currentSkin's width
        this.height = this.currentSkin.getHeight();                                                                                 // Set the height equal to the currentSkin's height
        this.x = x;                                                                                                                 // Set the x-coordinate of the Card
        this.y = y;                                                                                                                 // Set the y-coordinate of the Card
        
        this.mouseHovering = false;                                                                                                 // Set the mouseHovering status to false by default
        this.selected = false;                                                                                                      // Set the selected status to false by default
        this.inMotion = false;                                                                                                      // Set the inMotion status to false by default
        
        this.timer = 0;                                                                                                             // Set the Card's timer to 0 by default
        this.dx = 200;                                                                                                              // Set the movement speed along the x-axis
        this.dy = 200;                                                                                                              // Set the movement speed along the y-axis
        this.dw = 0;                                                                                                                // Set the scaling speed for the width of the Card
        this.dh = 0;                                                                                                                // Set the scaling speed for the height of the Card

    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS
    
    // Returns the Card's currently active skin
    public Texture getCurrentSkin() {
        return this.currentSkin;
    }
    
    // Sets the Card's front skin
    public void setFrontSkin(int cardSuit, int cardValue) {
        this.skinFront = skins[cardSuit][cardValue];                                                                                // Sets the Card's frontSkin based on the passed in suit and value
    }
    
    // Sets the Card's back skin
    public void setBackSkin(int cardSuit, int cardValue) {
        this.skinBack = skins[cardSuit][cardValue];                                                                                 // Sets the Card's backSkin based on the passed in suit and value
    }
    
    
    // Plays the card
    public void play() {
        
    }
    
    // Adjusts the Card's position by dx and dy over the duration of moveTimer
    public void adjustPosition(float timer, float dx, float dy, float dw, float dh) {
        this.timer = timer;
        this.dw = dw;
        this.dh = dh;
        this.dx = dx;
        this.dy = dy;
    }
    
    // Flips the Card from its current state (face up or face down)
    public void flip() {
        if (this.currentSkin == this.skinBack) {                                                                                    // If the Card's back skin is the currently active skin...
            this.currentSkin = this.skinFront;                                                                                          // Switch the Card's front skin to be the currently active skin
        }
        else {                                                                                                                      // Otherwise...
            this.currentSkin = this.skinBack;                                                                                           // Switch the Card's back skin to be the currently active skin
        }
        
        this.width = this.currentSkin.getWidth();                                                                                   // Set the Card's width equal to the width of the currently active skin
        this.height = this.currentSkin.getHeight();                                                                                 // Set the Card's height equal to the width of the currently active skin
    }
    
    // Sets the selected state of the card to the opposite of what it was at the time of the function call
    public void toggleSelected() {
        if (this.currentSkin == this.skinFront) {                                                                                   // If the Card's front skin is the currently active skin...
            this.mouseHovering = Mouse.checkHover((int) this.x, (int) this.y, (int) this.width, (int) this.height);                                         // Determine whether or not the mouse is hovering over the Card
        
            if (this.mouseHovering && Mouse.checkClick()) {                                                                         // If the mouse is hovering over the Card AND the Mouse was clicked on the Card...
                if (this.selected) {
                    this.selected = false;
                    this.adjustPosition((float) 0.1, 0, -250, -80, -140);
                }
                else {
                    this.selected = true;
                    this.adjustPosition((float) 0.1, 0, 250, 80, 140);
                }
            }
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
   
    // GETTER AND SETTER METHODS //
    
    // Returns the suit of the Card
    public int getSuit() {
        return this.suit;
    }
    
    // Returns the value of the Card
    public int getValue() {
        return this.value;
    }
    
    // Returns the width of the Card
    public float getWidth() {
        return this.width;
    }
    
    // Returns the height of the Card
    public float getHeight() {
        return this.height;
    }
    
    // Returns the x-coordinate of the Card
    public float getX() {
        return this.x;
    }
    
    // Returns the y-coordinate of the Card
    public float getY() {
        return this.y;
    }
    
    // Sets the position of the Card
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }   
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // STATE HANDLING FUNCTIONS //
    
    // Updates the state of the Card
    public void update() {
        float dt = Gdx.graphics.getDeltaTime();                                                                                     // Gets the amount of time since the last frame occurred
        
        if (timer > 0) {                                                                                                        // If the Card is currently in motion...
            this.timer = this.timer - dt;                                                                                       // Adjust the timer by the amount of time since the last frame occurred
            this.width = (this.width + (this.dw * dt));                                                                             // Adjust the Card's width by the amount the Card should scale up this frame
            this.height = (this.height + (this.dh * dt));                                                                           // Adjust the Card's height by the amount the Card should scale up this frame
            this.x = (this.x + (this.dx * dt));                                                                                     // Adjust the Card's x-value by the amount the Card should move along the x-axis this frame
            this.y = (this.y + (this.dy * dt));                                                                                     // Adjust the Card's y-value by the amount the Card should move along the y-axis this frame
            return;                                                                                                                 // Return so that the Card's movement isn't interrupted
        } 
        else if (timer < 0) {
            this.width = (this.width + (this.dw * this.timer));
            this.height = (this.height + (this.dh * this.timer));
            this.x = (this.x + (this.dx * this.timer));
            this.y = (this.y + (this.dy * this.timer));
            this.timer = 0;
        }
        
        this.toggleSelected();                                                                                                      // Check to see if the Card has been selected
    }
    
    // Draws the Card onto the screen in its current state so it is visible to the user
    public void draw(SpriteBatch batch) {
        if(this.selected) {                                                                                                         // If the Card is selected...
            batch.setColor((float) 1, (float) 0.51, (float) 0.59, 1);                                                               // Set the color to be reddish to indicate that the Card is selected
        }
        else {                                                                                                                      // Otherwise...
            if (this.mouseHovering) {                                                                                                   // If the mouse is hovering over the Card...
                batch.setColor((float) 0.7, (float) 0.7, (float) 0.7, 1);                                                               // Set the color to be a slightly darker shade than normal to indicate that the Card is being hovered over
            }
        }
        
        batch.draw(this.currentSkin, this.x, this.y, this.width, this.height);                                                                    // Draw the Card at it's x and y coordinates with it's current skin
        batch.setColor(1, 1, 1, 1);                                                                                         // Set the color to be the normal shade of the Card
    }    
}
