package com.example.sun.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// 控制器

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mBackButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private int isTrue;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private int checkAnswer(Question q, boolean stat){
        if (q.isAnswerTrue() == stat) {
            return R.string.correct_toast;
        } else {
            return R.string.incorrect_toast;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(this);

        mTrueButton = (Button) findViewById(R.id.true_Btn);
        mTrueButton.setOnClickListener(this);
        mFalseButton = (Button) findViewById(R.id.false_Btn);
        mFalseButton.setOnClickListener(this);

        mBackButton = (Button) findViewById(R.id.back_Btn);
        mBackButton.setOnClickListener(this);
        mNextButton = (Button) findViewById(R.id.next_Btn);
        mNextButton.setOnClickListener(this);

        updateQuestion();
}

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.true_Btn:
                isTrue = checkAnswer(mQuestionBank[mCurrentIndex],true);
                Toast.makeText(view.getContext(),isTrue,Toast.LENGTH_SHORT).show();
                break;
            case R.id.false_Btn:
                isTrue = checkAnswer(mQuestionBank[mCurrentIndex],false);
                Toast.makeText(view.getContext(),isTrue,Toast.LENGTH_SHORT).show();
                break;
            case R.id.next_Btn:
            case R.id.question_text_view:
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                break;
            case R.id.back_Btn:
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
                break;
            default:
                break;
        }
    }
}
