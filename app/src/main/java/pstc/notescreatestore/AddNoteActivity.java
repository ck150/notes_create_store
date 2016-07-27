package pstc.notescreatestore;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class AddNoteActivity extends AppCompatActivity {


    private EditText editHeading;
    private EditText editContent;
    private boolean intentEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editHeading = (EditText) findViewById(R.id.addnote_heading);
        editContent = (EditText) findViewById(R.id.addnote_content);
        intentEdit = false;
        Intent intent = getIntent();
        if(intent.getAction().equals(Constants.EDIT_NOTE_ACTION)){
            intentEdit=true;
            editHeading.setText(intent.getStringExtra(Constants.HEADING_FOR_EDIT));
            editContent.setText(intent.getStringExtra(Constants.CONTENT_FOR_EDIT));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            String messageHeading = editHeading.getText().toString();
            String messageContent = editContent.getText().toString();

            if(editHeading.length()==0){
                Toast.makeText(this,"Heading cannot be left blank",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!intentEdit){
                Intent intent=new Intent();
                intent.putExtra(Constants.HEADING_SAVE_MESSAGE,messageHeading);
                intent.putExtra(Constants.CONTENT_SAVE_MESSAGE,messageContent);
                setResult(Constants.ADD_NOTE_REQUESTCODE, intent);
                finish();//finishing activity
                return true;
            }else{
                Intent i = new Intent();
                i.putExtra(Constants.HEADING_UPDATE_MESSAGE,messageHeading);
                i.putExtra(Constants.CONTENT_UPDATE_MESSAGE,messageContent);
                setResult(Constants.EDIT_NOTE_REQUESTCODE,i);
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
