package squadra.helix.smarthome;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Lights extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private RelativeLayout light_1_off,light_1_on,light_2_off,light_2_on,light_3_off,light_3_on,light_4_off,light_4_on;
    private RelativeLayout progressbar_holder;
    private TextView light1,light2,light3,light4,lightscountdisplay,lights_mode;
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
        setContentView(R.layout.activity_lights);

        progressbar_holder =findViewById(R.id.progressbar_holder);
        Device_online_time_d_indicator =findViewById(R.id.Device_online_time_d_indicator);

        light1 =findViewById(R.id.light1);
        light2 =findViewById(R.id.light2);
        light3 =findViewById(R.id.light3);
        light4 =findViewById(R.id.light4);
        lightscountdisplay =findViewById(R.id.lightscountdisplay);
        lights_mode =findViewById(R.id.lights_mode);

        light_1_off =findViewById(R.id.light_1_off);
        light_1_on =findViewById(R.id.light_1_on);
        light_2_off =findViewById(R.id.light_2_off);
        light_2_on =findViewById(R.id.light_2_on);
        light_3_off =findViewById(R.id.light_3_off);
        light_3_on =findViewById(R.id.light_3_on);
        light_4_off =findViewById(R.id.light_4_off);
        light_4_on =findViewById(R.id.light_4_on);


        progressbar_holder.setVisibility(View.VISIBLE);
        //-------------------------firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference Lights_system_mode = database.getReference("Lights_system_mode");
        Lights_system_mode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Lights_system_mode = dataSnapshot.getValue(Integer.class);
                progressbar_holder.setVisibility(View.GONE);
                if(Lights_system_mode.equals(1)){
                    lights_mode.setText("Manual Mode");
                }
                else{
                    lights_mode.setText("Auto Mode");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Lights_bulb_1_status = database.getReference("Lights_bulb_1_status");
        Lights_bulb_1_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Lights_bulb_1_status = dataSnapshot.getValue(Integer.class);
                light1.setText(Lights_bulb_1_status.toString());
                countOnLights();

                if (Lights_bulb_1_status.equals(1)) {
                    light_1_off.setVisibility(View.GONE);
                    light_1_on.setVisibility(View.VISIBLE);
                } else {
                    light_1_off.setVisibility(View.VISIBLE);
                    light_1_on.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Lights_bulb_2_status = database.getReference("Lights_bulb_2_status");
        Lights_bulb_2_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Lights_bulb_2_status = dataSnapshot.getValue(Integer.class);
                light2.setText(Lights_bulb_2_status.toString());
                countOnLights();
                if (Lights_bulb_2_status.equals(1)) {
                    light_2_off.setVisibility(View.GONE);
                    light_2_on.setVisibility(View.VISIBLE);
                } else {
                    light_2_off.setVisibility(View.VISIBLE);
                    light_2_on.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Lights_bulb_3_status = database.getReference("Lights_bulb_3_status");
        Lights_bulb_3_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Lights_bulb_3_status = dataSnapshot.getValue(Integer.class);
                light3.setText(Lights_bulb_3_status.toString());
                countOnLights();
                if (Lights_bulb_3_status.equals(1)) {
                    light_3_off.setVisibility(View.GONE);
                    light_3_on.setVisibility(View.VISIBLE);
                } else {
                    light_3_off.setVisibility(View.VISIBLE);
                    light_3_on.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Lights_bulb_4_status = database.getReference("Lights_bulb_4_status");
        Lights_bulb_4_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Lights_bulb_4_status = dataSnapshot.getValue(Integer.class);
                light4.setText(Lights_bulb_4_status.toString());
                countOnLights();
                if (Lights_bulb_4_status.equals(1)) {
                    light_4_off.setVisibility(View.GONE);
                    light_4_on.setVisibility(View.VISIBLE);
                } else {
                    light_4_off.setVisibility(View.VISIBLE);
                    light_4_on.setVisibility(View.GONE);
                }
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

    private void countOnLights() {
        Integer light1_value = Integer.parseInt(light1.getText().toString().trim());
        Integer light2_value = Integer.parseInt(light2.getText().toString().trim());
        Integer light3_value = Integer.parseInt(light3.getText().toString().trim());
        Integer light4_value = Integer.parseInt(light4.getText().toString().trim());

        Integer lightCount = 0;
        if(light1_value.equals(1)){
            lightCount++;
        }
        if(light2_value.equals(1)){
            lightCount++;
        }
        if(light3_value.equals(1)){
            lightCount++;
        }
        if(light4_value.equals(1)){
            lightCount++;
        }

        lightscountdisplay.setText(lightCount.toString());

    }


    public void toggle_light_1 (View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Lights_bulb_1_status = database.getReference("Lights_bulb_1_status");
        Integer light1_value = Integer.parseInt(light1.getText().toString().trim());
        if (light1_value.equals(0)){
            Lights_bulb_1_status.setValue(1);
            DatabaseReference Lights_system_mode = database.getReference("Lights_system_mode");
            Lights_system_mode.setValue(1);
        }else{
            Lights_bulb_1_status.setValue(0);
        }
    }
    public void toggle_light_2 (View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Lights_bulb_2_status = database.getReference("Lights_bulb_2_status");
        Integer light2_value = Integer.parseInt(light2.getText().toString().trim());
        if (light2_value.equals(0)){
            Lights_bulb_2_status.setValue(1);
            DatabaseReference Lights_system_mode = database.getReference("Lights_system_mode");
            Lights_system_mode.setValue(1);
        }else{
            Lights_bulb_2_status.setValue(0);
        }
    }
    public void toggle_light_3 (View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Lights_bulb_3_status = database.getReference("Lights_bulb_3_status");
        Integer light3_value = Integer.parseInt(light3.getText().toString().trim());
        if (light3_value.equals(0)){
            Lights_bulb_3_status.setValue(1);
            DatabaseReference Lights_system_mode = database.getReference("Lights_system_mode");
            Lights_system_mode.setValue(1);
        }else{
            Lights_bulb_3_status.setValue(0);
        }
    }
    public void toggle_light_4 (View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Lights_bulb_4_status = database.getReference("Lights_bulb_4_status");
        Integer light4_value = Integer.parseInt(light4.getText().toString().trim());
        if (light4_value.equals(0)){
            Lights_bulb_4_status.setValue(1);
            DatabaseReference Lights_system_mode = database.getReference("Lights_system_mode");
            Lights_system_mode.setValue(1);
        }else{
            Lights_bulb_4_status.setValue(0);
        }
    }

    private void Switch_color() {
        Device_online_time_d_indicator.setTextColor(getResources().getColor(R.color.transparent));
    }

    private void logout_fun() {
        Paper.book().destroy();
        Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Lights.this, Signin.class);
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
        Intent intent = new Intent(Lights.this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_water (View v) {
        Intent intent = new Intent(Lights.this, Water.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_pirith (View v) {
        Intent intent = new Intent(Lights.this, Pirith.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_light (View v) {

    }

    public void switch_to_device (View v) {
        Intent intent = new Intent(Lights.this, Device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
