package com.jeevan.csemil.iot;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    Button btn11;
    Button btn10;
    int retVal;
    Intent i1;
    String name;
    static  TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);





        //ADDING BUTTONS OF LIGHT (ON->btn11,OFF->btn10)
        btn11=(Button)findViewById(R.id.btn11);
        btn10=(Button)findViewById(R.id.btn10);



        //GETTING DATA FROM PREVIOUS ACTIVITY
        String ip=getIntent().getStringExtra("ip"); //getting ip info
        //Bundle extras=getIntent().getExtras();



        // byte[] realTimeState=getIntent().getExtras().getByteArray("rts");//getting status info from mqtt
        //try {
        //Log.d("Value: " + byte[].toString(realTimeState), "ADebugTag");
        //name=Base64.encodeToString(realTimeState,0);

        //String name=new String();
       /*} catch (UnsupportedEncodingException e) {
           e.printStackTrace();
        }*/
        //String name=new String(realTimeState);
        //Toast.makeText(Main2Activity.this, realTimeState , Toast.LENGTH_SHORT).show();
        //int port=getIntent().getIntExtra("port",-1);


        //DISPLAY THE DATA RECEIVED FROM PREVIOUS ACTIVITY//

        TextView ob1=(TextView)findViewById(R.id.ipid);
        ob1.setText(ip);


        //String realTimeState=getIntent().getStringExtra("rts");
        MainActivity.subscrib("state");
        t1=(TextView)findViewById(R.id.status1);
        //Toast.makeText(Main2Activity.this, MainActivity.rts, Toast.LENGTH_LONG).show();
        /*if(MainActivity.rts=="1") {

            t1.setText("On");
        }
        else {
            t1.setText("Off");
        }*/

        //STATUS BUTTON/


        //ADDING TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        //Light 1//

        btn11.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                onnClick("light");
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                offClick("light");
            }
        });

    }

    //TO DISPLAY MENU OPTIONS//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inside,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int i=item.getItemId();
        if(i==R.id.menu_in_id1)
        {
            //ADDING DEVICE
            //FIRST THING IS TO CREATE A DIALOG BOX//ENQUIRING ABOUT THE NAME OF THE DEVICE//
            AlertDialog.Builder menuDialog1=new AlertDialog.Builder(Main2Activity.this);
            menuDialog1.setMessage("Do you want to add a device")
                    .setCancelable(true)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alert=menuDialog1.create();
            alert.setTitle("Add Device");
            alert.show();

        }
        else if(i==R.id.menu_in_id2)
        {
            //DISCONNECT
            AlertDialog.Builder menuDialog2=new AlertDialog.Builder(Main2Activity.this);
            menuDialog2.setMessage("Do you want to Disconnect")
                    .setCancelable(true)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
            AlertDialog alert=menuDialog2.create();
            alert.setTitle("Disconnect");
            alert.show();


        }
        else if(i==R.id.menu_in_id3)
        {
            //ABOUT
            AlertDialog.Builder menuDialog3=new AlertDialog.Builder(Main2Activity.this);
            menuDialog3.setMessage("This is an IOT based application-SmartHome" +
                    "\nCreated by-JEEVAN\n" +
                    "Arina\n" +
                    "Prakash")
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

            AlertDialog alert=menuDialog3.create();
            alert.setTitle("About");
            alert.show();

        }
        return super.onOptionsItemSelected(item);
    }


    public void onnClick(String strTopic)
    {
        //String val=btn11.getText().toString();
        //publis(null,"1","light");

        String topic = strTopic;
        String message = "1";
        retVal=MainActivity.publis("1",strTopic);
        if(retVal==1)
        {
            //t1.setText("On");
        }
        else
        {
            Toast.makeText(Main2Activity.this, "SERVER CONNECTION FAILED", Toast.LENGTH_SHORT).show();
            startActivity(i1);
        }

        //Toast.makeText(this.getApplicationContext(),"You have written ON",Toast.LENGTH_SHORT).show();
    }

    public void offClick(String strTopic)
    {

        //String val=btn11.getText().toString();
        String topic = strTopic;
        String message = "0";
        //Toast.makeText(Main2Activity.this,"OFFFFF",Toast.LENGTH_SHORT).show();
        retVal=MainActivity.publis("0",strTopic);
        if(retVal==1)
        {
            //t1.setText("Off");
        }
        else
        {
            Toast.makeText(Main2Activity.this, "SERVER CONNECTION FAILED", Toast.LENGTH_SHORT).show();
            startActivity(i1);
        }

    }

}
