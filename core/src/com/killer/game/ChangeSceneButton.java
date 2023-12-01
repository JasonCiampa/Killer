package com.killer.game;

import com.badlogic.gdx.math.Vector3;

public class ChangeSceneButton extends Button {
    Scene current;
    Scene next;
    
    public ChangeSceneButton(int x, int y, float scaleX, float scaleY, String text, Scene current, Scene next) {
        super(x, y, scaleX, scaleY, text, current);
        this.current = current;
        this.next = next;
    }
        
    @Override
    public void clickAction() {
        this.current.disable();
        this.next.enable();
        Killer.activeScene = this.next;
        Killer.activeScene.load();
    }
}
