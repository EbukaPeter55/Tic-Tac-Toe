package com.example.ceoebukapeter.tictactoegame3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("JavaDoc")
public class FiveBoardMatrix extends AppCompatActivity implements View.OnClickListener {

    /**
     * A two dimensional array, which implements a 5 by 5 matrix..
     * A Boolean that makes player one to always take the first turn using X
     * The roundCount variable counts the number of rounds, and check if it is up to 25.
     * The variables Player1points and Player2points are use to record number of points obtained by each player
     * The variables textViewPlayer1 and textViewPlayer2 are use to display the points of each players
     */

    private final Button[][] buttons = new Button[5][5];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_board_matrix);
        /*
          Get references to our textViews by using the findViewById method
          Make references to our button by using a nested for-loop
         */
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    /**
     * The onClick method is called when users click on any of the buttons.
     * The conditioner statement at line 70 is use to check if buttons in the matrix have been filled with either X or O.
     * The condition statement at line 76 is use to determine turns or whether it is player 1's turn.
     * The roundCount is incremented to tell that a particular round is over.
     */
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 25) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    /**
     * The checkForWin method is use to determine if there is a winner.
     * Using a nested For-loop, we go through all the columns, rows and diagonals to see if there is a winner..
     */
    private boolean checkForWin() {
        String[][] field = new String[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        //Checking for rows
        for (int i = 0; i < 5; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && field[i][0].equals(field[i][3])
                    && field[i][0].equals(field[i][4])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        //Checking for columns
        for (int i = 0; i < 5; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && field[0][i].equals(field[3][i])
                    && field[0][i].equals(field[4][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        //Checking for diagonals from left to right
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && field[0][0].equals(field[3][3])
                && field[0][0].equals(field[4][4])
                && !field[0][0].equals("")) {
            return true;
        }
        //Checking for diagonals from right to left
        if (field[0][4].equals(field[1][3])
                && field[0][4].equals(field[2][2])
                && field[0][4].equals(field[3][1])
                && field[0][4].equals(field[4][0])
                && !field[0][4].equals("")) {
            return true;
        }

        return false;
    }

    /**
     * The player1 method is called whenever player1 wins during each game.
     * The player2 method is called whenever player2 wins during each game.
     * The draw method is called whenever there is a tie or draw during each game..
     * The updatePointsText method is use to update the Score board after each round of play.
     * The resetBoard method is use to reset all buttons.
     * The resetGame method is use to reset all points in the score board
     */
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    /**
     * The onSaveInstanceState method is called when we rotate our device,
     *
     * @param outState help us to save our values when we rotate our device so that we don't loose them.
     * The onRestoreInstanceState is use to restore our values after rotating our devices.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
