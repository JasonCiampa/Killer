package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class TitleScreen extends Scene {
    
    // FIELDS // 
    
    // IMAGES
    private Texture killerLogo;                                                                                                                                                      // Stores the Killer Logo to be drawn on the screen
    
    // BUTTONS
    private ChangeSceneButton playButton;                                                                                                                                                    // Stores the play Button which will start the game
    private Button quitButton;                                                                                                                                                               // Stores the quit Button which will exit the game
        
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // CONSTRUCTOR //
    public TitleScreen() {
        super("images/main_menu/background.jpg", "music/Ending Theme - Super Mario World.mp3", 0, 0);                                      // Calls the Scene's constructor to build the framework for TitleScreen
        
        killerLogo = new Texture("images/main_menu/killer_logo.png");                                                                                                    // Stores the killerLogo texture
        
        playButton = new ChangeSceneButton(Button.LONG, (this.width / 2) - (this.width / 4), (this.height / 2) - 50, "Play Game", this, null);            // Creates a playButton that changes the Scene to casinoTable when clicked on
            
        quitButton = new Button(Button.LONG, (this.width / 2) - (this.width / 4), (this.height / 2) - 250, "Quit Game", this) {                          // Creates a quitButton
            @Override
            public void clickAction() {                                                                                                                                              // Overriding the abstract clickAction method from the Button class                                                                    
                Gdx.app.exit();                                                                                                                                                      // Quits the program, ends the application
            }
        };
    }
    
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // METHODS //
    
    // Anything that should happen immediately as TitleScreen becomes active should go in load
    @Override
    protected void load() {
        playButton.next = Killer.casinoTable;                                                                                                                                         // Sets the play button so that it switches to the CasinoTable Scene when clicked
    }
    
    @Override
    protected void update() {
        this.updateButtons();                                                                                                                                                         // Updates all of TitleScreen's Buttons
    }
    
    @Override
    protected void draw(SpriteBatch batch) {
        batch.draw(this.backgroundImage, this.x, this.y, this.width, this.height);                                                                             // Draws TitleScreen's background image onto the screen
        batch.draw(killerLogo, this.x, this.y);                                                                                                                            // Draws the killerLogo onto the screen
        this.drawButtons(batch);                                                                                                                                                      // Draw all of TitleScreen's Buttons onto the screen
    }
    
}
