package com.example.adolfo.juego_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by adolf on 26/01/2017.
 */
public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private int x = 0;
    private int xSpeed = 1;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
    }


    protected void Draw(Canvas canvas) {
        if (x >= getWidth() - bmp.getWidth()) {
            xSpeed = -5;
        }
        if (x == 0) {
            xSpeed = 5;
        }
        x = x + xSpeed;
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmp, x , 10, null);
    }
}