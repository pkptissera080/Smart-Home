package squadra.helix.smarthome;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Home extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private TextView tile_water_percentage_view,tile_pirith_status_view,Device_online_time_d_indicator,tile_light_status_view;
    private RelativeLayout progressbar_holder;

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
        setContentView(R.layout.activity_home);

        progressbar_holder =findViewById(R.id.progressbar_holder);

        Device_online_time_d_indicator =findViewById(R.id.Device_online_time_d_indicator);

        tile_water_percentage_view = findViewById(R.id.tile_water_percentage_view);
        tile_pirith_status_view = findViewById(R.id.tile_pirith_status_view);
        tile_light_status_view =findViewById(R.id.tile_light_status_view);


        progressbar_holder.setVisibility(View.VISIBLE);
        //-------------------------firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference WATER_watertank_capacity = database.getReference("WATER_watertank_capacity");
        WATER_watertank_capacity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer WATER_watertank_capacity = dataSnapshot.getValue(Integer.class);

                tile_water_percentage_view.setText(WATER_watertank_capacity.toString()+"%");
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

        DatabaseReference Pirith_system_remote_control = database.getReference("Pirith_system_remote_control");
        Pirith_system_remote_control.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Pirith_system_remote_control = dataSnapshot.getValue(Integer.class);

                if(Pirith_system_remote_control.equals(0)){
                    tile_pirith_status_view.setText("Auto");
                }else{
                    tile_pirith_status_view.setText("Manual");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference Lights_system_mode = database.getReference("Lights_system_mode");
        Lights_system_mode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Lights_system_mode = dataSnapshot.getValue(Integer.class);

                if(Lights_system_mode.equals(0)){
                    tile_light_status_view.setText("Auto");
                }else{
                    tile_light_status_view.setText("Manual");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void Switch_color() {
        Device_online_time_d_indicator.setTextColor(getResources().getColor(R.color.transparent));
    }

        private void logout_fun() {
        Paper.book().destroy();
        Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Home.this, Signin.class);
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

    }

    public void switch_to_water (View v) {

        Intent intent = new Intent(Home.this, Water.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_pirith (View v) {

        Intent intent = new Intent(Home.this, Pirith.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_light (View v) {
        Intent intent = new Intent(Home.this, Lights.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_device (View v) {
        Intent intent = new Intent(Home.this, Device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
