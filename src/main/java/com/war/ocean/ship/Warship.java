package com.war.ocean.ship;

import com.war.ocean.constant.ShipType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pradeep.
 */
public class Warship implements Battleship {

    private ShipType shipType;
    private Map<String, Integer> section;

    private Warship(ShipType shipType, Map<String, Integer> section) {
        this.shipType = shipType;
        this.section = section;
    }

    public boolean isAlive() {
        return section.entrySet().stream().anyMatch(section -> section.getValue() > 0);
    }

    public boolean isHit(String area) {
        boolean isHit = false;

        Integer power = this.section.get(area);
        if (power != null && power > 0) {
            isHit = true;
            this.section.put(area, --power);
        }

        return isHit;
    }

    public static class WarshipBuilder {

        String type;
        String dimension;
        String location;

        public WarshipBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public WarshipBuilder setDimension(String dimension) {
            this.dimension = dimension;
            return this;
        }

        public WarshipBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Warship build() {
            ShipType type = ShipType.valueOf(this.type);
            Map<String, Integer> section = buildShipSection();
            return new Warship(type, section);
        }

        private Map<String, Integer> buildShipSection() {
            Map<String, Integer> section = new HashMap<>();

            String[] dimensions = dimension.split(" ");
            int x = Integer.parseInt(dimensions[0]);
            int y = Integer.parseInt(dimensions[1]);

            char row = location.charAt(0);
            int column = location.charAt(1) - 48;

            Integer power = fetchPower(this.type);
            section.put(location, power);
            x--;
            while (x > 0) {
                column++;
                section.put(row + "" + column, power);
                x--;
            }

            y--;
            column = location.charAt(1) - 48;
            while (y > 0) {
                row++;
                section.put(row + "" + column, power);
                y--;
            }

            return section;
        }

        private Integer fetchPower(String type) {
            Integer power;

            ShipType shipType = ShipType.valueOf(type);

            switch (shipType) {
                case P:
                    power = 1;
                    break;
                case Q:
                    power = 2;
                    break;
                default:
                    power = -1;
                    break;
            }

            return power;
        }
    }
}
