package no.ntnu.game.Models.Factory.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class RectangleButtonFactory implements AbstractButtonFactory {
    private final Skin SKIN = new Skin(Gdx.files.internal("uiskin.json"));

    @Override
    public Button createButton(String text, InputListener inputListener) {
        TextButton button = new TextButton(text, SKIN);
        button.getLabel().setFontScale(5);

        if (inputListener != null) {
            button.addListener(inputListener);
        }

        return button;
    }
}
