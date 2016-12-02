package com.example.sun.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean mAnswer;
    private Button mShowAnswers;
    private TextView mAnswer_TV;
    private Button mBackToQues;
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.sun.myapplication.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.example.sun.myapplication.answer_shown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mShowAnswers = (Button) findViewById(R.id.showAnswerBtn);
        mShowAnswers.setOnClickListener(this);
        mBackToQues = (Button) findViewById(R.id.backtoQues_Btn);
        mBackToQues.setOnClickListener(this);

        mAnswer_TV = (TextView) findViewById(R.id.answer_Tv);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showAnswerBtn:
                mAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
                if (mAnswer) {
                    mAnswer_TV.setText(R.string.strue_Btn);
                } else {
                    mAnswer_TV.setText(R.string.sfalse_Btn);
                }
                break;
            case R.id.backtoQues_Btn:
                finish();
                break;
            default:
                break;
        }
        setAnswerShownResult(true);
    }

    // 这个方法实现了在这个类中声明绑定到这一 Activity 的 Intent，这样在别的 Activity 里只需要调用这个方法
    // 就可以得到这个 Intent,而且在下面的代码中，Extra 的标识符也不用在其他地方搞了，而且修改起来也很简单。

    public static Intent newIntent(Context packageContext, boolean answerIsTrue)  {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }


    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
