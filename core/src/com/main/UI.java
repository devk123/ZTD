package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI {
    static BitmapFont font = new BitmapFont();
    static int money, wave, score, life;

    static void draw(SpriteBatch b){
        font.getData().setScale(2f);
        font.setColor(Color.PINK);
        font.draw(b, "Money: " + money, 2, 597);
        font.setColor(Color.PINK);
        font.draw(b, "Wave:" + wave, 2, 597 - 15 * font.getData().scaleX);
        font.setColor(Color.PINK);
        font.draw(b, "Score:" + score, 2, 597 - 15 * font.getData().scaleX * 2);
        font.getData().setScale(1f);
    }
}
