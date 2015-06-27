package com.example.android.saveatserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deba on 18/6/15.
 */
public class SQLView extends Activity {
Button sendToServer_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlview);

        TextView tv = (TextView) findViewById(R.id.tvSQLinfo);


        DB_Connection info = new DB_Connection(this);


        try {
            info.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String data = info.getData();
        info.close();
        tv.setText(data);


        sendToServer_btn = (Button)findViewById(R.id.sendToServer_btn);
        sendToServer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBAdapter dbAdapter=DBAdapter.getDBAdapterInstance(this);
                try {
                    dbAdapter.createDataBase();
                } catch (IOException e) {
                    Log.i("*** select ", e.getMessage());
                }

                dbAdapter.openDataBase();
                String query="SELECT * FROM registration";
                ArrayList<ArrayList<String>> stringList = dbAdapter.selectRecordsFromDBList(query, null);
                dbAdapter.close();

                String url="http://www.debasde.net78.net/DB_Connect.php";

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                try {

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    for (int i = 0; i < stringList.size(); i++) {
                        ArrayList<String> list = stringList.get(i);
                       // nameValuePairs.add(new BasicNameValuePair("_id", list.get(0)));
                        nameValuePairs.add(new BasicNameValuePair("std_name", list.get(1)));
                        nameValuePairs.add(new BasicNameValuePair("std_age", list.get(2)));
                        nameValuePairs.add(new BasicNameValuePair("std_qualification", list.get(3)));
                        nameValuePairs.add(new BasicNameValuePair("emp_org", list.get(4)));
                        nameValuePairs.add(new BasicNameValuePair("emp_skill", list.get(5)));
                        nameValuePairs.add(new BasicNameValuePair("std_colg", list.get(6)));
                        nameValuePairs.add(new BasicNameValuePair("std_sub", list.get(7)));

                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                    }
                } catch (UnsupportedEncodingException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
    }

}
