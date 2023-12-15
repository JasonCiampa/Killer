package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS // 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public abstract class Button {
        
    // FIELDS//
    protected final static Texture[] skins = {new Texture("images/user_interface/button/longButton.jpg"), new Texture("images/user_interface/button/shortButton.jpg")};     // Stores the Button skins                                                                                                                                        

    public static final int LONG = 0;                                                                                                                                       // Static field to reference a long button
    public static final int SHORT = 1;                                                                                                                                      // Static field to reference a short button
    
    protected final Texture skin;
    
    protected int width;                                                                                                                                                    // Width of the Button (px)
    protected int height;                                                                                                                                                   // Height of the Button (px)
    protected int x;                                                                                                                                                        // x-coordinate of the Button (px)
    protected int y;                                                                                                                                                        // y-coordinate of the Button (px)
    protected float scaleX;                                                                                                                                                 // Scale factor for x-axis
    protected float scaleY;                                                                                                                                                 // Scale factor for y-axis
    
    protected String text;                                                                                                                                                  // Text to be displayed on the Button
    protected BitmapFont font;                                                                                                                                              // Font for the Button's text
    protected GlyphLayout textDimensions;                                                                                                                                   // Holds the width and height of the Button's text
    
    protected final Sound clickSfx;                                                                                                                                         // Sound effect to be played when the Button is clicked on
    
    protected boolean mouseHovering;                                                                                                                                        // Whether or not the mouse is hovering over the Button
    
    protected Scene buttonLocation;                                                                                                                                         // The Scene that the Button is being stored in
    
    protected boolean active;                                                                                                                                               // Whether or not the Button is active
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR //
    public Button(int buttonType, int x, int y, String text, Scene buttonLocation) {
        this.skin = skins[buttonType];    
        this.width = skin.getWidth();                                                                                                                                       // Sets the Button width (to the width of the skin)                        
        this.height = skin.getHeight();                                                                                                                                     // Sets the Button height (to the height of the skin)    
        this.x = x;                                                                                                                                                         // Sets the x-coordinate of the Button
        this.y = y;                                                                                                                                                         // Sets the y-coordinate of the Button
        this.scaleX = scaleX;                                                                                                                                               // Sets the scale factor for the x-axis of the Button
        this.scaleY = scaleY;                                                                                                                                               // Sets the scale factor for the y-axis of the Button
        this.text = text;                                                                                                                                                   // Sets the Button's text    
        this.font = new BitmapFont(Gdx.files.internal("fonts/showcard_gothic.fnt"));                                                                                        // Sets the Button's font
        this.textDimensions = new GlyphLayout(this.font, this.text);                                                                                                        // Stores the Button's text dimensions

        this.clickSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/button_click.mp3"));                                                                                     // Sets the Button's click noise   
        
        this.mouseHovering = false;                                                                                                                                         // Sets the Button's mouseHovering state to false
        
        this.buttonLocation = buttonLocation;                                                                                                                               // Sets the Scene that the Button belongs to
        this.buttonLocation.buttons.add(this);                                                                                                                            // Adds the Button into a Scene (represented by buttonLocation)
        
        this.active = true;                                                                                                                                                 // Sets the Button to be active
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
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // GETTER AND SETTER METHODS //
    
    // Returns the width of the Button
    public int getWidth() {
        return this.width;
    }
    
    // Returns the height of the Button
    public int getHeight() {
        return this.height;
    }
    
    // Returns the x-coordinate of the Button
    public int getX() {
        return this.x;
    }
    
    // Returns the y-coordinate of the Button
    public int getY() {
        return this.y;
    }
    
    // Returns whether or not the mouse is hovering over the Button
    public boolean getMouseHovering() {
        return this.mouseHovering;
    }
    
    // Sets whether or not the mouse is hovering over the Button
    public void setMouseHovering(boolean isHovering) {
        this.mouseHovering = isHovering;
    }
    
    // Sets the position of the Button
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Sets the Button's text
    public void setText(String text) {
        this.text = text;
        this.textDimensions = new GlyphLayout(this.font, this.text);
    }
    
    // Sets the Button's active state
    public void setActive(boolean active) {
        this.active = active;
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // STATE HANDLING FUNCTIONS //
    
    protected void update() {
        if (this.active) {
            this.setMouseHovering(Mouse.checkHover(this.getX(), this.getY(), this.getWidth(), this.getHeight()));                                       // Check if the mouse is hovering over the Button    
            
            if (this.mouseHovering && Mouse.checkClick()) {                                                                                                                 // If the button was clicked on...    
                this.performAction();                                                                                                                                           // Perform the Button's action
                return;                                                                                                                                                         // Return now since only one Button can be pressed at once
            }     
        }
    }
    
    // Draws the Button on the Screen
    protected void draw(SpriteBatch batch) {
        if (this.active) {
            if (this.mouseHovering) {                                                                                                                                        // If the Button is being hovered over by the mouse...
                batch.setColor((float) 0.5, (float) 0.5, (float) 0.5, 1);                                                                                                       // Make the color dimmer and darker to indicate a hovering state
            }

        
            batch.draw(skin, this.x, this.y, this.width, this.height);                                                                                                       // Draw the Button
            font.draw(batch, this.text, (this.x + (this.width / 2) - (this.textDimensions.width / 2)), (this.y + this.height / 2) + (this.textDimensions.height / 2));       // Writes the Button's text
            batch.setColor(1, 1, 1, 1);                                                                                                                                      // Sets the current drawing color to normal    
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
