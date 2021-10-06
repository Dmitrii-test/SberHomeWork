package ru.dmitrii.gof;

public enum Orientation {
    NORTH ("Север") {
        @Override
        public Orientation orientations() {
            return EAST;
        }

        @Override
        public int[] move(int[] p) {
            return new int[]{p[0], p[1] + 1};
        }
    },

    WEST ("Запад") {
        @Override
        public Orientation orientations() {
            return NORTH;
        }

        @Override
        public int[] move(int[] p) {
            return new int[] { p[0] - 1, p[1]};
        }
    },

    SOUTH ("Юг") {
        @Override
        public Orientation orientations() {
            return WEST;
        }

        @Override
        public int[] move(int[] p) {
            return new int[]{ p[0], p[1] - 1 };
        }
    },
    EAST ("Восток") {
        @Override
        public Orientation orientations() {
            return SOUTH;
        }

        @Override
        public int[] move(int[] p) {
            return new int[]{p[0] + 1, p[1]};
        }
    };
    public abstract Orientation orientations();
    public abstract int[] move(int[] p);
    public String name;

    Orientation(String name) {
        this.name = name;
    }
}
