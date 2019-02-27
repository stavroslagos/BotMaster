package botmaster.rolen4lab.lan;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class BotSummit extends AppCompatActivity {
    TextView mSecondaryScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_summit);
        mSecondaryScreen = (TextView) findViewById(R.id.tv_secondary_screen);

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
}
