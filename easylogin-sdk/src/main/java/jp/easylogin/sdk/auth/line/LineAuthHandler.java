package jp.easylogin.sdk.auth.line;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

import jp.easylogin.sdk.AuthResult;
import jp.easylogin.sdk.EasyLoginException;
import jp.easylogin.sdk.auth.AbstractAuthHandler;
import jp.easylogin.sdk.auth.Channel;

public class LineAuthHandler extends AbstractAuthHandler {

    private static final int REQUEST_CODE_LINE_AUTH = 100;

    @Override
    public void performAuthorize(Activity activity) {
        try {
            final Channel channel = getAuthSession().getChannel();
            LineAuthenticationParams.BotPrompt botPrompt =
                    (channel.getOptions().contains("add_friend"))
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
            Log.e("EasyLoginSDK", e.getMessage(), e);
        }
    }

    @Override
    public String getProviderName() {
        return "line";
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE_LINE_AUTH) {
            Log.d("EasyLoginSDK", "Unsupported Request Code");
            return false;
        }

        LineLoginResult lineResult = LineLoginApi.getLoginResultFromIntent(data);
        switch (lineResult.getResponseCode()) {
            case SUCCESS:
                AuthResult result = new LineAuthResult(lineResult, getAuthSession());
                getEasyLogin().onProviderAuthSuccess(getProviderName(), result);
                return true;
            case CANCEL:
                Log.w("EasyLoginSDK", "LINE Login canceled by user.");
                break;
            default:
                Log.e("EasyLoginSDK", "Login FAILED! " +
                        lineResult.getErrorData().toString());
        }
        getEasyLogin().onProviderAuthFailure(getProviderName(),
                new EasyLoginException("LINE Login failed, response code: "
                        + lineResult.getResponseCode()));
        return true;
    }
}
