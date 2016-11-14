package com.quicksort.simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.quicksort.simulation.entities.Building;
import com.quicksort.simulation.entities.Pig;
import com.quicksort.simulation.entities.Rectangle;
import com.quicksort.simulation.overlays.OnscreenControls;
import com.quicksort.simulation.utils.Contants;
import com.quicksort.simulation.utils.Enums;
import com.sun.org.apache.bcel.internal.Constants;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.sql.Time;
import java.util.LinkedList;

import sun.rmi.runtime.Log;

/**
 * Created by PEDRO on 11/11/2016.
 */
public class Level extends ScreenAdapter {

    public static final  String TAG = Screen.class.getName();

    // let's be careful width this !!
    public static Enums.Sortstate sortstate;
    public static Enums.PillarStates pillarStates;

    public Pig pig;

    private BitmapFont font;

    private Enums.Stepping stepping;
    private Enums.Down down;


    private OrthographicCamera camera;
    OnscreenControls onscreenControls;

    private SpriteBatch batch;
    private ShapeRenderer rec;
    private Texture background;
    private Texture street;

    private Array<Building> buildings;
    private Enums.GameState gameState;

    private Array<Rectangle> pillars;

    private int[] randNumber;
    private int tempNumber;
    private int position;
    private int listSize;


    private int first;
    private int last;
    private int middle;
    private int pivot;
    private int pivotType;
    private int i;
    private int j;

    private int iR;
    private int jR;

    private long  startTime;
    private float  timeLimit;

    private boolean getTime;
    private boolean setIandJ;

    private boolean[] state;

    private LinkedList<String> tempIndex;
    private LinkedList<String> tempJIndex;

    private boolean isOver;
    private boolean[] colors;

    private int score;
    private int change;


    @Override
    public void show() {
        sortstate = Enums.Sortstate.IDLE;
        pillarStates = Enums.PillarStates.IDLE;
        pig = new Pig();

        font = new BitmapFont();
        font.getData().setScale(1);
        font.setColor(Color.WHITE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(0.7f, 0.7f);


        score = 0;
        change = 0;

        isOver = false;
        colors = new boolean[6];

        for(int l = 0; l < colors.length; l++){
            colors[l] = false;

        }
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Contants.APP_WIDTH, Contants.APP_HEIGHT);
        pillars = new Array<Rectangle>();
        state = new boolean[10];

        randNumber = new int[12];
        tempNumber = 0;
        listSize = 12;
        position = 0;
        pivotType = 0;
        for(int i = 0; i < randNumber.length; i++){
            randNumber[i] = 0;
        }

        for(int j = 0 ; j < state.length; j++){
            state[j] = false;
        }

        buildings = new Array<Building>();
        gameState = Enums.GameState.Start1;
        down = Enums.Down.IDLE;
        rec = new ShapeRenderer();
        rec.setProjectionMatrix(camera.combined);

        onscreenControls = new OnscreenControls(this);
        tempIndex = new LinkedList<String>();
        tempJIndex = new LinkedList<String>();
        Gdx.input.setInputProcessor(onscreenControls);

        setSprites();
        setBuildings();
        //setPillars();

        getTime = false;
        startTime = 0;
        timeLimit = 0.5f;
        setIandJ = false;

    }

    private void readyQuickSort(){
        first = 0;
        last = pillars.size - 1;
        tempIndex.clear();
        tempJIndex.clear();
        score = 0;
        change = 0;
        isOver = false;
        for(int l = 0; l < colors.length; l++){
            colors[l] = false;
        }
        pig.setX(Contants.PIG_START_X);
        pig.setIdleState(false);
        setControllers();
        startTime = System.currentTimeMillis();
        pillarStates  = Enums.PillarStates.MOVING1;
        i = first;
        j = last;
        for(int l = 0; l < state.length; l++)
            state[l] = false;
        for(int l = 0; l < pillars.size; l++){
            System.out.println("["+ l +"]" + "Number "+ pillars.get(l).getNumber());
        }
        sortstate = Enums.Sortstate.RUN;

    }

