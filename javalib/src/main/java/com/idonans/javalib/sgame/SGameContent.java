package com.idonans.javalib.sgame;

import java.awt.Point;

/**
 * Created by idonans on 2016/11/24.
 */
public class SGameContent {

    private static class InstanceHolder {
        private static final SGameContent sInstance = new SGameContent();
    }

    public static SGameContent getInstance() {
        return InstanceHolder.sInstance;
    }

    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 20;
    private final int[] mMap = new int[MAP_WIDTH * MAP_HEIGHT];

    private Cell mCell;

    private SGameContent() {
        init();
    }


    private void init() {
        int size = mMap.length;
        for (int i = 0; i < size; i++) {
            mMap[i] = 0;
        }

        mCell = new CellLine4(10, 0);
    }

    public void dispatch(SGameEngine.Step step) {
        Cell cell = mCell;
        if (cell == null || step == null) {
            return;
        }

        boolean updateView = false;

        switch (step.getArrow()) {
            case SGameEngine.Step.ARROW_UP: {
                Cell newCell = cell.next();
                if (newCell.match(mMap)) {
                    updateView = true;
                    mCell = newCell;
                }
            }
            break;
            case SGameEngine.Step.ARROW_LEFT: {
                int size = step.getSize();
                Cell newCell = cell;
                for (int i = 0; i < size; i++) {
                    newCell = newCell.moveLeft();
                    if (newCell.match(mMap)) {
                        updateView = true;
                        mCell = newCell;
                    } else {
                        break;
                    }
                }
            }
            break;
            case SGameEngine.Step.ARROW_RIGHT: {
                int size = step.getSize();
                Cell newCell = cell;
                for (int i = 0; i < size; i++) {
                    newCell = newCell.moveRight();
                    if (newCell.match(mMap)) {
                        updateView = true;
                        mCell = newCell;
                    } else {
                        break;
                    }
                }
            }
            break;
            case SGameEngine.Step.ARROW_DOWN: {
                int size = step.getSize();
                Cell newCell = cell;
                for (int i = 0; i < size; i++) {
                    newCell = newCell.moveDown();
                    if (newCell.match(mMap)) {
                        updateView = true;
                        mCell = newCell;
                    } else {
                        break;
                    }
                }
            }
            break;
            default:
                break;
        }

        if (updateView) {
            dispatchUpdate();
        }
    }

    private void dispatchUpdate() {
        GamePanel.getInstance().dispatchUpdate();
        ActionPanel.getInstance().dispatchUpdate();
    }

    public int[] getMap() {
        return mMap;
    }

    public Cell getCell() {
        return mCell;
    }

    public abstract static class Cell {

        protected final int mX;
        protected final int mY;
        protected final Point[] mPoints;

        protected Cell(int x, int y, Point[] points) {
            mX = x;
            mY = y;
            mPoints = points;
        }

        public int getX() {
            return mX;
        }

        public int getY() {
            return mY;
        }

        public Point[] getPoints() {
            return mPoints;
        }

        public abstract Cell next();

        public abstract Cell moveLeft();

        public abstract Cell moveRight();

        public abstract Cell moveDown();

        protected boolean match(int[] map) {
            int x;
            int y;
            for (Point point : mPoints) {
                x = mX + point.x;
                y = mY + point.y;
                if (x < 0 || y < 0 || x >= MAP_WIDTH || y >= MAP_HEIGHT
                        || map[x * MAP_WIDTH + y] != 0) {
                    return false;
                }
            }
            return true;
        }

    }

    public abstract static class DefaultMoveCell extends Cell {

        protected DefaultMoveCell(int x, int y, Point[] points) {
            super(x, y, points);
        }

        @Override
        public Cell moveDown() {
            return newInstance(getX(), getY() + 1, getPointsCopy());
        }

        @Override
        public Cell moveLeft() {
            return newInstance(getX() - 1, getY(), getPointsCopy());
        }

        @Override
        public Cell moveRight() {
            return newInstance(getX() + 1, getY(), getPointsCopy());
        }

        protected Point[] getPointsCopy() {
            Point[] points = getPoints();
            Point[] copy = new Point[points.length];
            for (int i = 0; i < points.length; i++) {
                copy[i] = new Point(points[i]);
            }
            return copy;
        }

        protected abstract DefaultMoveCell newInstance(int x, int y, Point[] points);

    }

    public static class CellOnePoint extends DefaultMoveCell {

        public CellOnePoint(int x, int y) {
            this(x, y, new Point[]{
                    new Point(0, 0)
            });
        }

        protected CellOnePoint(int x, int y, Point[] points) {
            super(x, y, points);
        }

        @Override
        protected DefaultMoveCell newInstance(int x, int y, Point[] points) {
            return new CellOnePoint(x, y, points);
        }

        @Override
        public Cell next() {
            return newInstance(getX(), getY(), getPointsCopy());
        }

    }

    public static class CellLine4 extends DefaultMoveCell {

        public CellLine4(int x, int y) {
            this(x, y, new Point[]{
                    new Point(0, 0),
                    new Point(1, 0),
                    new Point(2, 0),
                    new Point(3, 0)
            });
        }

        protected CellLine4(int x, int y, Point[] points) {
            super(x, y, points);
        }

        @Override
        protected DefaultMoveCell newInstance(int x, int y, Point[] points) {
            return new CellLine4(x, y, points);
        }

        @Override
        public Cell next() {
            if (mPoints[1].x == 1) {
                // 当前是水平的
                int x = mX + 2;
                int y = mY - 2;
                if (y < 0) {
                    y = 0;
                }
                if (y > MAP_HEIGHT - 4) {
                    y = MAP_HEIGHT - 4;
                }
                return newInstance(x, y, new Point[]{
                        new Point(0, 0),
                        new Point(0, 1),
                        new Point(0, 2),
                        new Point(0, 3)
                });
            } else {
                // 当前是垂直的
                int x = mX - 2;
                int y = mY + 2;
                if (x < 0) {
                    x = 0;
                }
                if (x > MAP_WIDTH - 4) {
                    x = MAP_WIDTH - 4;
                }
                return newInstance(x, y, new Point[]{
                        new Point(0, 0),
                        new Point(1, 0),
                        new Point(2, 0),
                        new Point(3, 0)
                });
            }
        }

    }

}