package engman.jeremy.simpletodo;

import android.content.ClipData;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //three variables for the ArrayList, an ArrayAdapter, and the ListView
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finds view by id, creates the new arraylist, creates the new ArrayAdapter(bridge between listview and data in list, then sets the adapter for listViewItems
        listViewItems = (ListView) findViewById(R.id.listViewItems);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listViewItems.setAdapter(itemsAdapter);
        //call item removal listener for long press
        longPressListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //new method that is attached to the addItemButton
    public void onClick(View v) {
        //new edit text variable, with xml edittext box assigned by id
        EditText editTextNewItem = (EditText) findViewById(R.id.editTextNewItem);
        //new string variable with text in editTextNewItem textbox assigned and converted to string
        String itemText = editTextNewItem.getText().toString();
        //Array adapter adds new item to array
        itemsAdapter.add(itemText);

        /** sqlite database code
        //instantiate the dbhelper class
        ItemEntryDbHelper itemDbHelper = new ItemEntryDbHelper(this);
        //gets data repository in write mode
        SQLiteDatabase database = itemDbHelper.getWritableDatabase();
        //create new map of values, column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLDatabaseClass.ItemEntry.COLUMN_NAME_ITEM_ID, items.size()+1);
        values.put(SQLDatabaseClass.ItemEntry.COLUMN_NAME_ITEM_TEXT, itemText);

        //inserts the new row in the database, returning the primary key value of the new row
        long newRowId;
        newRowId = database.insert(SQLDatabaseClass.ItemEntry.TABLE_NAME, null, values);
        */

        //blanks the editTextNewItem text box since it's been added to the list
        editTextNewItem.setText("");
    }

    //creates an event handler that listens for long presses on items in the ListView
    private void longPressListener() {
        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //remove item in the items array at position that has been long pressed
                items.remove(position);
                //refresh the adapter
               itemsAdapter.notifyDataSetChanged();
                //return true to show that the even has been handled
                return true;
            }
        });
    }


}
