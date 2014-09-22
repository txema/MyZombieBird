package com.kilobolt.gameobjects;

/**
 * Created by txema on 9/18/14.
 */
public class Grass  extends Scrollable {

    // When Grass's constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Grass(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);

    }


    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }




}
