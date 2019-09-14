package com.hfad.gameoffaces;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity{

    int score = 0;
    Button _answerButton;
    int _imageId;
    String _answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        createQuestion();
    }

    public void createQuestion() {
        HashMap<String, Integer> data = ImageData.Data();
        Random rand = new Random();
        HashSet<Integer> fourNumbers = new HashSet<>();
        while (fourNumbers.size() != 4) {
            fourNumbers.add(rand.nextInt(data.size()));
        }
        List<Integer> fourChosenNumbers = new ArrayList<Integer>(fourNumbers);

        Object[] keys = data.keySet().toArray();
        //this is answer key
        Object answer = (keys[fourChosenNumbers.get(3)]);
        _answer = String.valueOf(answer);
        //this is answer image id
        _imageId = data.get(answer);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(_imageId);

        //Create an array that contains all of our buttons
        List<Button> listButton = new ArrayList<Button>();
            listButton.add((Button) findViewById(R.id.option1));
            listButton.add((Button) findViewById(R.id.option2));
            listButton.add((Button) findViewById(R.id.option3));
            listButton.add((Button) findViewById(R.id.option4));

        //choose which button will contain the answer
        int index = new Random().nextInt(listButton.size());
        _answerButton = listButton.get(index);
        listButton.remove(index);
        _answerButton.setText(_answer);

        //rest of the three are set with text
        for (int i = 0; i < 3; i++) {
            listButton.get(i).setText(String.valueOf(keys[fourChosenNumbers.get(i)]));
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {

                case R.id.option1:
                    onClickHelper(R.id.option1);

                case R.id.option2:
                    onClickHelper(R.id.option2);

                case R.id.option3:
                    onClickHelper(R.id.option3);

                case R.id.option4:
                    onClickHelper(R.id.option4);

                default:
                    break;
            }
        }


    public void onClickHelper(int id) {
        if (id == _answerButton.getId()) {
//            score += 1;
//            TextView textScore = (TextView) findViewById(R.id.score);
//            textScore.setText("Score: " + Integer.toString(score));
            createQuestion();
        } else {
            CharSequence text = "Wrong Answer";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(GameActivity.this, text, duration);
            toast.show();
            createQuestion();
        }
    }

    public void onClickImage(View view) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, _answer);
        startActivity(intent);
    }

    public void onClickEnd(View view) {
        alert();
    }

    public void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to stop the game?");
        builder.setCancelable(true);

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CharSequence text = "Game Ended";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(GameActivity.this, text, duration);
                        toast.show();
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        AlertDialog alert = builder.create();
        alert.show();
    }
}
