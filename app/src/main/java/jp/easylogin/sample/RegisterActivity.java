package jp.easylogin.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import jp.easylogin.sdk.AuthResult;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AuthResult result = (AuthResult) getIntent().getSerializableExtra("auth_result");

        findViewById(R.id.btn_register).setOnClickListener(v -> {
            result.activateProfileAsync(response -> {
                setResult(response.isSuccess() ? RESULT_OK : RESULT_CANCELED, getIntent());
                finish();
            });
            setResult(RESULT_OK, getIntent());
            finish();
        });

        findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
