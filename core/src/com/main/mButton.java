package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class mButton {
    int x, y, w ,h, b = 2;
    Color color;
    String type;
    BitmapFont font = new BitmapFont();
    GlyphLayout layout = new GlyphLayout();
    static final int bw = 150;
    static final int bh = 75;
    mButton(String type, int x, int y, int w, int h, Color color){
        this.color = color;
        this.type = type;
        font.setColor(Resources.inverse_color(color));
        while((layout.width < w - (float)w/5) && (layout.height < h - (float)h/5)){
            font.getData().setScale(font.getData().scaleX + 0.1f);
            layout.setText(
                    font,
                    type.equals("start") ? "Start" :
                            type.equals("about") ? "About" :
                                    "Button"
            );
        }
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    void draw(SpriteBatch batch){
        batch.draw(Resources.create_texture(color), x, y, w, h);
        font.setColor(color);
        font.draw(batch, layout, x + (float)w/2 - (float)layout.width / 2, y + (float)h /2 + (float)layout.height / 2);
        batch.draw(Resources.create_texture(Resources.inverse_color(color)), x, y, w, b);
        batch.draw(Resources.create_texture(Resources.inverse_color(color)), x, y + h, w, b);
        batch.draw(Resources.create_texture(Resources.inverse_color(color)), x, y, b, h);
        batch.draw(Resources.create_texture(Resources.inverse_color(color)), x + w - b, y, b, h);
    }

    Rectangle hitbox(){ return new Rectangle(x, y, w, h); }
}
