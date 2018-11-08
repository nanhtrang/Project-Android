package fu.projectandroid.projectandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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


    //boom
    private ImageView boom;
    private Thread boomThread;
    private Handler boomHandler;
    private int checkBoom;

    //mainThread
    private Thread mainThread;
    private Handler mainHandler;
    private boolean isRunning;


    //get life
    private ImageView life1;
    private ImageView life2;
    private ImageView life3;
    private ImageView life4;
    private ImageView life5;
    private int lifeInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        hero = findViewById(R.id.hero);
        arrow = findViewById(R.id.arrow);
        boom = findViewById(R.id.boom);
        gameCanvas = findViewById(R.id.gameCanvas);
        //get life
        score = findViewById(R.id.score);
        life1 = findViewById(R.id.life1);
        life2 = findViewById(R.id.life2);
        life3 = findViewById(R.id.life3);
        life4 = findViewById(R.id.life4);
        life5 = findViewById(R.id.life5);
        lifeInt = 5;
        //set boom location
        boom.setVisibility(View.INVISIBLE);
        scoreInt = 0;
        score.setText(String.valueOf(scoreInt));
        leftToRight = true;
        changePicFlag = true;
        shooted = true;
        move();
    }

    public void initMainThread() {
        isRunning = true;
        mainHandler = new Handler();
        mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    if (isHit()) {

                    }
                }
            }
        });
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
                                hero.setX(hero.getX() + 5 + (scoreInt - (scoreInt - 3)));
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
                                hero.setX(hero.getX() - 5 - (scoreInt - (scoreInt - 3)));
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
        hit = false;
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
                                if (hit == false) {
                                    switch (lifeInt) {
                                        case 5:
                                            life5.setVisibility(View.INVISIBLE);
                                            lifeInt--;
                                            break;
                                        case 4:
                                            life4.setVisibility(View.INVISIBLE);
                                            lifeInt--;
                                            break;
                                        case 3:
                                            life3.setVisibility(View.INVISIBLE);
                                            lifeInt--;
                                            break;
                                        case 2:
                                            life2.setVisibility(View.INVISIBLE);
                                            lifeInt--;
                                            break;
                                        case 1:
                                            //get score
                                            gameOver();
                                            finish();
                                            break;
                                    }
                                }
                                arrow.setY(0);
                                shooted = true;
                            }
                            if (isHit()) {
                                scoreInt++;
                                score.setText(String.valueOf(scoreInt));
                                arrow.setY(0);
                                shooted = true;
                                hit = true;
                                boomBoom();
                            }
                            //check fire fail
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

    public void boom() {
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
            if (x2 < x3) {
                return false;
            }
            if (x1 > x4) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private void boomBoom() {
        boomHandler = new Handler();
        boomThread = new Thread(new Runnable() {
            @Override
            public void run() {
                checkBoom = 0;
                while (checkBoom != 11) {
                    boomHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (checkBoom == 0) {
                                boom.setX(arrow.getX());
                                boom.setY(hero.getY());
                                boom.setVisibility(View.VISIBLE);
                                boom.setImageResource(R.drawable.boom1);
                            }
                            checkBoom = checkBoom + 1;
                            switch (checkBoom) {
                                case 1:
                                    boom.setImageResource(R.drawable.boom1);
                                    break;
                                case 2:
                                    boom.setImageResource(R.drawable.boom2);
                                    break;
                                case 3:
                                    boom.setImageResource(R.drawable.boom3);
                                    break;
                                case 4:
                                    boom.setImageResource(R.drawable.boom4);
                                    break;
                                case 5:
                                    boom.setImageResource(R.drawable.boom5);
                                    break;
                                case 6:
                                    boom.setImageResource(R.drawable.boom6);
                                    break;
                                case 7:
                                    boom.setImageResource(R.drawable.boom7);
                                    break;
                                case 8:
                                    boom.setImageResource(R.drawable.boom8);
                                    break;
                                case 9:
                                    boom.setImageResource(R.drawable.boom9);
                                    break;
                                case 10:
                                    boom.setImageResource(R.drawable.boom10);
                                    break;
                                case 11:
                                    boom.setImageResource(R.drawable.boom11);
                                    break;
                            }
                            if (checkBoom == 11) {
                                boom.setVisibility(View.INVISIBLE);
                            }
                        }

                    });
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        boomThread.start();
    }


    public void gameOver(){
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra("score", scoreInt);
        startActivity(intent);
    }
}
