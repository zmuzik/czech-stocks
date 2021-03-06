package zmuzik.czechstocks.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zmuzik.czechstocks.App;
import zmuzik.czechstocks.R;
import zmuzik.czechstocks.activities.StockDetailActivity;
import zmuzik.czechstocks.dao.CurrentQuote;
import zmuzik.czechstocks.dao.Stock;
import zmuzik.czechstocks.dao.StockDetail;
import zmuzik.czechstocks.utils.Utils;

public class StockInfoFragment extends Fragment {

    @InjectView(R.id.lastPrice) TextView lastPrice;
    @InjectView(R.id.delta) TextView delta;
    @InjectView(R.id.pe) TextView pe;

    Stock mStock;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof StockDetailActivity) {
            mStock = ((StockDetailActivity) activity).getStock();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_info, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override public void onResume() {
        super.onResume();
        updateBasicInfo();
    }

    @Override public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    private void updateBasicInfo() {
        CurrentQuote currentQuote = mStock.getCurrentQuote();
        Resources res = App.get().getResources();
        lastPrice.setText(Utils.getFormattedCurrencyAmount(currentQuote.getPrice()));

        delta.setText(Utils.getFormattedPercentage(currentQuote.getDelta()));
        delta.setTextColor(res.getColor((currentQuote.getDelta() >= 0) ? R.color.lime : R.color.red));

        for (StockDetail stockDetail : mStock.getStockDetailList()) {
            if ("P/E".equals(stockDetail.getIndicator()) && stockDetail.getValue() != null) {
                if ("O".equals(stockDetail.getValue())) {
                    pe.setText("N/A");
                } else {
                    pe.setText(stockDetail.getValue().replace('.', Utils.getDecimalSeparator()));
                }
                break;
            }
        }
    }
}