    private void quickSort(float delta){


        if(i <= j ){
            if(pillarStates  == Enums.PillarStates.MOVING1){
                long elapseTime = (System.currentTimeMillis() - startTime) / 1000;
                if((float)elapseTime > timeLimit ){
                    if(pillars.get(i).getNumber()  < pivot){
                        i++;
                        score++;
                        startTime = System.currentTimeMillis();
                    }else{
                        pillars.get(i).setPillarStates(Enums.PillarStates.SELECT);
                        pillarStates  = Enums.PillarStates.MOVING2;
                    }

                }
            }else if(pillarStates  == Enums.PillarStates.MOVING2){
                long elapseTime = (System.currentTimeMillis() - startTime) / 1000;
                if((float)elapseTime > timeLimit ){
                    if(pillars.get(j).getNumber()  >  pivot){
                        j--;
                        score++;
                        startTime = System.currentTimeMillis();
                    }else{
                        pillars.get(j).setPillarStates(Enums.PillarStates.SELECT);
                        pillarStates  = Enums.PillarStates.STEPPING;
                        stepping = Enums.Stepping.ONE;
                    }

                }
            }else if(pillarStates  == Enums.PillarStates.STEPPING) {

                if (i <= j) {
                    if (stepping == Enums.Stepping.ONE) {
                        long elapseTime = (System.currentTimeMillis() - startTime) / 1000;
                        if ((float) elapseTime > timeLimit) {
                            if (!state[0]) {
                                pillars.get(j).setStepping(Enums.Stepping.ONE);
                                stepping = Enums.Stepping.SECOND;
                                startTime = System.currentTimeMillis();
                                state[0] = true;
                            }
                        }
                    } else if (stepping == Enums.Stepping.SECOND) {

                        long elapseTime = (System.currentTimeMillis() - startTime) / 1000;
                        if ((float) elapseTime > timeLimit) {
                            if (!state[1]) {
                                pillars.get(i).setStepping(Enums.Stepping.ONE);
                                state[1] = true;
                            }

                            if (pillars.get(i).getStepping() == Enums.Stepping.IDLE) {
                                stepping = Enums.Stepping.CHANGE;
                            }
                        }
                    } else if (stepping == Enums.Stepping.CHANGE) {

                        if (!state[2]) {
                            Rectangle tmp;
                            tmp = pillars.get(i);
                            float x = tmp.getX();

                            pillars.get(i).setX(pillars.get(j).getX());
                            pillars.get(j).setX(x);

                            pillars.set(i, pillars.get(j));
                            pillars.set(j, tmp);

                            down = Enums.Down.DOWN1;
                            startTime = System.currentTimeMillis();
                            change++;
                            state[2] = true;
                        }


                        if (down == Enums.Down.DOWN1) {
                            long elapseTime = (System.currentTimeMillis() - startTime) / 1000;
                            if ((float) elapseTime > timeLimit) {
                                if (!state[3]) {
                                    pillars.get(j).setStepping(Enums.Stepping.SECOND);
                                    down = Enums.Down.DOWN2;
                                    startTime = System.currentTimeMillis();
                                    state[3] = true;
                                }
                            }
                        } else if (down == Enums.Down.DOWN2) {
                            long elapseTime = (System.currentTimeMillis() - startTime) / 1000;
                            if ((float) elapseTime > timeLimit) {
                                if (!state[4]) {
                                    pillars.get(i).setStepping(Enums.Stepping.SECOND);
                                    state[4] = true;
                                }

                                if (pillars.get(i).getStepping() == Enums.Stepping.IDLE) {
                                    pillars.get(i).setPillarStates(Enums.PillarStates.NORMAL);
                                    pillars.get(j).setPillarStates(Enums.PillarStates.NORMAL);

                                    for(int l = 0; l < pillars.size; l++){
                                        if(pillars.get(l).getNumber() == pivot){
                                            pillars.get(l).setPillarStates(Enums.PillarStates.PIVOT);
                                        }
                                    }

                                    for(int l = 0; l < state.length; l++)
                                        state[l] = false;

                                    startTime = System.currentTimeMillis();
                                    i++;
                                    j--;
                                    pillarStates  = Enums.PillarStates.MOVING1;
                                }
                            }
                        }

                    }
                }

            }else{
                System.out.println(" Finish!!! ");
                if(!isOver){
                    for(int l = 0; l < pillars.size; l++){
                        pillars.get(l).setOver(true);
                        pillars.get(l).setY(Contants.RECTANGLE_POSITION_NEW_Y);
                    }
                    pig.setIdleState(true);
                    isOver = true;
                }
            }

        }else{
            if(i >= 0){
                pillars.get(i).setPillarStates(Enums.PillarStates.NORMAL);
            }else{
                pillars.get(0).setPillarStates(Enums.PillarStates.NORMAL);
            }

            if(j >= 0){
                pillars.get(j).setPillarStates(Enums.PillarStates.NORMAL);
            }else{
                pillars.get(0).setPillarStates(Enums.PillarStates.NORMAL);
            }


            for(int l = 0; l < pillars.size; l++){
                if(pillars.get(l).getNumber() == pivot){
                    pillars.get(l).setPillarStates(Enums.PillarStates.PIVOT);
                }
            }

            for(int l = 0; l < state.length; l++)
                state[l] = false;

            if(first < j){
                startTime = System.currentTimeMillis();
                tempIndex.push(Integer.toString(i));
                tempJIndex.push(Integer.toString(last));
                i = first;
                last = j;
                setPivot();
                pillarStates  = Enums.PillarStates.MOVING1;

            }else{
                if(tempIndex.size() != 0)
                    i   = Integer.parseInt(tempIndex.pop());
                if(tempJIndex.size() != 0)
                    last  = Integer.parseInt(tempJIndex.pop());

                j  = last;
                first = i;
                if(!state[6]){
                    System.out.println("i ->" + i);
                    System.out.println("last ->"+ last);
                    state[6] = true;
                }

                if((i < last)){
                    setPivot();
                    pillarStates  = Enums.PillarStates.MOVING1;
                }else{
                    pillarStates  = Enums.PillarStates.NORMAL;
                }

            }



        }




    }


