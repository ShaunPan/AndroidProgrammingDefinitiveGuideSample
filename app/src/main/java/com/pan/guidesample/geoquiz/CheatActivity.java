package com.pan.guidesample.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pan.androidprogrammingdefinitiveguidesample.R;

/*
 * File Name:CheatActivity
 * Author:Pan
 * Date:2016/1/12 15:11
 * Description:
 */
public class CheatActivity extends Activity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.pan.guidesample.geoquiz.QuizActivity.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.pan.guidesample.geoquiz.QuizActivity.answer_shown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });

        mVersion = (TextView) findViewById(R.id.tv_version);
        //api版本号
        mVersion.setText(getText(R.string.api_level)+""+Build.VERSION.SDK_INT);
    }

    /**
     * 将查看答案的状态返回到上一页面
     * @param isAnswerShown 是否查看答案，
     */
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(CheatActivity.EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
