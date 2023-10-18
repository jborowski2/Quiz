package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private int score;
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
        if(userAnswer == correctAnswer){
            resultMessageId = R.string.correct_answer;
            score++;
        } else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        scoreView.setText(Integer.toString(score));
    }
    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());

    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        scoreView = findViewById(R.id.score_view);
        setNextQuestion();
        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }

        });
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
            }
        });

    }
}