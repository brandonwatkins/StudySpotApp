package com.example.android.studyspotapp.RecordedHours;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.studyspotapp.R;
import com.example.android.studyspotapp.StudySession;

import java.util.List;

/**
 * Created by brandonwatkins on 8/03/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<StudySession> sessionList;
    private View.OnLongClickListener longClickListener;
    private View.OnClickListener clickListener;
    private Context mContext;

    public void setContext(Context c){
        mContext = c;
    }

    // TODO Why isnt this method used?
    RecyclerViewAdapter(List<StudySession> sessionList,
                        View.OnLongClickListener longClickListener,
                        View.OnClickListener clickListener) {
        this.sessionList = sessionList;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {

        //Debt debt = debtList.get(position);
        StudySession session = sessionList.get(position);

        //Fill the cardview with the data about the study session
        holder.mSessionTitle.setText(session.getTitle());
        holder.mSessionTimeLbl.setText("Session Time:");
        holder.mSessionTime.setText(Double.toString(session.getSessionLength()));

        holder.itemView.setTag(session); // TODO What does this do?
        holder.itemView.setOnLongClickListener(longClickListener);
        holder.itemView.setOnClickListener(clickListener);
    }


    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    // TODO What does this method needed for?
    void addItems(List<StudySession> debtList) {
        this.sessionList = debtList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mSessionTitle;
        private TextView mSessionTimeLbl;
        private TextView mSessionTime;

        RecyclerViewHolder(View view) {
            super(view);
            mSessionTitle =   view.findViewById(R.id.tv_session_title);
            mSessionTimeLbl =   view.findViewById(R.id.tv_session_time_label);
            mSessionTime =   view.findViewById(R.id.tv_session_time);
        }
    }


}
