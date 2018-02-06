package edu.cnm.deepdive.craps.controllers;

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
import edu.cnm.deepdive.craps.helpers.DiceTextAdapter;
import edu.cnm.deepdive.craps.models.Craps;
import edu.cnm.deepdive.craps.models.Game;

public class MainActivity extends AppCompatActivity {

  private TextView playsValue;
  private TextView winsValue;
  private TextView winsPercentage;
  private Button play;
  private ToggleButton run;
  private Button reset;
  private Game game;
  private ListView rollsList;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    playsValue = findViewById(R.id.plays_value);
    winsValue = findViewById(R.id.wins_value);
    winsPercentage = findViewById(R.id.percentage_value);
    play = findViewById(R.id.play);
    run = findViewById(R.id.play_on);
    reset = findViewById(R.id.reset);
    rollsList = findViewById(R.id.rolls_list);
    game = new Game();
    setupEvents();

  }

  private void setupEvents() {
    play.setOnClickListener(new PlayButtonListener());
//    play.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Craps.State state = game.play();
//        long wins = game.getWins();
//        long losses = game.getLosses();
//        long plays = wins + losses;
//        double percentage = 100.0 * wins / plays;
//        playsValue.setText(String.format("%d", plays));
//        winsValue.setText(String.format("%d", wins));
//        winsPercentage.setText(String.format("%.2f%%", percentage));
//        rollsList.setAdapter(new DiceTextAdapter(
//            MainActivity.this, R.layout.item_roll, game.getCraps().getRolls(), state));
//      }
//    });
    reset.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        game.reset();
        playsValue.setText(String.format("%d", 0));
        winsValue.setText(String.format("%d", 0));
        winsPercentage.setText(String.format("%.2f%%", 0d));
      }
    });

  }

  private class PlayButtonListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      Craps.State state = game.play();
        long wins = game.getWins();
        long losses = game.getLosses();
        long plays = wins + losses;
        double percentage = 100.0 * wins / plays;
        playsValue.setText(String.format("%d", plays));
        winsValue.setText(String.format("%d", wins));
        winsPercentage.setText(String.format("%.2f%%", percentage));
        rollsList.setAdapter(new DiceTextAdapter(
            MainActivity.this, R.layout.item_roll, game.getCraps().getRolls(), state));
      }
    }
  }




