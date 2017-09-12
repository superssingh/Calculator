package com.santoshkumarsingh.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.lang.Double.parseDouble;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView)
    TextView textView;

    private double value = 0;
    private String operator = "";
    private boolean operatorPressed = false;
    private DecimalFormat decimalFormat = new DecimalFormat("#.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView.setText("");

    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9})
    protected void onNumberClicked(Button button) {
        if (!operatorPressed) {
            textView.setText(textView.getText().toString() + button.getText().toString());
        } else {
            textView.setText(button.getText().toString());
            operatorPressed = false;
        }
    }

    @OnClick({R.id.btn_add, R.id.btn_subs, R.id.btn_multi, R.id.btn_devide, R.id.btn_dec, R.id.btn_equal})
    protected void onOperatorClicked(Button button) {
        if (button.getText().toString().equals(".")) {
            if (!operatorPressed) {
                textView.setText((textView.getText().toString().contains(".")
                        ? textView.getText().toString()
                        : textView.getText().toString() + "."));
            } else {
                textView.setText("0.");
                operatorPressed = false;
            }
        } else {
            Calculate(button.getText().toString());
        }
    }

    private void Calculate(String sign) {
        if (textView.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.EMPTY_TEXT), Toast.LENGTH_LONG).show();
        } else {
            if (!sign.equals("=")) {
                if (!operatorPressed) {
                    value = (operator.equals("") ? parseDouble(textView.getText().toString()) : result(value, parseDouble(textView.getText().toString()), operator));
                    operator = sign;
                } else {
                    operator = sign;
                }
            } else {
                value = (operator.equals("=") ? parseDouble(textView.getText().toString()) : result(value, parseDouble(textView.getText().toString()), operator));
            }
            textView.setText(decimalFormat.format(value));
            operatorPressed = true;
        }
    }

    private double result(double value1, double value2, String sign) {
        double result = 0.0;
        switch (sign) {
            case "+":
                result = value1 + value2;
                break;
            case "-":
                result = value1 - value2;
                break;
            case "*":
                result = value1 * value2;
                break;
            case "/":
                result = value1 / value2;
                break;
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.Clear) {
            textView.setText("");
            value = 0;
            operator = "";
            operatorPressed = false;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
