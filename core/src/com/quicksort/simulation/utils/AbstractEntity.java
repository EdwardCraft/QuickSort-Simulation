package com.quicksort.simulation.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by PEDRO on 11/11/2016.
 */
public abstract class AbstractEntity {

    protected float x;
    protected float y;
    protected Enums.ObjectID id;
    protected Vector2 position;

    public AbstractEntity(float x, float y, Enums.ObjectID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void update(float delta, Enums.GameState gameState);
    public abstract void render(SpriteBatch batch);

    public Vector2 getPosition(){return position;}

    public float getX(){return x;}
    public void  setX(float x){this.x =  x;}
    public float getY(){return y;}
    public void  setY(float y){this.y =  y;}


}
