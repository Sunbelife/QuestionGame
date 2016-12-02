package com.example.sun.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

// 控制器

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    // 成员初始化
    // 布局部分
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mBackButton;
    private TextView mScoreTextview;
    private TextView mQuestionTextView;
    private TextView mUsernameTextview;
    private TextView mCheatCountTextView;
    private TextView mIsaCheaterTextView;

    // 数据部分
    private int mCurrentIndex = 0;

    // Intent Tag
    private static final String KEY_INDEX = "index";
    private static final String USER_NAME = "username";
    private static final String SCORE = "score";
    private static final String QUESTION_NUM = "questionnum";

    // Log Tag
    private static final String TAG = "QuestionActivity";
    private int mScore = 0;
    private boolean isTrue = false;
    private int mCheatnum = 0; // 作弊次数
    private int mNumcount = 1; // 答题数量
    private String mUsername = "无名氏";

    // 检测作弊
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;

    // 题目初始化
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private void setQuestion(int mQuestion) {
        mQuestionTextView.setText(mQuestionBank[mQuestion].getTextResId());
    }

    private void checkScore(int mScore) {
        if(mScore < 0) {
            new AlertDialog.Builder(QuestionActivity.this)
                    .setTitle("你输啦")
                    .setMessage("您已经输了，积分清零，重新开始吧。")
                    .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent z = new Intent(QuestionActivity.this, LoginActivity.class);
                            startActivity(z);
                        }
                    }).show();
        } else {
            mScoreTextview.setText("得分:" + mScore);
        }
    }
    private void checkQuestionNum(int mQuestionnum) {
        if (mQuestionnum == 0) {
            mBackButton.setVisibility(View.INVISIBLE);
        } else {
            mBackButton.setVisibility(View.VISIBLE);
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

        init(); // 初始化组件

        // 还原 InstanceState
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mUsername = savedInstanceState.getString(USER_NAME);
            mScore = savedInstanceState.getInt(SCORE);
            mNumcount = savedInstanceState.getInt(QUESTION_NUM);
            mUsernameTextview.setText("哈喽，" + mUsername + "，这是你的第" + mNumcount + "题");
        }

}
    public void onClick(View view) {
        checkCheater(mIsCheater,mCheatnum);
        switch (view.getId()) {
            case R.id.true_Btn:
                isTrue = checkAnswer(mQuestionBank[mCurrentIndex],true);
                ShowResult(isTrue,view);
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                setQuestion((mCurrentIndex) % mQuestionBank.length);
                break;
            case R.id.false_Btn:
                isTrue = checkAnswer(mQuestionBank[mCurrentIndex],false);
                ShowResult(isTrue,view);
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                setQuestion((mCurrentIndex) % mQuestionBank.length);
                break;
            case R.id.next_Btn:
                mUsernameTextview.setText("哈喽，" + mUsername + "，这是你的第" + mNumcount + "题");
            case R.id.question_text_view:
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                setQuestion((mCurrentIndex) % mQuestionBank.length);
                break;
            case R.id.back_Btn:
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                setQuestion((mCurrentIndex) % mQuestionBank.length);
                break;
            default:
                break;
        }
        mNumcount++;
        mUsernameTextview.setText("哈喽，" + mUsername + "，这是你的第" + mNumcount + "题");
        checkScore(mScore);
        checkQuestionNum(mCurrentIndex);
        Log.d(TAG,mCurrentIndex+"");
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

        // 恢复分数和题目

        Log.d(TAG,"mCurrentIndex:" + mCurrentIndex);

        checkScore(mScore);
        setQuestion(mCurrentIndex);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }


    // 处理 CheatActivity 的返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) { // 如果未返回正确，直接返回。
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) { // 判断返回状态
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data); // 检测作弊状态
            if (mIsCheater) {
                mCheatnum++;
            }
        }
    }

    // 初始化组件
    protected void init() {
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        mQuestionTextView.setOnClickListener(this);

        mTrueButton = (Button) findViewById(R.id.true_Btn);
        mTrueButton.setOnClickListener(this);
        mFalseButton = (Button) findViewById(R.id.false_Btn);
        mFalseButton.setOnClickListener(this);

        mBackButton = (Button) findViewById(R.id.back_Btn);
        mBackButton.setVisibility(View.INVISIBLE);
        mBackButton.setOnClickListener(this);
        mNextButton = (Button) findViewById(R.id.next_Btn);
        mNextButton.setOnClickListener(this);

//        mCheatButton = (Button) findViewById(R.id.cheat_button);
//        mCheatButton.setOnClickListener(this);

        mScoreTextview = (TextView) findViewById(R.id.score_Tv);
        mScoreTextview.setText("得分: 0");

        mUsernameTextview = (TextView) findViewById(R.id.user_TV);
        mUsername = getIntent().getStringExtra(USER_NAME);
        mUsernameTextview.setText("哈喽，" + mUsername + "，这是你的第" + mNumcount + "题");

        mCheatCountTextView = (TextView) findViewById(R.id.cheat_Tv);

        mCheatCountTextView.setText("您的作弊次数:" + mCheatnum);

        mIsaCheaterTextView = (TextView) findViewById(R.id.isaCheater_Tv);

//        mUsername = (TextView) findViewById(R.id.user_TV);
//        mUsername.setText(i.getBundleExtra("username").to);
    }

    private void ShowResult(boolean answeristrue, View view) {
        if(answeristrue) {
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
                            Intent z = CheatActivity.newIntent(QuestionActivity.this,mQuestionBank[mCurrentIndex].isAnswerTrue());
                            startActivityForResult(z, REQUEST_CODE_CHEAT);
                        }
                    })
                    .show();
        }
    }

    private void checkCheater(boolean isCheater, int mCheatCount) {
        if (!isCheater) {
            mIsaCheaterTextView.setVisibility(View.INVISIBLE);
            mCheatCountTextView.setVisibility(View.INVISIBLE);
            return;
        } else {
            mIsaCheaterTextView.setVisibility(View.VISIBLE);
            mCheatCountTextView.setVisibility(View.VISIBLE);
        }
        this.mIsCheater = isCheater;
        this.mCheatnum = mCheatCount;
        mIsaCheaterTextView.setText(R.string.isacheater_str);
        mCheatCountTextView.setText(mCheatnum + "");
    }
}
