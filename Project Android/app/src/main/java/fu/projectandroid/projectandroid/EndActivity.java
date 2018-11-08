package fu.projectandroid.projectandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    private SharedPreferences pref = null;
    private TextView txtScore;
    private TextView txtBest;
    private int best;
    private int scoreInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        txtScore = findViewById(R.id.txtScore);
        txtBest = findViewById(R.id.txtBest);
        setScore();
    }
    public void setScore(){
        Intent intent = getIntent();
        scoreInt = (int) intent.getExtras().get("score");
        txtScore.setText(String.valueOf(scoreInt));
        pref = getSharedPreferences("best", Context.MODE_PRIVATE);
        best = pref.getInt("best", 0);
        if (best < scoreInt) {
            best = scoreInt;
        }
        txtBest.setText(String.valueOf(best));
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = pref.edit();
        if (best < scoreInt) {
            best = scoreInt;
        }
        editor.putInt("best", best);
        editor.commit();
    }
}
