package com.quicksort.simulation.utils;

/**
 * Created by PEDRO on 11/11/2016.
 */
public class Enums {

    public enum BuildingBackSprite{
        ONE, TWO, TREE, FOUR, FIVE, SIX, SEVEN;
    }

    public enum ObjectID{
        Player(),
        Building(),
        Pillar(),
        Enemy();

    }

    public enum GameState {
        Start1,Start2, Running, GameOver, Wait
    }

    public enum Sortstate{
        IDLE, STARTING, RUN, STEPPING, PAUSED
    }

    public enum PillarStates{
        SELECT, PIVOT, NORMAL, MOVING1, MOVING2, STEPPING, IDLE
    }

    public enum Stepping{
        ONE,SECOND,CHANGE,IDLE, DOWN1, DOWN2
    }

    public enum Down{
        DOWN1, DOWN2, IDLE
    }



}
