package dreamguys.in.co.gigs.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dreamguys.in.co.gigs.R;
import dreamguys.in.co.gigs.adapter.WalletTransactionHistoryAdapter;

public class TransactionHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerViewTransactionHistory;
    WalletTransactionHistoryAdapter walletTransactionHistoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        recyclerViewTransactionHistory = findViewById(R.id.rcv_transaction_history);
        recyclerViewTransactionHistory.setHasFixedSize(true);
        recyclerViewTransactionHistory.setLayoutManager(new LinearLayoutManager(this));

        walletTransactionHistoryAdapter = new WalletTransactionHistoryAdapter();
        walletTransactionHistoryAdapter.bindData();
        recyclerViewTransactionHistory.setAdapter(walletTransactionHistoryAdapter);

    }
}
