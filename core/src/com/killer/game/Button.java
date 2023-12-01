package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS // 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import javax.swing.event.EventListenerList;


// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public abstract class Button {
        
    // FIELDS//
    protected final Texture skin;                                                                                                                                           // Skin for the Button

    protected int width;                                                                                                                                                    // Width of the Button (px)
    protected int height;                                                                                                                                                   // Height of the Button (px)
    protected int x;                                                                                                                                                        // x-coordinate of the Button (px)
    protected int y;                                                                                                                                                        // y-coordinate of the Button (px)

    protected String text;                                                                                                                                                  // Text to be displayed on the Button
    protected BitmapFont font;                                                                                                                                              // Font for the Button's text
    protected GlyphLayout textDimensions;                                                                                                                                   // Holds the width and height of the Button's text
    
    protected final Sound clickSfx;                                                                                                                                         // Sound effect to be played when the Button is clicked on
    
    protected boolean mouseHovering;                                                                                                                                        // Whether or not the mouse is hovering over the Button
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR //
    public Button(int x, int y, String text, Scene buttonLocation) {
        this.skin = new Texture("images/buttonSkin.jpg");                                                                                                       // Sets the Button skin
            
        this.width = this.skin.getWidth();                                                                                                                                  // Sets the Button width (to the width of the skin)                        
        this.height = this.skin.getHeight();                                                                                                                                // Sets the Button height (to the height of the skin)    
        this.x = x;                                                                                                                                                         // Sets the x-coordinate of the Button
        this.y = y;                                                                                                                                                         // Sets the y-coordinate of the Button
        this.text = text;                                                                                                                                                   // Sets the Button's text    
        this.font = new BitmapFont(Gdx.files.internal("fonts/showcard_gothic.fnt"));                                                                           // Sets the Button's font
        this.textDimensions = new GlyphLayout(this.font, this.text);                                                                                               // Stores the Button's text dimensions

        this.clickSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/button_click.mp3"));                                                                      // Sets the Button's click noise   
        
        this.mouseHovering = false;                                                                                                                                         // Sets the Button's mouseHovering state to false
        
        buttonLocation.buttons.add(this);                                                                                                                                 // Adds the Button into a Scene (represented by buttonLocation)
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS // 

    // Action to perform when Button is clicked on
    protected abstract void clickAction();     
    
    // Performs the Button's action
    protected void performAction() {
        this.clickSfx.play();                                                                                                                                               // Play the Button click sound effect
        this.clickAction();                                                                                                                                                 // Initiate the Button's click action
    }
    
    // Returns the width of the Button
    protected int getWidth() {
        return this.width;
    }
    
    // Returns the height of the Button
    protected int getHeight() {
        return this.height;
    }
    
    // Returns the x-coordinate of the Button
    protected int getX() {
        return this.x;
    }
    
    // Returns the y-coordinate of the Button
    protected int getY() {
        return this.y;
    }
    
    // Returns whether or not the mouse is hovering over the Button
    protected boolean getMouseHovering() {
        return this.mouseHovering;
    }
    
    // Sets whether or not the mouse is hovering over the Button
    protected void setMouseHovering(boolean isHovering) {
        this.mouseHovering = isHovering;
    }
    
    // Sets the position of the Button
    protected void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Draws the Button on the Screen
    protected void draw(SpriteBatch batch) {
        if (this.mouseHovering) {                                                                                                                                        // If the Button is being hovered over by the mouse...
            Color currentColor = batch.getColor();                                                                                                                          // Get the current drawing color
            batch.setColor(currentColor.mul((float) 0.5, (float) 0.5, (float) 0.5, 1));                                                                              // Make the color dimmer and darker to indicate a hovering state
        }

        batch.draw(this.skin, this.x, this.y, 0, 0, this.width, this.height);                                                       // Draws the Button's skin at the Button's x and y coordinates with the Button's specified width and height.
        font.draw(batch, text, (this.x + (this.width / 2) - (this.textDimensions.width / 2)), (this.y + this.height / 2) + (this.textDimensions.height / 2));         // Writes the Button's text
        batch.setColor(1, 1, 1, 1);                                                                                                                               // Sets the current drawing color to normal
    }
}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
