package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tipcalculator.databinding.ActivityMainBinding;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    private String billAMountString = "";
    private float tipPercent = .15f;

    private EditText billAmountEditTxt;
    private TextView percentageTxt;
    private Button minusBtn;
    private Button plusBtn;
    private TextView tipTxt;
    private TextView totalTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get references to the UI controls
        billAmountEditTxt = binding.billAmounteditText;
        percentageTxt = binding.prcentageTxt;
        minusBtn = binding.minusBtn;
        plusBtn = binding.plusBtn;
        tipTxt = binding.tipTxt;
        totalTxt = binding.totalTxt;

        //set the listeners
        billAmountEditTxt.setOnEditorActionListener(this);
        minusBtn.setOnClickListener(this);
        plusBtn.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("billAmountString", billAMountString);
        outState.putFloat("tipPercent", tipPercent);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState !=null){
            billAMountString = savedInstanceState.getString("billAmountString", "");
            tipPercent = savedInstanceState.getFloat("tipPercent", 0.15f);

            billAmountEditTxt.setText(billAMountString);
            calculateDisplay();
        }
    }

    public void calculateDisplay() {
        billAMountString = billAmountEditTxt.getText().toString();
        float billAmount;

        if (billAMountString.equals("")) {
            billAmount = 0;
        } else {
            billAmount = Float.parseFloat(billAMountString);
        }

        //calculate the tip and total
        float tipAmount = billAmount * tipPercent;
        float total = billAmount + tipAmount;

        //display the result
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTxt.setText(currency.format(tipAmount));
        totalTxt.setText(currency.format(total));

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentageTxt.setText(percent.format(tipPercent));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.minusBtn) {
            tipPercent = tipPercent - 0.01f;
            calculateDisplay();
        }
        if (view.getId() == R.id.plusBtn) {
            tipPercent = tipPercent + 0.01f;
            calculateDisplay();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        calculateDisplay();
        return false;
    }
}