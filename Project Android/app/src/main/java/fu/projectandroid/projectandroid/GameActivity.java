package fu.projectandroid.projectandroid;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private TextView wall1;
    private TextView wall2;
    private ImageView hero;
    private TextView stick;


    //Thread
    private Thread stickHightUpThread;
    private Handler stickHightUpHandler;
    private boolean isTouching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        wall1 = findViewById(R.id.wall1);
        wall2 = findViewById(R.id.wall2);
        hero = findViewById(R.id.hero);
        stick = findViewById(R.id.stick);

        setWall1Location();

    }

    public void setWall1Location() {
        wall2.setX(400);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //Toast.makeText(this, String.valueOf(stick.getHeight()), Toast.LENGTH_SHORT).show();
            isTouching = true;
            stickHightUp();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isTouching = false;
        }
        return super.onTouchEvent(event);
    }

    public void stickHightUp() {
        stickHightUpHandler = new Handler();
        stickHightUpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isTouching) {
                    stickHightUpHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            stick.setHeight(stick.getHeight()+8);
                            stick.setY(stick.getY() - 3);

                        }
                    });
                    try {
                        Thread.sleep(7);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        stickHightUpThread.start();
    }

}
