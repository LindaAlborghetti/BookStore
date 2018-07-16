package com.example.android.bookstoreinventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstoreinventoryapp.data.BooksContract;

/**
 * {@link BooksCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BooksCursorAdapter extends CursorAdapter {

    // Constructs a new BooksCursorAdapter.
    public BooksCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    // Makes a new blank list item view. No data is set (or bound) to the views yet.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    // This method binds the book data (in the current row pointed to by cursor) to the given
    // list item layout. For example, the title for the current book can be set on the name TextView
    // in the list item layout.
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);

        // Get the sale button view
        Button saleButton = (Button) view.findViewById(R.id.sale);

        // Find the columns of books attributes that we are interested in
        final int idColumnIndex = cursor.getInt(cursor.getColumnIndex(BooksContract.BooksEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(BooksContract.BooksEntry.COLUMN_BOOK_TITLE);
        int priceColumnIndex = cursor.getColumnIndex(BooksContract.BooksEntry.COLUMN_PRICE);
        final int quantityColumnIndex = cursor.getColumnIndex(BooksContract.BooksEntry.COLUMN_QUANTITY);

        // Read the books attributes from the cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        String bookPrice = cursor.getString(priceColumnIndex);
        final int bookQuantity = Integer.parseInt(cursor.getString(quantityColumnIndex));

        // Logic for hiding the sale button when the quantity of the current
        // book is 0 (we are out of stock), so you cannot buy the book anymore
        if (bookQuantity > 0) {
            // Button visible if the quantity > 0
            saleButton.setVisibility(View.VISIBLE);
        }

        if (bookQuantity == 0) {
            // Button gone if quantity = 0
            saleButton.setVisibility(View.GONE);
        }

        // Update the TextViews with the attributes for the current book
        nameTextView.setText(bookName);
        priceTextView.setText("Price: " + "€ " + bookPrice);
        quantityTextView.setText("Quantity: " + bookQuantity);

        // Decrease the quantity pf the book
        // by 1 on each click on the sale button
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Display toast message for every click on the sale button
                Toast.makeText(context, "Thanks for buying!", Toast.LENGTH_SHORT).show();

                Uri quantityUri = ContentUris.withAppendedId(BooksContract.BooksEntry.CONTENT_URI, idColumnIndex);
                ContentValues values = new ContentValues();
                values.put(BooksContract.BooksEntry.COLUMN_QUANTITY, bookQuantity - 1);
                context.getContentResolver().update(quantityUri, values, null, null);
            }
        });
    }
}
