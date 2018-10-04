package com.example.destr.weather20;


import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import java.util.jar.Attributes;


public class DelayAutoCompleteTextView extends AutoCompleteTextView {

    private static final int message_text_changed = 100;
    private static final int default_autocomplete_delay = 750;

    private int mautocompletedelay = default_autocomplete_delay;
    private ProgressBar mLoadingIndicator;

    private android.os.Handler mHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };

    public DelayAutoCompleteTextView(Context context) {
        super(context);
    }

    public DelayAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DelayAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DelayAutoCompleteTextView(Context context, Attributes attrs) {
        super(context, (AttributeSet) attrs);
    }

    public void setLoadingIndicator(ProgressBar progressBar) {
        mLoadingIndicator = progressBar;
    }

    public void setAutoCompleteDelay(int autoCompleteDelay) {
        mautocompletedelay = autoCompleteDelay;
    }

    @Override
    protected void performFiltering(CharSequence text, int KeyCode) {
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        mHandler.removeMessages(message_text_changed);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(message_text_changed, text), mautocompletedelay);
    }

    @Override
    public void onFilterComplete(int count){
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setVisibility(View.GONE);
        }
        super.onFilterComplete(count);
    }
}
