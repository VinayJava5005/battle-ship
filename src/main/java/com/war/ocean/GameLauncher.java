package com.war.ocean;

import com.war.ocean.constant.ShipType;
import com.war.ocean.ship.Warship;
import com.war.ocean.user.Player;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by pradeep.
 */
public class GameLauncher {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter area boundaries : ");
        String area = scanner.nextLine();

        if (!isValidArea(area)) {
            System.out.println("Given width height of battle area : " + area);
            System.out.println("1 <= Width of Battle area (M’) <= 9, A <= Height of Battle area (N’) <= Z");
            System.exit(1);
        }

        Player player1 = new Player()
                .setName("Player-1");

        Player player2 = new Player()
                .setName("Player-2");


        for (int index = 1; index <= 2; index++) {
            System.out.print("Type for battleship " + index + " : ");
            String type = scanner.nextLine();

            if (!isValidType(type)) {
                System.out.println("Given ship type : " + type);
                System.out.println("Type of ship should be : " + Arrays.asList(ShipType.values()));
                System.exit(0);
            }

            System.out.print("Dimension for battleship " + index + " : ");
            String dimension = scanner.nextLine();

            if (!isValidDimension(dimension)) {
                System.out.println("Given dimension : " + dimension);
                System.out.println("1 <= Width of Battle area (M’) <= 9, 1 <= Height of Battle area (N’) <= 26");
                System.exit(1);
            }

            System.out.print("Location of battleship " + index + " for player 1 : ");
            String locationA = scanner.nextLine();

            if (!isValidLocation(locationA, dimension, area)) {
                System.out.println("Given location " + locationA + " with dimension " + dimension + " is outside battle area " + area);
                System.exit(1);
            }

            System.out.print("Location of battleship " + index + " for player 2 : ");
            String locationB = scanner.nextLine();
            if (!isValidLocation(locationB, dimension, area)) {
                System.out.println("Given location " + locationB + " with dimension " + dimension + " is outside battle area " + area);
                System.exit(1);
            }

            Warship aWarship = new Warship.WarshipBuilder()
                    .setType(type)
                    .setDimension(dimension)
                    .setLocation(locationA)
                    .build();
            player1.getBattleships().add(aWarship);


            Warship bWarship = new Warship.WarshipBuilder()
                    .setType(type)
                    .setDimension(dimension)
                    .setLocation(locationB)
                    .build();

            player2.getBattleships().add(bWarship);

        }
        System.out.print("Missile targets for user 1 : ");
        String player1Targets = scanner.nextLine();

        System.out.print("Missile targets for user 2 : ");
        String player2Targets = scanner.nextLine();

        BattleShipGame battleShipGame = new BattleShipGame(player1, player1Targets, player2, player2Targets);
        battleShipGame.start();
    }

    private static boolean isValidLocation(String locationA, String dimension, String area) {
        String dimensions[] = dimension.split(" ");

        boolean result = false;
        try {
            String[] areas = area.split(" ");
            int M = Integer.parseInt(areas[0]);
            char N = areas[1].charAt(0);

            int x = Integer.parseInt(dimensions[0]);
            int y = Integer.parseInt(dimensions[1]);

            char row = locationA.charAt(0);
            int col = locationA.charAt(1) - 48;


            result = col <= M
                    && row <= N
                    && (row + (y - 1)) <= 90
                    && (row + (y - 1)) >= 65
                    && (col + (x - 1)) <= 9
                    && (col + (x - 1)) >= 1;
        } catch (NumberFormatException e) {

        }

        return result;
    }

    private static boolean isValidDimension(String dimension) {
        String dimensions[] = dimension.split(" ");

        boolean result = false;
        try {
            int x = Integer.parseInt(dimensions[0]);
            int y = Integer.parseInt(dimensions[1]);

            result = x >= 1 && x <= 9 && y >= 1 && y <= 26;
        } catch (NumberFormatException e) {

        }

        return result;
    }

    private static boolean isValidType(String type) {
        boolean isValid = false;
        try {
            ShipType.valueOf(type);
            isValid = true;
        } catch (IllegalArgumentException e) {

        }

        return isValid;
    }

    private static boolean isValidArea(String area) {
        String[] dimensions = area.split(" ");

        boolean result = false;
        try {
            int M = Integer.parseInt(dimensions[0]);
            char N = dimensions[1].charAt(0);

            result = M >= 1 && M <= 9 && N >= 65 && N <= 90;
        } catch (NumberFormatException e) {

        }

        return result;
    }
}
