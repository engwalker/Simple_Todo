package engman.jeremy.simpletodo;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //three variables for the ArrayList, an ArrayAdapter, and the ListView
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listViewItems;

    //declare database, cursor, and length variables
    SQLiteDatabase db;
    Cursor c;
    int dbLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finds view by id, creates the new arraylist
        listViewItems = (ListView) findViewById(R.id.listViewItems);
        items = new ArrayList<>();

        //call item removal listener for long press
        longPressListener();

        //create (if not exist) or open database and table
        db = openOrCreateDatabase("ItemDB", Context.MODE_PRIVATE, null);
        //db.execSQL("CREATE TABLE IF NOT EXISTS item(itemnum INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, itemtext VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS item(itemid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, itemtext VARCHAR);");

        //runs an SQL query selecting all from table item
        c = db.rawQuery("SELECT * FROM item", null);
        //populate arrayList if there are items in the database
        dbLength = c.getCount();
        //c.movetofirst not working?
        if (dbLength != 0 )
        {
            while (c.moveToNext()) {
                items.add(c.getString(1));
            }
        }

        // defines ArrayAdapter(bridge between listview and data in list, then sets the adapter for listViewItems
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listViewItems.setAdapter(itemsAdapter);
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

        db.execSQL("INSERT INTO item VALUES(NULL,'" + itemText + "');");

        //blanks the editTextNewItem text box since it's been added to the list
        editTextNewItem.setText("");
    }

    //creates an event handler that listens for long presses on items in the ListView
    private void longPressListener() {
        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //delete item from list that matches itemText. Gives possibilities of duplicates, need to revise
                db.execSQL("DELETE FROM item WHERE itemtext='" + items.get(position) +"'");

                //remove item in the items array at position that has been long pressed
                items.remove(position);
                //refresh the adapter
               itemsAdapter.notifyDataSetChanged();
                //return true to show that the even has been handled
                return true;
            }
        });
    }

    //onStop called as app goes off screen, before onDestroy. Closing database
    public void onStop() {
        super.onStop();
        db.close();
    }


}
