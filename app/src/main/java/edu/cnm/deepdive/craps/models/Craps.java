package edu.cnm.deepdive.craps.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Craps {

  private State state = State.COME_OUT;
  private Random rng = new Random();
  private List<int[]> rolls = new LinkedList<>();

  public List<int[]> getRolls() {
    List<int[]> copy = new LinkedList<>(rolls);
    return copy;
  }

  protected void reset() {
    state = State.COME_OUT;
    rolls.clear();
  }
  public State play() {
    reset();
    do {
      roll();
    } while(state == State.POINT);
    return state;
  }
  protected int roll() {
    int[] dice = {
       1 + rng.nextInt(6),
       1 + rng.nextInt(6),
    };
    int sum = dice[0] + dice[1];
    rolls.add(dice);
    state = state.roll(sum);
    return sum;
  }

  public enum State {
    COME_OUT, WIN, LOSS, POINT, TERMINAL;

    private int point = 0;

    public State roll(int diceSum) {
      switch (this) {
//this refers to what i am. if i am the comeout state or the point state im going to do...
        case COME_OUT:
          switch (diceSum) {
            case 2:
            case 3:
            case 12:
              return LOSS;
            case 7:
            case 11:
              return WIN;
            default:
              POINT.point = diceSum;
              return POINT;

          }
        case POINT:
          if (diceSum == point) {
            return WIN;
          } else if (diceSum == 7) {
            return LOSS;
          } else {
            return this;
          }
        default:
          return this;
      }

    }

  public State playAgain() {
      if (this == WIN || this == LOSS) {
        return COME_OUT;
      }else {
        return this;
      }
  }
  public State surrender () {
      if (this != COME_OUT) {
        return TERMINAL;
      }else {
        return this;
      }
  }

  }

}
