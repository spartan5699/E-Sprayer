package com.example.weatherbox;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {  //Code By Pratik Ghodake

    EditText editTextphone,editTextotp;
    FirebaseAuth mAuth;
    String codesent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextotp=findViewById(R.id.otp);
        editTextphone=findViewById(R.id.mobileno);
        mAuth=FirebaseAuth.getInstance();

        findViewById(R.id.get_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sendverificationcode();
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifycode();
            }
        });
    }

    private  void verifycode()
    {
        String code=editTextotp.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),
                                "Login Successfull",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(login.this,Sensor_data.class));
                            }
                        else
                            {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(getApplicationContext(),
                                            "Invalid OTP",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }



    private void sendverificationcode()
    {
        String phone=editTextphone.getText().toString();

        if(phone.isEmpty())
        {
            editTextphone.setError("Phone number is required");
            editTextphone.requestFocus();
            return;
        }

        if(phone.length() < 10)
        {
            editTextphone.setError("Please enter valid number");
            editTextphone.requestFocus();
            return;
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                codesent=s;
            }
        };
}
