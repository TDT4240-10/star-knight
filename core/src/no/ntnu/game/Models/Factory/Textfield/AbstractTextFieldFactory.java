package no.ntnu.game.Models.Factory.Textfield;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public interface AbstractTextFieldFactory {
    TextField createTextfield(String placeholder);
}
