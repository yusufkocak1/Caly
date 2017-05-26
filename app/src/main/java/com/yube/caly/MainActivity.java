package com.yube.caly;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.yube.caly.mongoAdapter.getDataAdapter;
import com.yube.caly.mongoAdapter.setDataAdapter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private HorizontalCalendar horizontalCalendar;
    EditText dailyET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        visualItem();



        Bundle extras = getIntent().getExtras();
        String newString;
        if (extras == null) {
            newString = null;
        } else {
            newString = extras.getString("mesaj");
            viewDialog dialog = new viewDialog();
            dialog.showdialog(this, newString);
        }
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        /** end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        /** start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        final Calendar defaultDate = Calendar.getInstance();
        defaultDate.add(Calendar.MONTH, -1);
        defaultDate.add(Calendar.DAY_OF_WEEK, +5);

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .showDayName(true)
                .showMonthName(true)
                .defaultSelectedDate(defaultDate.getTime())
                .textColor(Color.LTGRAY, Color.WHITE)
                .selectedDateBackground(Color.TRANSPARENT)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                Toast.makeText(MainActivity.this, DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
                new getDataAdapter(MainActivity.this).execute("http://192.168.0.150:1000/api/status");
            }

        });
        horizontalCalendar.goToday(false);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalCalendar.goToday(false);
            }
        });

    }

    private void visualItem() {
        dailyET=(EditText) findViewById(R.id.daily);
    }

    public void sign_out(MenuItem item) {
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FirebaseAuth auth;
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            }
        });
    }

    public void appSettings(MenuItem item) {
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "Yakında..", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void hesapayarlari(MenuItem item) {
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            }
        });
    }

    public void savebtn(View view) {
        viewDialog dialog=new viewDialog();
        setDataAdapter mongoobject=new setDataAdapter();
        if(dailyET.getText().toString()!="") {

            new setDataAdapter().execute("http://192.168.0.150:1000/api/status",dailyET.getText().toString(),DateFormat.getDateInstance().format(new Date()));;
            dialog.showdialog(MainActivity.this,"kayıt başarılı");
        }
        else {
            dialog.showdialog(MainActivity.this,"Lütfen Günlüğünüzü boş bırakmayın");

        }
    }


    class viewDialog {

        public void showdialog(Activity activity, String message) {
            final Dialog dialog = new Dialog(activity);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.notification_alert);
            Button okbntn = (Button) dialog.findViewById(R.id.okbtn);
            TextView mesagetext = (TextView) dialog.findViewById(R.id.Message);
            mesagetext.setText(message);

            okbntn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }
}
