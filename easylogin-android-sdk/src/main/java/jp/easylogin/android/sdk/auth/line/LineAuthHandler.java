package jp.easylogin.android.sdk.auth.line;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

import jp.easylogin.android.sdk.AuthResult;
import jp.easylogin.android.sdk.EasyLoginException;
import jp.easylogin.android.sdk.auth.AbstractAuthHandler;
import jp.easylogin.android.sdk.auth.Channel;

public class LineAuthHandler extends AbstractAuthHandler {

    @Override
    public void performAuthorize(Activity activity, Channel channel) {
        try {
            // App-to-app login
            LineAuthenticationParams.BotPrompt botPrompt = (channel.getOptions().contains("add_friend"))
                    ? LineAuthenticationParams.BotPrompt.aggressive : null;
            Intent loginIntent = LineLoginApi.getLoginIntent(
                    activity,
                    channel.getClientId(),
                    new LineAuthenticationParams.Builder()
                            .botPrompt(botPrompt)
                            .scopes(Scope.convertToScopeList(channel.getScopes()))
                            .build());
            activity.startActivityForResult(loginIntent, REQUEST_CODE_LINE_AUTH);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }

    @Override
    public String getProviderName() {
        return "line";
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE_LINE_AUTH) {
            Log.e("ERROR", "Unsupported Request");
            return false;
        }

        LineLoginResult lineResult = LineLoginApi.getLoginResultFromIntent(data);
        switch (lineResult.getResponseCode()) {
            case SUCCESS:
                // Login successful
                AuthResult result = new LineAuthResult(lineResult, getAuthSession());
                getEasyLogin().onProviderAuthSuccess(getProviderName(), result);
                break;
            case CANCEL:
                // Login canceled by user
                Log.e("ERROR", "LINE Login Canceled by user.");
                break;
            default:
                // Login canceled due to other error
                Log.e("ERROR", "Login FAILED!");
                Log.e("ERROR", lineResult.getErrorData().toString());
        }
        getEasyLogin().onProviderAuthFailure(getProviderName(),
                new EasyLoginException("Authorized failed, response code: "
                        + lineResult.getResponseCode()));
        return false;
    }
}
