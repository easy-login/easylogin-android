package jp.easylogin.android.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import jp.easylogin.android.sdk.AuthDelegate;
import jp.easylogin.android.sdk.AuthListener;
import jp.easylogin.android.sdk.AuthResult;
import jp.easylogin.android.sdk.EasyLogin;

public class MainActivity extends AppCompatActivity implements AuthListener {

    private final AuthDelegate delegate = AuthDelegate.Factory.create();
    private TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EasyLogin easyLogin = EasyLogin.Factory.create(this, "4");
        easyLogin.addAuthListener(this);
        easyLogin.setAuthDelegate(delegate);

        findViewById(R.id.line_login_btn).setOnClickListener(v -> easyLogin.authorize("line"));
        textContent = findViewById(R.id.txt_content);
    }

    @Override
    public void onAuthSuccess(String provider, @NonNull AuthResult result) {
        String s = "Login success, socialId: " + result.getProfile().getSocialId();
        textContent.setText(s);
    }

    @Override
    public void onAuthFailure(String provider, @Nullable Throwable throwable) {
        String s = "Login failed, " + (throwable != null ? throwable.getMessage() : "");
        textContent.setText(s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.delegate.onActivityResult(requestCode, resultCode, data);
    }
}
