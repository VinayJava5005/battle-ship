package com.war.ocean;

import com.war.ocean.ship.Battleship;
import com.war.ocean.user.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pradeep.
 */
public class BattleShipGame {

    private Player player1;
    private Player player2;
    private List<String> player1Targets;
    private List<String> player2Targets;


    public BattleShipGame(Player player1, String player1Targets, Player player2, String player2Targets) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Targets = new LinkedList<>();
        for (String target : player1Targets.split(" ")) {
            this.player1Targets.add(target);
        }
        this.player2Targets = new LinkedList<>();
        for (String target : player2Targets.split(" ")) {
            this.player2Targets.add(target);
        }
    }

    public void start() {

        Player attacker = player1;
        Player enemy = player2;
        List<String> targets = player1Targets;
        int playerNumber = 1;

        while (true) {

            boolean isHit = attack(enemy, targets.get(0));
            String target = targets.remove(0);

            System.out.println(attacker.getName() + " fires a missile with target " + target + " which " + (isHit ? "hit" : "missed"));

            if (!isHit) {
                if (playerNumber == 1) {
                    if (!hasTargetLeft(player2Targets)) {
                        System.out.println(player2.getName() + " has no more missiles left");
                        playerNumber = 1;
                    } else {
                        playerNumber = 2;
                    }
                } else {
                    if (!hasTargetLeft(player1Targets)) {
                        System.out.println(player1.getName() + " has no more missiles left");
                        playerNumber = 2;
                    } else {
                        playerNumber = 1;
                    }
                }
                attacker = playerNumber == 1 ? player1 : player2;
                enemy = playerNumber == 1 ? player2 : player1;
                targets = playerNumber == 1 ? player1Targets : player2Targets;
            }

            if (!hasShipLeft(player1)) {
                System.out.println(player2.getName() + " won the battle");
                break;
            }
            if (!hasShipLeft(player2)) {
                System.out.println(player1.getName() + " won the battle");
                break;
            }


            if (!hasTargetLeft(player1Targets) && !hasTargetLeft(player2Targets)) {
                System.out.println("No arms left for battle");
                break;
            }

        }
    }

    private boolean hasTargetLeft(List<String> player) {
        return player.size() > 0;
    }

    private boolean hasShipLeft(Player player) {
        return player.getBattleships()
                .stream()
                .filter(b -> b.isAlive())
                .findFirst()
                .orElse(null) == null ? false : true;

    }

    private boolean attack(Player enemy, String area) {

        boolean isHit = false;
        for (Battleship battleship : enemy.getBattleships()) {
            isHit = battleship.isHit(area);

            if (isHit) {
                break;
            }
        }

        return isHit;
    }
}
