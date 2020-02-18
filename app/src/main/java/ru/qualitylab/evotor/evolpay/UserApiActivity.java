package ru.qualitylab.evotor.evolpay;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ru.evotor.framework.core.IntegrationAppCompatActivity;
import ru.evotor.framework.users.Grant;
import ru.evotor.framework.users.User;
import ru.evotor.framework.users.UserApi;

/**
 * С помощью User API вы можете узнать данные всех пользователей или пользователя,
 * который авторизован на смарт-терминале в данный момент
 */
public class UserApiActivity extends IntegrationAppCompatActivity {


    TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_api);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tvLog = (TextView) findViewById(R.id.tvLog);

        findViewById(R.id.btnGetAllUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Получение списка всех пользователей терминала
                tvLog.setText("");
                List<User> users = UserApi.getAllUsers(getApplicationContext());
                StringBuilder sb = new StringBuilder();
                if (users != null) {
                    for (User user : users) {
                        sb.append("FirstName: ")
                                .append(user.getFirstName())
                                .append("\nSecondName: ")
                                .append(user.getSecondName())
                                .append("\nRoleTitle: ")
                                .append(user.getRoleTitle())
                                .append("\nPin: ")
                                .append(user.getPin())
                                .append("\nUuid: ")
                                .append(user.getUuid())
                                .append("\n");
                    }
                } else {
                    sb.append("null\n");
                }
                tvLog.setText(sb.toString());
            }
        });

        findViewById(R.id.btnGetAuthUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Получение данных авторизованного пользователя
                tvLog.setText("");
                User user = UserApi.getAuthenticatedUser(getApplicationContext());
                StringBuilder sb = new StringBuilder();
                sb.append("FirstName: ")
                        .append(user.getFirstName())
                        .append("\nSecondName: ")
                        .append(user.getSecondName())
                        .append("\nRoleTitle: ")
                        .append(user.getRoleTitle())
                        .append("\nPin: ")
                        .append(user.getPin())
                        .append("\nUuid: ")
                        .append(user.getUuid())
                        .append("\n");
                tvLog.setText(sb.toString());
            }
        });

        findViewById(R.id.btnGetAllGrants).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Получить список всех доступных прав
                tvLog.setText("");
                List<Grant> grants = UserApi.getAllGrants(getApplicationContext());
                StringBuilder sb = new StringBuilder();
                if (grants != null) {
                    for (Grant grant : grants) {
                        sb.append("RoleUuid: ")
                                .append(grant.getRoleUuid())
                                .append("\nTitle: ")
                                .append(grant.getTitle())
                                .append("\n");
                    }
                } else {
                    sb.append("null\n");
                }
                tvLog.setText(sb.toString());
            }
        });

        findViewById(R.id.btnGetGrantsAuthUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Получить список прав для авторизованного пользователя
                tvLog.setText("");
                List<Grant> grants = UserApi.getGrantsOfAuthenticatedUser(getApplicationContext());
                StringBuilder sb = new StringBuilder();
                if (grants != null) {
                    for (Grant grant : grants) {
                        sb.append("RoleUuid: ")
                                .append(grant.getRoleUuid())
                                .append("\nTitle: ")
                                .append(grant.getTitle())
                                .append("\n");
                    }
                } else {
                    sb.append("null\n");
                }
                tvLog.setText(sb.toString());
            }
        });

        findViewById(R.id.btnSendRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Получить список прав для авторизованного пользователя
                tvLog.setText("");
                StringBuffer sb = new StringBuffer();

                try {

                    //URL url = new URL("https://reqres.in/api/users");
                    URL url = new URL("https://7ac263e0.ngrok.io/evotor/v1/payment");
                    //URL url = new URL("https://api.evolpay.ru/test");

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setInstanceFollowRedirects(false);
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);

                    con.setRequestProperty("Content-Type", "application/json; utf-8");
                    con.setRequestProperty("Accept", "application/json");

                    sb.append("2");

                    //JSON String need to be constructed for the specific resource.
                    //We may construct complex JSON using any third-party JSON libraries such as jackson or org.json
                    String jsonInputString = "{\"kktNumber\": 1234578, \"product\": [{\"title\": \"sdfsdgsg\"}, { \"title\": \"gdfhfdfj\"}], \"amount\": 100, \"data\": \"url,qr:200\"}";
                    //String jsonInputString = "";
                    //String jsonInputString = "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";
                    sb.append(jsonInputString);
//                  "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";

                    try (OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    sb.append("3");

                    int code = con.getResponseCode();
                    sb.append("4");
                    sb.append(code);
                    //System.out.println(code);

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }

                        sb.append(response);

                        //System.out.println(response.toString());
                    }

                    sb.append("5");

                } catch (IOException exc)
                {
                    sb.append(exc.getMessage());
                }


                tvLog.setText(sb.toString());
            }
        });
    }
}
