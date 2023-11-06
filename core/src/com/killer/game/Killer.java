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
    
    // Declares a new three-dimensional vector that will store the converted coordinates
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
        
    // Detects a mouseClick and documents the coordinates of the click in the clickCoordinates vector
    private boolean mouseClick() {
        
        // If the screen is currently being touched or clicked on, then convert the touch/mouse click coordinates into our camera's coordinate system.
        if(Gdx.input.isTouched()) {
            
            // Sets the clickCoordinates vector to the current touch/mouse coordinates.
            clickCoordinates.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            // Converts the touch/mouse coordinates into the camera coordinates
            camera.unproject(clickCoordinates);
            
            // Moves the bucket to wherever the user pressed and centers it at that position
            // bucket.x = touchPos.x - 64 / 2;
            
            return true;
        }
        
        return false;
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
        ScreenUtils.clear(1, 1, 1, 1);
        
        mouseClick();
               
        camera.update();    // Update the camera once every frame.
        
        batch.setProjectionMatrix(camera.combined); // Tells the SpriteBatch to use the camera's coordinate system (matrix).
        
        batch.begin();  // Starts the new "batch" of drawings for this frame.
            if(mainMenu.active) {
                mainMenu.draw(batch);                                                                  // Draw the main menu and all of its components
                batch.draw(killerLogo, mainMenu.x, mainMenu.y);
                
                for (Button button: mainMenu.buttons) {                                                 // Check all of main menu's buttons
                    if (button.isClicked((int) clickCoordinates.x, (int) clickCoordinates.y)) {         // If the button was clicked on...
                        
                        if(button.getName() == "play") {                                                // If the button is the play button...             
                            mainMenu.disable();                                                       // Set main menu to be inactive
                            game.enable();                                                            // Set game to be active
                        }
                        
                        else if(button.getName() == "quit") {                                           // If the button is the quit button...
                            Gdx.app.exit();                                                                // End the game
                        }
                    }
                }
             }
            else if (game.active) {
                game.draw(batch);
            }
            
//        batch.setColor(1, 1, 1, 1);
               
        
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
