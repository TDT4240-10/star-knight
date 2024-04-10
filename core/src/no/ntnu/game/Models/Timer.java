package no.ntnu.game.Models;

public class Timer {
    private static Timer instance; // Singleton instance
//    private BitmapFont font;
    private float elapsedTime;
    private boolean running;

    public Timer() {
        elapsedTime = 0;
        running = false;
    }

    // Static method to get the instance
    public static Timer getInstance() {
        if (instance == null) {
            instance = new Timer();
        }
        return instance;
    }

    public void start() {
        running = true;
    }

    public void pause() {
        running = false;
    }

    public void reset() {
        elapsedTime = 0;
    }

    // Optionally, continue from where you left off
    public void resume() {
        running = true;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    // Returns the elapsed time formatted as a MM:SS string
    public String getFormattedTime() {
        int minutes = (int) (elapsedTime / 60);
        int seconds = (int) (elapsedTime % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

//    public void update(float deltaTime) {
//        if (running) {
//            elapsedTime += deltaTime;
//        }
//    }


//    public void render(SpriteBatch sb) {
//        sb.begin();
//        font.draw(sb, "Time: " + getFormattedTime(), 100, Gdx.graphics.getHeight() - 700); // Adjust position as needed
//        sb.end();
//    }
}



