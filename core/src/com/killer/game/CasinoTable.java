package com.killer.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class CasinoTable extends Scene {
    
    Button playButton;
    ChangeSceneButton quitButton;
    
    public CasinoTable() {
        super("images/casino/killer_table.jpg", "music/Tokens, Please! - Super Paper Mario.mp3", 0, 0);
        
        playButton = new Button((this.width / 2) - (this.width / 4), (this.height / 4) - 50, "Play Selected Cards", this) {                               // Creates a playButton
            @Override
            public void clickAction() {                                                                                                                                                  // Overriding the abstract clickAction method from the Button class
                // Play cards code will go here
                // OR
                //This could trigger something in the GameHandler code (most likely option)
            }
        };
        
        quitButton = new ChangeSceneButton((this.width / 2) - (this.width / 4), (this.height / 2) - 50, "Quit to Title Screen", this, null);  // Creates a quitButton that changes the Scene to casinoTable when clicked on

    }
    
    // Anything that should happen immediately as CasinoTable becomes active should go in load
    @Override
    protected void load() {
        quitButton.next = Killer.titleScreen;
    }
    
    @Override
    protected void update() {
        this.updateButtons();                                                                                                                                                           // Updates all of CasinoTable's Buttons
    }
    
    @Override
    protected void draw(SpriteBatch batch) {
        batch.draw(this.backgroundImage, this.x, this.y, this.width, this.height);                                                                               // Draws CasinoTable's background image onto the screen
        this.drawButtons(batch);                                                                                                                                                        // Draws all of CasinoTable's Buttons onto the screen
        
        // Eventually...
        // Add code to draw all cards in the game (those cards/decks will likely be stored in the GameHandler.java file)
    }
    
}
