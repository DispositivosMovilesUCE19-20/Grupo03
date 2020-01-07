package ec.edu.uce.optativa3.controlador;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyService extends FirebaseMessagingService {

    String TAG = "mensajes firebase: ";
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        //sendRegistrationToServer(token);
    }
}
