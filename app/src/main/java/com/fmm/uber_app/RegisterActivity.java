package com.fmm.uber_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmailRegister, inputSenhaRegister, inputNomeRegister;
    private Button btnRegister;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmailRegister = findViewById(R.id.inputEmailRegister);
        inputSenhaRegister = findViewById(R.id.inputSenhaRegister);
        inputNomeRegister = findViewById(R.id.inputNomeRegister);

        btnRegister= findViewById(R.id.btnRegister);

        firebaseAuth= FirebaseAuth.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmailRegister.getText().toString().trim();
                String senha = inputSenhaRegister.getText().toString();
               final String nome = inputNomeRegister.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Conta criada",Toast.LENGTH_SHORT).show();

                            DatabaseReference  driverPositionReference = FirebaseDatabase.getInstance().getReference().child("position");


                            driverPositionReference.child(FirebaseAuth.getInstance().getUid()).child("nome").setValue(nome);


                            startActivity(new Intent(getApplicationContext(),DriverMapsActivity.class));
                            finish();
                        }
                    }
                });


            }
        });

    }
}