    public void testValues(){

        int rand_test  = MathUtils.random(1 , 5);

        switch (rand_test){
            case 1:{
                int k = 0;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        12, 12 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        9, 9 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        4, 4 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        10, 10 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        1, 1 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        6, 6 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        3, 3 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        8, 8 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        11, 11 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        7, 7 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        5, 5 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        2, 2 * Contants.RECTANGLE_HEIGHT ));
                k++;
                break;
            }
            case 2:{
                int k = 0;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        5, 5 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        1, 1 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        4, 4 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        11, 11 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        2, 2 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        6, 6 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        12, 12 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        7, 7 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        10, 10 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        3, 3 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        9, 9 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        8, 8 * Contants.RECTANGLE_HEIGHT ));
                k++;

                break;
            }
            case 3:{
                int k = 0;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        1, 1 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        7, 7 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        6, 6 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        10, 10 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        8, 8 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        2, 2 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        3, 3 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        5, 5 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        11, 11 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        12, 12 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        9, 9 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        4, 4 * Contants.RECTANGLE_HEIGHT ));
                k++;

                break;
            }
            case 4:{
                int k = 0;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        7, 7 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        3, 3 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        2, 2 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        4, 4 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        1, 1 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        6, 6 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        12, 12 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        8, 8 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        11, 11 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        10, 10 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        9, 9 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        5, 5 * Contants.RECTANGLE_HEIGHT ));
                k++;

                break;
            }
            case 5:{
                int k = 0;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        12, 12 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        9, 9 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        6, 6 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        11, 11 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        8, 8 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        4, 4 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        3, 3 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        2, 2 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        5, 5 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        1, 1 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        10, 10 * Contants.RECTANGLE_HEIGHT ));
                k++;
                pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                        Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                        7, 7 * Contants.RECTANGLE_HEIGHT ));
                k++;
                break;
            }
            default: break;
        }


    }


    private void setSprites(){
        background = new Texture(Contants.BACKGROUND_IMAGE);
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        street = new Texture(Contants.STREET_IMAGE);
        street.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    }

    private void setBuildings(){
        buildings.add(new Building( 50, -5,
                Enums.ObjectID.Building, Enums.BuildingBackSprite.ONE, this));

        buildings.add(new Building(170 + 150,-5,
                Enums.ObjectID.Building, Enums.BuildingBackSprite.SIX, this));

        buildings.add(new Building(240 + 170 + 160, -5,
                Enums.ObjectID.Building, Enums.BuildingBackSprite.TWO, this));

        buildings.add(new Building(310 + 240 + 170 + 200, -5,
                Enums.ObjectID.Building, Enums.BuildingBackSprite.FOUR, this));
    }


    public void setPillars(){

        pillars.clear();
        position = 0;
        for(int k = 0; k < randNumber.length; k++ )
            randNumber[k] = 0;

        testValues();
        /*int tempSize =  0;
        for(int k = 0; k < listSize; k++){
            tempSize =  getRandNumber(0);
            pillars.add(new Rectangle( Contants.RECTANGLE_POSITION_X + (k * Contants.RECTANGLE_X_OFFSET),
                    Contants.RECTANGLE_POSITION_Y, Enums.ObjectID.Pillar,
                    tempSize, tempSize * Contants.RECTANGLE_HEIGHT ));
        }*/

       /* for(int k = 0; k < pillars.size; k++){
            System.out.println("position-> " + k + " number"+ pillars.get(k).getNumber());
        }*/
        readyQuickSort();

    }

    private int getRandNumber(int index){

        if(index == randNumber.length){
            randNumber[position] = tempNumber;
            position++;
            return tempNumber;
        }else{

            if(index == 0){
                tempNumber = MathUtils.random(1 , 12);
            }

            if(randNumber[index] != tempNumber){
                return  getRandNumber( ++index );
            }else{
                return getRandNumber( 0 );
            }
        }

    }

    private void setControllers(){

        setPivot();



    }


    private void setPivot(){

        switch (pivotType){
            case 0:{
                middle = (first + last) / 2;
                pivot = pillars.get(middle).getNumber();
                pillars.get(middle).setPillarStates(Enums.PillarStates.PIVOT);
                System.out.println("pivot -> " + pivot);
                break;
            }
            case 1:{

                break;
            }
            case 2:{

                break;
            }
            default:{
                break;
            }
        }

    }


    private void update(float delta){
        camera.update();

        if(sortstate == Enums.Sortstate.RUN){
            quickSort(delta);


        }

        if(pillarStates  == Enums.PillarStates.STEPPING){
            for(int l = 0; l < pillars.size; l++){
                pillars.get(l).update(delta, gameState);
            }

        }

        pig.update(delta);





    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Contants.BACKGROUND_COLOR.r, Contants.BACKGROUND_COLOR.g, Contants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);


        for(Building b: buildings){
            b.update(delta, gameState);
        }

        batch.setProjectionMatrix(camera.combined);
        rec.begin(ShapeRenderer.ShapeType.Filled);
        batch.begin();


        batch.draw(background, 0, 0);
        for(Building b : buildings){
            b.render(batch);
        }
        batch.draw(street, 0, -10);

        for(Rectangle r: pillars){
            r.render(batch, rec);
        }




        onscreenControls.render(batch);

        final String hudString =
                Contants.HUD_SCORE_LABEL + score + "\n" +
                        Contants.HUD_CHANGE_LABEL + change;

        font.draw(batch, hudString, 10f , camera.viewportHeight - 505);


        pig.render(batch);


        batch.end();
        rec.end();



    }

    @Override
    public void resize(int width, int height) {
        camera.update();
        onscreenControls.viewport.update(width, height, true);
        onscreenControls.recalculateButtonPositions();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        street.dispose();
        font.dispose();
    }
}
