package com.example.android.saveatserver;

/**
 * Created by deba on 17/6/15.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.android.saveatserver.R.id.radioButton_yes;

public class Register extends Activity implements OnClickListener {

    /** Variable Declaration for SQLlite Database  **/
    Button sql_Save_button, sql_bSQLopenView;
    EditText sql_Name_editText, sql_Age_editText, sql_Organization_editText, sql_Eskill_editText, sql_College_editText, sql_Subject_editText;
    Spinner sql_Qualification_spinner;



/** Variable Declaration for Visible true and False of Radio Button  **/

    private RadioGroup choice;
    private TextView Org_textView;
    private EditText Org_editText;
    private TextView Eskill_txtView;
    private EditText Eskill_etText;

    private TextView Clg_textView;
    private EditText Clg_editText;
    private TextView Sub_txtView;
    private EditText Sub_etText;

    /** called when activity is first created **/

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        /** Variable Assignment for SQLITE Database Connection  **/
        sql_Save_button = (Button)findViewById(R.id.Save_button);
        sql_bSQLopenView = (Button)findViewById(R.id.bSQLopenView);
        sql_Name_editText = (EditText)findViewById(R.id.Name_editText);
        sql_Age_editText = (EditText)findViewById(R.id.Age_editText);
        sql_Organization_editText = (EditText)findViewById(R.id.Organization_editText);
        sql_Eskill_editText = (EditText)findViewById(R.id.Eskill_editText);
        sql_College_editText = (EditText)findViewById(R.id.College_editText);
        sql_Subject_editText = (EditText)findViewById(R.id.Subject_editText);
        sql_Qualification_spinner = (Spinner)findViewById(R.id.Qualification_spinner);


        sql_Save_button.setOnClickListener(this);
        sql_bSQLopenView.setOnClickListener(this);
        /** Variable Assignment for Visible true and False of Radio Button  **/

       choice = (RadioGroup) findViewById(R.id.choice);
        Org_textView = (TextView)findViewById(R.id.Organization_textView);
        Org_editText = (EditText)findViewById(R.id.Organization_editText);
        Eskill_txtView = (TextView)findViewById(R.id.Eskill_textView);
        Eskill_etText = (EditText)findViewById(R.id.Eskill_editText);

        Clg_textView = (TextView)findViewById(R.id.College_textView);
        Clg_editText = (EditText)findViewById(R.id.College_editText);
        Sub_txtView = (TextView)findViewById(R.id.Subject_textView);
        Sub_etText = (EditText)findViewById(R.id.Subject_editText);

        choice.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {

                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId) {

                            case radioButton_yes:
                                Org_textView.setVisibility(View.VISIBLE);
                                Org_editText.setVisibility(View.VISIBLE);
                                Eskill_txtView.setVisibility(View.VISIBLE);
                                Eskill_etText.setVisibility(View.VISIBLE);

                                Clg_textView.setVisibility(View.GONE);
                                Clg_editText.setVisibility(View.GONE);
                                Sub_txtView.setVisibility(View.GONE);
                                Sub_etText.setVisibility(View.GONE);
                                break;

                            case R.id.radioButton_no:
                                Org_textView.setVisibility(View.GONE);
                                Org_editText.setVisibility(View.GONE);
                                Eskill_txtView.setVisibility(View.GONE);
                                Eskill_etText.setVisibility(View.GONE);

                                Clg_textView.setVisibility(View.VISIBLE);
                                Clg_editText.setVisibility(View.VISIBLE);
                                Sub_txtView.setVisibility(View.VISIBLE);
                                Sub_etText.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
    }

    public void onClick(View arg0){
/** Auto Generated Method Stuf**/
       switch(arg0.getId()){
           case R.id.Save_button:
                boolean didItWork = true;
               try {
                   String std_Name = sql_Name_editText.getText().toString();
                   String std_Age = sql_Age_editText.getText().toString();
                   String std_Org = sql_Organization_editText.getText().toString();
                   String std_Eskill = sql_Eskill_editText.getText().toString();
                   String std_Colg = sql_College_editText.getText().toString();
                   String std_Sub = sql_Subject_editText.getText().toString();
                   String std_Quali = sql_Qualification_spinner.getSelectedItem().toString();

                   DB_Connection entry = new DB_Connection(Register.this);
                   entry.open();
                   entry.createEntry(std_Name, std_Age, std_Quali, std_Org, std_Eskill, std_Colg, std_Sub);

                   entry.close();

               }catch (Exception e){
                didItWork = false;
                   String error = e.toString();
                   Dialog d = new Dialog(this);
                   d.setTitle("Dang it!");
                   TextView tv = new TextView(this);
                   tv.setText(error);
                   d.setContentView(tv);
                   d.show();
               }finally {
                   if (didItWork){
                       Dialog d = new Dialog(this);
                       d.setTitle("DATA INSERTED!!");
                       TextView tv = new TextView(this);
                       tv.setText("Success");
                       d.setContentView(tv);
                       d.show();
                   }
               }
               break;

           case R.id.bSQLopenView:
               Intent i = new Intent("android.intent.action.SQLVIEW");

               /**  Intent i = new Intent(this, SQLView.class);  **/
               startActivity(i);

               break;
       }
    }
}
