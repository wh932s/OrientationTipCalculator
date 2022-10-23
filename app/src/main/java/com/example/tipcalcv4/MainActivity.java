package com.example.tipcalcv4;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Editable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextWatcher;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private static final String MA = "MainActivity";
    private TipCalculator tipcalc;
    public NumberFormat money = NumberFormat.getCurrencyInstance();
    private EditText billEditText;
    private EditText tipEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration config = getResources().getConfiguration();
        modifyLayout(config);


        tipcalc = new TipCalculator(0.17f, 100.0f);



        billEditText = (EditText) findViewById(R.id.amount_bill);
        tipEditText = (EditText) findViewById(R.id.amount_tip_percent);


        TextChangeHandler tch = new TextChangeHandler();
        billEditText.addTextChangedListener(tch);
        tipEditText.addTextChangedListener(tch);



    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.w(MA, "Inside onConfigurationChanged");
        super.onConfigurationChanged(newConfig);

        modifyLayout(newConfig);

    }

    private void modifyLayout(Configuration newConfig) {

        Log.w("MainActivity", "Inside modifyLayout");

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_main_landscape);
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main);

    }


    public void calculate() {
        EditText billEditText = (EditText) findViewById(R.id.amount_bill);
        EditText tipEditText = (EditText) findViewById(R.id.amount_tip_percent);
        String billString = billEditText.getText().toString();
        String tipString = tipEditText.getText().toString();

        TextView tipTextView =
                (TextView) findViewById(R.id.amount_tip);
        TextView totalTextView =
                (TextView) findViewById(R.id.amount_total);


        try {
            float billAmount = Float.parseFloat(billString);
            int tipPercent = Integer.parseInt(tipString);

            tipcalc.setBill(billAmount);
            tipcalc.setTip(.01f * tipPercent);

            float tip = tipcalc.tipAmount();
            float total = tipcalc.totalAmount();

            tipTextView.setText(money.format(tip));
            totalTextView.setText(money.format(total));


        } catch (NumberFormatException nfe) {
            // pop up alert view goes here
        }
    }



    private class TextChangeHandler implements TextWatcher {
        public void afterTextChanged(Editable e) {
            calculate();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int after) {

        }


    }
}

