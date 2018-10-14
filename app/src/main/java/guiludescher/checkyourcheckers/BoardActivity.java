package guiludescher.checkyourcheckers;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static guiludescher.checkyourcheckers.GameManager.BORDER;
import static guiludescher.checkyourcheckers.GameManager.CAPTURE_OK;
import static guiludescher.checkyourcheckers.GameManager.DARK;
import static guiludescher.checkyourcheckers.GameManager.EMPTY;
import static guiludescher.checkyourcheckers.GameManager.EOG_NO_CHECKERS;
import static guiludescher.checkyourcheckers.GameManager.EOG_NO_MOVES;
import static guiludescher.checkyourcheckers.GameManager.INVALID_SPACE;
import static guiludescher.checkyourcheckers.GameManager.LIGHT;
import static guiludescher.checkyourcheckers.GameManager.MOVE_OK;
import static guiludescher.checkyourcheckers.GameManager.MUST_CAPTURE;
import static guiludescher.checkyourcheckers.GameManager.PAWN;
import static guiludescher.checkyourcheckers.GameManager.QUEEN;
import static guiludescher.checkyourcheckers.GameManager.SPACE_OK;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener {

    static ImageView[][] imageCheckerMatrix;
    static ImageButton[][] buttonSpaceMatrix;

    TextView textPlayer1;
    TextView textPlayer2;

    TextView textPlayAgain;
    Button buttonYes, buttonNo;
    ImageView imagePlayAgain;

    String namePlayer1;
    String namePlayer2;

    int[] selectedSpace = new int[2];
    int[] selectedOrigin = new int[2];


    GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Intent getFromMain = getIntent();
        namePlayer1 = getFromMain.getStringExtra("namePlayer1");
        namePlayer2 = getFromMain.getStringExtra("namePlayer2");

        imagePlayAgain = (ImageView) findViewById(R.id.imagePlayAgain);
        textPlayAgain = (TextView) findViewById(R.id.textPlayAgain);
        buttonNo = (Button) findViewById(R.id.buttonNah);
        buttonNo.setOnClickListener(this);
        buttonYes = (Button) findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(this);

        textPlayer1 = (TextView) findViewById(R.id.textPlayer1);
        textPlayer1.setText(namePlayer1);

        textPlayer2 = (TextView) findViewById(R.id.textPlayer2);
        textPlayer2.setText(namePlayer2);

        buttonSpaceMatrix =  new ImageButton[8][8];

        buttonSpaceMatrix[0][1] = (ImageButton) findViewById(R.id.buttonSpace01);
        buttonSpaceMatrix[0][1].setOnClickListener(this);
        buttonSpaceMatrix[0][3] = (ImageButton) findViewById(R.id.buttonSpace03);
        buttonSpaceMatrix[0][3].setOnClickListener(this);
        buttonSpaceMatrix[0][5] = (ImageButton) findViewById(R.id.buttonSpace05);
        buttonSpaceMatrix[0][5].setOnClickListener(this);
        buttonSpaceMatrix[0][7] = (ImageButton) findViewById(R.id.buttonSpace07);
        buttonSpaceMatrix[0][7].setOnClickListener(this);

        buttonSpaceMatrix[1][0] = (ImageButton) findViewById(R.id.buttonSpace10);
        buttonSpaceMatrix[1][0].setOnClickListener(this);
        buttonSpaceMatrix[1][2] = (ImageButton) findViewById(R.id.buttonSpace12);
        buttonSpaceMatrix[1][2].setOnClickListener(this);
        buttonSpaceMatrix[1][4] = (ImageButton) findViewById(R.id.buttonSpace14);
        buttonSpaceMatrix[1][4].setOnClickListener(this);
        buttonSpaceMatrix[1][6] = (ImageButton) findViewById(R.id.buttonSpace16);
        buttonSpaceMatrix[1][6].setOnClickListener(this);

        buttonSpaceMatrix[2][1] = (ImageButton) findViewById(R.id.buttonSpace21);
        buttonSpaceMatrix[2][1].setOnClickListener(this);
        buttonSpaceMatrix[2][3] = (ImageButton) findViewById(R.id.buttonSpace23);
        buttonSpaceMatrix[2][3].setOnClickListener(this);
        buttonSpaceMatrix[2][5] = (ImageButton) findViewById(R.id.buttonSpace25);
        buttonSpaceMatrix[2][5].setOnClickListener(this);
        buttonSpaceMatrix[2][7] = (ImageButton) findViewById(R.id.buttonSpace27);
        buttonSpaceMatrix[2][7].setOnClickListener(this);

        buttonSpaceMatrix[3][0] = (ImageButton) findViewById(R.id.buttonSpace30);
        buttonSpaceMatrix[3][0].setOnClickListener(this);
        buttonSpaceMatrix[3][2] = (ImageButton) findViewById(R.id.buttonSpace32);
        buttonSpaceMatrix[3][2].setOnClickListener(this);
        buttonSpaceMatrix[3][4] = (ImageButton) findViewById(R.id.buttonSpace34);
        buttonSpaceMatrix[3][4].setOnClickListener(this);
        buttonSpaceMatrix[3][6] = (ImageButton) findViewById(R.id.buttonSpace36);
        buttonSpaceMatrix[3][6].setOnClickListener(this);

        buttonSpaceMatrix[4][1] = (ImageButton) findViewById(R.id.buttonSpace41);
        buttonSpaceMatrix[4][1].setOnClickListener(this);
        buttonSpaceMatrix[4][3] = (ImageButton) findViewById(R.id.buttonSpace43);
        buttonSpaceMatrix[4][3].setOnClickListener(this);
        buttonSpaceMatrix[4][5] = (ImageButton) findViewById(R.id.buttonSpace45);
        buttonSpaceMatrix[4][5].setOnClickListener(this);
        buttonSpaceMatrix[4][7] = (ImageButton) findViewById(R.id.buttonSpace47);
        buttonSpaceMatrix[4][7].setOnClickListener(this);

        buttonSpaceMatrix[5][0] = (ImageButton) findViewById(R.id.buttonSpace50);
        buttonSpaceMatrix[5][0].setOnClickListener(this);
        buttonSpaceMatrix[5][2] = (ImageButton) findViewById(R.id.buttonSpace52);
        buttonSpaceMatrix[5][2].setOnClickListener(this);
        buttonSpaceMatrix[5][4] = (ImageButton) findViewById(R.id.buttonSpace54);
        buttonSpaceMatrix[5][4].setOnClickListener(this);
        buttonSpaceMatrix[5][6] = (ImageButton) findViewById(R.id.buttonSpace56);
        buttonSpaceMatrix[5][6].setOnClickListener(this);

        buttonSpaceMatrix[6][1] = (ImageButton) findViewById(R.id.buttonSpace61);
        buttonSpaceMatrix[6][1].setOnClickListener(this);
        buttonSpaceMatrix[6][3] = (ImageButton) findViewById(R.id.buttonSpace63);
        buttonSpaceMatrix[6][3].setOnClickListener(this);
        buttonSpaceMatrix[6][5] = (ImageButton) findViewById(R.id.buttonSpace65);
        buttonSpaceMatrix[6][5].setOnClickListener(this);
        buttonSpaceMatrix[6][7] = (ImageButton) findViewById(R.id.buttonSpace67);
        buttonSpaceMatrix[6][7].setOnClickListener(this);

        buttonSpaceMatrix[7][0] = (ImageButton) findViewById(R.id.buttonSpace70);
        buttonSpaceMatrix[7][0].setOnClickListener(this);
        buttonSpaceMatrix[7][2] = (ImageButton) findViewById(R.id.buttonSpace72);
        buttonSpaceMatrix[7][2].setOnClickListener(this);
        buttonSpaceMatrix[7][4] = (ImageButton) findViewById(R.id.buttonSpace74);
        buttonSpaceMatrix[7][4].setOnClickListener(this);
        buttonSpaceMatrix[7][6] = (ImageButton) findViewById(R.id.buttonSpace76);
        buttonSpaceMatrix[7][6].setOnClickListener(this);

        imageCheckerMatrix = new ImageView[8][8];

        imageCheckerMatrix[0][1] = (ImageView) findViewById(R.id.imageChecker01);
        imageCheckerMatrix[0][3] = (ImageView) findViewById(R.id.imageChecker03);
        imageCheckerMatrix[0][5] = (ImageView) findViewById(R.id.imageChecker05);
        imageCheckerMatrix[0][7] = (ImageView) findViewById(R.id.imageChecker07);

        imageCheckerMatrix[1][0] = (ImageView) findViewById(R.id.imageChecker10);
        imageCheckerMatrix[1][2] = (ImageView) findViewById(R.id.imageChecker12);
        imageCheckerMatrix[1][4] = (ImageView) findViewById(R.id.imageChecker14);
        imageCheckerMatrix[1][6] = (ImageView) findViewById(R.id.imageChecker16);

        imageCheckerMatrix[2][1] = (ImageView) findViewById(R.id.imageChecker21);
        imageCheckerMatrix[2][3] = (ImageView) findViewById(R.id.imageChecker23);
        imageCheckerMatrix[2][5] = (ImageView) findViewById(R.id.imageChecker25);
        imageCheckerMatrix[2][7] = (ImageView) findViewById(R.id.imageChecker27);

        imageCheckerMatrix[3][0] = (ImageView) findViewById(R.id.imageChecker30);
        imageCheckerMatrix[3][2] = (ImageView) findViewById(R.id.imageChecker32);
        imageCheckerMatrix[3][4] = (ImageView) findViewById(R.id.imageChecker34);
        imageCheckerMatrix[3][6] = (ImageView) findViewById(R.id.imageChecker36);

        imageCheckerMatrix[4][1] = (ImageView) findViewById(R.id.imageChecker41);
        imageCheckerMatrix[4][3] = (ImageView) findViewById(R.id.imageChecker43);
        imageCheckerMatrix[4][5] = (ImageView) findViewById(R.id.imageChecker45);
        imageCheckerMatrix[4][7] = (ImageView) findViewById(R.id.imageChecker47);

        imageCheckerMatrix[5][0] = (ImageView) findViewById(R.id.imageChecker50);
        imageCheckerMatrix[5][2] = (ImageView) findViewById(R.id.imageChecker52);
        imageCheckerMatrix[5][4] = (ImageView) findViewById(R.id.imageChecker54);
        imageCheckerMatrix[5][6] = (ImageView) findViewById(R.id.imageChecker56);

        imageCheckerMatrix[6][1] = (ImageView) findViewById(R.id.imageChecker61);
        imageCheckerMatrix[6][3] = (ImageView) findViewById(R.id.imageChecker63);
        imageCheckerMatrix[6][5] = (ImageView) findViewById(R.id.imageChecker65);
        imageCheckerMatrix[6][7] = (ImageView) findViewById(R.id.imageChecker67);

        imageCheckerMatrix[7][0] = (ImageView) findViewById(R.id.imageChecker70);
        imageCheckerMatrix[7][2] = (ImageView) findViewById(R.id.imageChecker72);
        imageCheckerMatrix[7][4] = (ImageView) findViewById(R.id.imageChecker74);
        imageCheckerMatrix[7][6] = (ImageView) findViewById(R.id.imageChecker76);

        gameManager = new GameManager();
        displayCurrentTurn(gameManager.getPlayerTurn());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonNah:
                Intent in = new Intent(this, MainActivity.class);
                startActivity(in);

                imagePlayAgain.setVisibility(View.GONE);
                textPlayAgain.setVisibility(View.GONE);
                buttonYes.setVisibility(View.GONE);
                buttonNo.setVisibility(View.GONE);
                break;

            case R.id.buttonYes:
                gameManager = new GameManager();
                displayCurrentTurn(gameManager.getPlayerTurn());

                imagePlayAgain.setVisibility(View.GONE);
                textPlayAgain.setVisibility(View.GONE);
                buttonYes.setVisibility(View.GONE);
                buttonNo.setVisibility(View.GONE);
                textPlayer1.setText(namePlayer1);
                textPlayer2.setText(namePlayer2);
                break;

            case R.id.buttonSpace01:
                selectedSpace[0] = 0;
                selectedSpace[1] = 1;
                break;
            case R.id.buttonSpace03:
                selectedSpace[0] = 0;
                selectedSpace[1] = 3;
                break;
            case R.id.buttonSpace05:
                selectedSpace[0] = 0;
                selectedSpace[1] = 5;
                break;
            case R.id.buttonSpace07:
                selectedSpace[0] = 0;
                selectedSpace[1] = 7;
                break;
            case R.id.buttonSpace10:
                selectedSpace[0] = 1;
                selectedSpace[1] = 0;
                break;
            case R.id.buttonSpace12:
                selectedSpace[0] = 1;
                selectedSpace[1] = 2;
                break;
            case R.id.buttonSpace14:
                selectedSpace[0] = 1;
                selectedSpace[1] = 4;
                break;
            case R.id.buttonSpace16:
                selectedSpace[0] = 1;
                selectedSpace[1] = 6;
                break;
            case R.id.buttonSpace21:
                selectedSpace[0] = 2;
                selectedSpace[1] = 1;
                break;
            case R.id.buttonSpace23:
                selectedSpace[0] = 2;
                selectedSpace[1] = 3;
                break;
            case R.id.buttonSpace25:
                selectedSpace[0] = 2;
                selectedSpace[1] = 5;
                break;
            case R.id.buttonSpace27:
                selectedSpace[0] = 2;
                selectedSpace[1] = 7;
                break;
            case R.id.buttonSpace30:
                selectedSpace[0] = 3;
                selectedSpace[1] = 0;
                break;
            case R.id.buttonSpace32:
                selectedSpace[0] = 3;
                selectedSpace[1] = 2;
                break;
            case R.id.buttonSpace34:
                selectedSpace[0] = 3;
                selectedSpace[1] = 4;
                break;
            case R.id.buttonSpace36:
                selectedSpace[0] = 3;
                selectedSpace[1] = 6;
                break;
            case R.id.buttonSpace41:
                selectedSpace[0] = 4;
                selectedSpace[1] = 1;
                break;
            case R.id.buttonSpace43:
                selectedSpace[0] = 4;
                selectedSpace[1] = 3;
                break;
            case R.id.buttonSpace45:
                selectedSpace[0] = 4;
                selectedSpace[1] = 5;
                break;
            case R.id.buttonSpace47:
                selectedSpace[0] = 4;
                selectedSpace[1] = 7;
                break;
            case R.id.buttonSpace50:
                selectedSpace[0] = 5;
                selectedSpace[1] = 0;
                break;
            case R.id.buttonSpace52:
                selectedSpace[0] = 5;
                selectedSpace[1] = 2;
                break;
            case R.id.buttonSpace54:
                selectedSpace[0] = 5;
                selectedSpace[1] = 4;
                break;
            case R.id.buttonSpace56:
                selectedSpace[0] = 5;
                selectedSpace[1] = 6;
                break;
            case R.id.buttonSpace61:
                selectedSpace[0] = 6;
                selectedSpace[1] = 1;
                break;
            case R.id.buttonSpace63:
                selectedSpace[0] = 6;
                selectedSpace[1] = 3;
                break;
            case R.id.buttonSpace65:
                selectedSpace[0] = 6;
                selectedSpace[1] = 5;
                break;
            case R.id.buttonSpace67:
                selectedSpace[0] = 6;
                selectedSpace[1] = 7;
                break;
            case R.id.buttonSpace70:
                selectedSpace[0] = 7;
                selectedSpace[1] = 0;
                break;
            case R.id.buttonSpace72:
                selectedSpace[0] = 7;
                selectedSpace[1] = 2;
                break;
            case R.id.buttonSpace74:
                selectedSpace[0] = 7;
                selectedSpace[1] = 4;
                break;
            case R.id.buttonSpace76:
                selectedSpace[0] = 7;
                selectedSpace[1] = 6;
                break;
            default:
                break;
        }

        int winner;
        String loserName;

        //int result = gameManager.nextStepInRound(selectedSpace[0], selectedSpace[1]);
        switch (gameManager.nextStepInRound(selectedSpace[0], selectedSpace[1])) {
            case INVALID_SPACE:
                Toast.makeText(getApplicationContext(), "Select a valid space!", Toast.LENGTH_SHORT).show();
                break;
            case SPACE_OK:
                selectedOrigin = selectedSpace;
                changeSpaceColor();
                break;
            case MOVE_OK:
                displayCurrentTurn(gameManager.getPlayerTurn());
                break;
            case MUST_CAPTURE:
                Toast.makeText(getApplicationContext(), "You must capture!", Toast.LENGTH_SHORT).show();
                break;
            case CAPTURE_OK:
                displayCurrentTurn(gameManager.getPlayerTurn());
                break;
            case EOG_NO_CHECKERS:
                winner = gameManager.getWinner();
                if (winner == DARK)
                    loserName = namePlayer1;
                else
                    loserName = namePlayer2;
                Toast.makeText(getApplicationContext(), loserName + " has no checkers left!", Toast.LENGTH_SHORT).show();
                displayWinner(winner);
            case EOG_NO_MOVES:
                winner = gameManager.getWinner();
                if (winner == DARK)
                    loserName = namePlayer1;
                else
                    loserName = namePlayer2;
                Toast.makeText(getApplicationContext(), loserName + " has no moves left!", Toast.LENGTH_SHORT).show();
                displayWinner(winner);
                break;
            default:
                break;
        }
    }

    public static void updateBoard(int line, int column, int valueOfChecker){

        imageCheckerMatrix[line][column].setVisibility(View.VISIBLE);
        buttonSpaceMatrix[line][column].setImageResource(R.drawable.regular_space);
        switch (valueOfChecker) {
            case DARK * PAWN:
                imageCheckerMatrix[line][column].setImageResource(R.drawable.blue_pawn);
                break;
            case LIGHT * PAWN:
                imageCheckerMatrix[line][column].setImageResource(R.drawable.grey_pawn);
                break;
            case EMPTY:
                imageCheckerMatrix[line][column].setVisibility(View.INVISIBLE);
                break;
            case DARK * QUEEN:
                imageCheckerMatrix[line][column].setImageResource(R.drawable.blue_queen);
                break;
            case LIGHT * QUEEN:
                imageCheckerMatrix[line][column].setImageResource(R.drawable.grey_queen);
                break;
        }
    }

    public void changeSpaceColor() {
        buttonSpaceMatrix[selectedSpace[0]][selectedSpace[1]].setImageResource(R.drawable.selected_space);
    }

    public void displayWinner(int winner) {
        switch (winner){
            case DARK:
                textPlayer2.setTextColor(ContextCompat.getColor(this, R.color.neonBlue));
                textPlayer2.setText(namePlayer2 + " won! =D");
                textPlayer1.setTextColor(ContextCompat.getColor(this, R.color.transparentNeonBlue));
                textPlayer1.setText(namePlayer1 + " lost =c");
                break;
            case LIGHT:
                textPlayer1.setTextColor(ContextCompat.getColor(this, R.color.neonBlue));
                textPlayer1.setText(namePlayer1 + " won! =D");
                textPlayer2.setTextColor(ContextCompat.getColor(this, R.color.transparentNeonBlue));
                textPlayer2.setText(namePlayer2 + " lost =c");
                break;
        }

        imagePlayAgain.setVisibility(View.VISIBLE);
        textPlayAgain.setVisibility(View.VISIBLE);
        buttonYes.setVisibility(View.VISIBLE);
        buttonNo.setVisibility(View.VISIBLE);
    }

    public void displayCurrentTurn(int player) {
        switch (player){
            case DARK:
                textPlayer1.setTextColor(ContextCompat.getColor(this, R.color.transparentNeonBlue));
                textPlayer2.setTextColor(ContextCompat.getColor(this, R.color.neonBlue));
                break;
            case LIGHT:
                textPlayer2.setTextColor(ContextCompat.getColor(this, R.color.transparentNeonBlue));
                textPlayer1.setTextColor(ContextCompat.getColor(this, R.color.neonBlue));
                break;
        }
    }
}
