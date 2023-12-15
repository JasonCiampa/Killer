package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class CasinoTable extends Scene {
    
    // FIELDS //
    
    // BUTTONS //
    private Button playButton;                                                                                                                      // Button that will be used to play selected Cards
    private Button passButton;                                                                                                                      // Button that will be used to pass on a turn
    private ChangeSceneButton quitButton;                                                                                                           // ChangeSceneButton that will be used to quit to title screen
    private Button rulesButton;                                                                                                                     // Rules Button that will be used to open/close the rules menu
    private Button rulesPreviousButton;                                                                                                             // Button that will be used to navigate to the previous page in the rules menu
    private Button rulesNextButton;                                                                                                                 // Button that will be used to navigate to the next page in the rules menu

    // RULES MENU
    private Texture[] rulesPages;                                                                                                                   // Texture array that will hold all of the pages in the rules menu
    private int rulesCurrentPage;                                                                                                                   // Int that will keep track of the index of the current rules page
    private boolean rulesMenuActive;                                                                                                                // Boolean that will keep track of whether or not the rules menu is active

    // GAMEHANDLER //
    public static GameHandler game;                                                                                                                 // GameHandler that will be used to handle the progression of the game

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // CONSTRUCTOR //
    public CasinoTable() {
        super("images/casino/killer_table.jpg", "music/Tokens, Please! - Super Paper Mario.mp3", 0, 0);         // Call the Scene's constructor to build the framework for TitleScreen
        
        this.playButton = new Button(Button.SHORT, 470, 20, "Play Cards", this) {                                        // Create the playButton
            @Override
            public void clickAction() {                                                                                                             // Override the abstract clickAction method from the Button class
                game.playCards();                                                                                                                   // Play the Player's selected Cards 
            }
        };
        
        this.passButton = new Button(Button.SHORT, 970, 20, "Pass Turn", this) {                                         // Create the passButton
            @Override
            public void clickAction() {                                                                                                             // Override the abstract clickAction method from the Button class
                game.passTurn();                                                                                                                    // Pass the Player's turn in the game
            }
        };
        
        this.quitButton = new ChangeSceneButton(Button.SHORT, 20, 960, "Quit to Title", this, null);                      // Create the quitButton

        this.rulesButton = new Button(Button.SHORT, 1420, 960, "How To Play", this) {                                    // Creates a playButton
            @Override
            public void clickAction() {                                                                                                             // Override the abstract clickAction method from the Button class
                if (rulesMenuActive) {                                                                                                              // If the rule menu is active when the Button was clicked...
                    this.setText("How To Play");                                                                                                    // Set the rulesButton text to "How To Play"
                    rulesMenuActive = false;                                                                                                            // Disable the rules menu
                    rulesPreviousButton.setActive(false);                                                                                          // Disable the rulesPreviousButton
                    rulesNextButton.setActive(false);                                                                                              // Disable the rulesNextButton
                    playButton.setActive(true);                                                                                                    // Enable the playButton
                    passButton.setActive(true);                                                                                                    // Enable the passButton
                }
                else {                                                                                                                              // Otherwise...
                    this.setText("Close Rules");                                                                                                    // Set the rulesButton text to "Close Rules"
                    rulesMenuActive = true;                                                                                                             // Enable the rules menu
                    rulesPreviousButton.setActive(true);                                                                                           // Enable the rulesPreviousButton
                    rulesNextButton.setActive(true);                                                                                               // Enable the rulesNextButton
                    playButton.setActive(false);                                                                                                   // Disable the playButton
                    passButton.setActive(false);                                                                                                   // Disable the passButton
                }
            }
        };
        
        this.rulesPreviousButton = new Button(Button.SHORT, 470, 20, "Previous Page", this) {                            // Create the rulesPreviousButton
            @Override
            public void clickAction() {                                                                                                             // Override the abstract clickAction method from the Button class
                rulesCurrentPage--;                                                                                                                 // Decrement the rulesCurrentPage index by 1
                if (rulesCurrentPage < 0) {                                                                                                         // If the rulesCurrentPage index is less than 0 (out of bounds)...
                    rulesCurrentPage = rulesPages.length - 1;                                                                                           // Set the rulesCurrentPage index to be the last index in the rulesPages array
                }
            }
        };
        
        this.rulesNextButton = new Button(Button.SHORT, 970, 20, "Next Page", this) {                                    // Create the rulesNextButton
            @Override
            public void clickAction() {                                                                                                             // Override the abstract clickAction method from the Button class
                rulesCurrentPage++;                                                                                                                 // Increment the rulesCurrentPage index by 1
                if (rulesCurrentPage > rulesPages.length - 1) {                                                                                     // If the rulesCurrentPage index is greater than the last index in the rulesPages array...
                    rulesCurrentPage = 0;                                                                                                               // Set the rulesCurrentPage index to be the first index in the rulesPages array
                }            
            }
        };
                
        this.rulesPages = new Texture[] {                                                                                                           // Load and store all of the pages of the rule menu
            new Texture("images/rules_pages/introduction_page.jpg"),                                                                         
            new Texture("images/rules_pages/move_types_page.jpg"), 
            new Texture("images/rules_pages/valid_moves_1_page.jpg"), 
            new Texture("images/rules_pages/valid_moves_2_page.jpg")
        };
    }
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS //
    
    // Anything that should happen immediately as CasinoTable becomes active should go in load
    @Override
    protected void load() {
        quitButton.next = Killer.titleScreen;                                                                                                      // Sets the quit button so that it switches to the TitleScreen Scene when clicked        
        game = new GameHandler();                                                                                                                  // Create a new GameHandler
        this.rulesCurrentPage = 0;                                                                                                                 // Set the rulesCurrentPage index to 0
        this.rulesMenuActive = false;                                                                                                              // Disable the rule menu
        this.rulesButton.setText("How to Play");                                                                                               // Set the rulesButton text to "How To Play"
        this.rulesPreviousButton.setActive(false);                                                                                            // Disable the rulesPreviousButton 
        this.rulesNextButton.setActive(false);                                                                                                // Disable the rulesNextButton 
        this.playButton.setActive(true);                                                                                                      // Enable the playButton
        this.passButton.setActive(true);                                                                                                      // Enable the passButton
    }
    
    @Override
    protected void update() {
        quitButton.update();                                                                                                                       // Update the quit button
        rulesButton.update();                                                                                                                      // Update the rules button
        
        if (this.rulesMenuActive) {                                                                                                                // If the rules menu is active...
            if (this.rulesCurrentPage == 0) {                                                                                                          // If the first page is the current page of the rules menu...
                this.rulesNextButton.update();                                                                                                             // Update the rulesNextButton
            }
            else if (this.rulesCurrentPage == this.rulesPages.length - 1) {                                                                        // Otherwise, if the last page is the current page of the rules menu...
                this.rulesPreviousButton.update();                                                                                                     // Update the rulesPreviousButton
            }   
            else {                                                                                                                                 // Otherwise...
                rulesPreviousButton.update();                                                                                                          // Update the rulesPreviousButton
                rulesNextButton.update();                                                                                                              // Update the rulesNextButton    
            }
        }
        else {                                                                                                                                    // Otherwise...
            game.update();                                                                                                                            // Update the GameHandler
        
            if (game.getPlayerInputAllowed() && !this.rulesMenuActive) {
                playButton.update();                                                                                                             // Update the playButton
                passButton.update();                                                                                                            // Update the passButton
            }    
        }
    }
    
    @Override
    protected void draw(SpriteBatch batch) {                                                            
        batch.draw(this.backgroundImage, this.x, this.y, this.width, this.height);                                                          // Draw CasinoTable's background image onto the screen
        game.draw(batch);                                                                                                                 // Draw all of the components in the game
        this.quitButton.draw(batch);
        this.rulesButton.draw(batch);
        
        if (this.rulesMenuActive) {                                                                                                        // If the rules menu is active...
            batch.draw(this.rulesPages[this.rulesCurrentPage], 160, 140);                                                                 // Draw the current rules page
            
            if (this.rulesCurrentPage == 0) {                                                                                              // If the first page is the current page of the rules menu...
                batch.setColor((float) 0.5, (float) 0.5, (float) 0.5, 1);                                                                    // Darken the drawing color
                this.rulesPreviousButton.draw(batch);                                                                                          // Draw the rulesPreviousButton in the darkened drawing color (to indicate that it can't be pressed since there is no previous page before the first page)
                this.rulesNextButton.draw(batch);                                                                                              // Draw the rulesNextButton in the normal drawing color
            }
            else if (this.rulesCurrentPage == this.rulesPages.length){                                                                     // Otherwise, if the last page is the current page of the rules menu...
                this.rulesPreviousButton.draw(batch);                                                                                          // Draw the rulesPreviousButton in the normal drawing color
                batch.setColor((float) 0.5, (float) 0.5, (float) 0.5, 1);                                                                    // Darken the drawing color
                this.rulesNextButton.draw(batch);                                                                                              // Draw the rulesNextButton in the darkened drawing color (to indicate that it can't be pressed since there is no next page after the last page)
            }
            else {                                                                                                                         // Otherwise...
                this.rulesPreviousButton.draw(batch);                                                                                          // Draw the rulesPreviousButton in the normal drawing color
                this.rulesNextButton.draw(batch);                                                                                              // Draw the rulesNextButton in the normal drawing color
            }
        }
        else {                                                                                                                             // Otherwise...
            if (!game.getPlayerInputAllowed()) {                                                                                               // If the Player isn't allowed to interact...
                batch.setColor((float) 0.5, (float) 0.5, (float) 0.5, 1);                                                                        // Darken the drawing color
                this.playButton.draw(batch);                                                                                                            // Draw the playButton with the darker color (to indicate that it is not pressable currently)
                batch.setColor((float) 0.5, (float) 0.5, (float) 0.5, 1);                                                                        // Darken the drawing color
                this.passButton.draw(batch);                                                                                                            // Draw the passButton with the darker color (to indicate that it is not pressable currently)
                this.quitButton.draw(batch);                                                                                                            // Draw the quitButton (in the default drawing color)
                this.rulesButton.draw(batch);                                                                                                           // Draw the rulesButton (in the default drawing color)
            }    
            else {
                this.playButton.draw(batch);                                                                                              // Draw the playButton in the normal drawing color
                this.passButton.draw(batch);                                                                                              // Draw the passButton in the normal drawing color
            }
        }
    }
    
}
