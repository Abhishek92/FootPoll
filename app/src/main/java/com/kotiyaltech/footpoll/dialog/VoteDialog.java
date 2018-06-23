package com.kotiyaltech.footpoll.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.activity.PollResultsActivity;


/**
 * Created by hp pc on 05-05-2018.
 */

public class VoteDialog extends Dialog {
    private String teamName;
    private int pollId;
    private Context context;

    public VoteDialog(@NonNull Context context, String teamName, int pollId) {
        super(context);
        this.teamName = teamName;
        this.context = context;
        this.pollId = pollId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_dialog_layout);
        TextView cancelTxt = findViewById(R.id.cancel_txt);
        TextView seePollTxrt = findViewById(R.id.see_poll_txt);
        TextView dialogMessageTextView = findViewById(R.id.header_txt);
        String message = String.format(context.getString(R.string.dialog_txt), teamName);
        dialogMessageTextView.setText(message);

        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        seePollTxrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                PollResultsActivity.startActivity(context, pollId);
            }
        });

    }


}
