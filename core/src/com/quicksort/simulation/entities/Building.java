package com.quicksort.simulation.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.quicksort.simulation.Level;
import com.quicksort.simulation.utils.AbstractEntity;
import com.quicksort.simulation.utils.Enums;

/**
 * Created by PEDRO on 11/11/2016.
 */
public class Building extends AbstractEntity{
    public static final String TAG = Building.class.getName();


    private Animation building;
    private Level level;


    private Enums.BuildingBackSprite sprite;

    private Vector2 velocity;

    private long startTIme;


    private Texture[] frames;

    public Building(float x, float y, Enums.ObjectID id, Enums.BuildingBackSprite sprite, Level level) {
        super(x, y, id);
        position = new Vector2();
        position.x = x;
        position.y = y;
        this.level = level;
        this.sprite = sprite;
        velocity = new Vector2();
        frames = new Texture[5];

        velocity.x = 0;
        velocity.y = 0;


        startTIme = TimeUtils.nanoTime();

        buildingSprites(sprite);




    }


    private void buildingSprites( Enums.BuildingBackSprite sprite){
        switch (sprite){
            case ONE:

                frames[0] = new Texture("building1.png");
                frames[1] = new Texture("building2.png");
                frames[2] = new Texture("building3.png");

                frames[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                frames[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                frames[2].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

                building = new Animation(.8f,
                        new TextureRegion(frames[0]),
                        new TextureRegion(frames[1]),
                        new TextureRegion(frames[2]));

                building.setPlayMode(Animation.PlayMode.LOOP);

                break;
            case TWO:

                frames[0]   = new Texture("buildingTree1.png");
                frames[1]   = new Texture("buildingTree2.png");

                frames[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                frames[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

                building = new Animation(.2f,
                        new TextureRegion( frames[0]),
                        new TextureRegion( frames[1]));

                building.setPlayMode(Animation.PlayMode.LOOP);

                break;
            case TREE:

                frames[0] = new Texture("buildingTwo.png");
                frames[1] = new Texture("building-Two-1.png");

                frames[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                frames[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

                building = new Animation(.8f,
                        new TextureRegion(frames[0]),
                        new TextureRegion(frames[1]));

                building.setPlayMode(Animation.PlayMode.LOOP);

                break;
            case FOUR:
                frames[0] = new Texture("building-new-1.png");
                frames[1] = new Texture("building-new-1.png");
                frames[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                frames[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                building = new Animation(.8f,
                        new TextureRegion(frames[0]),
                        new TextureRegion(frames[1]));

                building.setPlayMode(Animation.PlayMode.LOOP);
                break;
            case FIVE:
                frames[0]   = new Texture("building-new-2.png");
                frames[1]   = new Texture("building-new-2.png");
                frames[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                frames[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                building = new Animation(.8f,
                        new TextureRegion(frames[0]),
                        new TextureRegion(frames[1]));

                building.setPlayMode(Animation.PlayMode.LOOP);
                break;
            case SIX:
                frames[0]   = new Texture("building-new-3.png");
                frames[1]   = new Texture("building-new-3.png");
                frames[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                frames[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                building = new Animation(.8f,
                        new TextureRegion(frames[0]),
                        new TextureRegion(frames[1]));

                building.setPlayMode(Animation.PlayMode.LOOP);
                break;

            case SEVEN:
                frames[0]   = new Texture("building-new-4.png");
                frames[1]   = new Texture("building-new-4.png");
                frames[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                frames[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                building = new Animation(.8f,
                        new TextureRegion(frames[0]),
                        new TextureRegion(frames[1]));

                building.setPlayMode(Animation.PlayMode.LOOP);
                break;

            default:
                frames[0]   = new Texture("buildingTwo.png");
                break;

        }
    }




    @Override
    public void update(float delta, Enums.GameState gameState) {

    }

    @Override
    public void render(SpriteBatch batch) {
        final float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTIme);
        batch.draw(building.getKeyFrame(elapsedTime), position.x, position.y,
                building.getKeyFrame(elapsedTime).getRegionWidth() + 80,
                building.getKeyFrame(elapsedTime).getRegionHeight() + 100);


    }


    public Texture[] getFrames(){return frames;}

}
