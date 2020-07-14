package squadra.helix.smarthome;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;
import squadra.helix.smarthome.RememberMeData.RememberMeData;

public class Signin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText input_email,input_password;
    private CheckBox checkBoxRememberMe;
    private RelativeLayout progressbar_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        input_email =(EditText) findViewById(R.id.input_email);
        input_password =(EditText) findViewById(R.id.input_password);
        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);

        Paper.init(this);
        progressbar_holder =(RelativeLayout) findViewById(R.id.progressbar_holder);

    }

    public void login_user(View v) {
        String input_email_txt = input_email.getText().toString().trim();
        String input_password_txt = input_password.getText().toString().trim();

        if (checkBoxRememberMe.isChecked()){
            Paper.book().write(RememberMeData.UserEmailKey,input_email_txt);
            Paper.book().write(RememberMeData.UserPasswordKey,input_password_txt);
        }

        if(input_email_txt.isEmpty()){
            input_email.setError("Requied");
            input_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(input_email_txt).matches()){
            input_email.setError("Invalid Email");
            input_email.requestFocus();
            return;
        }
        if(input_password_txt.isEmpty()){
            input_password.setError("Requied");
            input_password.requestFocus();
            return;
        }

        progressbar_holder.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(input_email_txt, input_password_txt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressbar_holder.setVisibility(View.GONE);
                            Intent intent = new Intent(Signin.this,Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            progressbar_holder.setVisibility(View.GONE);
                            input_password.setText("");
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public void switch_to_register_activity(View v) {
        Intent intent = new Intent(Signin.this,Signup.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
