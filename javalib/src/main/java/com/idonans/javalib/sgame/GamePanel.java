package com.idonans.javalib.sgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Created by idonans on 2016/11/24.
 */
public class GamePanel {

    private static class InstanceHolder {
        private static final GamePanel sInstance = new GamePanel();
    }

    public static GamePanel getInstance() {
        return InstanceHolder.sInstance;
    }

    private JPanel mPanel;

    private GamePanel() {
        WindowEnv windowEnv = WindowEnv.getInstance();
        mPanel = new Buffer((int) windowEnv.getGamePanelWidth(), (int) windowEnv.getGamePanelHeight());
    }

    public void attachTo(JFrame parent) {
        WindowEnv windowEnv = WindowEnv.getInstance();
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension((int) windowEnv.getGamePanelWidth() + 100, (int) windowEnv.getGamePanelHeight() + 100));
        panel.setBackground(Color.BLACK);
        parent.add(panel);
        panel.add(mPanel);
    }

    public void dispatchUpdate() {
        mPanel.repaint();
    }

    private class Buffer extends JPanel {

        private final int mWidth;
        private final int mHeight;
        private final int mCellWidth;
        private final int mCellHeight;
        private final BufferedImage mBufferedImage;

        private Buffer(int width, int height) {
            mWidth = width;
            mHeight = height;
            mCellWidth = (int) (1f * mWidth / SGameContent.MAP_WIDTH);
            mCellHeight = (int) (1f * mHeight / SGameContent.MAP_HEIGHT);
            mBufferedImage = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration()
                    .createCompatibleImage(width, height);
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBuffer();
            g.drawImage(mBufferedImage, 0, 0, null);
        }

        private void drawBuffer() {
            Graphics2D g = mBufferedImage.createGraphics();
            g.setBackground(Color.GREEN);
            g.clearRect(0, 0, mWidth, mHeight);

            SGameContent content = SGameContent.getInstance();

            g.setColor(Color.BLUE);
            SGameContent.Cell cell = content.getCell();
            if (cell != null) {
                Point[] points = cell.getPoints();
                if (points != null) {
                    int x;
                    int y;
                    for (Point p : points) {
                        x = (p.x + cell.getX()) * mCellWidth;
                        y = (p.y + cell.getY()) * mCellHeight;
                        g.drawRect(x, y, mCellWidth, mCellHeight);
                    }
                }
            }
        }

    }

}