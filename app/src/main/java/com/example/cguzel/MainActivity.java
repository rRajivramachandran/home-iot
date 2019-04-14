package com.example.cguzel.nodemcu_app;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by cguzel on 26.04.2016.
 */

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    private EditText ipAddress;

    private String serverAdress;
    private Button b1,b2,b3,b4,pl1,pl2,pl3,pl4,ml1,ml2,ml3,ml4,btn_addr;
    private boolean rm1,rm2,rm3,rm4;
    private ProgressBar p4,p3,p2,p1,po;
    private LinearLayout bout;
    private RelativeLayout r1,r2,r3,r4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        po=(ProgressBar) findViewById(R.id.po);

        ipAddress = (EditText) findViewById(R.id.edt_ip);
        btn_addr=(Button) findViewById(R.id.btn_ledOn);
        bout=(LinearLayout) findViewById(R.id.bout);
        p1=(ProgressBar) findViewById(R.id.p1);

        p2=(ProgressBar) findViewById(R.id.p2);
        p3=(ProgressBar) findViewById(R.id.p3);
        p4=(ProgressBar) findViewById(R.id.p4);
        b1=(Button) findViewById(R.id.b1);
        r1=(RelativeLayout) findViewById(R.id.r1);
        r2=(RelativeLayout) findViewById(R.id.r2);
        r3=(RelativeLayout) findViewById(R.id.r3);
        r4=(RelativeLayout) findViewById(R.id.r4);
        b2=(Button) findViewById(R.id.b2);
        b3=(Button) findViewById(R.id.b3);
        b4=(Button) findViewById(R.id.b4);
        pl1=(Button) findViewById(R.id.pl1);
        pl2=(Button) findViewById(R.id.pl2);
        pl3=(Button) findViewById(R.id.pl3);
        pl4=(Button) findViewById(R.id.pl4);
        ml1=(Button) findViewById(R.id.ml1);
        ml2=(Button) findViewById(R.id.ml2);
        ml3=(Button) findViewById(R.id.ml3);
        ml4=(Button) findViewById(R.id.ml4);
        rm1=false;
        rm2=false;
        rm3=false;
        rm4=false;
        turnoff(r1,pl1,ml1,p1,1);
        turnoff(r2,pl2,ml2,p2,2);
        turnoff(r3,pl3,ml3,p3,3);
        turnoff(r4,pl4,ml4,p4,4);
        initialblackout();
      //  Log.i("t",p1.getProgress()+" ");


    }

    public void crsend(int i,int j)
    {
        String send=Integer.toString(i)+Integer.toString(j);
       // Log.e( "crsend: ",serverAdress );
        HttpRequestTask a=new HttpRequestTask(serverAdress);

        a.execute(send);
    }
    public void initialblackout()
    {
        po.setVisibility(View.INVISIBLE);
        bout.setVisibility(View.INVISIBLE);
        ipAddress.setVisibility(View.VISIBLE);
        btn_addr.setVisibility(View.VISIBLE);
        crsend(1,0);
        crsend(2,0);
        crsend(3,0);
        crsend(4,0);

    }

    public void setprogress(ProgressBar p, boolean sign,int j)
    {
        int i;
        if(sign)
        {
            i=p.getProgress();
            if(i!=4)
            {
                p.setProgress(i+1);
                i=i+1;
            }

        }
        else
        {
            i=p.getProgress();
            if(i!=0)
            {
                p.setProgress(i-1);
                i=i-1;
            }

        }
        crsend(j,i);
        if(p1.getProgress()==0)
        {
            b1.setText("Turn On");
            rm1=false;
            roomhandle(r1,false,pl1,ml1,p1,1);
        }
        if(p2.getProgress()==0)
        {
            b2.setText("Turn On");
            rm2=false;
            roomhandle(r2,false,pl2,ml2,p2,2);
        }
        if(p3.getProgress()==0)
        {
            b3.setText("Turn On");
            rm3=false;
            roomhandle(r3,false,pl3,ml3,p3,3);
        }
        if(p4.getProgress()==0)
        {
            b4.setText("Turn On");
            rm4=false;
            roomhandle(r4,false,pl4,ml4,p4,4);
        }


    }
    public void roomhandle(RelativeLayout r,boolean rm,Button b1,Button b2,ProgressBar p,int i)
    {
        if(!rm)
        {

            r.setBackgroundColor(Color.parseColor("#000000"));
            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.INVISIBLE);
            p.setVisibility(View.INVISIBLE);
            crsend(i,0);
        }
        else
        {

            r.setBackgroundColor(Color.parseColor("#ff9900"));
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            p.setProgress(1);
            crsend(i,1);

        }
    }
    public void buttonhandler(Button b,int i,RelativeLayout r,Button b1,Button b2,ProgressBar p)
    {

        switch (i)
        {
            case 1: if(rm1) {
                b.setText("Turn On");
                rm1 = false;
            }
                    else
            { b.setText("Turn Off");
            rm1=true;}
                    roomhandle(r,rm1,b1,b2,p,i);
                    break;
            case 2: if(rm2) {
                b.setText("Turn On");
                rm2 = false;
            }
            else
            { b.setText("Turn Off");
                rm2=true;}
                roomhandle(r,rm2,b1,b2,p,i);
                break;
            case 3: if(rm3) {
                b.setText("Turn On");
                rm3 = false;
            }
            else
            { b.setText("Turn Off");
                rm3=true;}
                roomhandle(r,rm3,b1,b2,p,i);
                break;
            case 4: if(rm4) {
                b.setText("Turn On");
                rm4 = false;
            }
            else
            { b.setText("Turn Off");
                rm4=true;}
                roomhandle(r,rm4,b1,b2,p,i);
                break;


        }
    }
    public void turnoff(RelativeLayout r,Button b1,Button b2,ProgressBar p,int i) {


            r.setBackgroundColor(Color.parseColor("#000000"));
            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.INVISIBLE);
            p.setVisibility(View.INVISIBLE);

            crsend(i, 0);

    }
    public void allout(View view)
    {
        crsend(1,0);
        crsend(2,0);
        crsend(3,0);
        crsend(4,0);
        rm1=false;
        rm2=false;
        rm3=false;
        rm4=false;
        turnoff(r1,pl1,ml1,p1,1);
        turnoff(r2,pl2,ml2,p2,2);
        turnoff(r3,pl3,ml3,p3,3);
        turnoff(r4,pl4,ml4,p4,4);
        b1.setText("Turn On");
        b2.setText("Turn On");
        b3.setText("Turn On");
        b4.setText("Turn On");
    }
    public void pp1(View view)
    {
        setprogress(p1,true,1);
    }
    public void pp2(View view)
    {
        setprogress(p2,true,2);
    }
    public void pp3(View view)
    {
        setprogress(p3,true,3);
    }
    public void pp4(View view)
    {
        setprogress(p4,true,4);
    }
    public void mm1(View view)
    {
        setprogress(p1,false,1);
    }
    public void mm2(View view)
    {
        setprogress(p2,false,2);
    }
    public void mm3(View view)
    {
        setprogress(p3,false,3);
    }
    public void mm4(View view)
    {
        setprogress(p4,false,4);
    }
    public  void ro1(View view)
    {
        buttonhandler(b1,1,r1,pl1,ml1,p1);
    }
    public  void ro2(View view)
    {
        buttonhandler(b2,2,r2,pl2,ml2,p2);
    }
    public  void ro3(View view)
    {
        buttonhandler(b3,3,r3,pl3,ml3,p3);
    }
    public  void ro4(View view)
    {
        buttonhandler(b4,4,r4,pl4,ml4,p4);
    }
    /** When the button clicks this method executes**/
    public void buttonClick(View view) {
        String ledStatus;

        if (ipAddress.getText().toString().equals(""))
            Toast.makeText(MainActivity.this, "Please enter the ip address...", Toast.LENGTH_SHORT).show();
        //Connect to default port number. Ex: http://IpAddress:80
        serverAdress = ipAddress.getText().toString() + ":" + "80";
        po.setVisibility(View.GONE);
        bout.setVisibility(View.VISIBLE);
        ipAddress.setVisibility(View.GONE);
        btn_addr.setVisibility(View.GONE);
        Log.i("hdhd",serverAdress);

    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {

        private String serverAdress;
        private String serverResponse = "";


        public HttpRequestTask(String serverAdress) {
            this.serverAdress = serverAdress;


        }

        @Override
        protected String doInBackground(String... params) {

            String val = params[0];
            final String url = "http://" + serverAdress +'/'+ val;

            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet();
                getRequest.setURI(new URI(url));
                HttpResponse response = client.execute(getRequest);

                InputStream inputStream = null;
                inputStream = response.getEntity().getContent();
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream));

                serverResponse = bufferedReader.readLine();
                inputStream.close();

            } catch (URISyntaxException e) {
                e.printStackTrace();
                serverResponse = e.getMessage();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                serverResponse = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                serverResponse = e.getMessage();
            }

            return serverResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            //dialog.setMessage(serverResponse);

            Log.i("Tag",s);
        }


    }

}
