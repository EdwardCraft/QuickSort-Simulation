package com.quicksort.simulation.utils;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by PEDRO on 11/11/2016.
 */
public class Contants {

    public static final int APP_WIDTH = 1200;
    public static final int APP_HEIGHT = 700;
    public static final Color BACKGROUND_COLOR = Color.BLACK;

    public static final String HUD_SCORE_LABEL = "Comparaciones: ";
    public static final String HUD_CHANGE_LABEL = "Intercambios: ";
    public static final float HUD_VIEWPORT_SIZE = 480;


    //pig
    public static final float PIG_START_X = 50f;
    public static final float PIG_START_Y = 40f;
    public static final float RUNNER_WIDTH = 35f;
    public static final float RUNNER_HEIGHT = 25f;
    public static final float RUNNER_WALK_ANIMATION_SPEED = 0.03f;
    public static final float RUNNER_JUMP_ANIMATION_SPEED = 0.03f;
    public static final float RUNNER_LAND_ANIMATION_SPEED = 0.03f;
    public static final float RUNNER_RUNNING_ANIMATION_SPEED = 0.03f;

    public static final float RUNNING__SPEED = 70f;


    //Rectangle
    public static final float RECTANGLE_POSITION_X = 200f;
    public static final float RECTANGLE_POSITION_Y = 140f;
    public static final float RECTANGLE_POSITION_NEW_Y = 120f;
    public static final float RECTANGLE_VELOCITY_X = 100f;
    public static final float RECTANGLE_VELOCITY_Y = 100f;



    //State Colors
    public static final Color PILLAR_COLOR = new Color(0, 1, 0, 1);
    public static final Color PIVOT_COLOR = new Color(1, 0, 0, 1);
    public static final Color SELECT_COLOR = new Color(0, 0, 1, 1);

    //Finish
    public static final Color FINISH_COLOR_1 = new Color(202f / 255f, 58f / 255f, 58f / 255f, 1f);


    public static final float RECTANGLE_WIDTH = 40;
    public static final float RECTANGLE_HEIGHT = 20;
    public static final float RECTANGLE_X_OFFSET = RECTANGLE_WIDTH + 30;

    //sprites
    public static final String BACKGROUND_IMAGE = "background.png";
    public static final String STREET_IMAGE = "street.png";


    //Onscreen Controls
    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 200;
    public static final String NEW_SORT_BUTTON = "button-new-sort.png";
    public static final String MOVE_LEFT_BUTTON = "button-move-left";
    public static final String MOVE_RIGHT_BUTTON = "button-move-right";
    public static final String SHOOT_BUTTON = "button-shoot";
    public static final String JUMP_BUTTON = "button-jump";
    public static final Vector2 BUTTON_CENTER = new Vector2(22, 22);
    public static final float BUTTON_RADIUS = 32;



}
