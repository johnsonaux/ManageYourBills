package Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by marca on 03.09.2017.
 */

public final class DBContract {

    public static final String CONTENT_AUTHORITY = "com.example.marca.justjava";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BILLS = "rechnungen";

    private DBContract() {
    }

    public static final class DBEntry implements BaseColumns {

        public final static String TABLE_NAME_BILLS = "rechnungen";

        public final static String TABLE_NAME_USER = "user";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_BILLS_WHO = "wer";

        public final static String COLUMN_BILLS_AMOUNT = "betrag";

        public final static String COLUMN_BILLS_DATE = "datum";

        public final static String COLUMN_BILLS_CATEGORY = "kategorie";

        public final static String COLUMN_BILLS_COMMENT = "kommentar";

        //stored because of setting background color when bill is loaded from db
        public final static String COLUMN_BILLS_GENDER = "geschlecht";

        public final static String COLUMN_USER_GENDER = "gender";

        public final static String COLUMN_USER_NAME = "name";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BILLS);
    }
}
