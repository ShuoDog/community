package shuodog.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import shuodog.community.dto.AccessTokenDTO;
import shuodog.community.dto.GithubUser;

import java.util.concurrent.TimeUnit;


@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

//        OkHttpClient client = new OkHttpClient();

        //可能电脑不行，或者网络不行，请求经常超时，重新设置久一点
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {

//        OkHttpClient client = new OkHttpClient();

        //可能电脑不行，或者网络不行，请求经常超时，重新设置久一点
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

//        此方法已经被GitHub废弃，因为不安全
//        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token="+accessToken)
//                .build();

        //GitHub重新修改了第三方登录的请求方法
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
