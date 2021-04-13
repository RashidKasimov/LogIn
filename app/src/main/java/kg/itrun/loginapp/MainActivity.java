package kg.itrun.loginapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE_SIGN_UP = 777;
    EditText login;
    EditText password;
    TextView signIn;
    TextView signUp;
    DataBase dataBase;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        String username = prefs.getString("username", null);
        if (username != null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        login = findViewById(R.id.login_edit_text);
        password = findViewById(R.id.password_edit_text);
        signIn = findViewById(R.id.sign_in_text);
        signUp = findViewById(R.id.sign_up_text);
        dataBase = new DataBase();



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginString = login.getText().toString();
                String passwordString = password.getText().toString();
                UserModel user = dataBase.getUserFromDB(loginString, passwordString);
                if (user != null) {
                    prefs = getSharedPreferences("userdata", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", user.name);
                    editor.putString("position", user.position);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error")
                            .setMessage("Login or password incorrect")
                            .setPositiveButton("Okay", null)
                            .show();
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SIGN_UP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_UP) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                String position = data.getStringExtra("position");
                String login = data.getStringExtra("login");
                String password  = data.getStringExtra("password");
                UserModel user = new UserModel(login, password, name, position);
                dataBase.addUser(user);

                prefs = getSharedPreferences("userdata", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", user.name);
                editor.putString("position", user.position);
                editor.commit();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }
    }
}