package guiludescher.checkyourcheckers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonStartGame;
    String namePlayer1, namePlayer2;
    EditText editTextPlayer1, editTextPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartGame = (Button) findViewById(R.id.buttonStartGame);
        buttonStartGame.setOnClickListener(this);

        editTextPlayer1 = (EditText) findViewById(R.id.editTextPlayer1);
        editTextPlayer2 = (EditText) findViewById(R.id.editTextPlayer2);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.buttonStartGame:
                //GameManager.newGame();
                Toast.makeText(getApplicationContext(), "Game started!", Toast.LENGTH_SHORT).show();

                if (editTextPlayer1.getText().toString().equals(""))
                    namePlayer1 = "Player 1";
                else
                    namePlayer1 = editTextPlayer1.getText().toString();
                if (editTextPlayer2.getText().toString().equals(""))
                    namePlayer2 = "Player 2";
                else namePlayer2 = editTextPlayer2.getText().toString();

                Intent intent = new Intent(this, BoardActivity.class);
                intent.putExtra("namePlayer1", namePlayer1);
                intent.putExtra("namePlayer2", namePlayer2);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
