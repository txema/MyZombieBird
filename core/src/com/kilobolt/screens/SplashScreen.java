package com.kilobolt.screens;

/**
 * Created by txema on 9/20/14.
 */

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kilobolt.TweenAccessors.SpriteAccessor;
import com.kilobolt.zbhelpers.AssetLoader;
import com.kilobolt.zombiebird.ZBGame;

public class SplashScreen implements Screen {

    private TweenManager manager;
    private SpriteBatch batcher;
    private Sprite sprite;
    private ZBGame game;

    public SplashScreen(ZBGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        sprite = new Sprite(AssetLoader.logo);
        sprite.setColor(1, 1, 1, 0);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float desiredWidth = width * .7f;
        float scale = desiredWidth / sprite.getWidth();

        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height / 2)
                - (sprite.getHeight() / 2));
        setupTween();
        batcher = new SpriteBatch();
    }

    private void setupTween() {
        // 1. This line registers a new Accessor. We are basically saying,
        // "I want to be able to modify Sprite objects using the Tween Engine.
        // Here's the Accessor I made for it that follows your specifications
        // (that we must have a getValues method and a setValues method)."

        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        //2. The Tween Engine requires a TweenManager to work, which is updated
        // in our render method with the delta value.
        // This manager will then handle the interpolation for us using our SpriteAccessor.
        manager = new TweenManager();

        //3. We can create something called a TweenCallback, which is an object whose methods are called when Tweening is complete.
        // In this case, we create a new TweenCallback called cb, whose onEvent method
        // (which will be called when our Tweening is finished), will send us to the GameScreen.
        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameScreen());
            }
        };

        //Before we move on to the most important stuff, let's see what we are trying to accomplish.
        // We are trying to take our logo sprite, start it at 0 opacity, increase it to 1 (100%)
        // and bring it back down to zero.
        //
        //This says: We are going to tween the sprite object using the SpriteAccessor's ALPHA tweenType.
        // We want this to take .8 seconds. We want you to modify the starting alpha value
        // (this is specified in the SpriteAccessor class) to the desired target value of 1.
        //
        //- We want this to use a quadratic interpolation, and repeat once as a Yoyo
        // (with .4 seconds between the repetition). This accomplishes the desired effect of bringing
        // our opacity back down to zero once it hits one.
        //              .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
        //
        // This then says, use the callback that we have created above called cb, and notify it when tweening is complete.
        //               .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
        //
        // this specifies which manager will be doing the work
        //               .start(manager);
        //
        Tween.to(sprite, SpriteAccessor.ALPHA, .8f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        sprite.draw(batcher);
        batcher.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
