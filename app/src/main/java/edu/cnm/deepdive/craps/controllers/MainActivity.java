package edu.cnm.deepdive.craps.controllers;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.cnm.deepdive.craps.R;
import edu.cnm.deepdive.craps.helpers.DiceImageAdapter;
import edu.cnm.deepdive.craps.helpers.DiceTextAdapter;
import edu.cnm.deepdive.craps.model.Craps;
import edu.cnm.deepdive.craps.model.Game;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final String FACE_PREFIX = "face_";


  private TextView tally;
  private Button play;
  private ToggleButton run;
  private Button reset;
  private Game game;
  private ListView rollsList;
  private boolean running = false;
  private Runner runner = null;
  private Drawable[] faces;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    tally = findViewById(R.id.tally);
    play = findViewById(R.id.play);
    run = findViewById(R.id.play_on);
    reset = findViewById(R.id.reset);
    rollsList = findViewById(R.id.rolls_list);
    game = new Game();
    faces = loadDiceFaces();
    setupEvents();
    updateDisplay();

  }

  private void setupEvents() {
    play.setOnClickListener(new PlayButtonListener());

    reset.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        game.reset();
       updateDisplay();
      }
    });

    run.setOnClickListener(new OnClickListener() {
      @Override
          public void onClick(View v) {
            if (run.isChecked()) {
              running = true;
              play.setEnabled(false);
              reset.setEnabled(false);
              runner = new Runner();
              runner.start();
            }else {
              running = false;
              runner = null;
              play.setEnabled(true);
              reset.setEnabled(true);
            }
      }
    });

  }
  private void updateDisplay() {
    Craps.State state;
    long wins;
    long losses;
    List<int[]> rolls;
    synchronized (game) {
      state = game.getCraps().getState();
      wins = game.getWins();
      losses = game.getLosses();
      rolls = game.getCraps().getRolls();
    }
    long plays = wins + losses;
    double percentage = (plays > 0) ? (100.0 * wins / plays) : 0;
   tally.setText(getString(R.string.tally_format, wins, plays, percentage));
    rollsList.setAdapter(new DiceImageAdapter(
        MainActivity.this, R.layout.item_roll_dice, rolls, state, faces));
}

  private Drawable[] loadDiceFaces() {
    Drawable[] faces = new Drawable[6];
    Resources res = getResources();for (int i = 0; i < faces.length; i++) {
      int id = res.getIdentifier(FACE_PREFIX + (i + 1), "drawable", getPackageName());
    faces[i] = res.getDrawable(id, null);
    }
    return faces;
  }

  private class PlayButtonListener implements OnClickListener {
    @Override
    public void onClick(View v) {
      game.play();
      updateDisplay();

      }
    }

    private class Runner extends Thread {

      public static final int UPDATE_INTERVAL = 100_000;

      @Override
      public void run() {
        long counter = 0;
        while (running) {
          synchronized (game) {
            game.play();
          }
          if (++counter % UPDATE_INTERVAL == 0) {
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                updateDisplay();
              }
            });
          }
        }
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            updateDisplay();
          }
        });
      }

    }

  }




