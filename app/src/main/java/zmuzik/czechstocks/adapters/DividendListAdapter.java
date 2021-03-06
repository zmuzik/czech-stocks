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
import zmuzik.czechstocks.R;
import zmuzik.czechstocks.utils.Utils;
import zmuzik.czechstocks.dao.Dividend;

public class DividendListAdapter extends ArrayAdapter<Dividend> {

    private final String TAG = this.getClass().getSimpleName();

    public DividendListAdapter(Context context, List<Dividend> objects) {
        super(context, R.layout.list_item_dividend, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_dividend, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Dividend dividend = getItem(position);
        if (dividend.getExDate() != null) {
            if (dividend.getExDate() == 0) {
                holder.exDateTV.setText("");
            } else {
                holder.exDateTV.setText(Utils.getFormattedDate(dividend.getExDate()));
            }
        } else {
            holder.exDateTV.setText("");
        }
        if (dividend.getPaymentDate() != null) {
            holder.paymentDateTV.setText(Utils.getFormattedDate(dividend.getPaymentDate()));
        } else {
            holder.paymentDateTV.setText("");
        }
        holder.amountTV.setText(Utils.getFormattedDecimal(dividend.getAmount())+" "+ dividend.getCurrency());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.exDateTV)
        TextView exDateTV;
        @InjectView(R.id.paymentDateTV)
        TextView paymentDateTV;
        @InjectView(R.id.amountTV)
        TextView amountTV;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}