package com.quicksort.simulation.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.quicksort.simulation.utils.AbstractEntity;
import com.quicksort.simulation.utils.Contants;
import com.quicksort.simulation.utils.Enums;
import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;

/**
 * Created by PEDRO on 13/11/2016.
 */
public class Rectangle extends AbstractEntity{

    private final int number;
    private final float height;
    private Enums.PillarStates  pillarStates;

    private Color pillarColor;
    private float Y_LIMIT;
    private float Y_START;

    private boolean pivot;
    private float velocityX;
    private float velocityY;

    private Enums.Stepping stepping;
    private boolean isOver;
    private float r;
    private float g;
    private float b;
    private float z;
    private float a;

    private float offset = 0.05f;
    private boolean second = false;

    public Rectangle(float x, float y, Enums.ObjectID id ,int number, float height) {
        super(x, y, id);
        Y_START = y;
        this.number = number;
        this.height = height;
        pillarColor = Contants.PILLAR_COLOR;
        pivot = false;
        pillarStates = Enums.PillarStates.NORMAL;
        Y_LIMIT = Contants.APP_HEIGHT / 2;

        velocityX = Contants.RECTANGLE_VELOCITY_X;
        velocityY = Contants.RECTANGLE_VELOCITY_Y;
        isOver = false;
        stepping = Enums.Stepping.IDLE;
        r = 0;
        g = 0;
        b = 0;
        z = 0;
        a = 0;

    }

    @Override
    public void update(float delta, Enums.GameState gameState) {

        if(stepping == Enums.Stepping.ONE){
            if((y ) <= Y_LIMIT){
                y += velocityY * delta;
            }else{
                stepping = Enums.Stepping.IDLE;
            }
        }else if(stepping == Enums.Stepping.SECOND){
            if((y ) >= Y_START){
                y -= velocityY * delta;
            }else{
                stepping = Enums.Stepping.IDLE;
            }
        }


    }

    @Override
    public void render(SpriteBatch batch) {

    }


    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {

        if(!isOver){
            switch (pillarStates){
                case NORMAL:{
                    shapeRenderer.setColor(pillarColor);
                    break;
                }
                case SELECT:{
                    shapeRenderer.setColor(Contants.SELECT_COLOR);
                    break;
                }
                case PIVOT:{
                    shapeRenderer.setColor(Contants.PIVOT_COLOR);
                    break;
                }
                case MOVING1:{
                    break;
                }
                case MOVING2:{
                    break;
                }
                default:
                    break;
            }
        }else{
            shapeRenderer.setColor(Contants.FINISH_COLOR_1);
        }



        shapeRenderer.rect(x, y, Contants.RECTANGLE_WIDTH, height);



    }

    public int  getNumber(){return number;}
    public boolean isPivot(){return pivot;}
    public void setPivot(boolean pivot){this.pivot = pivot;}
    public Color getPillarColor(){return pillarColor;}
    public void setPillarColor(Color pillarColor){this.pillarColor = pillarColor;}

    public Enums.PillarStates getPillarStates(){return pillarStates;}
    public void setPillarStates(Enums.PillarStates  pillarStates){this.pillarStates = pillarStates;}

    public Enums.Stepping getStepping(){return stepping;}
    public void setStepping(Enums.Stepping stepping){this.stepping = stepping;}

    public boolean isOver(){return isOver;}
    public void setOver(boolean isOver){this.isOver = isOver;}



}
