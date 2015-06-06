package engman.jeremy.simpletodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeremy on 6/6/15.
 * implementation of the SQLiteOpenHelper
 */
public class ItemEntryDbHelper extends SQLiteOpenHelper{
    //to change db schema, increment db version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todo.db";

    public ItemEntryDbHelper(Context c) {
        super (c, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQLDatabaseClass.getSqlCreateEntries());
    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //This simply discards data and starts over on upgrade, may need to change
        database.execSQL(SQLDatabaseClass.getSqlDeleteEntries());
        onCreate(database);
    }
    public void onDownGrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onUpgrade(database, oldVersion, newVersion);
    }
}
