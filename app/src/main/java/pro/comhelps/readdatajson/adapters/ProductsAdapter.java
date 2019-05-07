package pro.comhelps.readdatajson.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.L;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.db_components.DbHelper;

public class ProductsAdapter extends Adapter<ProductsAdapter.ProductsViewHolder> {

    private Context pContext;
    private Cursor pCursor;

    public ProductsAdapter(Context pContext, Cursor pCursor) {
        this.pContext = pContext;
        this.pCursor = pCursor;
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        public ImageView prodImage;
        public TextView prodTitle, prodPrice, prodQty, prodSum;


        public ProductsViewHolder(View itemView) {
            super(itemView);

            prodImage = itemView.findViewById(R.id.image_prod);
            prodTitle = itemView.findViewById(R.id.prod_title);
            prodPrice = itemView.findViewById(R.id.prod_val);
            prodQty = itemView.findViewById(R.id.prod_qty);
            prodSum = itemView.findViewById(R.id.prod_sum);

        }
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(pContext);
        View view = layoutInflater.inflate(R.layout.products_item, parent, false);

        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        if (!pCursor.moveToNext()) {
            return;
        }


        String img = pCursor.getString(pCursor.getColumnIndex(DbHelper.COLUMN_IMG));
        String title = pCursor.getString(pCursor.getColumnIndex(DbHelper.COLUMN_TITLE));
        String price = pCursor.getString(pCursor.getColumnIndex(DbHelper.COLUMN_PRICE));
        int qty = pCursor.getInt(pCursor.getColumnIndex(DbHelper.COLUMN_QTY));
        int sum = pCursor.getInt(pCursor.getColumnIndex(DbHelper.COLUMN_SUM));
        int id = pCursor.getInt(pCursor.getColumnIndex(DbHelper.COLUMN_ID));

        // Вывод изображения в Анонсе
        ImageLoader.getInstance().displayImage(img, holder.prodImage);

        holder.itemView.setTag(id);
        holder.prodTitle.setText(title);
        holder.prodPrice.setText(price + "  \u20BD");
        holder.prodQty.setText(String.valueOf(qty) + " шт.");
        holder.prodSum.setText(String.valueOf(sum + "  \u20BD"));


    }

    @Override
    public int getItemCount() {
        return pCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (pCursor != null) {
            pCursor.close();
        }
        pCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
