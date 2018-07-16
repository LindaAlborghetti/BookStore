package com.example.android.bookstoreinventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

public class BooksContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BooksContract() {
    }

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.bookstoreinventoryapp";


    // Use CONTENT_AUTHORITY to create the base of all URI's
    // which apps will use to contact the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Name of the path
    public static final String PATH_BOOKS = "books";

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */

    // The MIME type of the content uri for a list of books.
    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;


    // The MIME type of the content uri for a single book.
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;


    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BooksEntry implements BaseColumns {

        // The content URI to access the book data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        // Name of database table for books
        public final static String TABLE_NAME = "books";

        // Unique ID number for the book (only for use in the database table).
        public final static String _ID = BaseColumns._ID;

        // Title of the book
        public final static String COLUMN_BOOK_TITLE = "name";

        // Author of the book
        public final static String COLUMN_AUTHOR = "author";

        // Quantity value of the book
        public final static String COLUMN_QUANTITY = "quantity";

        // Price of the book
        public final static String COLUMN_PRICE = "price";

        // Name of the supplier
        public final static String COLUMN_SUPPLIER = "supplier";

        // Phone number of the supplier
        public final static String COLUMN_PHONE_NUMBER = "phone";
    }
}
