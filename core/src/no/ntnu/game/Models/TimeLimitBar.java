package no.ntnu.game.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Shape;

public class TimeLimitBar {

    private float maxTimeLimit;
    private float currentTime;
    private float barWidth;
    private float barHeight;
    private float barX;
    private float barY;
    private float barWidthPerSecond;

    public TimeLimitBar(float currentTime, float maxTimeLimit, float width, float height, float x, float y) {
        this.currentTime = currentTime;
        this.maxTimeLimit = maxTimeLimit;
        barWidth = width;
        barHeight = height;
        barX = x;
        barY = y;
        barWidthPerSecond = barWidth / maxTimeLimit;
    }

    public float getCurrentTime() {
        return currentTime;
    }
    public void setMaxTimeLimit(float time) {
        this.maxTimeLimit = time;
    }

    public void updateTime(float deltaTime) {
        currentTime -= deltaTime;
        if (currentTime < 0) {
            currentTime = 0;
        }
    }

    public void resetTime() {
        currentTime = maxTimeLimit;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, currentTime * barWidthPerSecond, barHeight);
        shapeRenderer.end();
    }

    public boolean isTimeUp() {
        return currentTime <= 0;
    }

    public float getTimeLimit() {
        return currentTime;
    }
    public void addTime(float timeToAdd) {
        currentTime += timeToAdd;
        if (currentTime > maxTimeLimit) {
            currentTime = maxTimeLimit;
        }
    }

    public void setPosition(float x, float y) {
        barX = x;
        barY = y;
    }
}

