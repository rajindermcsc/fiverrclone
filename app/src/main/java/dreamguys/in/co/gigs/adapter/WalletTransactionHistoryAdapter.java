package dreamguys.in.co.gigs.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dreamguys.in.co.gigs.R;

public class WalletTransactionHistoryAdapter extends RecyclerView.Adapter<WalletTransactionHistoryAdapter.ViewHolder> {
    @NonNull
    @Override
    public WalletTransactionHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_wallet_transaction_history, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletTransactionHistoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void bindData() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
