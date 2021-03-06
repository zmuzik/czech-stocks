package zmuzik.czechstocks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zmuzik.czechstocks.App;
import zmuzik.czechstocks.R;
import zmuzik.czechstocks.utils.Utils;
import zmuzik.czechstocks.dao.CurrentQuote;
import zmuzik.czechstocks.dao.Stock;

public class QuoteListAdapter extends ArrayAdapter<Stock> {

    private final String TAG = this.getClass().getSimpleName();

    public QuoteListAdapter(Context context, List<Stock> objects) {
        super(context, R.layout.list_item_quote_list, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_quote_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Stock stock = getItem(position);
        holder.stockNameTV.setText(stock.getName());
        CurrentQuote currentQuote = stock.getCurrentQuote();
        if (currentQuote != null) {
            double delta =  currentQuote.getDelta();
            holder.stockDeltaTV.setText(Utils.getFormattedPercentage(delta));
            if (delta < 0) {
                holder.stockDeltaTV.setTextColor(App.get().getResources().getColor(R.color.red));
            } else {
                holder.stockDeltaTV.setTextColor(App.get().getResources().getColor(R.color.lime));
            }
            holder.stockPriceTV.setText(Utils.getFormattedDecimal(currentQuote.getPrice()));
        } else {
            holder.stockDeltaTV.setText("");
            holder.stockPriceTV.setText("");
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.stockNameTV)   TextView stockNameTV;
        @InjectView(R.id.stockDeltaTV)  TextView stockDeltaTV;
        @InjectView(R.id.stockPriceTV)  TextView stockPriceTV;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}