package util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.rail.shuyun.R;

/**
 * Created by rail on 2016/6/29.
 */
public class CountDownTimerUtils extends CountDownTimer{
    private TextView mTextView;

    public CountDownTimerUtils(TextView textView,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView=textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);
        mTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        mTextView.setBackgroundResource(R.drawable.bg_identify_code_press);
        SpannableString spannableString=new SpannableString(mTextView.getText().toString());
        ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(foregroundColorSpan,0,2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
         mTextView.setText("重新获取验证码");
        mTextView.setClickable(true);
        mTextView.setBackgroundResource(R.drawable.bg_identify_code_normal);
    }
}
