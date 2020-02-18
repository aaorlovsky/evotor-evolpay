package ru.qualitylab.evotor.evolpay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.evotor.framework.core.IntegrationActivity;
import ru.evotor.framework.core.action.event.receipt.payment.system.result.PaymentSystemPaymentErrorResult;
import ru.evotor.framework.core.action.event.receipt.payment.system.result.PaymentSystemPaymentOkResult;
import ru.evotor.framework.payment.PaymentType;

/**
 * Пример операции для службы взаимодействия со сторонними платёжными системами
 */
public class MyPaymentActivity extends IntegrationActivity {
    public static final String EXTRA_NAME_OPERATION = "EXTRA_NAME_OPERATION";
    String operationID;


    public String paymentRequestUrl = "http://1b008f10.ngrok.io/evotor/v1/payment";
    public String paymentRequestBody = "{\"kktNumber\": 1234578, \"product\": [{\"title\": \"sdfsdgsg\"}, { \"title\": \"gdfhfdfj\"}], \"amount\": 100, \"data\": \"url,qr:200\"}";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String paymentStatusRequestUrl = "http://1b008f10.ngrok.io/evotor/v1/paymentStatus";
    public String paymentStatusRequestUrlWithParams = null;
    public Boolean paymentStatusFinal = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payment);

        //Try HttpOkLibrary

        try {
            postRequest(paymentRequestUrl, paymentRequestBody);

        } catch (IOException e) {
            e.printStackTrace();
        }


        //old code with HttpUrlResponse
        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url;
        StringBuffer sb;




        try {

            String TAG = "Try_request";

            Log.i(TAG, "simple_log_info_1");

            sb = new StringBuffer();
            url = new URL("https://1b008f10.ngrok.io/evotor/v1/payment");



            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setInstanceFollowRedirects(false);
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

            String jsonInputString = "{\"kktNumber\": 1234578, \"product\": [{\"title\": \"sdfsdgsg\"}, { \"title\": \"gdfhfdfj\"}], \"amount\": 100, \"data\": \"url,qr:200\"}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                sb.append(response);
            }


            JSONObject jsonObject = new JSONObject(sb.toString());
            String qrCode = jsonObject.getJSONObject("result").getJSONObject("data").getString("qr");
            operationID = jsonObject.getJSONObject("result").getString("operationId");


            ImageView imageView = findViewById(R.id.imageView);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String imageString = qrCode;
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(decodedImage);


            sb = new StringBuffer();*/
        /*Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {

                    try {
                        String kktNumber = "1234578";
                        StringBuilder stringBuilder = new StringBuilder("https://7ac263e0.ngrok.io/evotor/v1/paymentStatus");
                        stringBuilder.append("?kktNumber=");
                        stringBuilder.append(URLEncoder.encode(kktNumber, "UTF-8"));
                        stringBuilder.append("&operationId=");
                        stringBuilder.append(URLEncoder.encode(operationID, "UTF-8"));

                        URL url = new URL(stringBuilder.toString());
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Accept-Charset", "UTF-8");

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String line;
                        StringBuffer responseSB = new StringBuffer();

                        while ((line = in.readLine()) != null) {
                            responseSB.append(line);
                        }
                        in.close();

                        JSONObject jsonObject = new JSONObject(responseSB.toString());
                        Boolean status = jsonObject.getBoolean("status");

                        if (status) {
                            StringBuilder rrn = new StringBuilder();
                            Random random = new Random();
                            for (int i = 0; i < 10; i++)
                                rrn.append(random.nextInt(10));
                            //Текст, который будет напечатан на чеке в двух экземплярах
                            List<String> slip = new ArrayList<>();
                            slip.add("EVOLPAY SLIP START");
                            slip.add("RRN:");
                            slip.add(rrn.toString());
                            slip.add("EVOLPAY SLIP END");
                            setIntegrationResult(new PaymentSystemPaymentOkResult(rrn.toString(), slip, "123qwe", PaymentType.ELECTRON));
                            finish();
                        }
                    } catch (Exception e){

                    }
                } // This is your code
            };
            mainHandler.post(myRunnable);*/
        /*} catch (Exception exc){
            String excMessage = exc.getMessage();
        }*/


        //В случае успешной обработки события служба должна возвращать результат PaymentSystemPaymentOkResult
        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    StringBuilder rrn = new StringBuilder();
                    Random random = new Random();
                    for (int i = 0; i < 10; i++)
                        rrn.append(random.nextInt(10));
                    //Текст, который будет напечатан на чеке в двух экземплярах
                    List<String> slip = new ArrayList<>();
                    slip.add("EVOLPAY SLIP START");
                    slip.add("RRN:");
                    slip.add(rrn.toString());
                    slip.add("EVOLPAY SLIP END");
                    setIntegrationResult(new PaymentSystemPaymentOkResult(rrn.toString(), slip, "123qwe", PaymentType.ELECTRON));
                    finish();


                    //запрос статуса через HttpUrl
                    /*String kktNumber = "1234578";
                    StringBuilder stringBuilder = new StringBuilder("https://1b008f10.ngrok.io/evotor/v1/paymentStatus");
                    stringBuilder.append("?kktNumber=");
                    stringBuilder.append(URLEncoder.encode(kktNumber, "UTF-8"));
                    stringBuilder.append("&operationId=");
                    stringBuilder.append(URLEncoder.encode(operationID, "UTF-8"));

                    URL url = new URL(stringBuilder.toString());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept-Charset", "UTF-8");

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String line;
                    StringBuffer responseSB = new StringBuffer();

                    while ((line = in.readLine()) != null) {
                        responseSB.append(line);
                    }
                    in.close();

                    JSONObject jsonObject = new JSONObject(responseSB.toString());
                    Boolean status = jsonObject.getBoolean("status");

                    if (status) {
                        StringBuilder rrn = new StringBuilder();
                        Random random = new Random();
                        for (int i = 0; i < 10; i++)
                            rrn.append(random.nextInt(10));
                        //Текст, который будет напечатан на чеке в двух экземплярах
                        List<String> slip = new ArrayList<>();
                        slip.add("EVOLPAY SLIP START");
                        slip.add("RRN:");
                        slip.add(rrn.toString());
                        slip.add("EVOLPAY SLIP END");
                        setIntegrationResult(new PaymentSystemPaymentOkResult(rrn.toString(), slip, "123qwe", PaymentType.ELECTRON));
                        finish();
                    }*/
                } catch (Exception exc) {

                }
            }
        });
        //Уникальный идентификатор платежа, который понадобится при отмене транзакции
        /*StringBuilder rrn = new StringBuilder();
                Random random = new Random();
                for (int i = 0; i < 10; i++)
                    rrn.append(random.nextInt(10));
                //Текст, который будет напечатан на чеке в двух экземплярах
                List<String> slip = new ArrayList<>();
                slip.add("SLIP START");
                slip.add("RRN:");
                slip.add(rrn.toString());
                slip.add("SLIP EMD");
                setIntegrationResult(new PaymentSystemPaymentOkResult(rrn.toString(), slip, "123qwe", PaymentType.ELECTRON));
                finish();*/
        /*}
        });*/

        //В случае ошибки служба должна возвращать результат PaymentSystemPaymentErrorResult
        findViewById(R.id.btnError).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIntegrationResult(new PaymentSystemPaymentErrorResult("Error was happened"));
                finish();
            }
        });

        //Тип текущей операции с платежной системой
        if (getIntent().hasExtra(EXTRA_NAME_OPERATION)) {
            ((TextView) findViewById(R.id.tvOperation)).setText(getIntent().getStringExtra(EXTRA_NAME_OPERATION));
        }
    }

    void postRequest(String postUrl, String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(postBody, JSON);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String qrCode = jsonObject.getJSONObject("result").getJSONObject("data").getString("qr");
                    operationID = jsonObject.getJSONObject("result").getString("operationId");

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    String imageString = qrCode;
                    byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                    final Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    MyPaymentActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageView = findViewById(R.id.imageView);
                            imageView.setImageBitmap(decodedImage);
                        }
                    });


                    Thread.sleep(2000);


                    if (operationID != "") {
                        String kktNumber = "1234578";
                        StringBuilder stringBuilder = new StringBuilder(paymentStatusRequestUrl);
                        stringBuilder.append("?kktNumber=");
                        stringBuilder.append(URLEncoder.encode(kktNumber, "UTF-8"));
                        stringBuilder.append("&operationId=");
                        stringBuilder.append(URLEncoder.encode(operationID, "UTF-8"));

                        paymentStatusRequestUrlWithParams = stringBuilder.toString();

                        int count = 0;

                        while (!paymentStatusFinal || count < 10) {
                            paymentStatus();
                            count++;
                            Thread.sleep(5000);
                        }


                        StringBuilder rrn = new StringBuilder();
                        Random random = new Random();
                        for (int i = 0; i < 10; i++)
                            rrn.append(random.nextInt(10));
                        //Текст, который будет напечатан на чеке в двух экземплярах
                        List<String> slip = new ArrayList<>();
                        slip.add("EVOLPAY BADSLIP START");
                        slip.add("RRN:");
                        slip.add(rrn.toString());
                        slip.add("EVOLPAY BADSLIP END");
                        setIntegrationResult(new PaymentSystemPaymentOkResult(rrn.toString(), slip, "123qwe", PaymentType.ELECTRON));
                        finish();


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void paymentStatus() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(paymentStatusRequestUrlWithParams)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    paymentStatusFinal = jsonObject.getBoolean("status");

                    if (paymentStatusFinal) {
                        StringBuilder rrn = new StringBuilder();
                        Random random = new Random();
                        for (int i = 0; i < 10; i++)
                            rrn.append(random.nextInt(10));
                        //Текст, который будет напечатан на чеке в двух экземплярах
                        List<String> slip = new ArrayList<>();
                        slip.add("EVOLPAY SLIP START");
                        slip.add("RRN:");
                        slip.add(rrn.toString());
                        slip.add("EVOLPAY SLIP END");
                        setIntegrationResult(new PaymentSystemPaymentOkResult(rrn.toString(), slip, "123qwe", PaymentType.ELECTRON));
                        finish();
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        setIntegrationResult(new PaymentSystemPaymentErrorResult("onBackPressed was happened"));
        finish();
    }
}
