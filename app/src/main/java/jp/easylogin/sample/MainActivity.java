package jp.easylogin.sample;

import android.app.Activity;
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
import jp.easylogin.sdk.EasyProfile;
import jp.easylogin.sdk.common.Utils;

public class MainActivity extends AppCompatActivity implements AuthListener {

    private static final int REGISTER_CODE = 1313;

    private final AuthDelegate delegate = AuthDelegate.Factory.create();
    private TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editAppId = findViewById(R.id.edit_app_id);

        findViewById(R.id.btn_line_login).setOnClickListener(v -> {
            textContent.setText(R.string.processing);
            EasyLogin easyLogin = EasyLogin.Factory.create(this, editAppId.getText().toString());
            easyLogin.addAuthListener(this);
            easyLogin.setAuthDelegate(delegate);
            easyLogin.authorize("line");
        });
        textContent = findViewById(R.id.txt_content);
    }

    @Override
    public void onAuthSuccess(String provider, @NonNull AuthResult result) {
        Log.i("EasyLoginDemo", "Auth token: " + result.getAuthToken().getTokenString());
        result.getProfileAsync(response -> {
            if (!response.isSuccess()) {
                String err = response.getError().getMessage();
                textContent.setText(err);
                return;
            }

            EasyProfile profile = response.getResponseData();
            if (!profile.isVerified()) {
                Intent i = new Intent(this, RegisterActivity.class);
                i.putExtra("profile", profile);
                i.putExtra("auth_result", result);
                startActivity(i);
            }
        });
    }

    @Override
    public void onAuthFailure(String provider, @Nullable Throwable throwable) {
        String s = throwable != null ? throwable.getMessage() : "";
        textContent.setText(s);
        Log.w("EasyLoginDemo", s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!this.delegate.onActivityResult(requestCode, resultCode, data)) {
            switch (requestCode) {
                case REGISTER_CODE:
                    if (resultCode == Activity.RESULT_CANCELED) {
                        textContent.setText("");
                        return;
                    }
                    EasyProfile profile = (EasyProfile) getIntent().getSerializableExtra("data");
                    String s = Utils.toJson(profile);
                    textContent.setText(s);
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
