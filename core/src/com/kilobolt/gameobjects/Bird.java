package com.kilobolt.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.kilobolt.zbhelpers.AssetLoader;

/**
 * Created by txema on 9/17/14.
 */
public class Bird {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    private Circle boundingCircle;

    private boolean isAlive;

    private float originalY;


    public Bird(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
        this.originalY = y;

        boundingCircle = new Circle();

        isAlive = true;

    }

    // cada vez que se actualiza el pajaro, se hacen dos cosas:
    // 1 - sumar el vector de aceleracion al vector de velocidad. Esto incrementa la velocidad del pajaro
    // 2 - sumar la nueva velocidad resultante al vector de posición, asi se actualiza la posición del pajaro
    // el delta es la cantidad de tiempo que ha pasado desde la ultima llamada a este metodo, es decir, el FrameRate
    // Si el juego se decelera, el delta será mayor, porque el procesador habrá tomado más tiempo para completar
    // la ultima iteración. Si aplicamos el valor delta, conseguimos un framerate independiente del movimiento.
    // Ej: Es el metodo update tomó el doble de tiempo en ejecutarse, el delta será 2, y multimlicaremos
    // los vectores que correspondan (velocidad y posicion en este caso) para que el pajaro se posicione
    // donde le corresponde.
    public void update(float delta) {

        velocity.add(acceleration.cpy().scl(delta));

        // limitar la velocidad máxima del pajaro a 200 (velocidad terminal)
        if (velocity.y > 200) {
            velocity.y = 200;
        }

        // CEILING CHECK
        if (position.y < -13) {
            position.y = -13;
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(delta));

        // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);


        // Rotate counterclockwise
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            // control de rotacion hacia abajo. Si se pasa de 20, lo corregimos
            if (rotation < -20) {
                rotation = -20;
            }
        }

        // Rotate clockwise
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;

            // control de rotacion hacia arriba, si se pasa de 90 lo corregimos
            if (rotation > 90) {
                rotation = 90;
            }
        }


    }

    public void onClick() {
        if (isAlive) {
            AssetLoader.flap.play();
            velocity.y = -140;
        }
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }


    public void die() {
        isAlive = false;
        velocity.y = 0;
    }

    public void decelerate() {
        // We want the bird to stop accelerating downwards once it is dead.
        acceleration.y = 0;
    }

    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isFalling() {
        return velocity.y > 110;
    }

    public boolean shouldntFlap() {
        return velocity.y > 70 || !isAlive;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public boolean isAlive() {
        return isAlive;
    }


}
