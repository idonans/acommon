package com.idonans.javalib.sgame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Created by idonans on 2016/11/24.
 */
public class ActionPanel {

    private static class InstanceHolder {
        private static final ActionPanel sInstance = new ActionPanel();
    }

    public static ActionPanel getInstance() {
        return InstanceHolder.sInstance;
    }

    private JPanel mPanel;

    private ActionPanel() {
        mPanel = new JPanel();
        WindowEnv windowEnv = WindowEnv.getInstance();
        mPanel.setPreferredSize(new Dimension(windowEnv.getActionPanelWidth(), windowEnv.getActionPanelHeight()));
        mPanel.setBackground(Color.DARK_GRAY);
    }

    public void attachTo(JFrame parent) {
        parent.add(mPanel);
    }

    public void dispatchUpdate() {
        // TODO
    }

}