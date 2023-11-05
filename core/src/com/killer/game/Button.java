package com.killer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
    private int width;
    private int height;
    private int x;
    private int y;
    
//    private int[] color;
//    private int[] colorHover;
    
    private String text;
    private BitmapFont font;

//    private final int[] textColor;
//    private final int[] textColorHover;
    
    private final Sound clickSfx;
    
    private final Texture skin;
    
    private final String name;
    
    public Button(int width, int height, int x, int y, String text, String name) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        
//        this.color = color;
//        this.colorHover = colorHover;
        
        this.text = text;
//        this.textColor = textColor;
//        this.textColorHover = textColorHover;
        
        this.clickSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/button_click.mp3"));
        
        this.skin = new Texture("images/buttonSkin.jpg");
        
        this.name = name;
        
        this.font = new BitmapFont();
    }
    
    public boolean isClicked(int mouseX, int mouseY) {
        if ((mouseX >= this.x && mouseX <= this.width) && (mouseY >= this.y && mouseY <= this.height)) {
            System.out.println("Hello FART!");
            return true;
        }
        
        return false;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void draw(SpriteBatch batch) {
        batch.draw(this.skin, this.x, this.y, this.width, this.height);
        font.draw(batch, text, (this.x + this.width / 2), (this.y + this.height / 2));
    }
}
