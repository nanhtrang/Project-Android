package fu.projectandroid.projectandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class EndActivity extends AppCompatActivity {

    private SharedPreferences pref = null;
    private TextView txtScore;
    private TextView txtBest;
    private ImageView imgSound;
    private ImageView heroStandMain;
    private int best;
    private int scoreInt;
    private boolean soundFlag;
    private MediaPlayer bgSong = null;

    private Thread hairThread;
    private Handler hairThreadHandler;
    private boolean hairFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        txtScore = findViewById(R.id.txtScore);
        txtBest = findViewById(R.id.txtBest);
        heroStandMain = findViewById(R.id.imageView6);
        imgSound = findViewById(R.id.imgSound);
        hairFlag = true;
        Intent intent = getIntent();
        scoreInt = (int) intent.getExtras().get("score");
        soundFlag = (boolean) intent.getExtras().get("sound");

        bgSong = MediaPlayer.create(this, R.raw.bg1_sound);
        bgSong.start();
        mediaBackground();
        setScore();
        initThread();
    }

    public void mediaBackground(){
        if(soundFlag == true){
            bgSong.start();
            bgSong.setLooping(true);
        } else {
            imgSound.setImageResource(R.drawable.mute);
            bgSong.stop();
        }

    }

    public void ImgSound_Click(View view) {
        if (soundFlag) {
            imgSound.setImageResource(R.drawable.mute);
            bgSong.stop();
            soundFlag = false;
        } else {
            imgSound.setImageResource(R.drawable.sound);
            try {
                //if(bgSong.isPlaying())
                bgSong.prepare();
                bgSong.start();
                bgSong.setLooping(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            soundFlag = true;
        }
    }

    public void setScore(){
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

    public void goHome(View view){
        bgSong.stop();
        Intent intent = new Intent(this,MainActivity.class);
        if(soundFlag){
            intent.putExtra("sound", "on");
        } else {
            intent.putExtra("sound", "off");
        }
        this.startActivity(intent);
        this.finish();
    }

    public void goGame(View view){
        bgSong.stop();
        Intent intent = new Intent(this,Game2Activity.class);
        intent.putExtra("sound", soundFlag);
        this.startActivity(intent);
        this.finish();
    }

    public void initThread() {
        hairThreadHandler = new Handler();
        hairThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    hairThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            hairFlying();
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hairThread.start();
    }

    public void hairFlying() {
        if (hairFlag) {
            heroStandMain.setVisibility(View.INVISIBLE);
            hairFlag = false;
        } else {
            heroStandMain.setVisibility(View.VISIBLE);
            hairFlag = true;
        }
    }

}
