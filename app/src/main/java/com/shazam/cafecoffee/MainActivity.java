package com.shazam.cafecoffee;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    public int quantity = 1;
    public String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        orderMail(quantity);
    }

    public void displayOrder(View view) {
        previewOrder(quantity);
    }

    public void increment(View view) {
        quantity += 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity -= 1;
            display(quantity);
        }
    }

    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private boolean previewOrder(int number) {

        EditText nameField = findViewById(R.id.name);
        CheckBox whippedCream = findViewById(R.id.check_box_whipped_cream);
        CheckBox chocolate = findViewById(R.id.check_box_chocolate);
        TextView orderSummary = findViewById(R.id.order_summary);

        int cost = 500;

        if (whippedCream.isChecked()) {
            cost += 100;
        }

        if (chocolate.isChecked()) {
            cost += 200;
        }

        if (nameField.getText().toString().matches("")) {

            /* Context is an abstract class whose implementation is provided by the Android system. It allows access to application-specific resources and classes, as well as up-calls for application-level operations such as launching activities, broadcasting and receiving intents, etc. */
            Context context = getApplicationContext();
            CharSequence text = "Enter Name!";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();

            return false;
        } else {
            message = "Name: " + nameField.getText().toString() + "\nAdd Whipped Cream? " + whippedCream.isChecked() + "\nAdd Chocolate? " + chocolate.isChecked() + "\nTotal: " + NumberFormat.getCurrencyInstance().format(number * cost) + "\nThank you!";

            orderSummary.setText(message);
        }
        return true;
    }

    private void orderMail(int number) {
        if (previewOrder(number)) {
            EditText nameField = findViewById(R.id.name);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this

            intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + nameField.getText().toString());

            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"aabhaschopra@gmail.com"});

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
