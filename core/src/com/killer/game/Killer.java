package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class Killer extends ApplicationAdapter {
    // FIELDS //
    
    // CAMERA 
    public static OrthographicCamera camera;                                                                                // Camera is used to set viewport width and make conversions between different coordinate systems
               
    // SCENES
    public static Scene activeScene;                                                                                        // activeScene stores the currently active Scene
    public static Scene titleScreen;                                                                                        // titleScreen stores the TitleScreen Scene
    public static Scene casinoTable;                                                                                        // casinoTable stores the CasinoTable Scene
        
    // SPRITEBATCH (DRAWER)
    private SpriteBatch batch;                                                                                              // SpriteBatch is used to draw 2D images.
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CREATE METHOD (GAME LOAD / GAME SETUP)
    
    @Override
    public void create () {
        // CAMERA
        camera = new OrthographicCamera();                                                                                  // Creates a new Camera
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());                                        // Camera sets the viewport to 1080p
        
        // SCENES
        titleScreen = new TitleScreen();                                                                                    // Creates the TitleScreen Scene
        casinoTable = new CasinoTable();                                                                                    // Creates the CasinoTable Scene
      
        // ACTIVE SCENE
        activeScene = titleScreen;                                                                                          // Sets titleScreen as the active Scene
        activeScene.enable();                                                                                               // Enables the activeScene
        
        // SPRITEBATCH (DRAWER)
        batch = new SpriteBatch();                                                                                          // Creates the SpriteBatch
    }
            
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // RENDER METHOD (GAME UPDATE / GAME DRAW)
    @Override
    public void render () {
        ScreenUtils.clear(1, 1, 1, 1);                                                                                      // Sets the screen to white
        
        camera.update();                                                                                                    // Update the Camera once every frame
        batch.setProjectionMatrix(camera.combined);                                                                         // Tells the SpriteBatch to use the Camera's coordinate system (screen/image coords held in a matrix)
        batch.begin();                                                                                                      // Starts the new "batch" of drawings for this frame
        
        Mouse.update();                                                                                                     // Updates the Mouse
        
        activeScene.update();                                                                                               // Updates the activeScene
        activeScene.draw(batch);                                                                                          // Draws the activeScene

        batch.end();                                                                                                        // Ends the "batch" of drawings for this frame
    }

    // DISPOSE METHOD (GAME UNLOAD / BREAK DOWN)
    @Override
    public void dispose () {
        batch.dispose();                                                                                                    // Disposes of the SpriteBatch
    }
}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

