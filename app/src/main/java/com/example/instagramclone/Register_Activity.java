package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {
    EditText Registerusername, Registerfullname, Registeremail, RegisterPassword;
    Button btnregister;
    TextView txtregisterSignup;
    FirebaseAuth yetki;
    DatabaseReference yol;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Registerusername = findViewById(R.id.Registerusername);
        Registerfullname = findViewById(R.id.Registerfullname);
        Registeremail = findViewById(R.id.Registeremail);
        RegisterPassword = findViewById(R.id.RegisterPassword);

        btnregister = findViewById(R.id.btnregister);
        txtregisterSignup = findViewById(R.id.txtregisterSignup);
        yetki = FirebaseAuth.getInstance();

        txtregisterSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register_Activity.this,Login_Activity.class));


            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd=new ProgressDialog(Register_Activity.this);
                pd.setMessage(" Please waiting .. ");
                pd.show();


                String registerusername = Registerusername.getText().toString();
                String registerfullname = Registerfullname.getText().toString();
                String registeremail = Registeremail.getText().toString();
                String password = RegisterPassword.getText().toString();
                if (TextUtils.isEmpty(registerusername) || (TextUtils.isEmpty(registerfullname)) || (TextUtils.isEmpty(registeremail))
                        || (TextUtils.isEmpty(password))) {
                    Toast.makeText(Register_Activity.this, "Dont Empty", Toast.LENGTH_SHORT).show();

                } else if (password.length() < 6) {
                    Toast.makeText(Register_Activity.this, "must have a maximum of 6 characters ", Toast.LENGTH_SHORT).show();


                } else {
                    //yenikullnaıcı çagırma
                    save(registerusername,registerfullname,registeremail,password);
                }


            }
        });


    }
    private void save(String registerusername,String registerfullname,String registeremail,String password){

        yetki.createUserWithEmailAndPassword(registeremail,password).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseuser=yetki.getCurrentUser();
                    String usersID=firebaseuser.getUid();
                    yol= FirebaseDatabase.getInstance().getReference().child("users").child(usersID);
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("id",usersID);
                    hashMap.put("username",registerusername.toLowerCase());
                    hashMap.put("ad",registerfullname);
                    hashMap.put("bio","");
                    hashMap.put("resimurl","https://firebasestorage.googleapis.com/v0/b/instagramclone-5666d.appspot.com/o/placeholder.png?alt=media&token=4311336d-2927-47c3-9c1c-c603c1e7e87b");
                    yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pd.dismiss();
                            if (task.isSuccessful()){

                                pd.dismiss();

                                Intent intent =new Intent(Register_Activity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        }


                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(Register_Activity.this, "Email or password Failed Try Again Please", Toast.LENGTH_LONG).show();

                }
            }



        });

    }
}