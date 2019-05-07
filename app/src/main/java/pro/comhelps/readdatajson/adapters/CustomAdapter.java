package pro.comhelps.readdatajson.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.models.Custom;


import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    private Context context;
    private List list;

    public CustomAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        list = new ArrayList();

    }

    // Добавление объекта
    public void add(Custom object) {
        super.add(object);
        list.add(object);

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }



    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        CustomHolder customHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = layoutInflater.inflate(R.layout.response_layout, parent, false);

            customHolder = new CustomAdapter.CustomHolder();
            customHolder.img = row.findViewById(R.id.img_resp);
            customHolder.name = row.findViewById(R.id.txt_resp_name);
            customHolder.price = row.findViewById(R.id.txt_resp_price);
            customHolder.qty = row.findViewById(R.id.txt_resp_qty);
            customHolder.sum = row.findViewById(R.id.txt_resp_sum);
            customHolder.status = row.findViewById(R.id.txt_resp_status);


            row.setTag(customHolder);
        }else{
            customHolder = (CustomHolder) row.getTag();
        }
        final Custom custom = (Custom) this.getItem(position);
        assert custom != null;

        // Вывод изображения в Анонсе
        ImageLoader.getInstance().displayImage(custom.getTextImage(), customHolder.img);


        customHolder.name.setText(custom.getTextName());
        customHolder.price.setText("Цена " + custom.getTextPrice() + "  \u20BD");
        customHolder.qty.setText("Кол-во " + custom.getTextQty());
        customHolder.sum.setText("Сумма " + custom.getTextSum() + "  \u20BD");

        // Проверка статуса товара
        if(custom.getTextStatus().equals("1")){
            customHolder.status.setText("Оплачено");
        }else{
            customHolder.status.setText("Ожидает оплаты");
        }


        return row;
    }

    static class CustomHolder{
        ImageView img;
        TextView name;
        TextView price;
        TextView qty;
        TextView sum;
        TextView status;
    }
}
