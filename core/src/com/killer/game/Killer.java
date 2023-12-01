package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//


// IMPORTS
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//


public class Killer extends ApplicationAdapter {
    // FIELDS //
    
    // CAMERA 
    public static OrthographicCamera camera;                                        // Camera is used to set viewport width and make conversions between different coordinate systems
               
    // SCENES
    public static Scene activeScene;                                                // activeScene holds a reference to the currently active Scene
    public static Scene titleScreen;                                                // titleScreen holds a reference to the TitleScreen Scene
    public static Scene casinoTable;                                                // casinoTable stores the CasinoTable Scene
        
    // SPRITEBATCH (DRAWER)
    private SpriteBatch batch;                                                      // SpriteBatch is used to draw 2D images.
    
    // CARDS
    public Card testCard1;                                                           // just a test card for now...
    public Card testCard2;                                                           // just a test card for now...
    public Card testCard3;                                                           // just a test card for now...
    public Card testCard4;                                                           // just a test card for now...
    public Card testCard5;                                                           // just a test card for now...
    public Card testCard6;                                                           // just a test card for now...
    public Card testCard7;                                                           // just a test card for now...
    public Card testCard8;                                                           // just a test card for now...
    public Card testCard9;                                                           // just a test card for now...
    public Card testCard10;                                                           // just a test card for now...
    public Card testCard11;                                                           // just a test card for now...
    public Card testCard12;                                                           // just a test card for now...
    public Card testCard13;                                                           // just a test card for now...
    

    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    
    // CREATE METHOD (GAME LOAD / GAME SETUP)
    
    @Override
    public void create () {
        // CAMERA
        camera = new OrthographicCamera();                                          // Creates a new Camera
        camera.setToOrtho(false, 1920, 1080);        // Camera sets the viewport to 1080p
        
        // SCENES
        titleScreen = new TitleScreen();                                            // Creates the TitleScreen Scene
        casinoTable = new CasinoTable();                                            // Creates the CasinoTable Scene
      
        // ACTIVE SCENE
        activeScene = titleScreen;                                                  // Sets titleScreen as the active Scene
        activeScene.enable();                                                       // Enables the activeScene
        
        // SPRITEBATCH (DRAWER)
        batch = new SpriteBatch();                                                  // Creates the SpriteBatch
        
        // CARD            
        testCard1 = new Card(10, 150, Card.SPADE, Card.VALUE_3);     // Creates the test card... for now...
        testCard2 = new Card(155, 150, Card.CLUB, Card.VALUE_JACK);     // Creates the test card... for now...
        testCard3 = new Card(300, 150, Card.DIAMOND, Card.VALUE_5);     // Creates the test card... for now...
        testCard4 = new Card(445, 150, Card.DIAMOND, Card.VALUE_9);     // Creates the test card... for now...
        testCard5 = new Card(590, 150, Card.SPADE, Card.VALUE_QUEEN);     // Creates the test card... for now...
        testCard6 = new Card(735, 150, Card.HEART, Card.VALUE_8);     // Creates the test card... for now...
        testCard7 = new Card(880, 150, Card.CLUB, Card.VALUE_ACE);     // Creates the test card... for now...
        testCard8 = new Card(1025, 150, Card.SPADE, Card.VALUE_4);     // Creates the test card... for now...
        testCard9 = new Card(1170, 150, Card.DIAMOND, Card.VALUE_10);     // Creates the test card... for now...
        testCard10 = new Card(1315, 150, Card.SPADE, Card.VALUE_7);     // Creates the test card... for now...
        testCard11 = new Card(1460, 150, Card.CLUB, Card.VALUE_6);     // Creates the test card... for now...
        testCard12 = new Card(1605, 150, Card.HEART, Card.VALUE_2);     // Creates the test card... for now...
        testCard13 = new Card(1750, 150, Card.SPADE, Card.VALUE_KING);     // Creates the test card... for now...

    }
            
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    
    // RENDER METHOD (GAME UPDATE / GAME DRAW)
    @Override
    public void render () {
        ScreenUtils.clear(1, 1, 1, 1);                              // Sets the screen to white
        
        camera.update();                                                    // Update the Camera once every frame
        batch.setProjectionMatrix(camera.combined);               // Tells the SpriteBatch to use the Camera's coordinate system (screen/image coords held in a matrix)
        batch.begin();                                                      // Starts the new "batch" of drawings for this frame
        
        Mouse.update();                                                     // Updates the Mouse
        
        activeScene.update();                                               // Updates the activeScene
        activeScene.draw(batch);                                            // Draws the activeScene

        if (activeScene == casinoTable) {
            testCard1.update();
            testCard1.draw(batch);
            
            testCard2.update();
            testCard2.draw(batch);
            
            testCard3.update();
            testCard3.draw(batch);
            
            testCard4.update();
            testCard4.draw(batch);
            
            
            testCard5.update();
            testCard5.draw(batch);
            
            testCard6.update();
            testCard6.draw(batch);
            
            testCard7.update();
            testCard7.draw(batch);
            
            testCard8.update();
            testCard8.draw(batch);
            
            testCard9.update();
            testCard9.draw(batch);
            
            testCard10.update();
            testCard10.draw(batch);
            
            testCard11.update();
            testCard11.draw(batch);
            
            testCard12.update();
            testCard12.draw(batch);
            
            testCard13.update();
            testCard13.draw(batch);
        }
        
        batch.end();                                                        // Ends the "batch" of drawings for this frame
    }

    // DISPOSE METHOD (GAME UNLOAD / BREAK DOWN)
    @Override
    public void dispose () {
        batch.dispose();                                                    // Disposes of the SpriteBatch
    }
}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

