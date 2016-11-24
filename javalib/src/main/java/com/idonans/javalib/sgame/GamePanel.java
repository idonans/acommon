package com.idonans.javalib.sgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
        mPanel = new Buffer(windowEnv.getGamePanelWidth(), windowEnv.getGamePanelHeight());
    }

    public void attachTo(JFrame parent) {
        parent.add(mPanel);
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

        private final Color mBackgroundColor = Color.WHITE;
        private final Color mFillColor = Color.GRAY;
        private final Color mStrokeColor = Color.BLACK;

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
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBuffer();
            g.drawImage(mBufferedImage, 0, 0, null);
        }

        private void drawBuffer() {
            Graphics2D g = mBufferedImage.createGraphics();
            g.setBackground(mBackgroundColor);
            g.clearRect(0, 0, mWidth, mHeight);

            SGameContent content = SGameContent.getInstance();

            SGameContent.Cell cell = content.getCell();
            if (cell != null) {
                Point[] points = cell.getPoints();
                if (points != null) {
                    int x;
                    int y;
                    for (Point p : points) {
                        x = (p.x + cell.getX()) * mCellWidth;
                        y = (p.y + cell.getY()) * mCellHeight;
                        g.setColor(mFillColor);
                        g.fillRect(x, y, mCellWidth - 1, mCellHeight - 1);
                        g.setColor(mStrokeColor);
                        g.drawRect(x, y, mCellWidth - 1, mCellHeight - 1);
                    }
                }
            }
        }

    }

}