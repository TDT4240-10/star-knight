package no.ntnu.game.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TimeLimitBar {

    private final float maxTimeLimit;
    private float currentTime;
    private final float BAR_WIDTH;
    private final float BAR_HEIGHT;
    private float barX;
    private float barY;
    private final float BAR_WIDTH_PER_SECOND;

    public TimeLimitBar(float currentTime, float maxTimeLimit, float width, float height, float x, float y) {
        this.currentTime = currentTime;
        this.maxTimeLimit = maxTimeLimit;
        BAR_WIDTH = width;
        BAR_HEIGHT = height;
        barX = x;
        barY = y;
        BAR_WIDTH_PER_SECOND = BAR_WIDTH / maxTimeLimit;
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
        shapeRenderer.rect(barX, barY, BAR_WIDTH, BAR_HEIGHT);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, currentTime * BAR_WIDTH_PER_SECOND, BAR_HEIGHT);
        shapeRenderer.end();
    }

    public boolean isTimeUp() {
        return currentTime <= 0;
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
