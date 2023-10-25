package com.killer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Killer extends ApplicationAdapter {
    
    private int WIDTH;
    private int HEIGHT;
    
    private OrthographicCamera camera;  // OrthographicCamera helps ensure we can render using our target resolution no matter what the actual screen resolution is.
   
    private SpriteBatch batch;  // SpriteBatch is a special class that is used to draw 2D images.
    
    private Rectangle card;
    
    // Images
    private Texture spadeImage;
    private Texture clubImage;
    private Texture diamondImage;
    private Texture heartImage;
    private Texture jackImage;
    private Texture queenImage;
    private Texture aceImage;
    
    // Music
    private Music backgroundMusic;
    
    @Override
    public void create () {
        WIDTH = 1920;
        HEIGHT = 1080;
        
        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);   // Sets window to 1080p
        
        // SpriteBatch
        batch = new SpriteBatch();
        
        
        // Images
        spadeImage = new Texture("images/suits_spade.png");
        clubImage = new Texture("images/suits_club.png");
        diamondImage = new Texture("images/suits_diamond.png");
        heartImage = new Texture("images/suits_heart.png");
        
        // Card
        card = new Rectangle();
        card.width = spadeImage.getHeight();
        card.height = spadeImage.getWidth();
        card.x = (WIDTH / 2) - (card.width / 2);
        card.y = (HEIGHT / 2)- (card.height / 2);
        
        // Music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Ending Theme - Super Mario World.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        backgroundMusic.setVolume((float) 0.25);
       
    }

    @Override
    public void render () {
        ScreenUtils.clear(0, 0, 1, 1);
        camera.update();    // Update the camera once every frame.
        
        batch.setProjectionMatrix(camera.combined); // Tells the SpriteBatch to use the camera's coordinate system (matrix).
        
        batch.begin();  // Starts the new "batch" of drawings for this frame.
        batch.setColor(1, 0, 0, 1);
        batch.draw(spadeImage, card.x, card.y);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
