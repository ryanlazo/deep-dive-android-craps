package edu.cnm.deepdive.craps.helpers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.cnm.deepdive.craps.R;
import edu.cnm.deepdive.craps.models.Craps;
import java.util.List;

public class DiceTextAdapter extends ArrayAdapter<int[]> {

private int resourceId;
private List<int[]> rolls;
private Craps.State state;

  public DiceTextAdapter(Context context, int resource, List<int[]> objects, Craps.State state) {
    super(context, resource, objects);
    this.resourceId = resource;
    this.rolls = objects;
    this.state = state;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    int[] roll = rolls.get(position);
    TextView view = (TextView) LayoutInflater.from(getContext()).inflate(resourceId, null, false);
    view.setText(getContext() .getString(R.string.roll_format, roll[0], roll[1], roll[0] + roll[1]));
    if (position == rolls.size() -1) {
      if (state == Craps.State.WIN) {
        view.setBackgroundColor(Color.BLUE);
      }else {
        view.setBackgroundColor(Color.RED);

      }
    }
    return view;
  }
}
