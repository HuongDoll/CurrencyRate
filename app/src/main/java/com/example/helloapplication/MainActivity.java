package com.example.helloapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MAX_VALUE_INPUT = 11;
    // the exchange rate between currency
    Double listCurrency [];
    double doubleCurrency1;

    //
    TextView textCurrency1;
    TextView textCurrency2;

    //
    List<String> listItems;
    String op1;
    double result;
    int op;
    int maxValue;
    int numOfDot;
    Spinner itemsSpinner1,itemsSpinner2;
    ArrayAdapter<String> listsAdapter;
    String item[] = {"Viet Nam","Japan","Korea","China","United States"};
    int indexSelectedCountry1 ;
    int indexSelectedCountry2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCurrency1 = findViewById(R.id.currency1);
        textCurrency2 = findViewById(R.id.currency2);

        findViewById(R.id.btn_0).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);

        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_ce).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);

        op1="";
        maxValue = 0;
        numOfDot = 0;
        indexSelectedCountry1 = 0;
        indexSelectedCountry2 = 0;
        result = 0.0;

        listItems = new ArrayList<>();
        listItems.addAll(Arrays.asList("Viet Nam","Japan","Korea","China","United States"));
        listsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,listItems);
        //Initial spinner 1
        itemsSpinner1 = findViewById(R.id.spinner1);
        itemsSpinner1.setAdapter(listsAdapter);
        itemsSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                       indexSelectedCountry1 = position;
                                                       updateRate();
                                                   }

                                                   @Override
                                                   public void onNothingSelected(AdapterView<?> parent) {

                                                   }
                                               }
        );
        //Initial spinner 2
        itemsSpinner2 = findViewById(R.id.spinner2);
        itemsSpinner2.setAdapter(listsAdapter);
        itemsSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        indexSelectedCountry2 = position;
                                                        updateRate();
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                }
        );

        //currency rate: VietNam(0),Japan(1),Korea(2),China(3),UnitedStates(4)
        listCurrency = new Double[50];
        for (int i = 0 ; i < 50; i ++)
            listCurrency[i] = 1.0;
        listCurrency[10] = 218.2495;
        listCurrency[20] = 19.2208;
        listCurrency[30] = 3318.6561;
        listCurrency[40] = 23440.0;
        listCurrency[21] = 0.08807;
        listCurrency[31] = 15.2058;
        listCurrency[41] = 107.40;
        listCurrency[32] = 172.6593;
        listCurrency[42] = 1219.51;
        listCurrency[43] = 7.0631;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btn_1)
            addDigit(1);
        else if(id == R.id.btn_2)
            addDigit(2);
        else if(id == R.id.btn_3)
            addDigit(3);
        else if(id == R.id.btn_4)
            addDigit(4);
        else if(id == R.id.btn_5)
            addDigit(5);
        else if(id == R.id.btn_6)
            addDigit(6);
        else if(id == R.id.btn_7)
            addDigit(7);
        else if(id == R.id.btn_8)
            addDigit(8);
        else if(id == R.id.btn_9)
            addDigit(9);
        else if(id == R.id.btn_0)
            addDigit(0);
        else if(id == R.id.btn_dot)
            addDigit(10);
        else if (id == R.id.btn_ce)
            resetState();
        else if (id == R.id.btn_clear)
            clear();

    }
    public void clear()
    {
        if(!op1.isEmpty()) {
            if(op1.charAt(op1.length() -1) == '.')
                numOfDot = 0;
            op1 = removeCharAt(op1, op1.length() - 1);
            maxValue --;
        }
        if(op1.length() == 0)
        {
            op1 ="";
            maxValue = 0;
            textCurrency1.setText("0");
            textCurrency2.setText("0.0");
        }
        updateRate();

    }
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }
    public void resetState()
    {
        op1 = "";
        maxValue = 0;
        numOfDot = 0;
        textCurrency1.setText("0");
        textCurrency2.setText("0.0");
    }
    public void addDigit(int digit)
    {

        if(maxValue < MAX_VALUE_INPUT) {
            if(digit < 10) {
                op1 = op1.concat(String.valueOf(digit));
                maxValue++;
            }
            else
            {
                if(numOfDot == 0) {
                    op1 = op1.concat(".");
                    maxValue++;
                    numOfDot++;
                }
            }
            textCurrency1.setText(op1);
            updateRate();
        }
    }
    public double getRate()
    {
        int indexInList;
        if(indexSelectedCountry1 == indexSelectedCountry2)
            return 1;
        else if(indexSelectedCountry1 > indexSelectedCountry2) {
            indexInList = indexSelectedCountry1 * 10 + indexSelectedCountry2;
            return listCurrency[indexInList];
        }
        else {
            indexInList = indexSelectedCountry2 * 10 + indexSelectedCountry1;
            return 1 / listCurrency[indexInList];
        }
    }
    public void updateRate()
    {
        if(!op1.isEmpty()) {
            doubleCurrency1 = Double.valueOf(op1);
            result = doubleCurrency1 * getRate();
            textCurrency1.setText(op1);
            textCurrency2.setText(String.valueOf(result));
        }
    }
}
