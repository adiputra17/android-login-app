package adiputra.loginapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

public class FacebookActivity extends AppCompatActivity {

    TextView tvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        //logger.logPurchase(BigDecimal.valueOf(4.32), Currency.getInstance("USD"));
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        tvProfile = (TextView) findViewById(R.id.tv_profile);
        tvProfile.setText("Welcome : ");
    }
}
