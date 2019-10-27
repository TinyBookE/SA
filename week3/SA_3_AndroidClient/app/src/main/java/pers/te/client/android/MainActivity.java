package pers.te.client.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText toMailEdit;
    private EditText payloadEdit;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toMailEdit = (EditText)findViewById(R.id.toMailEdit);
        payloadEdit = (EditText)findViewById(R.id.payloadEdit);
        sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        new Thread(networkTask).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(MainActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            JSONObject json = new JSONObject();
            try {
                json.put("url", toMailEdit.getText().toString());
                json.put("payload", payloadEdit.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL("http://10.0.2.2:8080/api/rest/sendEmail");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.getOutputStream().write(json.toString().getBytes());
                conn.getOutputStream().flush();

                if (conn.getResponseCode() == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String responseStr = in.lines().collect(Collectors.joining("\n"));
                    JSONObject response = new JSONObject(responseStr);
                    Message msg = new Message();
                    System.out.println(response);
                    System.out.println(response.get("msg"));
                    if ("Y".equals(response.get("msg"))) {
                        msg.what = 1;
                    } else {
                        msg.what = 0;
                    }
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
