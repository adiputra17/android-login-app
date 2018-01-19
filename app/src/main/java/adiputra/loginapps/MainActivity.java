package adiputra.loginapps;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
        if(loggedIn == false){
            Toast.makeText(this, "loggedIn : "+AccessToken.getCurrentAccessToken(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "loggedIn : "+AccessToken.getCurrentAccessToken(), Toast.LENGTH_SHORT).show();
        }

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email","public_profile","user_friends"));
        // If using in a fragment
        //loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback(){

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.i("RESPONSE : ", response.toString());

                            try {
                                String title = "Success";
                                String email = object.getString("email");
                                String name = object.getString("name");
                                String gender = object.getString("gender");
                                String alertMessage = email +"\n"+ name +"\n"+ gender;
                                showResult(title, alertMessage);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                //Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                String title = "Error";
                String alertMessage = exception.getCause().getMessage();
                showResult(title, alertMessage);
            }

            private void showResult(String title, String alertMessage) {
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton("OK", null)
                    .show();
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
