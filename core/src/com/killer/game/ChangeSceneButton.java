package com.killer.game;

import com.badlogic.gdx.math.Vector3;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeSceneButton extends Button implements ActionListener {
    Scene current;
    Scene next;
    
    public ChangeSceneButton(int x, int y, String text, String name, Scene current, Scene next) {
        super(x, y, text, name);
        
        this.current = current;
        this.next = next;
        this.addActionListener(this);
    }
    
    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("We're in");
        checkMouseClick(Killer.clickCoordinates);
        this.current.enable();
        this.next.disable();
    }
}
