package fu.projectandroid.projectandroid;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game2Activity extends AppCompatActivity {

    private TextView score;
    private int scoreInt;
    private RelativeLayout gameCanvas;
    private ImageView hero;
    private TextView arrow;
    private boolean leftToRight;
    private Thread runThread;
    private Handler runHandler;
    private boolean changePicFlag;
    private float kickWall;

    //arrow
    private Thread shootThread;
    private Handler shootHandler;
    private int gravity = 1;
    private int gravitySpeed = 0;
    private boolean shooted;
    private boolean isShooting;
    private boolean hit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        hero = findViewById(R.id.hero);
        arrow = findViewById(R.id.arrow);
        gameCanvas = findViewById(R.id.gameCanvas);
        score = findViewById(R.id.score);
        scoreInt = 0;
        score.setText(String.valueOf(scoreInt));
        leftToRight = true;
        changePicFlag = true;
        shooted = true;
        move();
    }

    public void move() {
        kickWall = 0;
        runHandler = new Handler();
        runThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (leftToRight) {
                                if (changePicFlag) {
                                    hero.setImageResource(R.drawable.herorun);
                                    changePicFlag = false;
                                } else {
                                    hero.setImageResource(R.drawable.herostand);
                                    changePicFlag = true;
                                }
                                hero.setX(hero.getX() + 5);
                                kickWall = gameCanvas.getWidth() - hero.getWidth();
                                if (hero.getX() > kickWall) {
                                    leftToRight = false;
                                }
                            } else {
                                if (!changePicFlag) {
                                    hero.setImageResource(R.drawable.herorun1);
                                    changePicFlag = true;
                                } else {
                                    hero.setImageResource(R.drawable.herostand1);
                                    changePicFlag = false;
                                }
                                hero.setX(hero.getX() - 5);
                                kickWall = 0;
                                if (hero.getX() < kickWall) {
                                    leftToRight = true;
                                }
                            }
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        runThread.start();
    }

    public void fire() {
        shooted = false;
        gravity = 1;
        gravitySpeed = 0;
        hit= false;
        shootHandler = new Handler();
        shootThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (shooted == false) {
                    shootHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            gravitySpeed += gravity;
                            arrow.setY(arrow.getY() + gravitySpeed);
                            if (arrow.getY() > gameCanvas.getHeight()) {
                                arrow.setY(0);
                                shooted = true;
                            }
                            if (isHit()){
                                scoreInt++;
                                score.setText(String.valueOf(scoreInt));
                                arrow.setY(0);
                                shooted = true;
                                hit =  true;
                            }
                        }
                    });
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        shootThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (shooted) {
                fire();
            }
        }
        return super.onTouchEvent(event);
    }

    public void boom(){
        
    }

    public boolean isHit() {
        float x1, x2, x3, x4;
        float y1, y2, y3, y4;
        x1 = arrow.getX();
        x2 = arrow.getX() + arrow.getWidth();
        x3 = hero.getX();
        x4 = hero.getX() + hero.getWidth();
        y3 = hero.getY();
        y2 = arrow.getY() + arrow.getWidth();
        if (y3 < y2) {
            if (x2< x3){
                return false;
            }
            if (x1>x4){
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
}
