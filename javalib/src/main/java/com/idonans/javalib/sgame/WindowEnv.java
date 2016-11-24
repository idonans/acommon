package com.idonans.javalib.sgame;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Created by idonans on 2016/11/24.
 */
public class WindowEnv {

    private static class InstanceHolder {
        private static final WindowEnv sInstance = new WindowEnv();
    }

    public static WindowEnv getInstance() {
        return InstanceHolder.sInstance;
    }

    private final Toolkit mToolkit;
    private int mWidth;
    private int mHeight;

    private int mActionPanelWidth;
    private int mActionPanelHeight;

    private int mGamePanelWidth;
    private int mGamePanelHeight;

    private int mOffsetX;
    private int mOffsetY;

    private WindowEnv() {
        mToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = mToolkit.getScreenSize();

        mHeight = (int) (screenSize.getHeight() * 0.8);
        mWidth = (int) (mHeight * 3 / 4.0);

        mWidth = mWidth / SGameContent.MAP_WIDTH * SGameContent.MAP_WIDTH;

        mActionPanelWidth = mWidth;
        mActionPanelHeight = (int) (mActionPanelWidth / 3.0);

        mHeight = mWidth + mActionPanelHeight;

        mGamePanelWidth = mWidth;
        mGamePanelHeight = mWidth;

        mOffsetX = (screenSize.width - mWidth) / 2;
        mOffsetY = (screenSize.height - mHeight) / 2;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getActionPanelWidth() {
        return mActionPanelWidth;
    }

    public int getActionPanelHeight() {
        return mActionPanelHeight;
    }

    public int getGamePanelWidth() {
        return mGamePanelWidth;
    }

    public int getGamePanelHeight() {
        return mGamePanelHeight;
    }

    public int getOffsetX() {
        return mOffsetX;
    }

    public int getOffsetY() {
        return mOffsetY;
    }

}