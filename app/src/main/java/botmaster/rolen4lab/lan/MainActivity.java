package botmaster.rolen4lab.lan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public TextView mMainScreen;
    public String serverport = "9999";
    public String message = "not null";
    public boolean serverstatus = true;
    public static int i=0;
    public String bot_name, bot_info, bot_status, filecontents;
    public static List<String> bot_list = new ArrayList<String>();
    public static Iterator<String> bot_list_iterator = bot_list.iterator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Adding dummy bots to bot list

        bot_list.add("Bot1");
        bot_list.add("Bot2");
        bot_list.add("Bot3");
        bot_list.add("Bot4");

        setContentView(R.layout.activity_main);
        mMainScreen = (TextView) findViewById(R.id.tv_main_screen);
        mMainScreen.setText(bot_list.size()+" ");

        // server started here to receive bots transmitions
        new receive().execute(serverport);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO - end server with close() if socket is still started
    }

    /*
    Attempt to have Tor services start.
     */
/*
    public class torservices extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String filestorageLocation = "torfiles";
            OnionProxymanager onionProxymanager = new AndroidOnionproxyManager(getApplicationContext(), filestorageLocation);
            int totalSecondsPerTorStartup = 4 * 60;
            int totaltriesPerTorStartup = 5;
            try{
                boolean ok = onionProxymanager.startWithRepeat(totalSecondsPerTorStartup, totaltriesPerTorStartup);
                if(!ok)
                    mMainScreen.append("Couldn't start Tor!");
            } catch (InterruptedException | IOException e){
                e.printStackTrace();
            }
        }
    }
*/
    /*
    Here we start the server to receive informations from bots
    Here we should add the Tor connection code
     */

    public class receive extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String[] str_port = strings;
            int port = Integer.parseInt(str_port[0]);
            ServerSocket ss;
            Socket sokket1 = null;
            message = "BotMaster server started";
            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    mMainScreen.append("Async Task : "+message+"\n");
                }
            });
            try {
                ss = new ServerSocket(port);
                while (true) {
                    new ServerThreads(ss.accept()).start();
                    // TODO - Saving recruit info after accept (Option 1 : using List<String>
                    i+=1;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return message;
        }

        @Override
        protected void onPostExecute(final String result) {
            //mMainScreen.append("On Post Execute : \n" + result);
            //message = "On Post Execute : Server stopped \n";
            //return message;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedID = item.getItemId();
        if(itemThatWasClickedID==R.id.action_startserver){
            Toast.makeText(this,"Start server", Toast.LENGTH_LONG).show();
            //new receive().execute(serverport);
        }else {
            if(itemThatWasClickedID==R.id.action_stopserver){
                Toast.makeText(this,"Tor server", Toast.LENGTH_LONG).show();
            } else {
                if (itemThatWasClickedID == R.id.action_openfile) {
                    startActivity(new Intent(MainActivity.this, BotSummit.class));
                    Toast.makeText(this, "Open bot list", Toast.LENGTH_LONG).show();
                } else {
                    if (itemThatWasClickedID == R.id.action_savefile) {
                        Toast.makeText(this, "Save bot list", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        return true;
    }

    public class ServerThreads extends Thread {
        public Socket sokket1 = null;

        public ServerThreads(Socket sokket) {
            this.sokket1 = sokket;

            try {
                InputStreamReader isr = new InputStreamReader(sokket1.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                message = br.readLine();
                //
                // Split string
                // https://stackoverflow.com/questions/3732790/android-split-string
                // Check what bots are transmiting
                //
                String[] separated = message.split("#");
                bot_status = separated[0];
                bot_name = separated[1];
                bot_info = separated[2];

                // TODO - Adding bot recruiters first attempt to a bot list
                bot_list.add(message);

                //filecontents = "BotID."+bot_name+"."+"Bot Info."+bot_info;

                //mMainScreen.append("Message received \n");
                // Adding new Bot recruit to json file
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mMainScreen.append(message+"\n");
                        if(!bot_status.isEmpty()){
                            mMainScreen.append(bot_status+"\n");
                        }
                        if(!bot_name.isEmpty()){
                            mMainScreen.append(bot_name+"\n");
                            // TODO - Add new bot recruit to bot_table
                        }
                        if(!bot_info.isEmpty()){
                            mMainScreen.append(bot_info+"\n");
                        }
                    }
                });
                //new Log(message);
                br.close();
                isr.close();
                } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public class Log
    {
        String bot_name;
        String bot_info;
        //String[] statustext= {"success","error"};
        //int status;
        //private final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String logfilename="botlist";
        String filecontents;
        FileOutputStream outputstream;
        //String workingdir = Environment.DIRECTORY_DOWNLOADS;
        //PrintWriter out;
        //Log(String s_machineid,String s_action,int s_status){
        //Calendar cal=Calendar.getInstance();
            //System.out.println(sdf.format(cal.getTime()));
            //System.out.println(machineid+semi+action+semi+statustext[status]);
            //System.out.println("\n"+workingdir);
        //FileWriter fw;

        Log(String mesage2json)
        {
            try {
                //fw = new FileWriter(workingdir+logfilename, true);
                //BufferedWriter bw = new BufferedWriter(fw);
                //PrintWriter out = new PrintWriter(bw);
                bot_name = mesage2json.split(Pattern.quote("."))[1];
                bot_info = mesage2json.split(Pattern.quote("."))[2];
                outputstream = openFileOutput(logfilename, Context.MODE_PRIVATE);
                filecontents = "BotID."+bot_name+"."+"Bot Info."+bot_info;
                outputstream.write(filecontents.getBytes());
                outputstream.close();
                //out.flush();
               // out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            // https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
    }
}
