package com.killer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Killer extends ApplicationAdapter {
    
    private Stage stage;
    
    private int WIDTH;
    private int HEIGHT;
            
    private OrthographicCamera camera;  // OrthographicCamera helps ensure we can render using our target resolution no matter what the actual screen resolution is.
   
    private Scene mainMenu;
    private Scene game;
    
    private SpriteBatch batch;  // SpriteBatch is a special class that is used to draw 2D images.
    
    private Rectangle card;
    
    // Declares a new three-dimensional vector that will store converted coordinates
    private Vector3 hoverCoordinates;
    private Vector3 clickCoordinates;
    
    // Images
    private Texture spadeImage;
    private Texture clubImage;
    private Texture diamondImage;
    private Texture heartImage;
    private Texture jackImage;
    private Texture queenImage;
    private Texture aceImage;
    private Texture killerLogo;
    
    // Music
    private Music backgroundMusic;
    
    // Buttons
    private Button[] buttons;
        
    // Detects the mouse and its behavior and also converts "touch" coords to "screen/image" coords 
    private void mouseDetect() {
        hoverCoordinates.set(Gdx.input.getX(), Gdx.input.getY(), 0);               // Set hoverCoordinates equal to the x and y position of the mouse
        camera.unproject(hoverCoordinates);                                  // Convert the touch/mouse hoverCoordinates into screen/image coordinates

       
        if(Gdx.input.isTouched()) {                                                      // If the screen is currently being touched or clicked on...
            clickCoordinates.set(Gdx.input.getX(), Gdx.input.getY(), 0);           // Set clickCoordinates equal to the x and y position of the mouse
            camera.unproject(clickCoordinates);                                 // Convert the touch/mouse clickCoordinates into screen/image coordinates 
            
            // Moves the bucket to wherever the user pressed and centers it at that position
            // bucket.x = touchPos.x - 64 / 2;
        }
    }
    
    
    @Override
    public void create () {
        
        WIDTH = 1920;
        HEIGHT = 1080;
        
        // Scenes
        mainMenu = new Scene("images/background.jpg", "music/Ending Theme - Super Mario World.mp3", 0, 0);
        mainMenu.enable();
        
        game = new Scene("images/casino.jpg", "music/Tokens, Please! - Super Paper Mario.mp3", 0, 0);
                
        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);   // Sets window to 1080p
        
        // Click Coordinates
        clickCoordinates = new Vector3(); 
        hoverCoordinates = new Vector3();
        
        // SpriteBatch
        batch = new SpriteBatch();
   
        // Images
        spadeImage = new Texture("images/suits_spade.png");
        clubImage = new Texture("images/suits_club.png");
        diamondImage = new Texture("images/suits_diamond.png");
        heartImage = new Texture("images/suits_heart.png");
        killerLogo = new Texture("images/main_menu/killer_logo.png");
        
        // Card
        card = new Rectangle();
        card.width = spadeImage.getHeight();
        card.height = spadeImage.getWidth();
        card.x = (WIDTH / 2) - (card.width / 2);
        card.y = (HEIGHT / 2)- (card.height / 2);
        
        // Music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Ending Theme - Super Mario World.mp3"));

        
        // Buttons
        mainMenu.makeButton((mainMenu.width / 2) - (mainMenu.width / 4), (mainMenu.height / 2) - 50, "Play Game", "play");
        mainMenu.makeButton((mainMenu.width / 2) - (mainMenu.width / 4), (mainMenu.height / 2) - 250, "Quit Game", "quit");
        
        game.makeButton((game.width / 2) - (game.width / 4), (game.height / 4) - 50, "Play Card", "playCards");
 
    }
                
    @Override
    public void render () {
        ScreenUtils.clear(1, 1, 1, 1);                              // Sets the screen to white
        mouseDetect();                                                      // Detects the mouse and its behavior and also converts "touch" coords to "screen/image" coords
        camera.update();                                                    // Update the Camera once every frame
        batch.setProjectionMatrix(camera.combined);               // Tells the SpriteBatch to use the Camera's coordinate system (screen/image coords held in a matrix)
        batch.begin();                                                      // Starts the new "batch" of drawings for this frame
        
        if(mainMenu.active) {                                               // If mainMenu is the active Scene...       
            mainMenu.draw(batch);                                              // Draw mainMenu and all of its components (buttons)
            batch.draw(killerLogo, mainMenu.x, mainMenu.y);         // Draw killerLogo on the screen

            for (Button button: mainMenu.buttons) {                            // For each button in mainMenu...
                button.setMouseHovering(button.checkMouseHover(hoverCoordinates));              
                if (button.checkMouseClick(clickCoordinates)) {        // If the button was clicked on...

                    if(button.getName() == "play") {                              // If the button is the play button...             
                        mainMenu.disable();                                          // Set main menu to be inactive
                        game.enable();                                               // Set game to be active
                    }

                    else if(button.getName() == "quit") {                         // If the button is the quit button...
                        Gdx.app.exit();                                              // End the game
                    }
                }
            }
         }
        else if (game.active) {
            game.draw(batch);
        }

    batch.end();
    
    clickCoordinates.set(Vector3.Zero); // Reset clickCoordinates so that the click only registers once
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
