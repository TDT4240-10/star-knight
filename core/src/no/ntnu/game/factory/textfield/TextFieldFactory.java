package no.ntnu.game.Factory.textfield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TextFieldFactory {
    private final Skin SKIN = new Skin(Gdx.files.internal("uiskin.json"));

    public TextField createTextfield(String placeHolder) {
        TextField field = new TextField("", SKIN);
        field.getStyle().font.getData().setScale(5f);
        return field;
    }
}
