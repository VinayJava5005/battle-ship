package com.war.ocean.user;

import com.war.ocean.ship.Battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pradeep.
 */
public class Player {

    private String name;
    private List<Battleship> battleships;


    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public List<Battleship> getBattleships() {
        if (battleships == null) {
            battleships = new ArrayList<>();
        }
        return battleships;
    }
}
