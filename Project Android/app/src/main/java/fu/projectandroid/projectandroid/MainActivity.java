package fu.projectandroid.projectandroid;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

//Main
public class MainActivity extends AppCompatActivity {

    //Object
    private ImageView imgPlay; //button play
    private ImageView heroStandMain; //hero in main menu
    private ImageView imgSound;

    //Thread lam toc hero bay trong gio
    private Thread hairThread;
    private Handler hairThreadHandler;
    private boolean hairFlag;

    //flag sound/mute
    private boolean soundFlag;

    private MediaPlayer mainSong = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgPlay = findViewById(R.id.imgPlay);
        heroStandMain = findViewById(R.id.heroStand_Main);
        imgSound = findViewById(R.id.imgSound);
        imgSound.setVisibility(View.INVISIBLE);
        soundFlag = true;
        hairFlag = true;

        String sound = getIntent().getStringExtra("sound");
        if(sound != null){
            if(sound.equals("off"))
            soundFlag = false;
        }
        mainSong = MediaPlayer.create(this, R.raw.bg1_sound);
        mainSong.start();
        mediaBackground();
        initThread();
    }

    public void ImgPlay_Click(View view) {
        mainSong.stop();
        // sang man hinh chinh cua tro choi
        Intent intent = new Intent(this,Game2Activity.class);
        intent.putExtra("sound", soundFlag);
        this.startActivity(intent);
        this.finish();
    }

    //ham tao thread
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

    //toc bay trong gio
    public void hairFlying() {
        if (hairFlag) {
            heroStandMain.setImageResource(R.drawable.sprite2);
            hairFlag = false;
        } else {
            heroStandMain.setImageResource(R.drawable.sprite1);
            hairFlag = true;
        }
    }

    public void mediaBackground(){
        if(soundFlag == true){
            mainSong.start();
            mainSong.setLooping(true);
        } else {
            imgSound.setImageResource(R.drawable.mute);
            mainSong.stop();
        }

    }

    public void ImgSound_Click(View view) {
        if (soundFlag) {
            imgSound.setImageResource(R.drawable.mute);
            mainSong.stop();
            soundFlag = false;
        } else {
            imgSound.setImageResource(R.drawable.sound);
            try {
                mainSong.prepare();
                mainSong.start();
                mainSong.setLooping(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            soundFlag = true;
        }
    }


}
