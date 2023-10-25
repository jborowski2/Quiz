package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_CURRENT_SCORE = "score";
    public static final String KEY_EXTRA_ANSWER = "com.example.myapplication.correctAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;
    private Button promptButton;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button listenerButton;
    private int score;
    private boolean answerWasShown;
    private TextView scoreView;
    private TextView questionTextView;
    private int currentIndex = 0;
    private Question[] questions = new Question[] {
            new Question(R.string.q_activity, false),
            new Question(R.string.q_find_resources, true),
            new Question(R.string.q_listener, false),
            new Question(R.string.q_resources, false),
            new Question(R.string.q_version, true)
    };
    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown){
            resultMessageId=R.string.answer_was_shown;
        }
        else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                score++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        scoreView.setText(Integer.toString(score));

    }
    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());

    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d("tag1", "Wywołana zostałą metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
        outState.putInt(KEY_CURRENT_SCORE, score);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){return;}
        if(resultCode == REQUEST_CODE_PROMPT) {
            if (data == null) {
                return;
            }
        }
        answerWasShown= data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("tag1", "Została wywołana metoda onStart");
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("tag1", "Została wywołana metoda onResume");
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("tag1", "Została wywołana metoda onPause");
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("tag1", "Została wywołana metoda onDestroy");
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("tag1", "Została wywołana metoda onCreate");
        if(savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            score = savedInstanceState.getInt(KEY_CURRENT_SCORE);

        }
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.prompt_button);
        questionTextView = findViewById(R.id.question_text_view);
        scoreView = findViewById(R.id.score_view);
        scoreView.setText(Integer.toString(score));
        setNextQuestion();
        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }

        });
        promptButton.setOnClickListener((v -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT,null);

        }));
        falseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);

            }

        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1)%questions.length;
                setNextQuestion();
                answerWasShown=false;
            }
        });

    }
}