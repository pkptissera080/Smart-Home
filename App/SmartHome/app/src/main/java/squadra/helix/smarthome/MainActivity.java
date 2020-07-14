package squadra.helix.smarthome;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;
import squadra.helix.smarthome.RememberMeData.RememberMeData;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Handler handler =new Handler();
    Runnable runnable =new Runnable() {
        @Override
        public void run() {
            startup();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Paper.init(this);
        String UserEmailKey = Paper.book().read(RememberMeData.UserEmailKey);
        String UserPasswordKey = Paper.book().read(RememberMeData.UserPasswordKey);

        if (UserEmailKey !="" && UserPasswordKey !="" ){
            if (!TextUtils.isEmpty(UserEmailKey) && !TextUtils.isEmpty(UserPasswordKey) ){


                LoginUser(UserEmailKey,UserPasswordKey);

            }
            else{
                handler.postDelayed(runnable,500);

            }
        }
        else{
            handler.postDelayed(runnable,500);

        }

    }

    private void LoginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this,Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            handler.postDelayed(runnable,500);
                        }
                    }
                });
    }


    private void startup(){
        Intent intent = new Intent(MainActivity.this,Signin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
