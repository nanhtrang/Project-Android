package fu.projectandroid.projectandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView wall1;
    private TextView wall2;
    private ImageView hero;
    private TextView stick;

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

    public void setWall1Location(){
        wall2.setX(150);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){

        }
        return super.onTouchEvent(event);
    }
}
