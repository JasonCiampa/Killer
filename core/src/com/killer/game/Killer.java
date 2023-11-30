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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Killer extends ApplicationAdapter {
    
    private Stage stage;
    
    private int WIDTH;
    private int HEIGHT;
            
    private static OrthographicCamera camera;  // OrthographicCamera helps ensure we can render using our target resolution no matter what the actual screen resolution is.
   
    private Scene mainMenu;
    private Scene game;
    
    private SpriteBatch batch;  // SpriteBatch is a special class that is used to draw 2D images.
    
    private Rectangle card;
    
    // Declares a new three-dimensional vector that will store the coordinates of the mouse cursor
    public static Vector3 mouseCoordinates;
    
    // Images
    private Texture spadeImage;
    private Texture clubImage;
    private Texture diamondImage;
    private Texture heartImage;
    private Texture jackImage;
    private Texture queenImage;
    private Texture aceImage;
    private Texture killerLogo;
    
    public Card testCard;

    
    // Music
    private Music backgroundMusic;
    
    // Buttons
    private Button[] buttons;
        
    // Updates the position of the mouse
    public static void updateMouse() {
        mouseCoordinates.set(Gdx.input.getX(), Gdx.input.getY(), 0);               // Set mouseCoordinates equal to the x and y position of the mouse
        camera.unproject(mouseCoordinates);                                  // Convert the touch/mouse coordinates into screen/image coordinates
    }
    
    // Determines whether or not the mouse was clicked.
    public static boolean checkMouseClick() {
        if(Gdx.input.isTouched())                                                       // If the mouse has been clicked on...
            return true;                                                                    // Return true to indicate that the mouse has been clicked
        else                                                                            // Otherwise...
            return false;                                                                   // Return false to indicate that the mouse hasn't been clicked
    }
    
        
    // Checks if the mouse is hovering over a certain rectangle defined by the arguments x, y, width, and height.
    public static boolean checkMouseHover(int x, int y, int width, int height) {
        int mouseX = (int) mouseCoordinates.x;                                                                                           // Sets mouseX equal  to the x-coord in the given coordinate system
        int mouseY = (int) mouseCoordinates.y;                                                                                           // Sets mouseY equal to the y-coord in the given coordinate system
        
        if ((mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height)))                                          // If the mouse is inside of the rectangle...
            return true;                                                                                                                    // Return true to indicate that the mouse is inside of the rectangle
        
        return false;                                                                                                                   // Return false to indicate that the mouse is not inside of the rectangle
    }
    
    
    @Override
    public void create () {
        
        WIDTH = 1920;
        HEIGHT = 1080;
        
        // Scenes
        mainMenu = new Scene("images/main_menu/background.jpg", "music/Ending Theme - Super Mario World.mp3", 0, 0);
        mainMenu.enable();
        
        game = new Scene("images/casino/killer_table.jpg", "music/Tokens, Please! - Super Paper Mario.mp3", 0, 0);
                
        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);   // Sets window to 1080p
        
        // Click Coordinates
        mouseCoordinates = new Vector3(); 
        
        // SpriteBatch
        batch = new SpriteBatch();
   
        // Images
        killerLogo = new Texture("images/main_menu/killer_logo.png");
        
        
        // Card            
        testCard = new Card(100, 100, Card.SPADE, Card.VALUE_7);

        
        // Music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Ending Theme - Super Mario World.mp3"));

        
        // Buttons
        mainMenu.makeButton((mainMenu.width / 2) - (mainMenu.width / 4), (mainMenu.height / 2) - 50, "Play Game", "play");
        mainMenu.makeButton((mainMenu.width / 2) - (mainMenu.width / 4), (mainMenu.height / 2) - 250, "Quit Game", "quit");
        
        game.makeButton((game.width / 2) - (game.width / 4), (game.height / 4) - 50, "Play Card", "playCards");
        ChangeSceneButton returnToMenu = game.makeChangeSceneButton((game.width / 2) - (game.width / 4), (game.height / 4) + 50, "Return to Menu", "returnToMenu", mainMenu);
    }
                
    @Override
    public void render () {
        ScreenUtils.clear(1, 1, 1, 1);                              // Sets the screen to white
        updateMouse();                                                      // Updates the mouse's coordinates and checks 
        camera.update();                                                    // Update the Camera once every frame
        batch.setProjectionMatrix(camera.combined);               // Tells the SpriteBatch to use the Camera's coordinate system (screen/image coords held in a matrix)
        batch.begin();                                                      // Starts the new "batch" of drawings for this frame
        
        if(mainMenu.active) {                                               // If mainMenu is the active Scene...       
            mainMenu.draw(batch);                                              // Draw mainMenu and all of its components (buttons)
            batch.draw(killerLogo, mainMenu.x, mainMenu.y);         // Draw killerLogo on the screen
                        
            for (Button button: mainMenu.buttons) {                                                         // For each button in mainMenu...
                button.setMouseHovering(checkMouseHover(button.getX(), button.getY(), button.getWidth(), button.getHeight()));              
                if (checkMouseClick()) {                                  // If the button was clicked on...

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
            testCard.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
