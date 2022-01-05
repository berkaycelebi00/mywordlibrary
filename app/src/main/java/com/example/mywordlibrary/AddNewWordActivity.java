package com.example.mywordlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywordlibrary.database_helper.DatabaseHelper;
import com.example.mywordlibrary.models.Word;

public class AddNewWordActivity extends AppCompatActivity {
    boolean isEditing = false;
    EditText wordEditText;
    EditText meaningEditText;
    EditText spellingEditText;
    EditText exampleEditText;
    Button addWordButton;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_add_new_word);


        wordEditText = findViewById(R.id.editTextWord);
        meaningEditText = findViewById(R.id.editTextMeaning);
        spellingEditText = findViewById(R.id.editTextSpelling);
        exampleEditText = findViewById(R.id.editTextExampleSentence);
        addWordButton = findViewById(R.id.btn_add_word);

        extras = getIntent().getExtras();
        if(extras == null){
            Log.i("anwa","adding word state");
        }else{
            isEditing = true;
            wordEditText.setText(extras.getString("word"));
            meaningEditText.setText(extras.getString("meaning"));
            spellingEditText.setText(extras.getString("spelling"));
            exampleEditText.setText(extras.getString("example"));
            addWordButton.setText("Edit Word");

        }
    }



    public void  addWord(View view){
        String wordText = wordEditText.getText().toString();
        String meaningText = meaningEditText.getText().toString();
        String spellingText = spellingEditText.getText().toString();
        String exampleText = exampleEditText.getText().toString();

        if(wordText.length() == 0){
            Toast.makeText(getApplicationContext(),"Please enter a word",Toast.LENGTH_LONG).show();
        }
        else if(meaningText.length() == 0){
            Toast.makeText(getApplicationContext(),"Please enter a meaning",Toast.LENGTH_LONG).show();
        }else if(spellingText.length() == 0){
            Toast.makeText(getApplicationContext(),"Please enter a spelling",Toast.LENGTH_LONG).show();
        }else if(exampleText.length() == 0){
            Toast.makeText(getApplicationContext(),"Please enter a example",Toast.LENGTH_LONG).show();
        }else{
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            if(isEditing){
                db.updateWord(new Word(extras.getInt("id"),wordText,meaningText,spellingText,exampleText,extras.getInt("isLearning")));
            }else{
                db.addWord(new Word(wordText,meaningText,spellingText,exampleText,1));
            }
            finish();

        }
    }
}