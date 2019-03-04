package botmaster.rolen4lab.lan;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Pattern;

import static botmaster.rolen4lab.lan.MainActivity.bot_list;
import static botmaster.rolen4lab.lan.MainActivity.bot_list_iterator;
import static botmaster.rolen4lab.lan.MainActivity.i;

public class BotSummit extends AppCompatActivity {
    TextView mSecondaryScreen;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_summit);
        //params.setMargins(8,0,8,0);
        //mSecondaryScreen = (TextView) findViewById(R.id.tv_secondary_screen);
        linearLayout = findViewById(R.id.ll_activity_bot_summit);

        //Adding a scrollview
        ScrollView scrollview = new ScrollView(this);
        scrollview.setLayoutParams(params);

        // Adding 12 textviews
        for (String tv_name : bot_list) {
            TextView textView = new TextView(this);
            textView.setText(tv_name);
            //textView.setLayoutParams(params);
            //textView.setPadding(8,8,8,8);
            CheckBox checkbox = new CheckBox((this));
            //checkbox.setLayoutParams(params);
            //checkbox.setPadding(8,8,8,8);
            linearLayout.addView(textView);
            linearLayout.addView(checkbox);
        }
        linearLayout.addView(scrollview);
        /*
        mSecondaryScreen.setText(bot_list.size()+" ");
        for (String tv_name:bot_list ){
            mSecondaryScreen.append(tv_name);
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.secondary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedID = item.getItemId();
        if(itemThatWasClickedID==R.id.action_openfile){
            Toast.makeText(this,"Open File", Toast.LENGTH_LONG).show();
        }else {
            if(itemThatWasClickedID==R.id.action_savefile){
                Toast.makeText(this,"Save File", Toast.LENGTH_LONG).show();
            } else {
                if (itemThatWasClickedID == R.id.action_back) {
                    finish();
                    Toast.makeText(this, "Back", Toast.LENGTH_LONG).show();
                }
            }
        }
        return true;
    }
    public class Log
    {
        //String bot_name;
        //String bot_info;
        //String[] statustext= {"success","error"};
        //int status;
        //private final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String logfilename="botlist";
        //PrintWriter out;
        //Log(String s_machineid,String s_action,int s_status){
        //Calendar cal=Calendar.getInstance();
        //System.out.println(sdf.format(cal.getTime()));
        //System.out.println(machineid+semi+action+semi+statustext[status]);
        //System.out.println("\n"+workingdir);
        File file = new File(logfilename);
        //BufferedReader br = new BufferedReader(fr);
        //bot_name = mesage2json.split(Pattern.quote("."))[1];
        //bot_info = mesage2json.split(Pattern.quote("."))[2];
        //out.println("BotID."+bot_name+"."+"Bot Info."+bot_info);
         Scanner sc;

        {
            try {
                sc = new Scanner(file);
                while (sc.hasNextLine()){
                    //mSecondaryScreen.setText(sc.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
    }

}
