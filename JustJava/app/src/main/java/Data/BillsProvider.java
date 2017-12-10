package Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by marca on 21.10.2017.
 */

//currently not in use - loading data with content provider will be implemented soon

public class BillsProvider extends ContentProvider {

    public static final String LOG_TAG = BillsProvider.class.getName();
    /*
    * when Content Provider will be implemented
    * needed to identify table query OR single row query
    * then equality to BILLS or BILLS_ID will be checked
    */
    private static final int BILLS = 100;
    private static final int BILLS_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.PATH_BILLS, BILLS);
        sUriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.PATH_BILLS + "/#", BILLS_ID);
    }

    private BillsDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new BillsDbHelper(getContext());
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case BILLS:
                cursor = database.query(DBContract.DBEntry.TABLE_NAME_BILLS, projection, null, null, null, null, null);
                break;
            case BILLS_ID:
                selection = DBContract.DBEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(DBContract.DBEntry.TABLE_NAME_BILLS, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can not query unknown URI: " + uri);
        }
        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BILLS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertPet(Uri uri, ContentValues contentValues) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(DBContract.DBEntry.TABLE_NAME_BILLS, null, contentValues);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }
}
