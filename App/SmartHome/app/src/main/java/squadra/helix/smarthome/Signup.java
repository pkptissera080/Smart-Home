package squadra.helix.smarthome;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{5,}" +               //at least 4 characters
                    "$");


    private FirebaseAuth mAuth;
    private EditText input_email,input_password,input_confirm_password;
    private RelativeLayout progressbar_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        progressbar_holder =(RelativeLayout) findViewById(R.id.progressbar_holder);

        input_email=(EditText) findViewById(R.id.input_email);
        input_password=(EditText) findViewById(R.id.input_password);
        input_confirm_password=(EditText) findViewById(R.id.input_confirm_password);
    }

    public void RegisterUser(View v) {

        final String input_email_txt = input_email.getText().toString().trim();
        final String input_password_txt = input_password.getText().toString().trim();
        final String input_confirm_password_txt = input_confirm_password.getText().toString().trim();



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
        if (!PASSWORD_PATTERN.matcher(input_password_txt).matches()){
            input_password.setError("Invalid Password");
            input_password.requestFocus();
            return;
        }
        if(input_confirm_password_txt.isEmpty()){
            input_confirm_password.setError("Requied");
            input_confirm_password.requestFocus();
            return;
        }

        if (!input_confirm_password_txt.equals(input_password_txt)){
            input_confirm_password.setError("Password Mismatch");
            input_confirm_password.requestFocus();
            input_confirm_password.setText("");
            input_password.setText("");
            return;
        }


        progressbar_holder.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(input_email_txt, input_password_txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    input_email.setText("");
                    input_password.setText("");
                    input_confirm_password.setText("");

                    progressbar_holder.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        input_email.setText("");
                        input_password.setText("");
                        input_confirm_password.setText("");

                        progressbar_holder.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressbar_holder.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });




    }


    public void switch_to_login_activity(View v) {
        Intent intent = new Intent(Signup.this,Signin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
