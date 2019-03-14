package jp.easylogin.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import jp.easylogin.sdk.AuthDelegate;
import jp.easylogin.sdk.AuthListener;
import jp.easylogin.sdk.AuthResult;
import jp.easylogin.sdk.EasyLogin;

public class MainActivity extends AppCompatActivity implements AuthListener {

    private final AuthDelegate delegate = AuthDelegate.Factory.create();
    private TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editAppId = findViewById(R.id.edit_app_id);

        findViewById(R.id.btn_line_login).setOnClickListener(v -> {
            EasyLogin easyLogin = EasyLogin.Factory.create(this, editAppId.getText().toString());
            easyLogin.addAuthListener(this);
            easyLogin.setAuthDelegate(delegate);
            easyLogin.authorize("line");
        });
        textContent = findViewById(R.id.txt_content);
    }

    @Override
    public void onAuthSuccess(String provider, @NonNull AuthResult result) {
        Log.i("EasyLogin", "Auth token: " + result.getAuthToken().getTokenString());
        result.getProfileAsync(response -> {
            if (response.isSuccess()) {
                String s = "Login success, socialId: " + response.getResponseData().getSocialId();
                textContent.setText(s);
                Log.i("EasyLogin", s);
            }
        });
    }

    @Override
    public void onAuthFailure(String provider, @Nullable Throwable throwable) {
        String s = "Login failed, " + (throwable != null ? throwable.getMessage() : "");
        textContent.setText(s);
        Log.w("EasyLogin", s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.delegate.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
