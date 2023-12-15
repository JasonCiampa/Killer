package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class ChangeSceneButton extends Button {
    
    // FIELDS //
    Scene current;                                                                                              // Stores the current Scene
    Scene next;                                                                                                 // Stores the Scene to change to next
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR //
    public ChangeSceneButton(int buttonType, int x, int y, String text, Scene current, Scene next) {
        super(buttonType, x, y, text, current);
        this.current = current;                                                                                 // Sets the current Scene
        this.next = next;                                                                                       // Sets the Scene to change to next
    }
        
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS //
    
    @Override
    public void clickAction() {
        this.current.disable();                                                                                 // Disables the current Scene
        this.next.enable();                                                                                     // Enables the next Scene
        Killer.activeScene = this.next;                                                                         // Sets the next Scene to be the active Scene
        Killer.activeScene.load();                                                                              // Loads the next Scene
    }
}
