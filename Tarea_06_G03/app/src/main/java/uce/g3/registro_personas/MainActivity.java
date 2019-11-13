package uce.g3.registro_personas;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }
    },2000);

    }


}
