package com.example.android.secondprototype;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

/***************************************************************************************
 *    Title: Android getting started with Firebase-login and Registration.
 *    Author: Ravi Tamada.
 *    Date Accessed: 26 Nov, 2017
 *    Availability: https://www.androidhive.info/2016/06/android-getting-started-firebase-simple-login-registration-auth/
 ***************************************************************************************/

public class SignUp extends AppCompatActivity {

    private EditText EmailAddress;
    private EditText Password;
    private EditText ConfirmPassword;
    private EditText FullName;
    private Button SignUp;
    private TextView alreadyuser;
    private String password;
    private String confirmpassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EmailAddress =(EditText) findViewById(R.id.userEmailId);
        Password     =(EditText) findViewById(R.id.password);
        ConfirmPassword     =(EditText) findViewById(R.id.confirmPassword);
        FullName     =(EditText) findViewById(R.id.fullName);
        firebaseAuth    =FirebaseAuth.getInstance();
        SignUp = (Button)  findViewById(R.id.signUpBtn);

        password = Password.getText().toString();
        confirmpassword = ConfirmPassword.getText().toString();
        alreadyuser = (TextView) findViewById(R.id.already_user);
        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.equals(confirmpassword)){
                    btnSignup_Click(v);
                }
                else {
                    Toast.makeText(SignUp.this, "Both Passwords doesn't Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void btnSignup_Click(View v){

        final ProgressDialog progressDialog = ProgressDialog.show(SignUp.this,"It may take a while","Processing ...");
        (firebaseAuth.createUserWithEmailAndPassword(EmailAddress.getText().toString(),
                Password.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    sendemailverification();
                    Intent i  = new Intent(SignUp.this,LoginActivity.class);
                    startActivity(i);
                }
                else {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendemailverification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUp.this, "Check your Email for Verification", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }
}