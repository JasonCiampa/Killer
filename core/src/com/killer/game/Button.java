package com.killer.game;


// Imports // 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class Button {
    
    // FIELDS//
    private final Texture skin;          // Skin for the Button

    private int width;                   // Width of the Button (px)
    private int height;                  // Height of the Button (px)
    private int x;                       // x-coordinate of the Button (px)
    private int y;                       // y-coordinate of the Button (px)

    private String text;                 // Text to be displayed on the Button
    private BitmapFont font;             // Font for the Button's text
    private GlyphLayout textDimensions;  // Holds the width and height of the Button's text
    
    private final Sound clickSfx;        // Sound effect to be played when the Button is clicked on
    private final String name;           // Name for the Button
    
    private boolean mouseHovering;       // Whether or not the mouse is hovering over the Button
    
    
    
    // CONSTRUCTORS //
    public Button(int x, int y, String text, String name) {
        this.skin = new Texture("images/buttonSkin.jpg");                                       // Sets the Button skin
            
        this.width = this.skin.getWidth();                                                                  // Sets the Button width (to the width of the skin)                        
        this.height = this.skin.getHeight();                                                                // Sets the Button height (to the height of the skin)    
        this.x = x;                                                                                         // Sets the x-coordinate of the Button
        this.y = y;                                                                                         // Sets the y-coordinate of the Button
        this.text = text;                                                                                   // Sets the Button's text    
        this.font = new BitmapFont(Gdx.files.internal("fonts/showcard_gothic.fnt"));           // Sets the Button's font
        this.textDimensions = new GlyphLayout(this.font, this.text);                               // Stores the Button's text dimensions

        this.clickSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/button_click.mp3"));      // Sets the Button's click noise   
        this.name = name;                                                                                   // Sets the Button's name
        
        this.mouseHovering = false;
    }

    
    // METHODS // 
    
    // Checks if the Button is being hovered over
    public boolean checkMouseHover(Vector3 coordinates) {
        int mouseX = (int) coordinates.x;                                                                                           // Sets mouseX equal  to the x-coord in the given coordinate system
        int mouseY = (int) coordinates.y;                                                                                           // Sets mouseY equal to the y-coord in the given coordinate system
        
        if ((mouseX >= this.x && mouseX <= (this.x + this.width)) && (mouseY >= this.y && mouseY <= (this.y + this.height))) {      // If the mouse position is inside of the Button....
            return true;                                                                                                 // Set the Button's mouseHovering state to true.
        }
        
        return false;                                                                                                 // Set the Button's mouseHovering state to false.
    }
   
    
    // Checks if the Button was clicked on
    public boolean checkMouseClick(Vector3 coordinates) {
        int mouseX =  (int) coordinates.x;                                                                                          // Sets mouseX equal to the x-coord in the given coordinate system
        int mouseY = (int) coordinates.y;                                                                                           // Sets mouseY equal to the y-coord in the given coordinate system
        
        if ((mouseX >= this.x && mouseX <= (this.x + this.width)) && (mouseY >= this.y && mouseY <= (this.y + this.height))) {      // If the location of the mouse pointer when the click occurred is inside the Button...
            this.clickSfx.play();                                                                                                       // Play the Button click sound effect
            return true;                                                                                                                // Return true because the Button was clicked on.
        }
        
        return false;                                                                                                               // Return false because the Button wasn't clicked on.
    }
    
    // Returns the name of the Button
    public String getName() {
        return this.name;
    }
    
    // Returns the width of the Button
    public int getWidth() {
        return this.width;
    }
    
    // Returns the height of the Button
    public int getHeight() {
        return this.height;
    }
    
    public void setMouseHovering(boolean isHovering) {
        this.mouseHovering = isHovering;
    }
    
    // Sets the position of the Button
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Draws the Button on the Screen
    public void draw(SpriteBatch batch) {
        if (this.mouseHovering) {
            Color currentColor = batch.getColor();
            batch.setColor(currentColor.mul((float) 0.5, (float) 0.5, (float) 0.5, 1));
        }

        batch.draw(this.skin, this.x, this.y, 0, 0, this.width, this.height);                                                       // Draws the Button's skin at the Button's x and y coordinates with the Button's specified width and height.
        font.draw(batch, text, (this.x + (this.width / 2) - (this.textDimensions.width / 2)), (this.y + this.height / 2) + (this.textDimensions.height / 2));         // Writes the Button's text
        batch.setColor(1, 1, 1, 1);
    }
    
    
}
