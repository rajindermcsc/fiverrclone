package dreamguys.in.co.gigs.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import dreamguys.in.co.gigs.R;
import dreamguys.in.co.gigs.adapter.WalletTransactionHistoryAdapter;
import dreamguys.in.co.gigs.utils.AppConstants;

public class WalletDashBoard extends AppCompatActivity implements View.OnClickListener {

    TextView textWalletAmount;
    ImageButton actionAddWallet;
    TextView labelTransactionHistory;
    TextView actionViewAll;
    RecyclerView rcvTransactionHistory;

    public static final int RESULT_BACK_ADD_WALLET = 1000;

    WalletTransactionHistoryAdapter walletTransactionHistoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_dashboard);

        rcvTransactionHistory = findViewById(R.id.rcv_transaction_history);
        actionAddWallet = findViewById(R.id.action_add_wallet);
        actionViewAll = findViewById(R.id.action_view_all);
        textWalletAmount = findViewById(R.id.text_wallet_amount);

        actionAddWallet.setOnClickListener(this);
        actionViewAll.setOnClickListener(this);

        rcvTransactionHistory.setLayoutManager(new LinearLayoutManager(this));
        rcvTransactionHistory.setHasFixedSize(true);
        walletTransactionHistoryAdapter = new WalletTransactionHistoryAdapter();
        walletTransactionHistoryAdapter.bindData();
        rcvTransactionHistory.setAdapter(walletTransactionHistoryAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_add_wallet:
                Intent intentAddWallet = new Intent(this, AddWalletActivity.class);
//                startActivityForResult(intentAddWallet, RESULT_BACK_ADD_WALLET);
                startActivity(intentAddWallet);
                break;
            case R.id.action_view_all:
                Intent intentTransactionHistory = new Intent(this, TransactionHistoryActivity.class);
                startActivity(intentTransactionHistory);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_BACK_ADD_WALLET && resultCode == RESULT_OK) {
            String cashAmount = data.getStringExtra(AppConstants.CASH_AMOUNT);
            Toast.makeText(this, cashAmount, Toast.LENGTH_SHORT).show();
        }
    }

}
