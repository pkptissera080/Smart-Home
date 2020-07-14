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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

import io.paperdb.Paper;

public class Device extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextView Device_online_time_d_indicator,lst_time_display,user_email_dis;
    private RelativeLayout progressbar_holder;
    private FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_device);

        progressbar_holder =findViewById(R.id.progressbar_holder);

        user_email_dis =findViewById(R.id.user_email_dis);
        Device_online_time_d_indicator = findViewById(R.id.Device_online_time_d_indicator);
        lst_time_display = findViewById(R.id.lst_time_display);

        progressbar_holder.setVisibility(View.VISIBLE);
        //-------------------------firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            user_email_dis.setText(email);
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference Device_online_time = database.getReference("Device_online_time");
        Device_online_time.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Device_online_time = dataSnapshot.getValue(String.class);
                progressbar_holder.setVisibility(View.GONE);

                lst_time_display.setBackgroundResource(R.color.white);
                lst_time_display.setTextColor(getResources().getColor(R.color.darkGray));
                Device_online_time_d_indicator.setTextColor(getResources().getColor(R.color.medimumseqgreen));
                lst_time_display.setText(Device_online_time);
                handler.postDelayed(runnable,4000);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference Device_restart = database.getReference("Device_restart");
        Device_restart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer Device_restart_val = dataSnapshot.getValue(Integer.class);
                if(Device_restart_val.equals(0)){
                    progressbar_holder.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //-------------------------

    }

    public void rebootdevice (View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Device_restart = database.getReference("Device_restart");
        Device_restart.setValue(1);
        progressbar_holder.setVisibility(View.VISIBLE);
    }

    private void Switch_color(){
        lst_time_display.setBackgroundResource(R.color.transparent);
        lst_time_display.setTextColor(getResources().getColor(R.color.white));
        Device_online_time_d_indicator.setTextColor(getResources().getColor(R.color.transparent));
    }

    public void logout (View v) {
        logout_fun();
    }

    private void logout_fun() {
        Paper.book().destroy();
        Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Device.this, Signin.class);
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
        Intent intent = new Intent(Device.this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_water (View v) {
        Intent intent = new Intent(Device.this, Water.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_pirith (View v) {
        Intent intent = new Intent(Device.this, Pirith.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_light (View v) {
        Intent intent = new Intent(Device.this, Lights.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void switch_to_device (View v) {

    }

}
