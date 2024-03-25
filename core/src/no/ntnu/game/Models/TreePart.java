package no.ntnu.game.Models;

import com.badlogic.gdx.graphics.Color;
public class TreePart {
            String value;
            Color color;
            float x,y;
            PowerUp powerup;
            public TreePart(String value , Color color){

            this.value = value;
            this.color = color;
            this.x = 100;
            this.y = 100;
            this.powerup = null;

        }

        public void setPos(float x , float y){
                this.x =x;
                this.y =y ;
    }

        public void setPowerup(PowerUp powerup){
                this.powerup = powerup;
        }
}
