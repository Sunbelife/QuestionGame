package com.example.sun.myapplication;

import android.content.Intent;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// 控制器

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mBackButton;
    private Button mCheatButton;
    private TextView mUsername;
    private TextView mScoreTextview;
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
    private static final String KEY_INDEX = "index";
    private static final String TAG = "QuestionActivity";
    private static int score = 0;
//    private Intent i;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        // Log.d(TAG, "Updating question text for question #" + mCurrentIndex,
//                new Exception());
        mQuestionTextView.setText(question);
    }

    private void updateScore() {
        mScoreTextview.setText("得分:" + score);
    }

    private int checkAnswer(Question q, boolean stat){
        if (q.isAnswerTrue() == stat) {
            score = score + 5;
            return R.string.correct_toast;
        } else {
            score = score - 5;
            return R.string.incorrect_toast;
        }
    }


    /*
       在处于 onStop onDestroy 时期时会调用如下函数，用来
       保存 Bundle 到 SaveintanceState 里供每次 onCreate 的 Activity 使用。
    */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveinstaceState loaded");
        outState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

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

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(this);

        mScoreTextview = (TextView) findViewById(R.id.score_text_view);


//        mUsername = (TextView) findViewById(R.id.user_TV);
//        mUsername.setText(i.getBundleExtra("username").to);



        updateQuestion();
        updateScore();

        Log.d(TAG,"onCreate Bundle Loaded");
}

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart Bundle Loaded");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy Bundle Loaded");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop Bundle Loaded");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume Bundle Loaded");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause Bundle Loaded");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.true_Btn:
                isTrue = checkAnswer(mQuestionBank[mCurrentIndex],true);
                Toast.makeText(view.getContext(),isTrue,Toast.LENGTH_SHORT).show();
                updateScore();
                break;
            case R.id.false_Btn:
                isTrue = checkAnswer(mQuestionBank[mCurrentIndex],false);
                Toast.makeText(view.getContext(),isTrue,Toast.LENGTH_SHORT).show();
                updateScore();
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
            case R.id.cheat_button:

                break;
            default:
                break;
        }
    }
}
