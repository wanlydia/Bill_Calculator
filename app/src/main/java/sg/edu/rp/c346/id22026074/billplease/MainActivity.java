package sg.edu.rp.c346.id22026074.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText etAmount;
    EditText etPax;
    ToggleButton tbSVS;
    ToggleButton tbGST;
    EditText etDiscount;
    RadioGroup rgPayment;
    Button btnSplit;
    Button btnReset;
    TextView tvTotalBill;
    TextView tvSplit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //general section
        tvTotalBill = findViewById(R.id.tvTotalBill);
        tvSplit = findViewById(R.id.tvSplit);
        etAmount = findViewById(R.id.etAmount);
        etPax = findViewById(R.id.etPax);
        btnSplit = findViewById(R.id.btnSplit);
        rgPayment = findViewById(R.id.rgPayment);
        btnReset = findViewById(R.id.btnReset);

        //calculation section
        tbSVS = findViewById(R.id.tbSVS);
        tbGST = findViewById(R.id.tbGST);
        etDiscount = findViewById(R.id.etDiscount);

        btnSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setting things to float
                float serviceCharge = 0;
                float originalAmount = Float.valueOf(etAmount.getText().toString());
                float splitPax = Float.valueOf(etPax.getText().toString());
                float discount = (Float.valueOf(etDiscount.getText().toString()))/100;

                //applying service charges
                if (tbSVS.isChecked()) {
                    serviceCharge += 0.1;
                }
                if (tbGST.isChecked()) {
                    serviceCharge += 0.07;
                }

                //applying payment method
                String payment;
                int checkedRadioId = rgPayment.getCheckedRadioButtonId();
                if(checkedRadioId == R.id.rbCash) {
                    payment = " in cash";
                } else {
                    payment = " via PayNow to 912345678";
                }

                //calculate the total
                String editedAmount = String.format("%.2f", originalAmount*(1-discount) + (originalAmount*serviceCharge));
                String getTotal = "Total Bill: $";
                tvTotalBill.setText(getTotal + editedAmount);

                //split amongst pax
                float finalAmount = Float.valueOf(editedAmount);
                String splitAmount = String.format("%.2f", (finalAmount / splitPax));
                String getSplit = "Each Pays: $";
                tvSplit.setText(getSplit + splitAmount + payment);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAmount.getText().clear();
                etPax.getText().clear();
                tbSVS.setChecked(false);
                tbGST.setChecked(false);
                etDiscount.getText().clear();
                rgPayment.clearCheck();
                tvTotalBill.setText("Total Bill:");
                tvSplit.setText("Each Pays:");
            }
        });
    }
}