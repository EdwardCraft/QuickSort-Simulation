package com.quicksort.simulation.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.quicksort.simulation.utils.Contants;
import com.quicksort.simulation.utils.Enums;

/**
 * Created by PEDRO on 14/11/2016.
 */
public class Pig {


    private Texture[] frames;
    private float stateTime;
    private float runningStartTime;
    private float idle;
    private Animation runningAnimation;
    private Animation isRunningAnimation;

    private float x;
    private float y;

    private float velocityX;


    private boolean idleState;

    public Pig() {

        Animations();
    }

    private void Animations(){
        frames = new Texture[16];
        for(int i = 0; i < 16; i++){
            frames[i] = new Texture("waking/p-" +(i + 1)+ ".png");
            frames[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        }

        runningAnimation = new Animation(Contants.RUNNER_WALK_ANIMATION_SPEED,
                new TextureRegion(frames[0]),new TextureRegion(frames[1]),new TextureRegion(frames[2]),
                new TextureRegion(frames[3]),new TextureRegion(frames[4]), new TextureRegion(frames[5]),
                new TextureRegion(frames[6]),new TextureRegion(frames[7]),  new TextureRegion(frames[8]),
                new TextureRegion(frames[9]),new TextureRegion(frames[10]),new TextureRegion(frames[11]),
                new TextureRegion(frames[12]),new TextureRegion(frames[13]),new TextureRegion(frames[14]),
                new TextureRegion(frames[15])
        );
        runningAnimation.setPlayMode(Animation.PlayMode.LOOP);





        for(int i = 0; i < 10; i++){
            frames[i] = new Texture("running/p-" +(i + 1)+ ".png");
            frames[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        }

        isRunningAnimation = new Animation(Contants.RUNNER_RUNNING_ANIMATION_SPEED,
                new TextureRegion(frames[0]),new TextureRegion(frames[0]),new TextureRegion(frames[0]),
                new TextureRegion(frames[1]),new TextureRegion(frames[2]),
                new TextureRegion(frames[3]),new TextureRegion(frames[4]), new TextureRegion(frames[5]),
                new TextureRegion(frames[6]),new TextureRegion(frames[7]),  new TextureRegion(frames[8]),
                new TextureRegion(frames[9])
        );
        isRunningAnimation.setPlayMode(Animation.PlayMode.LOOP);


        stateTime = 0f;
        runningStartTime = 0f;
        idle = 0f;
        idleState = false;
        x = Contants.PIG_START_X;
        y = Contants.PIG_START_Y;
        velocityX = Contants.RUNNING__SPEED;
    }


    public void update(float delta) {

        if(idleState){
            if(x <= Contants.APP_WIDTH + Contants.RUNNER_WIDTH){
                x += velocityX * delta;
            }
        }

    }

    public void render(SpriteBatch batch) {

            if(!idleState){
                idle += Gdx.graphics.getDeltaTime();
                batch.draw(runningAnimation.getKeyFrame(idle), x, y,
                        -Contants.RUNNER_WIDTH, Contants.RUNNER_HEIGHT);
            }else{
                runningStartTime += Gdx.graphics.getDeltaTime();
                batch.draw(isRunningAnimation.getKeyFrame(runningStartTime), x, y,
                        -(Contants.RUNNER_WIDTH - 5), Contants.RUNNER_HEIGHT - 2);
            }



    }

    public void setIdleState(boolean idleState){this.idleState = idleState;}
    public void setX(float x){this.x = x;}

}
