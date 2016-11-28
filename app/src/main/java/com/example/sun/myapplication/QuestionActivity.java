package com.example.sun.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
    private TextView mScoreTextview;
    private TextView mQuestionTextView;
    private TextView mUsernameTextview;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private static final String KEY_INDEX = "index";
    private static final String USER_NAME = "username";
    private static final String SCORE = "score";
    private static final String QUESTION_NUM = "questionnum";
    private static final String TAG = "QuestionActivity";
    private int mScore = 0;
    private boolean isTrue = false;
    private int mNumcount = 1;
    private String mUsername = "无名氏";

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        // Log.d(TAG, "Updating question text for question #" + mCurrentIndex,
//                new Exception());
        mQuestionTextView.setText(question);
    }

    private void updateScore() {
        if(mScore < 0) {
            new AlertDialog.Builder(QuestionActivity.this)
                    .setTitle("你输啦")
                    .setMessage("您已经输了，积分清零，重新开始吧。")
                    .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent z = new Intent(QuestionActivity.this,LoginActivity.class);
                            startActivity(z);
                        }
                    }).show();
        } else {
            mScoreTextview.setText("得分:" + mScore);
        }
    }

    private boolean checkAnswer(Question q, boolean stat){
        if (q.isAnswerTrue() == stat) {
            mScore = mScore + 5;
            return true;
        } else {
            mScore = mScore - 5;
            return false;
        }
    }


    /*
       在处于 onStop onDestroy 时期时会调用如下函数，用来
       保存 Bundle 到 Oncreate 里的 saveintanceState 里供每次 onCreate 的 Activity 使用。
    */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveinstaceState loaded");
        outState.putInt(KEY_INDEX,mCurrentIndex);
        outState.putString(USER_NAME,mUsername);
        outState.putInt(SCORE,mScore);
        outState.putInt(QUESTION_NUM,mNumcount);
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

//        mCheatButton = (Button) findViewById(R.id.cheat_button);
//        mCheatButton.setOnClickListener(this);

        mScoreTextview = (TextView) findViewById(R.id.score_Tv);

        mUsernameTextview = (TextView) findViewById(R.id.user_TV);
        mUsername = getIntent().getStringExtra(USER_NAME);
        mUsernameTextview.setText("哈喽，" + mUsername + "，这是你的第" + mNumcount + "题");
//        mUsername = (TextView) findViewById(R.id.user_TV);
//        mUsername.setText(i.getBundleExtra("username").to);

        // 还原 InstanceState

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mUsername = savedInstanceState.getString(USER_NAME);
            mScore = savedInstanceState.getInt(SCORE);
            mNumcount = savedInstanceState.getInt(QUESTION_NUM);
            mUsernameTextview.setText("哈喽，" + mUsername + "，这是你的第" + mNumcount + "题");
        }

        // 初始化题目和分数

        updateScore();
        updateQuestion();
}
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.true_Btn:
                isTrue = checkAnswer(mQuestionBank[mCurrentIndex],true);
                ShowResult(isTrue,view);
                updateScore();
                break;
            case R.id.false_Btn:
                isTrue = checkAnswer(mQuestionBank[mCurrentIndex],false);
                ShowResult(isTrue,view);
                updateScore();
                break;
            case R.id.next_Btn:
                mNumcount++;
                mUsernameTextview.setText("哈喽，" + mUsername + "，这是你的第" + mNumcount + "题");
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }
    private void ShowResult(boolean istrue, View view) {
        if(istrue) {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("不错嘛你")
                    .setMessage("答对了，加5分")
                    .setPositiveButton("继续答题", null)
                    .show();
        } else {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("不行嘛你")
                    .setMessage("答错了，减5分")
                    .setPositiveButton("继续答题", null)
                    .setNegativeButton("偷看答案", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent z = new Intent(QuestionActivity.this, CheatActivity.class);
                            z.putExtra("answeristrue",mQuestionBank[mCurrentIndex].isAnswerTrue());
                            startActivity(z);
                        }
                    })
                    .show();
        }
    }
}
