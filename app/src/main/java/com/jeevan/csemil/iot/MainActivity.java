package com.jeevan.csemil.iot;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MainActivity extends AppCompatActivity {
    static MqttAndroidClient client;
    //private void setSubsription();
    //public void pub(View v);
    MqttConnectOptions options;
    String topicStr;
    String ip;
    EditText etbtn0;
    //EditText etxt1;

    Intent ob1;
    Button fb1;
    Button fb2;


    //Byte [] realTimeState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1=(Button)findViewById(R.id.btnCon);
        etbtn0=(EditText)findViewById(R.id.ipid0);
        //etxt1=(EditText)findViewById(R.id.editText1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ob2=new Intent(this.getApplicationContext(),Main2Activity.class);
        //Storing data edit text buttons//
        //btnClick.putExtra("ip", ip);
        //String port=etbtn1.getText().toString();
        //int port=getText(R.id.portid0);
        //pageChange(View v);
        //MQTT OPTIONS//
        fb1=(Button)findViewById(R.id.fragBtn1);
        fb2=(Button)findViewById(R.id.fragBtn2);
        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frag1=new Intent(getApplicationContext(),Fragment_1.class);
                startActivity(frag1);
            }
        });
        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent frag2=new Intent(getApplicationContext(),Fragment_2.class);
            }
        });

    }

    public void pageChange(View v)
    {
        ob1=new Intent(this.getApplicationContext(),Main2Activity.class);

        String ip=etbtn0.getText().toString();
        options = new MqttConnectOptions();
        //Toast.makeText(MainActivity.this,ip,Toast.LENGTH_SHORT).show();
        String MQTTHOST="tcp://"+ip+":1883";
        String USERNAME="jeevan";
        String PASSWORD="timsina";




        //putExtra method to sent the data to another activity
        ob1.putExtra("ip", ip);

        //Connecting to MQTT Server
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(getApplicationContext(),MQTTHOST,
                clientId);

        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        connect();

        client.setCallback(new MqttCallback() {
            @Override

            public void connectionLost(Throwable cause) {
                //Toast.makeText(MainActivity.this, "FAILURE", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String rts=new String(message.getPayload());
                if(rts.equals("1"))
                {
                    Main2Activity.t1.setText("Off");
                }
                else
                {
                    if(rts.equals("0"))
                    {
                        Main2Activity.t1.setText("On");
                    }
                    else
                    {
                        Main2Activity.t1.setText("Offline");
                    }

                }

                //String rt="1";
                //ob2.putExtra("rts",rt);
                //Toast.makeText(MainActivity.this, rts, Toast.LENGTH_LONG).show();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }
    public void connect()
    {

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    //Toast.makeText(MainActivity.this, "MQTT SERVER CONNECTED", Toast.LENGTH_SHORT).show();
                    //publis("hello","light");

                    startActivity(ob1);

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "SERVER CONNECTION FAILED", Toast.LENGTH_SHORT).show();

                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }



    public static int publis(String data,String stopic) {
        String message = data;
        String topic = stopic;
        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        if(client.isConnected() == true)
            return 1;
        else
            return 0;
    }
    public static void subscrib(String topic)
    {

        try{
            client.subscribe(topic,0);

        }catch(MqttException e)
        {
            e.printStackTrace();
        }

    }

}



//page change//

    /*public void pageChange(View v){
        Intent p=new Intent(getApplicationContext(),Main2Activity.class);
        startActivity(p);

    }
    */


