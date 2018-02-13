package com.example.android.studyspotapp.Tabs;

//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.example.android.studyspotapp.R;

/**
 * Created by brandonwatkins on 9/02/18.
 */

public class TabThisWeek extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_this_week, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("This week");
        return rootView;
    }
}
