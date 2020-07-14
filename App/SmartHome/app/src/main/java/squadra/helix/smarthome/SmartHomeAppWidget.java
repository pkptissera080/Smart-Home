package squadra.helix.smarthome;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.os.Build.VERSION_CODES.N;

/**
 * Implementation of App Widget functionality.smart_home_app_widget
 */
public class SmartHomeAppWidget extends AppWidgetProvider {

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference WATER_watertank_capacity = database.getReference("WATER_watertank_capacity");

        WATER_watertank_capacity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer WATER_watertank_capacity_value = dataSnapshot.getValue(Integer.class);

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.smart_home_app_widget);
                views.setTextViewText(R.id.water_level_widget,WATER_watertank_capacity_value.toString()+"%" );
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference WATER_system_status_message = database.getReference("WATER_system_status_message");

        WATER_system_status_message.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String WATER_system_status_message_value = dataSnapshot.getValue(String.class);

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.smart_home_app_widget);
                views.setTextViewText(R.id.water_status_message_widget,WATER_system_status_message_value );
                appWidgetManager.updateAppWidget(appWidgetId, views);
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

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.smart_home_app_widget);
                views.setTextViewText(R.id.System_last_online_time_widget,Device_online_time );
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            Intent intent = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent =PendingIntent.getActivity(context,0,intent,0);

            RemoteViews views =new RemoteViews(context.getPackageName(), R.layout.smart_home_app_widget);
            views.setOnClickPendingIntent(R.id.bg_widget_layout,pendingIntent);

            updateAppWidget(context, appWidgetManager, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

