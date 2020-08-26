package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNo extends AppCompatActivity {

    String verificationCodeBySystem;
    Button verify;
    EditText phone;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);

        verify=findViewById(R.id.vbtn);
        phone=findViewById(R.id.verificationcode);
        progressBar=findViewById(R.id.progressBar);


        final String phone = getIntent().getStringExtra("phone");
        sendverificationCodeToUser(phone);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VerifyPhoneNo.this, "Processing..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendverificationCodeToUser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
               "+1" + phone,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mcallbacks);
    }
           private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
               @Override
               public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                   super.onCodeSent(s, forceResendingToken);
                   verificationCodeBySystem = s;
               }

               @Override
               public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                   String code = phoneAuthCredential.getSmsCode();
                   if (code!=null){
                       progressBar.setVisibility(View.VISIBLE);
                       verifyCode(code);
                   }
               }

               @Override
               public void onVerificationFailed(FirebaseException e) {
                   Toast.makeText(VerifyPhoneNo.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           };

           private void verifyCode(String codeByUser){
               PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
               signInTheUserByCredentails(credential);
           }

           private void signInTheUserByCredentails(PhoneAuthCredential credential){

               FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

               firebaseAuth.signInWithCredential(credential)
                       .addOnCompleteListener(VerifyPhoneNo.this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent i =new Intent(getApplicationContext(),DashBoard.class);
                                getIntent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(i);
                            }
                            else{
                                Toast.makeText(VerifyPhoneNo.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                           }
                       });

           }
}


