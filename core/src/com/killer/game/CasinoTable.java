package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class CasinoTable extends Scene {
    
    // FIELDS //
    
    // BUTTONS //
    Button playButton;
    ChangeSceneButton quitButton;
    ChangeSceneButton rulesButton;
    
    // GAMEHANDLER //
    public static GameHandler game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // CONSTRUCTOR //
    public CasinoTable() {
        super("images/casino/killer_table.jpg", "music/Tokens, Please! - Super Paper Mario.mp3", 0, 0);               // Calls the Scene's constructor to build the framework for TitleScreen
        
        playButton = new Button(Button.SHORT, 720, 20, "Play Cards", this) {                                                    // Creates a playButton
            @Override
            public void clickAction() {                                                                                                                         // Overriding the abstract clickAction method from the Button class
                game.playCards();
            }
        };
        
        quitButton = new ChangeSceneButton(Button.SHORT, 20, 960, "Quit to Title", this, null);                                  // Creates a quitButton that changes the Scene to casinoTable when clicked on
        rulesButton = new ChangeSceneButton(Button.SHORT, 1420, 960, "How To Play", this, null);                                 // Creates a rulesButton that changes the Scene to howToPlay
    }
    
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS //
    
    // Anything that should happen immediately as CasinoTable becomes active should go in load
    @Override
    protected void load() {
        quitButton.next = Killer.titleScreen;                                                                                                                   // Sets the quit button so that it switches to the TitleScreen Scene when clicked
        
        // GAMEHANDLER
        game = new GameHandler();                                                                                                                               // Create a new GameHandler
    }
    
    @Override
    protected void update() {
        game.update();                                                                                                                                          // Update the GameHandler
        this.updateButtons();                                                                                                                                   // Updates all of CasinoTable's Buttons
    }
    
    @Override
    protected void draw(SpriteBatch batch) {
        batch.draw(this.backgroundImage, this.x, this.y, this.width, this.height);                                                       // Draws CasinoTable's background image onto the screen
        game.draw(batch);                                                                                                                                       // Draws all of the Cards in the Killer game
        this.drawButtons(batch);                                                                                                                                // Draws all of CasinoTable's Buttons onto the screen
    }
    
}
