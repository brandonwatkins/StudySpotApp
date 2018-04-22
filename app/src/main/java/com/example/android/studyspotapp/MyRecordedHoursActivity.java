package com.example.android.studyspotapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.studyspotapp.Database.StudySpotDb;
import com.example.android.studyspotapp.Database.Tasks.GetWeeklyTotalStudySessionTask;
import com.example.android.studyspotapp.ListStudySessions.ListOverallStudySessionFragment;
import com.example.android.studyspotapp.ListStudySessions.ListStudySessionFragment;
import com.example.android.studyspotapp.ListStudySessions.ListThisWeekStudySessionFragment;
import com.example.android.studyspotapp.Tabs.TabAllSessions;
import com.example.android.studyspotapp.Tabs.TabThisWeek;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MyRecordedHoursActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recorded_hours);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending Recorded Hours...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Date date = new Date();
                String timeStamp = new SimpleDateFormat("MM-dd-yyyy").format(date);

                String fileName = "StudySpot_" + timeStamp + ".pdf";

                long weeklyTotal;
                long hoursRemaining;
                boolean hadEnoughHours = true;

                try {
                    String totalHoursCompleted;
                    String totalHoursRemaining;

                    weeklyTotal = new GetWeeklyTotalStudySessionTask(StudySpotDb.getDatabase(view.getContext())).execute().get();
                    totalHoursCompleted = String.format("%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(weeklyTotal),
                            TimeUnit.MILLISECONDS.toMinutes(weeklyTotal) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(weeklyTotal)),
                            TimeUnit.MILLISECONDS.toSeconds(weeklyTotal) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(weeklyTotal)));

                    hoursRemaining = 28800000 - weeklyTotal;

                    //Student-athlete does NOT have enough hours
                    if (hoursRemaining > 0) {
                        hadEnoughHours = false;
                    } else { //Student-athlete has EXTRA hours
                        long totalExtraHours = Math.abs(hoursRemaining);
                        hoursRemaining = totalExtraHours;
                    }

                    totalHoursRemaining = String.format("%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(hoursRemaining),
                            TimeUnit.MILLISECONDS.toMinutes(hoursRemaining) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(hoursRemaining)),
                            TimeUnit.MILLISECONDS.toSeconds(hoursRemaining) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(hoursRemaining)));

                    new pdfUtils().write(fileName, totalHoursRemaining, totalHoursCompleted, hadEnoughHours);

                } catch (InterruptedException i) {
                    i.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                String TO, SUBJECT, MESSAGE;

                File fileLocation = new File("/storage/emulated/0/Documents/StudySpotPDF/" + fileName);

                Uri path = Uri.fromFile(fileLocation);

                Intent emailIntent;

                SUBJECT = "StudySpot Weekly Report";
                MESSAGE = "Test Message";
                TO = "brandonwatkinsnz@gmail.com";
                emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{TO});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                emailIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
                // The attachment
                emailIntent.putExtra(Intent.EXTRA_STREAM, path);

                emailIntent.setType("message/rfc822");

                startActivity(Intent.createChooser(emailIntent, "Select Email Sending App:"));

            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_recorded_hours, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.home) {
            // Ends this activity
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {
                case 0:
//                    TabThisWeek tabThisWeek = new TabThisWeek();
//                    return tabThisWeek;
                    return new ListThisWeekStudySessionFragment();
                case 1:
//                    TabAllSessions tabAllSessions = new TabAllSessions();
//                    return tabAllSessions;
                    return new ListOverallStudySessionFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
