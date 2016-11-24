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
    private double mWidth;
    private double mHeight;

    private double mActionPanelWidth;
    private double mActionPanelHeight;

    private double mGamePanelWidth;
    private double mGamePanelHeight;

    private double mOffsetX;
    private double mOffsetY;

    private WindowEnv() {
        mToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = mToolkit.getScreenSize();

        mHeight = screenSize.getHeight() * 0.8;
        mWidth = mHeight * 3 / 4.0;

        mActionPanelWidth = mWidth;
        mActionPanelHeight = mActionPanelWidth / 3.0;

        mGamePanelWidth = mWidth;
        mGamePanelHeight = mGamePanelWidth;

        mOffsetX = (screenSize.getWidth() - mWidth) / 2.0;
        mOffsetY = (screenSize.getHeight() - mHeight) / 2.0;
    }

    public double getWidth() {
        return mWidth;
    }

    public double getHeight() {
        return mHeight;
    }

    public double getActionPanelWidth() {
        return mActionPanelWidth;
    }

    public double getActionPanelHeight() {
        return mActionPanelHeight;
    }

    public double getGamePanelWidth() {
        return mGamePanelWidth;
    }

    public double getGamePanelHeight() {
        return mGamePanelHeight;
    }

    public double getOffsetX() {
        return mOffsetX;
    }

    public double getOffsetY() {
        return mOffsetY;
    }

}