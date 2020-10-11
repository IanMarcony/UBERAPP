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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private Button btnMotorista, btnPassageiro, btnMotoristaCreate;
    private EditText inputEmail, inputSenha;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMotorista = findViewById(R.id.btnMotorista);
        btnPassageiro = findViewById(R.id.btnPassageiro);
        btnMotoristaCreate = findViewById(R.id.btnMotoristaCreate);

        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);

        firebaseAuth = FirebaseAuth.getInstance();

        btnMotoristaCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        btnMotorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = inputEmail.getText().toString().trim();
               String senha = inputSenha.getText().toString();

               firebaseAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(getApplicationContext(), DriverMapsActivity.class));
                    }

                       Toast.makeText(getApplicationContext(),"Email e/ou Senha inválidos!",Toast.LENGTH_SHORT).show();
                   }
               });


            }
        });
        btnPassageiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

    }





}