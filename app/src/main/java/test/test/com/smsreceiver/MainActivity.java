package test.test.com.smsreceiver;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    TextView tv_smstext;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_smstext = (TextView) findViewById(R.id.tv_smstext);
        setSupportActionBar(toolbar);

        //Marshmallo and greater versions require permission runtime
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (!checkPermission_OTP()) {
                Log.i("permission", "Permission Not granted receiving sms");
                requestPermissionOTP();
                return;
            }
        OtpReciver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.d("Text", messageText);
//                if (messageText.contains("OTP Number")) {
                //Any message received is shown as toast
                if (tv_smstext != null) {
//                    tv_smstext.setText(messageText.trim().replaceAll("[^0-9]", ""));
                    tv_smstext.setText(messageText.trim());
                }
                Toast.makeText(getApplicationContext(), "MessageReceived  :" + messageText.trim(), Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Marshmallo and greater versions require permission runtime
    private boolean checkPermission_OTP() {
        int result1 = ContextCompat.checkSelfPermission(this, READ_SMS);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionOTP() {
        ActivityCompat.requestPermissions(this, new String[]{READ_SMS}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
