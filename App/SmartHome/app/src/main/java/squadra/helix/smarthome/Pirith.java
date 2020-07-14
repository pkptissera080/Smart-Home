package squadra.helix.smarthome;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Pirith extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private TextView pirith_status_val,toggle_eve_settings_status_val,toggle_mid_settings_status_val,eve_p_str_time,eve_p_end_time,mid_p_str_time,mid_p_end_time,mid_time_duration;
    private RelativeLayout progressbar_holder,eve_settings_layout,mid_settings_layout,eve_play_btn,eve_stop_btn,mid_play_btn,mid_stop_btn;
    private ImageView eve_set_ex_more_img,eve_set_ex_less_img,mid_set_ex_more_img,mid_set_ex_less_img;
    private TextView Device_online_time_d_indicator;

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
        setContentView(R.layout.activity_pirith);

        progressbar_holder =findViewById(R.id.progressbar_holder);
        Device_online_time_d_indicator =findViewById(R.id.Device_online_time_d_indicator);

        pirith_status_val = findViewById(R.id.pirith_status_val);
        toggle_eve_settings_status_val =findViewById(R.id.toggle_eve_settings_status_val);
        eve_settings_layout =findViewById(R.id.eve_settings_layout);
        toggle_mid_settings_status_val =findViewById(R.id.toggle_mid_settings_status_val);

        mid_settings_layout =findViewById(R.id.mid_settings_layout);
        eve_set_ex_more_img = findViewById(R.id.eve_set_ex_more_img);
        eve_set_ex_less_img = findViewById(R.id.eve_set_ex_less_img);
        mid_set_ex_more_img = findViewById(R.id.mid_set_ex_more_img);
        mid_set_ex_less_img = findViewById(R.id.mid_set_ex_less_img);

        eve_play_btn = findViewById(R.id.eve_play_btn);
        eve_stop_btn = findViewById(R.id.eve_stop_btn);
        mid_play_btn = findViewById(R.id.mid_play_btn);
        mid_stop_btn = findViewById(R.id.mid_stop_btn);

        eve_p_str_time = findViewById(R.id.eve_p_str_time);
        eve_p_end_time = findViewById(R.id.eve_p_end_time);
        mid_p_str_time = findViewById(R.id.mid_p_str_time);
        mid_p_end_time = findViewById(R.id.mid_p_end_time);
        mid_time_duration =findViewById(R.id.mid_time_duration);



        progressbar_holder.setVisibility(View.VISIBLE);
        //-------------------------firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference Pirith_system_remote_control = database.getReference("Pirith_system_remote_control");
        Pirith_system_remote_control.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Pirith_system_remote_control = dataSnapshot.getValue(Integer.class);
                progressbar_holder.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Pirith_system_status = database.getReference("Pirith_system_status");
        Pirith_system_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Pirith_system_status = dataSnapshot.getValue(Integer.class);
                pirith_status_val.setText(Pirith_system_status.toString());
                progressbar_holder.setVisibility(View.GONE);
                if (Pirith_system_status.equals(0)){
                    eve_play_btn.setVisibility(View.VISIBLE);
                    eve_stop_btn.setVisibility(View.GONE);
                    mid_play_btn.setVisibility(View.VISIBLE);
                    mid_stop_btn.setVisibility(View.GONE);
                }
                else if (Pirith_system_status.equals(1)){
                    eve_play_btn.setVisibility(View.GONE);
                    eve_stop_btn.setVisibility(View.VISIBLE);
                    mid_play_btn.setVisibility(View.VISIBLE);
                    mid_stop_btn.setVisibility(View.GONE);
                }
                else if (Pirith_system_status.equals(2)){
                    eve_play_btn.setVisibility(View.VISIBLE);
                    eve_stop_btn.setVisibility(View.GONE);
                    mid_play_btn.setVisibility(View.GONE);
                    mid_stop_btn.setVisibility(View.VISIBLE);
                }
                else{
                    eve_play_btn.setVisibility(View.VISIBLE);
                    eve_stop_btn.setVisibility(View.GONE);
                    mid_play_btn.setVisibility(View.VISIBLE);
                    mid_stop_btn.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Pirith_eve_str_time = database.getReference("Pirith_eve_str_time");
        Pirith_eve_str_time.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Pirith_eve_str_time_value = dataSnapshot.getValue(Integer.class);
                eve_p_str_time.setText(Pirith_eve_str_time_value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Pirith_eve_end_time = database.getReference("Pirith_eve_end_time");
        Pirith_eve_end_time.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Pirith_eve_end_time_value = dataSnapshot.getValue(Integer.class);
                eve_p_end_time.setText(Pirith_eve_end_time_value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Pirith_mid_str_time = database.getReference("Pirith_mid_str_time");
        Pirith_mid_str_time.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Pirith_mid_str_time_value = dataSnapshot.getValue(Integer.class);
                mid_p_str_time.setText(Pirith_mid_str_time_value.toString());
                findDuration();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Pirith_mid_end_time = database.getReference("Pirith_mid_end_time");
        Pirith_mid_end_time.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Pirith_mid_end_time_value = dataSnapshot.getValue(Integer.class);
                mid_p_end_time.setText(Pirith_mid_end_time_value.toString());
                findDuration();
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


        //-------------------------

    }

    private void saveEveSettings() {
        Integer eve_p_str_time_value = Integer.parseInt(eve_p_str_time.getText().toString().trim());
        Integer eve_p_end_time_value = Integer.parseInt(eve_p_end_time.getText().toString().trim());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Pirith_eve_str_time_value = database.getReference("Pirith_eve_str_time");
        DatabaseReference Pirith_eve_end_time_value = database.getReference("Pirith_eve_end_time");

        Pirith_eve_str_time_value.setValue(eve_p_str_time_value);
        Pirith_eve_end_time_value.setValue(eve_p_end_time_value);
    }

    private void saveMidSettings() {
        Integer mid_p_str_time_value = Integer.parseInt(mid_p_str_time.getText().toString().trim());
        Integer mid_p_end_time_value = Integer.parseInt(mid_p_end_time.getText().toString().trim());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Pirith_mid_str_time_value = database.getReference("Pirith_mid_str_time");
        DatabaseReference Pirith_mid_end_time_value = database.getReference("Pirith_mid_end_time");

        Pirith_mid_str_time_value.setValue(mid_p_str_time_value);
        Pirith_mid_end_time_value.setValue(mid_p_end_time_value);
    }

    private void findDuration() {
        Integer mid_p_str_time_value = Integer.parseInt(mid_p_str_time.getText().toString().trim());
        Integer mid_p_end_time_value = Integer.parseInt(mid_p_end_time.getText().toString().trim());
        Integer midduration = 0;

        if (mid_p_str_time_value > mid_p_end_time_value) {
            midduration = ((mid_p_end_time_value + 24) - mid_p_str_time_value);
        } else if (mid_p_str_time_value < mid_p_end_time_value) {
            midduration = (mid_p_end_time_value - mid_p_str_time_value);
        } else {

        }
        mid_time_duration.setText(midduration.toString());
    }

    public void plus_eve_value (View v) {
        Integer eve_p_str_time_value = Integer.parseInt(eve_p_str_time.getText().toString().trim());
        Integer eve_p_end_time_value = Integer.parseInt(eve_p_end_time.getText().toString().trim());


        if (eve_p_str_time_value.equals(0)) {
            eve_p_str_time.setText("1");
            eve_p_end_time.setText("2");
        } else if (eve_p_str_time_value.equals(22)) {
            eve_p_str_time.setText("23");
            eve_p_end_time.setText("0");
        } else if (eve_p_str_time_value.equals(23)) {
            eve_p_str_time.setText("0");
            eve_p_end_time.setText("1");
        } else {
            eve_p_str_time_value++;
            eve_p_end_time_value++;
            eve_p_str_time.setText(eve_p_str_time_value.toString());
            eve_p_end_time.setText(eve_p_end_time_value.toString());
        }
        saveEveSettings();

    }

    public void min_eve_value (View v) {
        Integer eve_p_str_time_value = Integer.parseInt(eve_p_str_time.getText().toString().trim());
        Integer eve_p_end_time_value = Integer.parseInt(eve_p_end_time.getText().toString().trim());


        if (eve_p_str_time_value.equals(0)) {
            eve_p_str_time.setText("23");
            eve_p_end_time.setText("0");
        } else if (eve_p_str_time_value.equals(23)) {
            eve_p_str_time.setText("22");
            eve_p_end_time.setText("23");
        } else {
            eve_p_str_time_value--;
            eve_p_end_time_value--;
            eve_p_str_time.setText(eve_p_str_time_value.toString());
            eve_p_end_time.setText(eve_p_end_time_value.toString());
        }

        saveEveSettings();
    }

    public void plus_mid_str_value (View v) {
        Integer mid_time_duration_value = Integer.parseInt(mid_time_duration.getText().toString().trim());
        Integer mid_p_str_time_value = Integer.parseInt(mid_p_str_time.getText().toString().trim());
        Integer mid_p_end_time_value = 0;

        if (1 <= mid_time_duration_value && mid_time_duration_value <= 7) {
            mid_p_str_time_value++;
            if (mid_p_str_time_value.equals(24)) {
                mid_p_str_time.setText("0");
                mid_p_end_time.setText("7");
            } else {
                mid_p_str_time.setText(mid_p_str_time_value.toString());
                if (mid_p_str_time_value >= 17) {
                    mid_p_end_time_value = ((mid_p_str_time_value) + 7) - 24;
                    mid_p_end_time.setText(mid_p_end_time_value.toString());
                } else {
                    mid_p_end_time_value = (mid_p_str_time_value) + 7;
                    mid_p_end_time.setText(mid_p_end_time_value.toString());
                }

            }
        } else {

        }
        findDuration();
        saveMidSettings();
    }

    public void min_mid_str_value (View v) {
        Integer mid_time_duration_value = Integer.parseInt(mid_time_duration.getText().toString().trim());
        Integer mid_p_str_time_value = Integer.parseInt(mid_p_str_time.getText().toString().trim());
        Integer mid_p_end_time_value = 0;

        if (1 <= mid_time_duration_value && mid_time_duration_value <= 7) {
            mid_p_str_time_value--;
            if (mid_p_str_time_value.equals(-1)) {
                mid_p_str_time.setText("23");
                mid_p_end_time.setText("6");
            } else {
                mid_p_str_time.setText(mid_p_str_time_value.toString());
                if (mid_p_str_time_value >= 17) {
                    mid_p_end_time_value = ((mid_p_str_time_value) + 7) - 24;
                    mid_p_end_time.setText(mid_p_end_time_value.toString());
                } else {
                    mid_p_end_time_value = (mid_p_str_time_value) + 7;
                    mid_p_end_time.setText(mid_p_end_time_value.toString());
                }

            }
        } else {

        }
        findDuration();
        saveMidSettings();
    }

    public void plus_mid_end_value (View v) {
        Integer mid_time_duration_value = Integer.parseInt(mid_time_duration.getText().toString().trim());
        Integer mid_p_end_time_value = Integer.parseInt(mid_p_end_time.getText().toString().trim());


        if (1 <= mid_time_duration_value && mid_time_duration_value < 7) {
            mid_p_end_time_value++;
            if (mid_p_end_time_value.equals(24)) {
                mid_p_end_time.setText("0");
            } else {
                mid_p_end_time.setText(mid_p_end_time_value.toString());
            }
        } else {

        }
        findDuration();
        saveMidSettings();
    }

    public void min_mid_end_value (View v) {
        Integer mid_time_duration_value = Integer.parseInt(mid_time_duration.getText().toString().trim());
        Integer mid_p_end_time_value = Integer.parseInt(mid_p_end_time.getText().toString().trim());

        if (1 < mid_time_duration_value && mid_time_duration_value <= 7) {
            mid_p_end_time_value--;
            if (mid_p_end_time_value.equals(-1)) {
                mid_p_end_time.setText("23");
            } else {
                mid_p_end_time.setText(mid_p_end_time_value.toString());
            }
        } else {

        }
        findDuration();
        saveMidSettings();
    }

    public void toggle_eve_settings (View v) {
        String toggle_eve_settings_status_val_value = toggle_eve_settings_status_val.getText().toString().trim();

        if (toggle_eve_settings_status_val_value.equals("1")){
            toggle_eve_settings_status_val.setText("0");
            eve_settings_layout.setVisibility(View.GONE);
            eve_set_ex_less_img.setVisibility(View.GONE);
            eve_set_ex_more_img.setVisibility(View.VISIBLE);
        }
        else if(toggle_eve_settings_status_val_value.equals("0")){
            toggle_eve_settings_status_val.setText("1");
            eve_settings_layout.setVisibility(View.VISIBLE);
            eve_set_ex_more_img.setVisibility(View.GONE);
            eve_set_ex_less_img.setVisibility(View.VISIBLE);
        }
        else{
        }
    }

    public void toggle_mid_settings (View v) {
        String toggle_mid_settings_status_val_value = toggle_mid_settings_status_val.getText().toString().trim();

        if (toggle_mid_settings_status_val_value.equals("1")){
            toggle_mid_settings_status_val.setText("0");
            mid_settings_layout.setVisibility(View.GONE);
            mid_set_ex_less_img.setVisibility(View.GONE);
            mid_set_ex_more_img.setVisibility(View.VISIBLE);
        }
        else if(toggle_mid_settings_status_val_value.equals("0")){
            toggle_mid_settings_status_val.setText("1");
            mid_settings_layout.setVisibility(View.VISIBLE);
            mid_set_ex_more_img.setVisibility(View.GONE);
            mid_set_ex_less_img.setVisibility(View.VISIBLE);
        }
        else{
        }
    }

    public void eve_pirith_plst(View v) {

        String pirith_status_val_value = pirith_status_val.getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Pirith_system_remote_control = database.getReference("Pirith_system_remote_control");

        if (pirith_status_val_value.equals("0")){
            Pirith_system_remote_control.setValue(1);
            Toast.makeText(getApplicationContext(), "Commencing Evening Pirith Chanting ! ", Toast.LENGTH_SHORT).show();
        }
        else if (pirith_status_val_value.equals("1")){
            Pirith_system_remote_control.setValue(0);
            Toast.makeText(getApplicationContext(), "Stooping Evening Pirith Chanting ! ", Toast.LENGTH_SHORT).show();
        }
        else if (pirith_status_val_value.equals("2")){
            Pirith_system_remote_control.setValue(1);
            Toast.makeText(getApplicationContext(), "Commencing Evening Pirith Chanting ! ", Toast.LENGTH_SHORT).show();
        }
        else{

        }
    }

    public void mid_pirith_plst(View v) {

        String pirith_status_val_value = pirith_status_val.getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Pirith_system_remote_control = database.getReference("Pirith_system_remote_control");

        if (pirith_status_val_value.equals("0")){
            Pirith_system_remote_control.setValue(2);
            Toast.makeText(getApplicationContext(), "Commencing Midnight Pirith Chanting ! ", Toast.LENGTH_SHORT).show();
        }
        else if (pirith_status_val_value.equals("2")){
            Pirith_system_remote_control.setValue(0);
            Toast.makeText(getApplicationContext(), "Stooping Midnight Pirith Chanting ! ", Toast.LENGTH_SHORT).show();
        }
        else if (pirith_status_val_value.equals("1")){
            Pirith_system_remote_control.setValue(2);
            Toast.makeText(getApplicationContext(), "Commencing Midnight Pirith Chanting ! ", Toast.LENGTH_SHORT).show();
        }
        else{

        }
    }

    private void Switch_color() {
        Device_online_time_d_indicator.setTextColor(getResources().getColor(R.color.transparent));
    }

    private void logout_fun() {
        Paper.book().destroy();
        Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Pirith.this, Signin.class);
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
        Intent intent = new Intent(Pirith.this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_water (View v) {
        Intent intent = new Intent(Pirith.this, Water.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_pirith (View v) {

    }

    public void switch_to_light (View v) {
        Intent intent = new Intent(Pirith.this, Lights.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_device (View v) {
        Intent intent = new Intent(Pirith.this, Device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
