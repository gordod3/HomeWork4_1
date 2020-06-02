package com.example.homework4_1.phone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.homework4_1.MainActivity;
import com.example.homework4_1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    private EditText editPhoneNumber, editCodeNumber;
    private Button buttonEnterCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private PhoneAuthCredential authCredential;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editCodeNumber = findViewById(R.id.editCode);
        buttonEnterCode = findViewById(R.id.buttonEnterCode);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                authCredential = phoneAuthCredential;
                signInAlt(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                buttonEnterCode.setVisibility(View.VISIBLE);
                editCodeNumber.setVisibility(View.VISIBLE);
            }
        };
    }

    private void signInAlt(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();
                    startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                    finish();
                }else Log.e("lol", task.getException().toString());
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSentCode:
                String phoneNumber = this.editPhoneNumber.getText().toString();
                Log.d("lol", "+996" + phoneNumber);
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+996" + phoneNumber, 60, TimeUnit.SECONDS, this, callbacks);
                break;
            case R.id.buttonEnterCode:
                if (!editCodeNumber.getText().toString().isEmpty()) signIn(editCodeNumber.getText().toString());
                else editCodeNumber.setError("Введите код");
                break;
        }
    }
    private void signIn(String code){
        authCredential = PhoneAuthProvider.getCredential(verificationId, code);
        if (editCodeNumber.getText().toString().equals(code)){
            FirebaseAuth.getInstance().signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = task.getResult().getUser();
                        startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                        finish();
                    }
                }
            });
        } else editCodeNumber.setError("Неправильный код");
    }
}
