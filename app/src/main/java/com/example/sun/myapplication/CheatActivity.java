package com.example.sun.myapplication;

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
                mAnswer = getIntent().getBooleanExtra("answeristrue",false);
                if (mAnswer) {
                    mAnswer_TV.setText(R.string.strue_Btn);
                } else {
                    mAnswer_TV.setText(R.string.sfalse_Btn);
                }
                break;
            case R.id.backtoQues_Btn:
                Intent backtoQuest = new Intent(v.getContext(),QuestionActivity.class);
                startActivity(backtoQuest);
                break;
            default:
                break;
        }
    }
}
