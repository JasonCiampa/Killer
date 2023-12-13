package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class CasinoTable extends Scene {
    
    // FIELDS //
    
    // BUTTONS //
    private Button playButton;
    private Button passButton;
    private ChangeSceneButton quitButton;
    
    private Button rulesButton;
    private Texture[] rulesPages;
    private int rulesCurrentPage;
    private boolean ruleMenuActive;
    private Button rulesPreviousButton;
    private Button rulesNextButton;
    
    
    // GAMEHANDLER //
    public static GameHandler game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // CONSTRUCTOR //
    public CasinoTable() {
        super("images/casino/killer_table.jpg", "music/Tokens, Please! - Super Paper Mario.mp3", 0, 0);               // Calls the Scene's constructor to build the framework for TitleScreen
        
        this.rulesPages = new Texture[] {
            new Texture("images/rules_pages/introduction_page.png"), 
            new Texture("images/rules_pages/move_types_page.png"), 
            new Texture("images/rules_pages/valid_moves_1_page.png"), 
            new Texture("images/rules_pages/valid_moves_2_page.png")
        };
        
        this.rulesCurrentPage = 0;
        this.ruleMenuActive = false;
        
        this.playButton = new Button(Button.SHORT, 470, 20, "Play Cards", this) {                                                    // Creates a playButton
            @Override
            public void clickAction() {                                                                                                                         // Overriding the abstract clickAction method from the Button class
                game.playCards();
                ruleMenuActive = true;
            }
        };
        
        this.passButton = new Button(Button.SHORT, 970, 20, "Pass Turn", this) {                                                    // Creates a playButton
            @Override
            public void clickAction() {                                                                                                                         // Overriding the abstract clickAction method from the Button class
                game.passTurn();
            }
        };
        
        
        this.quitButton = new ChangeSceneButton(Button.SHORT, 20, 960, "Quit to Title", this, null);                                  // Creates a quitButton that changes the Scene to casinoTable when clicked on

        
        this.rulesButton = new Button(Button.SHORT, 1420, 960, "How To Play", this) {                                                    // Creates a playButton
            @Override
            public void clickAction() {                                                                                                                         // Overriding the abstract clickAction method from the Button class
//                 DISPLAY RULES TEXTURE AT X = 160 AND Y = 940
                if (ruleMenuActive) {
                    this.setText("How To Play");
                    ruleMenuActive = false;
                    rulesPreviousButton.setActive(false);
                    rulesNextButton.setActive(false);
                    playButton.setActive(true);
                    passButton.setActive(true);
                }
                else {
                    this.setText("Close Rules");
                    ruleMenuActive = true;
                    rulesPreviousButton.setActive(true);
                    rulesNextButton.setActive(true);
                    playButton.setActive(false);
                    passButton.setActive(false);
                }
            }
        };
        
        this.rulesPreviousButton = new Button(Button.SHORT, 470, 20, "Previous Page", this) {                                                    // Creates a playButton
            @Override
            public void clickAction() {                                                                                                                         // Overriding the abstract clickAction method from the Button class
                rulesCurrentPage--;
                if (rulesCurrentPage < 0) {
                    rulesCurrentPage = 3;
                }
            }
        };
        
        this.rulesNextButton = new Button(Button.SHORT, 970, 20, "Next Page", this) {                                                    // Creates a playButton
            @Override
            public void clickAction() {                                                                                                                         // Overriding the abstract clickAction method from the Button class
                rulesCurrentPage++;
                if (rulesCurrentPage > 3) {
                    rulesCurrentPage = 0;
                }            
            }
        };
        
        this.rulesPreviousButton.setActive(false);
        this.rulesNextButton.setActive(false);
        
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
        
        if (game.getPlayerInputAllowed() && !this.ruleMenuActive) {
            this.updateButtons();                                                                                                                                   // Updates all of CasinoTable's Buttons
        }
        else {
            quitButton.update();
            rulesButton.update();
            rulesPreviousButton.update();
            rulesNextButton.update();
        }
    }
    
    @Override
    protected void draw(SpriteBatch batch) {
        batch.draw(this.backgroundImage, this.x, this.y, this.width, this.height);                                                       // Draws CasinoTable's background image onto the screen
        game.draw(batch);                                                                                                                                       // Draws all of the Cards in the Killer game
        
        if (this.ruleMenuActive) {
            batch.draw(this.rulesPages[this.rulesCurrentPage], 160, 140);
        }
        
        if (!game.getPlayerInputAllowed()) {
            batch.setColor((float) 0.2, (float) 0.2, (float) 0.2, 1);
            playButton.draw(batch);
            batch.setColor((float) 0.2, (float) 0.2, (float) 0.2, 1);
            passButton.draw(batch);
            quitButton.draw(batch);
            rulesButton.draw(batch);
        }
        else {
            this.drawButtons(batch);                                                                                                                                // Draws all of CasinoTable's Buttons onto the screen
        }
    }
    
}
