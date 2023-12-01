package com.killer.game;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// IMPORTS
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Killer extends ApplicationAdapter {
    // FIELDS //
    
    // CAMERA 
    public static OrthographicCamera camera;   // Used to convert mouse coordinates into game coordinates
    
    // MOUSE HANDLER
    public static MouseHandler mouse;
               
    // SCENES
    public static Scene activeScene;      // Holds a reference to the currently active scene
    public static Scene titleScreen;
    public static Scene casinoTable;
    
    // SPRITEBATCH (DRAWER)
    private SpriteBatch batch;  // SpriteBatch is a special class that is used to draw 2D images.
    
    // CARDS
    public Card testCard;
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // CREATE METHOD (GAME LOAD / GAME SETUP)
    
    @Override
    public void create () {
        // CAMERA
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);   // Sets window to 1080p
        
        // MOUSE HANDLER
        mouse = new MouseHandler();
        
        // SCENES
        titleScreen = new TitleScreen();  
        casinoTable = new CasinoTable();
      
        // ACTIVE SCENE
        activeScene = titleScreen;
        activeScene.enable();
        
        // SPRITEBATCH (DRAWER)
        batch = new SpriteBatch();
        
        // CARD            
        testCard = new Card(100, 100, Card.SPADE, Card.VALUE_7);
    }
            
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // RENDER METHOD (GAME UPDATE / GAME DRAW)
    @Override
    public void render () {
        ScreenUtils.clear(1, 1, 1, 1);                              // Sets the screen to white
        
        camera.update();                                                    // Update the Camera once every frame
        batch.setProjectionMatrix(camera.combined);               // Tells the SpriteBatch to use the Camera's coordinate system (screen/image coords held in a matrix)
        batch.begin();                                                      // Starts the new "batch" of drawings for this frame
        
        mouse.update();
        
        activeScene.update();
        activeScene.draw(batch);

        batch.end();
    }

    // DISPOSE METHOD (GAME UNLOAD / BREAK DOWN)
    @Override
    public void dispose () {
        batch.dispose();
    }
}
