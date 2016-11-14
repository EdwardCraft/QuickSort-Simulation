package com.quicksort.simulation.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.quicksort.simulation.Level;
import com.quicksort.simulation.utils.Contants;

/**
 * Created by PEDRO on 13/11/2016.
 */
public class OnscreenControls extends InputAdapter{
    public static final String TAG = OnscreenControls.class.getName();

    public final Viewport viewport;
    private Level level;

    private Vector2 newCenter;
    private Texture newSortButton;
    SpriteBatch batchB;

    public OnscreenControls(Level level){
        this.level = level;
        this.viewport = new ExtendViewport(
                Contants.ONSCREEN_CONTROLS_VIEWPORT_SIZE,
                Contants.ONSCREEN_CONTROLS_VIEWPORT_SIZE
        );

        newSortButton = new Texture(Contants.NEW_SORT_BUTTON);
        newSortButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        newCenter = new Vector2();
        batchB = new SpriteBatch();
        recalculateButtonPositions();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if (viewportPosition.dst(newCenter) < Contants.BUTTON_RADIUS) {

            level.setPillars();

        }

        return super.touchDown(screenX, screenY, pointer, button);
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));



        return super.touchDragged(screenX, screenY, pointer);
    }

    public void render(SpriteBatch batch){
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.draw(newSortButton, newCenter.x - Contants.BUTTON_CENTER.x,
                newCenter.y - Contants.BUTTON_CENTER.y);

    }





    public void recalculateButtonPositions() {
        //moveLeftCenter.set(Constants.BUTTON_RAIDUS * 3 / 4, Constants.BUTTON_RAIDUS);
        //moveRightCenter.set(Constants.BUTTON_RAIDUS * 2, Constants.BUTTON_RAIDUS * 3 / 4);
        newCenter.set(
                viewport.getWorldWidth() - Contants.BUTTON_RADIUS * 2f,
                Contants.BUTTON_RADIUS * 3 / 4
        );
        /*jumpCenter.set(
                viewport.getWorldWidth() - Constants.BUTTON_RAIDUS * 3 / 4,
                Constants.BUTTON_RAIDUS
        );*/
    }








}
