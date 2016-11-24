package com.idonans.javalib.sgame;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Created by idonans on 2016/11/24.
 */

public class SGame {

    private final JFrame mRoot;

    public SGame() {
        mRoot = new JFrame("SGame");
        mRoot.setResizable(false);
        mRoot.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 2));
        mRoot.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        WindowEnv windowEnv = WindowEnv.getInstance();
        mRoot.setBounds((int) windowEnv.getOffsetX(), (int) windowEnv.getOffsetY(), (int) windowEnv.getWidth(), (int) windowEnv.getHeight());

        ActionPanel.getInstance().attachTo(mRoot);
        GamePanel.getInstance().attachTo(mRoot);

        mRoot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SGameEngine.getInstance().switchStatus();
            }
        });
        mRoot.addKeyListener(SGameEngine.getInstance().getKeyListener());
    }

    public void start() {
        mRoot.setVisible(true);
    }

}
