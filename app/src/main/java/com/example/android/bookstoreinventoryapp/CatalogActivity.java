package com.example.android.bookstoreinventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.bookstoreinventoryapp.data.BooksContract.BooksEntry;
import com.example.android.bookstoreinventoryapp.data.BooksDbHelper;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Name of the Loader
    private static final int BOOKS_LOADER = 0;

    // Adapter for the ListView
    BooksCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fabIntent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(fabIntent);
            }
        });

        // Find the ListView which will be populated with the books data
        ListView booksListView = (ListView) findViewById(R.id.list_view_books);

        // Find and set an empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        booksListView.setEmptyView(emptyView);

        // Set up an adapter to create a list item for each row of book data in the Cursor.
        // There is no book data yet (until the loader finishes) so pass in null for the Cursor
        mCursorAdapter = new BooksCursorAdapter(this, null);
        booksListView.setAdapter(mCursorAdapter);

        // Setup item click listener
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create a new intent to go to the EditorActivity
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                // Form the content Uri that represent the specific pet that was clicked on
                Uri currentBookUri = ContentUris.withAppendedId(BooksEntry.CONTENT_URI, id);
                intent.setData((currentBookUri));
                startActivity(intent);
            }
        });

        // Kick off the loader
        getLoaderManager().initLoader(BOOKS_LOADER, null, this);
    }


    // Method that allows us to insert a book in the database
    private void insertBook() {

        // Create a ContentValues object where column names are the keys,
        // and books' attributes are the values.
        ContentValues values = new ContentValues();
        values.put(BooksEntry.COLUMN_BOOK_TITLE, "Title");
        values.put(BooksEntry.COLUMN_AUTHOR, "Author");
        values.put(BooksEntry.COLUMN_QUANTITY, "0");
        values.put(BooksEntry.COLUMN_PRICE, "0");
        values.put(BooksEntry.COLUMN_SUPPLIER, "Supplier");
        values.put(BooksEntry.COLUMN_PHONE_NUMBER, "Number");

        // Insert a new row for the default book into the provider using the ContentResolver.
        // Use the {@link BookEntry#CONTENT_URI} to indicate that we want to insert
        // into the books database table.
        // Receive the new content URI that will allow us to access the data in the future.
        Uri newUri = getContentResolver().insert(BooksEntry.CONTENT_URI, values);
    }


    // Helper method to delete all pets in the database.
    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(BooksEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.catalog_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert Default Book" menu option
            case R.id.action_insert_dummy_data:
                insertBook();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BooksEntry._ID,
                BooksEntry.COLUMN_BOOK_TITLE,
                BooksEntry.COLUMN_PRICE,
                BooksEntry.COLUMN_QUANTITY};

        // Perform a query on the Provider using ContentResolver
        return new CursorLoader(this,
                BooksEntry.CONTENT_URI,   // The content URI of the words table
                projection,            // The columns to return for each row
                null,                  // Selection criteria
                null,                  // Selection criteria
                null);                  // The sort order for the returned rows
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the PetCursorAdapter with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data is deleted
        mCursorAdapter.swapCursor(null);

    }
}

