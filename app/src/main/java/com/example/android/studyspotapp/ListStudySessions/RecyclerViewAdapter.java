package com.example.android.studyspotapp.ListStudySessions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.R;

import java.util.List;

/**
 * This class handles the list view of all of the study sessions
 * Created by Brandon Watkins
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<StudySession> studySessionList;
    private View.OnLongClickListener longClickListener;
    private View.OnClickListener clickListener;
    private Context mContext;

    public void setContext(Context c){
        mContext = c;
    }

    RecyclerViewAdapter(List<StudySession> studySessionList,
                        View.OnLongClickListener longClickListener,
                        View.OnClickListener clickListener) {
        this.studySessionList = studySessionList;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.study_session_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {

        StudySession studySession = studySessionList.get(position);

        String title = Long.toString(studySession.getDateAndTime());
        holder.titleTextView.setText(title);

        String sessionLength = Double.toString(studySession.getSessionLength());
        holder.sessionLengthTextView.setText(sessionLength);

        holder.amountTextView.setText("Place Holder");
        holder.itemView.setTag(studySession);
        holder.itemView.setOnLongClickListener(longClickListener);
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return studySessionList.size();
    }

    void addItems(List<StudySession> studySessionList) {
        this.studySessionList = studySessionList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView sessionLengthTextView;
        private TextView amountTextView;

        RecyclerViewHolder(View view) {
            super(view);
            titleTextView           =    view.findViewById(R.id.tv_title);
            sessionLengthTextView   =    view.findViewById(R.id.tv_sessionLength);
            amountTextView          =    view.findViewById(R.id.tv_amount);
        }
    }

}
