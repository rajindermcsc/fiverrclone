package dreamguys.in.co.gigs.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dreamguys.in.co.gigs.R;
import dreamguys.in.co.gigs.utils.AppConstants;


public class AddWalletActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar tbWallet;
    EditText editTextAmount;
    TextView textViewCurrentBalance, action1000, action2000, action3000;
    Button actionAddCash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);
        tbWallet = findViewById(R.id.tb_wallet);
        setSupportActionBar(tbWallet);

        textViewCurrentBalance = findViewById(R.id.text_current_balance);
        editTextAmount = findViewById(R.id.edit_amount);
        action1000 = findViewById(R.id.action_1000);
        action2000 = findViewById(R.id.action_2000);
        action3000 = findViewById(R.id.action_3000);
        actionAddCash = findViewById(R.id.action_add_cash);

        action1000.setOnClickListener(this);
        action2000.setOnClickListener(this);
        action3000.setOnClickListener(this);
        actionAddCash.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_1000:
                editTextAmount.setText("");
                editTextAmount.setText("1,000");
                break;
            case R.id.action_2000:
                editTextAmount.setText("");
                editTextAmount.setText("2,000");
                break;
            case R.id.action_3000:
                editTextAmount.setText("");
                editTextAmount.setText("3,000");
                break;
            case R.id.action_add_cash:
                if (!editTextAmount.getText().toString().isEmpty()) {
                    Intent intentBacktoWalletDashBoard = new Intent(this, CardListActivity.class);
                    intentBacktoWalletDashBoard.putExtra(AppConstants.CASH_AMOUNT, editTextAmount.getText().toString());
                    startActivity(intentBacktoWalletDashBoard);
                } else {
                    Toast.makeText(this, "Please enter the amount to proceed...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
