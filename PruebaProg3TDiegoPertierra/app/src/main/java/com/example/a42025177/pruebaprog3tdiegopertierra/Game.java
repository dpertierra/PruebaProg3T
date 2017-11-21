package com.example.a42025177.pruebaprog3tdiegopertierra;

import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.instant.CallFuncN;
import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateBy;
import org.cocos2d.actions.interval.RotateTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.ScaleTo;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 42025177 on 21/11/2017.
 */

public class Game {
    CCGLSurfaceView _VistaJuego;
    CCSize PantallaDispositivo;
    Sprite spriteRojo;
    Sprite spriteAzul;
    int spriteElegido;
    int rotacion=0;

    public Game(CCGLSurfaceView Vistadeljuego)
    {
        _VistaJuego = Vistadeljuego;
    }
    public void startgame()
    {
        Director.sharedDirector().attachInView(_VistaJuego);
        PantallaDispositivo = Director.sharedDirector().displaySize();
        Director.sharedDirector().runWithScene(Gamescene());
    }
    private Scene Gamescene()
    {
        Scene scene = Scene.node();

        Capa C = new Capa();
        scene.addChild(C);

        return scene;

    }
    class Capa extends Layer {
        public Capa()

        {
            creaSprite();
            this.setIsTouchEnabled(true);
        }
        public void creaSprite(){
            spriteRojo = Sprite.sprite("cuadradorojo.png");
            spriteAzul = Sprite.sprite("cuadradoazul.png");

            spriteRojo.setPosition(PantallaDispositivo.getWidth()/2, PantallaDispositivo.getHeight()/2 + spriteRojo.getHeight());

            spriteAzul.setPosition(PantallaDispositivo.getWidth()/2, PantallaDispositivo.getHeight()/4 - spriteAzul.getHeight()/4);

            Log.d("Rojo position", " x " + spriteRojo.getPositionX() + " y " + spriteRojo.getPositionY());
            Log.d("Azul position", " x " + spriteAzul.getPositionX() + " y " + spriteAzul.getPositionY());

            super.addChild(spriteRojo);
            super.addChild(spriteAzul);

        }


        @Override
        public boolean ccTouchesBegan(MotionEvent event) {

            Random random = new Random();
            spriteElegido = random.nextInt(2);
            MoveTo moveToAzul = MoveTo.action(3f, PantallaDispositivo.getWidth() - spriteAzul.getWidth()/2, PantallaDispositivo.getHeight() - spriteAzul.getHeight()/2);
            MoveTo moveToRojo = MoveTo.action(3f, PantallaDispositivo.getWidth() - spriteRojo.getWidth()/2,PantallaDispositivo.getHeight() - spriteRojo.getHeight()/2);
            if (spriteElegido == 1)
            {
                spriteAzul.runAction(moveToAzul);
                Log.d("Desplaza", "Se desplaza la imagen azul");
            }
            else
            {
                spriteRojo.runAction(moveToRojo);
                Log.d("Desplaza", "Se desplaza la imagen roja");
            }

            return true;
        }


        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            RotateBy rotateBy = RotateBy.action(0.5f,15);
            spriteElegido =1;
            if (spriteElegido==1 && rotacion<360)
            {
                Log.d("Entro", "debug");
                spriteAzul.runAction(rotateBy);
                rotacion = rotacion +15;
                Log.d("Angulo", "" + rotacion);

            }
            if (spriteElegido==2 && rotacion<360)
            {
                spriteRojo.runAction(rotateBy);
                rotacion = rotacion +15;
                Log.d("Angulo", "" + rotacion);

            }

            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            IntervalAction Secuencia;
            IntervalAction Secuencia2;
            ScaleBy scale = ScaleBy.action(1f,1.3f);
            ScaleTo scale2 = ScaleTo.action(1f,1f);

            Secuencia = Sequence.actions(scale,scale2,scale,scale2,scale,scale2);

            ScaleBy scale3 = ScaleBy.action(1f,1.3f);
            ScaleTo scale4 = ScaleTo.action(1f,1f);

            Secuencia2 = Sequence.actions(scale3,scale4,scale3,scale4,scale3,scale4);

            spriteAzul.runAction(Secuencia);
            spriteRojo.runAction(Secuencia2);
            return true;
        }



    }



}
