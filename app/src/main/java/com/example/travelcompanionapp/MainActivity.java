package com.example.travelcompanionapp;

// This import is needed for the activity lifecycle
import android.os.Bundle;

// This import is needed for button click handling
import android.view.View;

// This import is used to load the arrays into the spinners
import android.widget.ArrayAdapter;

// This import is for the button
import android.widget.Button;

// This import is for the input box
import android.widget.EditText;

// This import is for the dropdown menus
import android.widget.Spinner;

// This import is for text views
import android.widget.TextView;

// This import is for short popup messages
import android.widget.Toast;

// This is the base activity class
import androidx.appcompat.app.AppCompatActivity;

// This is the main activity class
public class MainActivity extends AppCompatActivity {

    // This variable stores the input box
    EditText user_input;

    // These variables store the two spinners
    Spinner dropdown_menu1, dropdown_menu2;

    // This variable stores the convert button
    Button convert_button;

    // This variable stores the result text
    TextView result_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // This runs when the app opens
        super.onCreate(savedInstanceState);

        // This connects the Java file to the XML layout
        setContentView(R.layout.activity_main);

        // These lines connect the Java variables to the XML ids
        user_input = findViewById(R.id.user_input);
        dropdown_menu1 = findViewById(R.id.dropdown_menu1);
        dropdown_menu2 = findViewById(R.id.dropdown_menu2);
        convert_button = findViewById(R.id.convert_button);
        result_value = findViewById(R.id.result_value);

        // This creates the adapter for the first spinner
        // It loads the source units from strings.xml
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.source_units_array,
                android.R.layout.simple_spinner_item
        );

        // This sets the dropdown style for the first spinner
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // This connects the first adapter to the first spinner
        dropdown_menu1.setAdapter(adapter1);

        // This creates the adapter for the second spinner
        // It loads the destination units from strings.xml
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.destination_units_array,
                android.R.layout.simple_spinner_item
        );

        // This sets the dropdown style for the second spinner
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // This connects the second adapter to the second spinner
        dropdown_menu2.setAdapter(adapter2);

        // This runs when the convert button is clicked
        convert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This gets the value entered by the user
                String input = user_input.getText().toString().trim();

                // This checks if the input box is empty
                if (input.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                // This changes the text input into a number
                double value = Double.parseDouble(input);

                // This gets the selected source unit from spinner 1
                String fromUnit = dropdown_menu1.getSelectedItem().toString();

                // This gets the selected destination unit from spinner 2
                String toUnit = dropdown_menu2.getSelectedItem().toString();

                // This calls the main conversion method
                double result = convertUnits(value, fromUnit, toUnit);

                // This shows the result on the screen
                result_value.setText(String.format("Converted Value: %.4f", result));
            }
        });
    }

    // main conversion method
    private double convertUnits(double value, String fromUnit, String toUnit) {

        // If both units are the same, return the original value
        if (fromUnit.equals(toUnit)) {
            return value;
        }

        // ------------------------------
        // Currency conversion section
        // ------------------------------

        // This variable stores the value after converting to USD first
        double valueInUSD = 0;

        // Convert the source currency into USD
        if (fromUnit.equals("USD")) {
            valueInUSD = value;
        } else if (fromUnit.equals("AUD")) {
            valueInUSD = value / 1.55;
        } else if (fromUnit.equals("EUR")) {
            valueInUSD = value / 0.92;
        } else if (fromUnit.equals("JPY")) {
            valueInUSD = value / 148.50;
        } else if (fromUnit.equals("GBP")) {
            valueInUSD = value / 0.78;
        }

        // If the source value is a currency, convert from USD to the target currency
        if (fromUnit.equals("USD") || fromUnit.equals("AUD") || fromUnit.equals("EUR")
                || fromUnit.equals("JPY") || fromUnit.equals("GBP")) {

            if (toUnit.equals("USD")) {
                return valueInUSD;
            } else if (toUnit.equals("AUD")) {
                return valueInUSD * 1.55;
            } else if (toUnit.equals("EUR")) {
                return valueInUSD * 0.92;
            } else if (toUnit.equals("JPY")) {
                return valueInUSD * 148.50;
            } else if (toUnit.equals("GBP")) {
                return valueInUSD * 0.78;
            }
        }

        // ------------------------------
        // Fuel efficiency and distance section
        // ------------------------------

        // Miles per Gallon to Kilometers per Liter
        if (fromUnit.equals("Miles per Gallon (mpg)") && toUnit.equals("Kilometers per Liter (km/L)")) {
            return value * 0.425;
        }

        // Kilometers per Liter to Miles per Gallon
        if (fromUnit.equals("Kilometers per Liter (km/L)") && toUnit.equals("Miles per Gallon (mpg)")) {
            return value / 0.425;
        }

        // Gallon to Liters
        if (fromUnit.equals("Gallon (US)") && toUnit.equals("Liters")) {
            return value * 3.785;
        }

        // Liters to Gallon
        if (fromUnit.equals("Liters") && toUnit.equals("Gallon (US)")) {
            return value / 3.785;
        }

        // Nautical Mile to Kilometers
        if (fromUnit.equals("Nautical Mile") && toUnit.equals("Kilometers")) {
            return value * 1.852;
        }

        // Kilometers to Nautical Mile
        if (fromUnit.equals("Kilometers") && toUnit.equals("Nautical Mile")) {
            return value / 1.852;
        }

        // ------------------------------
        // Temperature conversion section
        // ------------------------------

        // Celsius to Fahrenheit
        if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            return (value * 1.8) + 32;
        }

        // Fahrenheit to Celsius
        if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            return (value - 32) / 1.8;
        }

        // Celsius to Kelvin
        if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
            return value + 273.15;
        }

        // Kelvin to Celsius
        if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
            return value - 273.15;
        }

        // Fahrenheit to Kelvin
        if (fromUnit.equals("Fahrenheit") && toUnit.equals("Kelvin")) {
            return ((value - 32) / 1.8) + 273.15;
        }

        // Kelvin to Fahrenheit
        if (fromUnit.equals("Kelvin") && toUnit.equals("Fahrenheit")) {
            return ((value - 273.15) * 1.8) + 32;
        }

        // If the conversion is not a valid pair, return the original value
        return value;
    }
}