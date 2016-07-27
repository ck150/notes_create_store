package pstc.notescreatestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


/**
 * An activity representing a single Note detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link NoteListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link NoteDetailFragment}.
 */
public class NoteDetailActivity extends AppCompatActivity {

    private NoteContent.Note mNote;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onStop(){
        super.onStop();
        CacheObjects.writeObject(this, Constants.LIST_CACHE_KEY, NoteContent.ITEMS);
    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_note_detail);

        mNote = NoteContent.ITEM_MAP.get(getIntent().getStringExtra(NoteDetailFragment.ARG_ITEM_ID));
        mPosition = getIntent().getIntExtra(Constants.NOTE_CLICKED_POS,0);
        String heading = mNote.getHeading();
        getSupportActionBar().setTitle("Note: "+heading);

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(NoteDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(NoteDetailFragment.ARG_ITEM_ID));
            NoteDetailFragment fragment = new NoteDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.note_detail_container, fragment)
                    .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constants.EDIT_NOTE_REQUESTCODE)
            try {
                String messageHeading = data.getStringExtra(Constants.HEADING_UPDATE_MESSAGE);
                String messageContent = data.getStringExtra(Constants.CONTENT_UPDATE_MESSAGE);
                //String note_id = Integer.toString(NoteContent.ITEMS.size() + 1);
                Log.v("tag1", messageHeading);

                //NoteContent.Note newNote = new NoteContent.Note(note_id, messageHeading, messageContent);
                //NoteContent.addItem(newNote);
                mNote.setHeading(messageHeading);
                mNote.setDetail(messageContent);
                NoteContent.Note updatedNote = new NoteContent.Note(mNote.id,messageHeading,messageContent);
                NoteContent.ITEMS.get(mPosition).setHeading(messageHeading);
                NoteContent.ITEMS.get(mPosition).setDetail(messageContent);

                NoteContent.ITEM_MAP.get(mNote.id).setHeading(messageHeading);
                NoteContent.ITEM_MAP.get(mNote.id).setDetail(messageContent);

                NoteListFragment.getAdapterInstance().notifyDataSetChanged();
            } catch (NullPointerException n) {
                Log.e("tag1", n.toString());
            }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, NoteListActivity.class));
            return true;
        }else if(id == R.id.action_edit){
            Intent i = new Intent(this,AddNoteActivity.class);
            i.setAction(Constants.EDIT_NOTE_ACTION);
            i.putExtra(Constants.HEADING_FOR_EDIT, mNote.getHeading());
            i.putExtra(Constants.CONTENT_FOR_EDIT,mNote.getDetail());
            startActivityForResult(i, Constants.EDIT_NOTE_REQUESTCODE);
        }

        return super.onOptionsItemSelected(item);
    }
}
