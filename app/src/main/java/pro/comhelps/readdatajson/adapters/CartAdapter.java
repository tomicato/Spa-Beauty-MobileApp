package pro.comhelps.readdatajson.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.models.Cart2;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {



    private List<Cart2> cart2s = new ArrayList<>();
    private Context mContext;

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_layout, parent, false);
        return new CartHolder(itemView);
    }



    @Override
    public void onBindViewHolder(CartHolder holder, int position) {
        Cart2 currentCart2 = cart2s.get(position);

        ImageView img =  holder.textImage.findViewById(R.id.view_img);
        ImageLoader.getInstance().displayImage(currentCart2.getImg(), img);
        holder.textTitle.setText(currentCart2.getTitle());
        holder.textPrice.setText(currentCart2.getPrice() + "  \u20BD");
        holder.textQty.setText(String.valueOf(currentCart2.getQty()) + "  шт.");
        holder.textSum.setText(String.valueOf(currentCart2.getSum()) + "  \u20BD");

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return cart2s.size();
    }

    public void setCart2s(List<Cart2> cart2s){
        this.cart2s = cart2s;

        notifyDataSetChanged();
    }

    public Cart2 getCart2At(int position){

        return cart2s.get(position);
    }


    class CartHolder extends RecyclerView.ViewHolder{

        private TextView  textTitle, textPrice, textQty;
        private ImageView textImage;
        private TextView textSum;



        public CartHolder(View itemView) {
            super(itemView);


            textImage = itemView.findViewById(R.id.view_img);
            textTitle = itemView.findViewById(R.id.view_title);
            textPrice = itemView.findViewById(R.id.price_val);
            textQty = itemView.findViewById(R.id.view_qty);
            textSum = itemView.findViewById(R.id.sum_val);

        }

        }
    }




