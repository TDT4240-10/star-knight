package no.ntnu.game.Factory.Button;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public interface AbstractButtonFactory {
    Button createButton(String text, InputListener inputListener);
}
