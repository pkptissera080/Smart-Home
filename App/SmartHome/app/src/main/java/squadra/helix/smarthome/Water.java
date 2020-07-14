package squadra.helix.smarthome;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Water extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextView water_act_water_percentage,water_status_message_text;
    private EditText WATER_watertank_end_water_level_input,WATER_watertank_critical_water_level_input;
    private RelativeLayout progressbar_holder;
    private LinearLayout set_input_layout,set_display_layout;
    private Button water_act_water_pump_btn;
    private TextView Device_online_time_d_indicator,WATER_watertank_end_water_level_display,WATER_watertank_critical_water_level_display;
    Handler handler =new Handler();
    Runnable runnable =new Runnable() {
        @Override
        public void run() {
            Switch_color();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        progressbar_holder =findViewById(R.id.progressbar_holder);
        Device_online_time_d_indicator =findViewById(R.id.Device_online_time_d_indicator);

        water_status_message_text = findViewById(R.id.water_status_message_text);
        water_act_water_pump_btn =findViewById(R.id.water_act_water_pump_btn);
        water_act_water_percentage = findViewById(R.id.water_act_water_percentage);

        set_display_layout = findViewById(R.id.set_display_layout);
        set_input_layout =findViewById(R.id.set_input_layout);

        WATER_watertank_end_water_level_display = findViewById(R.id.WATER_watertank_end_water_level_display);
        WATER_watertank_critical_water_level_display =findViewById(R.id.WATER_watertank_critical_water_level_display);

        WATER_watertank_end_water_level_input = findViewById(R.id.WATER_watertank_end_water_level_input);
        WATER_watertank_critical_water_level_input =findViewById(R.id.WATER_watertank_critical_water_level_input);



        progressbar_holder.setVisibility(View.VISIBLE);
        //-------------------------firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference WATER_watertank_capacity = database.getReference("WATER_watertank_capacity");
        WATER_watertank_capacity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Integer WATER_watertank_capacity = dataSnapshot.getValue(Integer.class);

                water_act_water_percentage.setText(WATER_watertank_capacity.toString()+"%");

                String txt_water_pump_btn = water_act_water_pump_btn.getText().toString().trim();

                if (txt_water_pump_btn.equals("on")){

                }
                else if (txt_water_pump_btn.equals("off")){
                    notify_me(1,"Water","Refilling in progress...      "+WATER_watertank_capacity+"%  ");
                }
                else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference WATER_waterpump_status = database.getReference("WATER_waterpump_status");
        WATER_waterpump_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Integer WATER_waterpump_status = dataSnapshot.getValue(Integer.class);
                if (WATER_waterpump_status.equals(0)){
                    water_act_water_pump_btn.setText("on");
                    water_act_water_pump_btn.setBackgroundResource(R.drawable.bg_off_btn);
                }
                else if (WATER_waterpump_status.equals(1)){
                    water_act_water_pump_btn.setText("off");
                    water_act_water_pump_btn.setBackgroundResource(R.drawable.bg_on_btn);
                }
                else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference WATER_system_status_message = database.getReference("WATER_system_status_message");
        WATER_system_status_message.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String WATER_system_status_message = dataSnapshot.getValue(String.class);

                water_status_message_text.setText(WATER_system_status_message);
                progressbar_holder.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Device_online_time = database.getReference("Device_online_time");
        Device_online_time.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Device_online_time = dataSnapshot.getValue(String.class);
                progressbar_holder.setVisibility(View.GONE);
                Device_online_time_d_indicator.setTextColor(getResources().getColor(R.color.medimumseqgreen));
                handler.postDelayed(runnable,4000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference WATER_watertank_end_water_level = database.getReference("WATER_watertank_end_water_level");
        WATER_watertank_end_water_level.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Integer WATER_watertank_end_water_level_val = dataSnapshot.getValue(Integer.class);

                WATER_watertank_end_water_level_input.setText(WATER_watertank_end_water_level_val.toString());
                WATER_watertank_end_water_level_display.setText(WATER_watertank_end_water_level_val.toString() + "%");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference WATER_watertank_critical_water_level = database.getReference("WATER_watertank_critical_water_level");
        WATER_watertank_critical_water_level.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Integer WATER_watertank_critical_water_level_val = dataSnapshot.getValue(Integer.class);

                WATER_watertank_critical_water_level_input.setText(WATER_watertank_critical_water_level_val.toString());
                WATER_watertank_critical_water_level_display.setText(WATER_watertank_critical_water_level_val.toString() + "%");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //-------------------------


    }


    public void Save_minimum_critical_water_level(View v){

        FirebaseDatabase database = FirebaseDatabase.getInstance();//Integer.parseInt

        DatabaseReference WATER_watertank_end_water_level = database.getReference("WATER_watertank_end_water_level");
        DatabaseReference WATER_watertank_critical_water_level = database.getReference("WATER_watertank_critical_water_level");

        String str_txt_WATER_watertank_end_water_level = WATER_watertank_end_water_level_input.getText().toString().trim();
        String str_txt_WATER_watertank_critical_water_level = WATER_watertank_critical_water_level_input.getText().toString().trim();

        if(str_txt_WATER_watertank_end_water_level.isEmpty()){
            WATER_watertank_end_water_level_input.setError("Requied");
            WATER_watertank_end_water_level_input.requestFocus();
            return;
        }
        if(str_txt_WATER_watertank_critical_water_level.isEmpty()){
            WATER_watertank_critical_water_level_input.setError("Requied");
            WATER_watertank_critical_water_level_input.requestFocus();
            return;
        }

        Integer txt_WATER_watertank_end_water_level = Integer.parseInt(str_txt_WATER_watertank_end_water_level);
        Integer txt_WATER_watertank_critical_water_level = Integer.parseInt(str_txt_WATER_watertank_critical_water_level);

        if(txt_WATER_watertank_end_water_level <= 0 || txt_WATER_watertank_end_water_level > 100 ){
            WATER_watertank_end_water_level_input.setError("invalid value!!");
            WATER_watertank_end_water_level_input.requestFocus();
            return;
        }
        if(txt_WATER_watertank_critical_water_level <= 0 || txt_WATER_watertank_critical_water_level > 90 ){
            WATER_watertank_critical_water_level_input.setError("invalid value!!");
            WATER_watertank_critical_water_level_input.requestFocus();
            return;
        }
        if(txt_WATER_watertank_critical_water_level >= txt_WATER_watertank_end_water_level ){
            WATER_watertank_critical_water_level_input.setError("Critical Water Level must be less than Max Water Level !!!");
            WATER_watertank_critical_water_level_input.requestFocus();
            return;
        }



        WATER_watertank_end_water_level.setValue(txt_WATER_watertank_end_water_level);
        WATER_watertank_critical_water_level.setValue(txt_WATER_watertank_critical_water_level);

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
        set_display_layout.setVisibility(View.VISIBLE);
        set_input_layout.setVisibility(View.GONE);

    }


    public void water_act_water_pump_btn_onClick (View v) {
        String txt_water_act_water_pump_btn = water_act_water_pump_btn.getText().toString().trim();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference WATER_waterpump_status = database.getReference("WATER_waterpump_status");


        if (txt_water_act_water_pump_btn.equals("on")){
            WATER_waterpump_status.setValue(1);
        }
        else if (txt_water_act_water_pump_btn.equals("off")){
            WATER_waterpump_status.setValue(0);
        }
        else {

        }
    }

    private void Switch_color() {
        Device_online_time_d_indicator.setTextColor(getResources().getColor(R.color.transparent));
    }

    public void notify_me(int notiid , String title, String text) {


        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.app_logo_gray)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.water))
                .setContentTitle(title)
                .setContentText(text)
                .setVisibility(0);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notiid, notificationBuilder.build());

    }



    public void switch_to_edit_settings (View v) {
        set_display_layout.setVisibility(View.GONE);
        set_input_layout.setVisibility(View.VISIBLE);
    }

    private void logout_fun() {
        Paper.book().destroy();
        Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Water.this, Signin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void more_options(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.option_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.logout:
                logout_fun();
                return true;
            default:
                return false;
        }
    }

    public void switch_to_overview (View v) {
        Intent intent = new Intent(Water.this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_water (View v) {

    }

    public void switch_to_pirith (View v) {

        Intent intent = new Intent(Water.this, Pirith.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_light (View v) {
        Intent intent = new Intent(Water.this, Lights.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_device (View v) {
        Intent intent = new Intent(Water.this, Device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
