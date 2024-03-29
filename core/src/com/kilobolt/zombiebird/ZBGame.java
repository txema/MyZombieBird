package com.kilobolt.zombiebird;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.kilobolt.screens.SplashScreen;
import com.kilobolt.zbhelpers.AssetLoader;

public class ZBGame extends Game {


    @Override
    public void create() {
        Gdx.app.log("ZBGame", "created");

        AssetLoader.load();
        setScreen(new SplashScreen(this));

    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }

}
