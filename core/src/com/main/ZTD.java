package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class ZTD {
    //TODO: GAME VARIABLES / OBJECTS
    static Random r = new Random();
    static String currenttype = " ";
    static boolean pause = false;

    //TODO: GAME LISTS
    static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    static ArrayList<Cannon> cannons = new ArrayList<Cannon>();
    static ArrayList<Button> buttons = new ArrayList<Button>();
    static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    static ArrayList<Effect> effects = new ArrayList<Effect>();
    static ArrayList<Wall> walls = new ArrayList<Wall>();

    ZTD(){
        setup();
    }

    void draw(SpriteBatch batch){
        batch.draw(Resources.bg,0,0);
        UI.draw(batch);
        for(Zombie z : zombies) z.draw(batch); //Foreach
        for(Cannon c : cannons) c.draw(batch); //Foreach
        for(Button b : buttons) b.draw(batch); //Foreach
        for(Bullet b : bullets) b.draw(batch); //Foreach
        for(Wall w : walls) w.draw(batch); //Foreach
        for(Effect e : effects) e.draw(batch); //Foreach
    }

    void update(){
        if(!pause){
            for(Zombie z : zombies) z.update(); //Foreach
            for(Cannon c : cannons) c.update(); //Foreach
            for(Button b : buttons) b.update(); //Foreach
            for(Bullet b : bullets) b.update(); //Foreach
            for(Wall w : walls) w.update(); //Foreach
        }
        //Clean up after updates
        housekeeping();
        spawn_zombies();
    }

    void housekeeping(){
        for(Zombie z : zombies) if(!z.active) {zombies.remove(z); break;}
        for(Bullet b : bullets) if(!b.active) {bullets.remove(b); break;}
        for(Effect e : effects) if(!e.active) {effects.remove(e); break;}
        for(Wall w : walls) if(!w.active) {walls.remove(w); break;}
        for(Cannon c : cannons) if(!c.active) {cannons.remove(c); break;}
    }

    void tap(int x, int y){
            effects.add(new Effect("click", x, y));

            for(Button b : buttons) {
                if (b.t != null && !b.t.hidden && b.t.close.hitbox().contains(x, y)) {
                    b.t.hidden = true;
                    return;
                }
                if (b.t != null && !b.t.hidden && b.t.hitbox().contains(x,y)) return;
                if (b.hitbox().contains(x, y)) {
                    if(b.type.equals("pause") || b.type.equals("play")){
                        pause = !pause;
                        b.type = pause ? "play" : "pause";
                        return;
                    }
                    if (b.locked) {
                        if (b.t.hidden) {
                            hidett();
                            b.t.hidden = false;
                        } else {
                            b.locked = false;
                            b.t.hidden = true;
                        }
                        return;
                    } else {
                        if(b.type.equals("wall") || b.type.equals("mounted")) {
                            if(walls.size() < 3) walls.add(new Wall(walls.size() * 50, 0, b.type.equals("mounted")));
                            return;
                        }
                        hidett();
                        deselect();
                        b.selected = true;
                        currenttype = b.type;
                    }
                    return;
                }
            }
            for(Cannon c : cannons) if(c.hitbox().contains(x,y)){
                if(c.damaged) c.active = false;
                return;
            }
            if(buildable(x,y) && UI.money >= Tables.values.get("place_" + currenttype)) {
                UI.money -= Tables.values.get("place_" + currenttype);
                cannons.add(new Cannon(currenttype, x, y));
            }
        }


    void deselect(){
        for(Button b : buttons) b.selected = false;
    }

    void hidett(){
        for(Button b : buttons) if(b.t != null) b.t.hidden = true;
    }

    boolean buildable(int x, int y){

        return ((x > 0 && x < 1000) && (y > 0 &&  y < 200 ||  y > 300 && y < 500));
    }

    void setup(){
        Tables.init();
        spawn_zombies();
        spawn_buttons();
    }

    ArrayList<String> ztypes = new ArrayList<String>();
    void spawn_zombies(){
        if(!zombies.isEmpty()) return;
        UI.wave++;

        switch(UI.wave){

            case 5:
                ztypes.add("fast");
                break;
            case 10:
                ztypes.add("speedy");
                break;
            case 25:
                ztypes.add("riot");
                break;
            case 100:
                ztypes.add("dif");
                break;
            default:
                ztypes.add("zzz");
        }
        for(int i = 0; i < UI.wave * 3; i++){
            System.out.println("ZTYPES SIZE: " + ztypes.size());
            int testvar = r.nextInt(ztypes.size());
            System.out.println("RANDOMLY SELECTED INDEX: " + testvar);
            zombies.add(new Zombie(ztypes.get(testvar), 1024 + i * 50, r.nextInt(450)));
        }
    }

    void spawn_buttons(){
        buttons.add(new Button("cannon", 200 + buttons.size() * 75, 525));
        buttons.get(buttons.size() - 1).selected = true;
        buttons.get(buttons.size() - 1).locked = false;
        currenttype = "cannon";
        buttons.add(new Button("fire", 200 + buttons.size() * 75, 525));
        buttons.add(new Button("super", 200 + buttons.size() * 75, 525));
        buttons.add(new Button("double", 200 + buttons.size() * 75, 525));
        buttons.add(new Button("laser", 200 + buttons.size() * 75, 525));
        buttons.add(new Button("wall", 200 + buttons.size() * 75, 525));
        buttons.get(buttons.size() - 1).locked = false;
        buttons.add(new Button("mounted", 200 + buttons.size() * 75, 525));
        buttons.add(new Button("pause", 1024 - 75, 525));
        buttons.get(buttons.size() - 1).selected = false;
        buttons.get(buttons.size() - 1).locked = false;
    }

}
