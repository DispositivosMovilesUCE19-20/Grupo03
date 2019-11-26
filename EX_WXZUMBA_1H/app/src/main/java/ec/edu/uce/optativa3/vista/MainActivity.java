package ec.edu.uce.optativa3.vista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import uce.g3.registro_personas.R;

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
