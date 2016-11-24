package com.idonans.javalib.sgame;

import com.idonans.javalib.sgame.util.TaskQueue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by idonans on 2016/11/24.
 */
public class SGameEngine {

    private static class InstanceHolder {
        private static final SGameEngine sInstance = new SGameEngine();
    }

    public static SGameEngine getInstance() {
        return InstanceHolder.sInstance;
    }

    private final Object STATUS_LOCK = new Object();
    private boolean mStart;
    private TaskQueue mTaskQueue;
    private CurrentKey mCurrentKey;
    private KeyListener mKeyListener;

    private SGameEngine() {
        mTaskQueue = new TaskQueue(1);
        mCurrentKey = new CurrentKey();
        mKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                mCurrentKey.setKeyEvent(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                mCurrentKey.setKeyEvent(null);
            }
        };
    }

    public void switchStatus() {
        synchronized (STATUS_LOCK) {
            mStart = !mStart;
            if (mStart) {
                enqueueTask();
            }
        }
    }

    private void enqueueTask() {
        mTaskQueue.enqueue(new Task());
    }

    private class Task implements Runnable {

        @Override
        public void run() {
            try {
                KeyProcessor keyProcessor = new KeyProcessor();
                while (true) {
                    synchronized (STATUS_LOCK) {
                        if (!mStart) {
                            break;
                        }
                    }

                    Step step = keyProcessor.next();
                    SGameContent.getInstance().dispatch(step);

                    try {
                        Thread.sleep(20);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public KeyListener getKeyListener() {
        return mKeyListener;
    }

    private class CurrentKey {

        private KeyEvent mKeyEvent;

        public void setKeyEvent(KeyEvent keyEvent) {
            mKeyEvent = keyEvent;
        }

        public KeyEvent getKeyEvent() {
            return mKeyEvent;
        }

    }

    private class KeyProcessor {

        private Step mLastStep;

        public Step next() {
            Step current = createStep(mCurrentKey.getKeyEvent());
            if (current == null) {
                mLastStep = null;
                return null;
            }

            if (mLastStep == null) {
                mLastStep = current;
                return current;
            }

            if (mLastStep.getArrow() != current.getArrow()) {
                mLastStep = current;
                return current;
            }

            switch (current.getArrow()) {
                case Step.ARROW_LEFT:
                case Step.ARROW_RIGHT:
                case Step.ARROW_DOWN:
                    mLastStep = createFasterStep(mLastStep);
                    return mLastStep;
            }

            mLastStep = current;
            return current;
        }

        private Step createFasterStep(Step step) {
            int size = step.getSize() * 2;
            return new Step(step.getArrow(), Math.min(size, Step.MAX_SIZE));
        }

        private Step createStep(KeyEvent keyEvent) {
            if (keyEvent == null) {
                return null;
            }

            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_UP:
                    return new Step(Step.ARROW_UP, Step.MIN_SIZE);
                case KeyEvent.VK_DOWN:
                    return new Step(Step.ARROW_DOWN, Step.MIN_SIZE);
                case KeyEvent.VK_LEFT:
                    return new Step(Step.ARROW_LEFT, Step.MIN_SIZE);
                case KeyEvent.VK_RIGHT:
                    return new Step(Step.ARROW_RIGHT, Step.MIN_SIZE);
                case KeyEvent.VK_SPACE:
                    return new Step(Step.ARROW_DOWN, Step.MAX_SIZE);
                default:
                    return null;
            }
        }

    }

    public static class Step {

        public static final int MIN_SIZE = 1;
        public static final int MAX_SIZE = 100;

        public static final int ARROW_UP = 1;
        public static final int ARROW_RIGHT = 2;
        public static final int ARROW_DOWN = 3;
        public static final int ARROW_LEFT = 4;

        private final int mArrow; // 移动的方向
        private final int mSize; // 指定方向上移动的大小

        public Step(int arrow, int size) {
            mArrow = arrow;
            mSize = size;
        }

        public int getArrow() {
            return mArrow;
        }

        public int getSize() {
            return mSize;
        }
    }

}