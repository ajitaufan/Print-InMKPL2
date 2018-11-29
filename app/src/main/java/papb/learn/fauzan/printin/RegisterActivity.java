package papb.learn.fauzan.printin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username,et_email,et_password;
    private TextView tv_login;
    private Button btn_register;
    private ProgressBar pb_register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email_register);
        et_password = findViewById(R.id.et_password_register);

        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        pb_register = findViewById(R.id.pb_register);
        pb_register.setVisibility(View.INVISIBLE);
        pb_register.setIndeterminate(true);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseAuth.getInstance().signOut();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Intent toLoginActivity = getIntent();
            setResult(RESULT_OK,toLoginActivity);
            finish();
        }
    }

    private void createUser(){
        pb_register.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        String email_register = et_email.getText().toString();
        String password_register = et_password.getText().toString();

        final String TAG_SUCCESS_CREATE = "SUCCESS";
        final String TAG_FAILED_CREATE = "ERROR";
        try {
            mAuth.createUserWithEmailAndPassword(email_register, password_register)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                Log.i(TAG_FAILED_CREATE, task.getException().toString());
                                pb_register.setVisibility(View.GONE);

                            } else {
                                Toast.makeText(RegisterActivity.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                pb_register.setVisibility(View.GONE);
                                Intent toLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(toLoginActivity);
                            }
                        }
                    });
        } catch (IllegalArgumentException e){
            Toast.makeText(RegisterActivity.this,"Field can't be empty.",
                    Toast.LENGTH_SHORT).show();
            pb_register.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                createUser();
                break;
            case R.id.tv_login:
                Intent toLoginActivity = getIntent();
                setResult(RESULT_OK,toLoginActivity);
                finish();
                break;
        }
    }
}
