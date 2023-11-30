package com.killer.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashMap;

public class Card {
    
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
    private static final Texture[][] skins = { 
        {new Texture("images/cards/spades/spades_0.jpg"), new Texture("images/cards/spades/spades_1.jpg"), new Texture("images/cards/spades/spades_2.jpg"), new Texture("images/cards/spades/spades_3.jpg"), new Texture("images/cards/spades/spades_4.jpg"), new Texture("images/cards/spades/spades_5.jpg"), new Texture("images/cards/spades/spades_6.jpg"), new Texture("images/cards/spades/spades_7.jpg"), new Texture("images/cards/spades/spades_8.jpg"), new Texture("images/cards/spades/spades_9.jpg"), new Texture("images/cards/spades/spades_10.jpg"), new Texture("images/cards/spades/spades_11.jpg"), new Texture("images/cards/spades/spades_12.jpg")}, 
        {new Texture("images/cards/clubs/clubs_0.jpg"), new Texture("images/cards/clubs/clubs_1.jpg"), new Texture("images/cards/clubs/clubs_2.jpg"), new Texture("images/cards/clubs/clubs_3.jpg"), new Texture("images/cards/clubs/clubs_4.jpg"), new Texture("images/cards/clubs/clubs_5.jpg"), new Texture("images/cards/clubs/clubs_6.jpg"), new Texture("images/cards/clubs/clubs_7.jpg"), new Texture("images/cards/clubs/clubs_8.jpg"), new Texture("images/cards/clubs/clubs_9.jpg"), new Texture("images/cards/clubs/clubs_10.jpg"), new Texture("images/cards/clubs/clubs_11.jpg"), new Texture("images/cards/clubs/clubs_12.jpg")}, 
        {new Texture("images/cards/diamonds/diamonds_0.jpg"), new Texture("images/cards/diamonds/diamonds_1.jpg"), new Texture("images/cards/diamonds/diamonds_2.jpg"), new Texture("images/cards/diamonds/diamonds_3.jpg"), new Texture("images/cards/diamonds/diamonds_4.jpg"), new Texture("images/cards/diamonds/diamonds_5.jpg"), new Texture("images/cards/diamonds/diamonds_6.jpg"), new Texture("images/cards/diamonds/diamonds_7.jpg"), new Texture("images/cards/diamonds/diamonds_8.jpg"), new Texture("images/cards/diamonds/diamonds_9.jpg"), new Texture("images/cards/diamonds/diamonds_10.jpg"), new Texture("images/cards/diamonds/diamonds_11.jpg"), new Texture("images/cards/diamonds/diamonds_12.jpg")},
        {new Texture("images/cards/hearts/hearts_0.jpg"), new Texture("images/cards/hearts/hearts_1.jpg"), new Texture("images/cards/hearts/hearts_2.jpg"), new Texture("images/cards/hearts/hearts_3.jpg"), new Texture("images/cards/hearts/hearts_4.jpg"), new Texture("images/cards/hearts/hearts_5.jpg"), new Texture("images/cards/hearts/hearts_6.jpg"), new Texture("images/cards/hearts/hearts_7.jpg"), new Texture("images/cards/hearts/hearts_8.jpg"), new Texture("images/cards/hearts/hearts_9.jpg"), new Texture("images/cards/hearts/hearts_10.jpg"), new Texture("images/cards/hearts/hearts_11.jpg"), new Texture("images/cards/hearts/hearts_12.jpg")}  
    };
    
    private Texture image;
    
    private int width;
    private int height;
    private int x;
    private int y;
    
    private int suit;
    private int value;
    
    private boolean selected;       // If the card is clicked on and a part of the user's selected cards
    
    
    public Card(int x, int y, int suit, int value) {
        this.suit = suit;
        this.value = value;
        this.image = this.skins[this.suit][this.value];
        
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        this.x = x;
        this.y = y;
    }
    
    
    // Draws the Card onto the screen so it is visible to the user
    public void draw(SpriteBatch batch) {
        batch.draw(this.image, this.x, this.y);
    }
    
    
    // Plays the card
    public void play() {
        
    }
    
    // Sets the selected state of the card to the opposite of what it was at the time of the function call
    public void toggleSelected() {
        this.selected = !this.selected;
    }
}
