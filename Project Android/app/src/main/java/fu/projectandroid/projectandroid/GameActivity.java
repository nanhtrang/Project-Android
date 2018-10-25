package fu.projectandroid.projectandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    //object
    private ImageView hero;
    private TextView wall1;
    private TextView wall2;
    private TextView stick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //get id for object do
        hero = findViewById(R.id.hero);
        wall1 = findViewById(R.id.wall1);
        wall2 = findViewById(R.id.wall2);
        stick = findViewById(R.id.stick);
        //end

        //khoi tao vi tri va do cao ban dau ban dau cho stick
        stickConstructor();
        //end

    }

    //khoi tao vi tri va do cao ban dau ban dau cho stick
    public void stickConstructor(){
        stick.setHeight(1);
        stick.setX(stick.getX()+100);
    }
}
