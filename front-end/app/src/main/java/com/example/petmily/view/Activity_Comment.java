package com.example.petmily.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.petmily.R;
import com.example.petmily.databinding.ActivityCommentBinding;
import com.example.petmily.model.data.post.remote.Result;
import com.example.petmily.viewModel.PostViewModel;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Activity_Comment extends AppCompatActivity {

    private ActivityCommentBinding binding;
    private Context context;
    private PostViewModel postViewModel;

    public String nickname;
    public String time;
    public String content;
    private int feedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment);
        binding.setComment(this);
        context = this;


        init();
    }
    public void init()
    {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        feedId = getIntent().getIntExtra("feedId", 0);
        postViewModel.getComments(feedId);

        Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        nickname = getIntent().getStringExtra("nickname");
        time = getIntent().getStringExtra("time");
        content = getIntent().getStringExtra("coment");
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postViewModel.addComment(feedId, binding.text.getText().toString());
                postViewModel.pushComment(binding.text.getText().toString(), feedId);
                binding.text.setText("");

            }
        });
        binding.content.setMovementMethod(LinkMovementMethod.getInstance());

        binding.content.setText(setTag(content));



    }
    public void initObserver()
    {

        final Observer<List<Result>> commentObserver = new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable final List<Result> results) {
                Adapter_Comment adapter_comment = new Adapter_Comment(results);
                binding.comments.setAdapter(adapter_comment);
            }
        };
        postViewModel.getCommentList().observe(this, commentObserver);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private SpannableString setTag(String tag) {
        int i;

        ArrayList<int[]> hashTag = getSpans(tag, '#', '@');

        SpannableString tagsContent = new SpannableString(tag);

        for (i = 0; i < hashTag.size(); i++) {
            int[] hashSpan = hashTag.get(i);
            int hashTagStart = hashSpan[0]-1;
            int hashTagEnd = hashSpan[1];

            Adapter_PostFull.HashTag hash = new Adapter_PostFull.HashTag(context);
            hash.setOnClickEventListener(new Adapter_PostFull.HashTag.ClickEventListener() {
                @Override
                public void onClickEvent(String data) {
                    if(data.charAt(0) == '#')
                    {

                    }
                    else if(data.charAt(0) == '@')
                    {
                        Intent intent = new Intent(context, Activity_Profile.class);
                        intent.putExtra("userId", data.substring(1, data.length()));
                        context.startActivity(intent);
                    }
                    Log.e("클릭 : ", data);
                }
            });

            tagsContent.setSpan(hash, hashTagStart, hashTagEnd, 0);
        }


        return tagsContent;
    }

    public ArrayList<int[]> getSpans(String body, char prefix, char prefix2) {
        ArrayList<int[]> spans = new ArrayList<int[]>();

        Pattern pattern = Pattern.compile(prefix + "\\w+");

        Pattern pattern2 = Pattern.compile(prefix2 + "\\w+");

        Matcher matcher = pattern.matcher(body);

        // Check all occurrences
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }
        matcher = pattern2.matcher(body);
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }

        return  spans;
    }
    public static class HashTag extends ClickableSpan {

        public interface ClickEventListener{
            void onClickEvent(String data);
        }

        private Adapter_PostFull.HashTag.ClickEventListener mClickEventListener = null;

        Context context;
        TextPaint textPaint;

        public HashTag(Context context) {
            super();
            this.context = context;
        }

        public void setOnClickEventListener(Adapter_PostFull.HashTag.ClickEventListener listener)
        {
            mClickEventListener = listener;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            textPaint = ds;
            ds.setColor(ds.linkColor);
            ds.setARGB(255, 30, 144, 255);
        }

        @Override
        public void onClick(View widget) {
            TextView tv = (TextView) widget;
            Spanned s = (Spanned) tv.getText();
            int start = s.getSpanStart(this);
            int end = s.getSpanEnd(this);
            String theWord = s.subSequence(start + 1, end).toString();
            mClickEventListener.onClickEvent(theWord);
        }
    }

}
