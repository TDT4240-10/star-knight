package no.ntnu.game.Models;

import com.badlogic.gdx.utils.TimeUtils;

public class Timer {
    private boolean running;
    private float elapsedTime;
    private long startTime;

    public Timer() {
        running = false;
        elapsedTime = 0;
        startTime = 0;
    }

    public void start() {
        if (!running) {
            startTime = TimeUtils.millis();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            elapsedTime += (TimeUtils.millis() - startTime) / 1000f; // Convert milliseconds to seconds
            running = false;
        }
    }

    public void reset() {
        elapsedTime = 0;
        startTime = 0;
        running = false;
    }

    public float getElapsedTime() {
        if (running) {
            return elapsedTime + (TimeUtils.millis() - startTime) / 1000f;
        } else {
            return elapsedTime;
        }
    }

    public void update(float dt) {
        // Update the elapsed time
        elapsedTime += dt;
    }
}
