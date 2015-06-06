package engman.jeremy.simpletodo;

import android.content.ClipData;
import android.provider.BaseColumns;

/**
 * Created by jeremy on 6/6/15.
 * This class defines how the arraylist will be written to an SQL database
 */
public class SQLDatabaseClass {
    //empty no-arg constructor to prevent an accidental instantiation
    public SQLDatabaseClass() {}

    //defines sql table contents
    public static abstract class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_ITEM_ID = "itemid";
        public static final String COLUMN_NAME_ITEM_TEXT = "itemtext";
    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }

    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + ItemEntry.TABLE_NAME + " (" + ItemEntry._ID + " INTEGER PRIMARY KEY," + ItemEntry.COLUMN_NAME_ITEM_ID + TEXT_TYPE + COMMA_SEP + ItemEntry.COLUMN_NAME_ITEM_TEXT + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME;
}

