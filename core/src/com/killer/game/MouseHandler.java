package com.killer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;


public class MouseHandler {
    private static Vector3 gameCoordinates;
    private static float clickCooldown;
    
    public MouseHandler() {
        gameCoordinates = new Vector3();
    }
    
    public void update() {
        if (clickCooldown > 0) {
            System.out.println("Hello!");
            clickCooldown = clickCooldown - Gdx.graphics.getDeltaTime();
        }
    }
    
    // Returns the mouse's x-coordinate
    public int getX() {
        gameCoordinates.x = Gdx.input.getX();                                           // Get the mouse's x-coordinate in mouseCoords
        Killer.camera.unproject(gameCoordinates);                           // Convert from mouseCoords to gameCoords
        return (int) gameCoordinates.x;                                                 // Return the x-coordinate in gameCoords
    }
    
    // Returns the mouse's y-coordinate
    public int getY() {
        gameCoordinates.y = Gdx.input.getY();                                            // Get the mouse's y-coordinate in mouseCoords
        Killer.camera.unproject(gameCoordinates);                            // Convert from mouseCoords to gameCoords
        return (int) gameCoordinates.y;                                                  // Return the y-coordinate in gameCoords
    }
    
    // Determines whether or not the mouse was clicked.
    public boolean checkClick() {
        if(clickCooldown <= 0 && Gdx.input.isTouched()) {                                                        // If the mouse has been clicked on...
            System.out.println("dt: " + value);
            clickCooldown = (float) 0.1;
            return true;                                                                    // Return true to indicate that the mouse has been clicked
        }
        return false;                                                                    // Return false to indicate that the mouse hasn't been clicked
    }
    
    // Checks if the mouse is hovering over a certain rectangle defined by the arguments x, y, width, and height.
    public boolean checkHover(int x, int y, int width, int height) {
        int mouseX = getX();                                                                                           // Sets mouseX equal  to the x-coord in the given coordinate system
        int mouseY = getY();                                                                                           // Sets mouseY equal to the y-coord in the given coordinate system
        
        if ((mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height))) {                                         // If the mouse is inside of the rectangle...
            return true;                                                                                                                    // Return true to indicate that the mouse is inside of the rectangle
        }
        return false;                                                                                                                   // Return false to indicate that the mouse is not inside of the rectangle
    }
}
