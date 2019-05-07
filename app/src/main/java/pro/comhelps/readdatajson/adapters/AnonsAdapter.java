package pro.comhelps.readdatajson.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.ImageLoader;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.activities.ContentActivity;
import pro.comhelps.readdatajson.db_components.DbHelper;
import pro.comhelps.readdatajson.db_components.Products;
import pro.comhelps.readdatajson.models.Anons;

import java.util.ArrayList;
import java.util.List;

public class AnonsAdapter extends ArrayAdapter {

    private Context context;
    private List list;
    DbHelper dbHelper;

    // Конструктор
    public AnonsAdapter(Context context, int resource) {
        super(context, resource);
        list = new ArrayList();
        this.context = context;
    }

    // Добавление объекта
    public void add(Anons object) {
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
    public View getView(int position, View convertView,  ViewGroup parent) {
        View row;
        row = convertView;
        AnonsHolder anonsHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = layoutInflater.inflate(R.layout.row_layout, parent, false);

            anonsHolder = new AnonsHolder();
            anonsHolder.img = row.findViewById(R.id.imag_view);
            anonsHolder.title = row.findViewById(R.id.text_view_title);
            anonsHolder.price = row.findViewById(R.id.txt_price);
            anonsHolder.stock = row.findViewById(R.id.txt_stock);
            anonsHolder.content = row.findViewById(R.id.txt_content);
            anonsHolder.cart = row.findViewById(R.id.img_cart);


            row.setTag(anonsHolder);
        }else{
            anonsHolder = (AnonsHolder) row.getTag();
        }

        final Anons anons1 = (Anons) this.getItem(position);
        assert anons1 != null;

        // Обработка клика по иконке корзины
        anonsHolder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Something
                // Toast.makeText(context, anons1.getTextAnons(), Toast.LENGTH_SHORT).show();

                dbHelper = new DbHelper(context, null, null, 1);
                Products product = new Products(
                        anons1.getTextTitle(),
                        anons1.getTextAnons(),
                        anons1.getTextPrice(),
                        1,
                        Integer.parseInt(anons1.getTextPrice()),
                        Integer.parseInt(anons1.getTextId()));
                dbHelper.addProduct(product);

                Toast.makeText(context, "Товар в корзине", Toast.LENGTH_LONG).show();
            }
        });

        // Вывод изображения в Анонсе
        ImageLoader.getInstance().displayImage(anons1.getTextTitle(), anonsHolder.img);

        // Вывод названия товара, цен и в наличии на складе
        anonsHolder.title.setText(anons1.getTextAnons());
        anonsHolder.price.setText("ЦЕНА " + anons1.getTextPrice() + "  \u20BD");

        // Проверка присутствует ли товар на складе
        if(anons1.getTextStock().equals("1")){
            anonsHolder.stock.setText("В НАЛИЧИИ ");
        }else{
            anonsHolder.stock.setText("НЕТ В НАЛИЧИИ, ПОД ЗАКАЗ ");
        }

        // Переход на страницу полного описания товара , при клике на отдельный товар
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("prod_id", anons1.getTextId());
                intent.putExtra("contentImg", anons1.getTextTitle());
                intent.putExtra("contentText", anons1.getTextContent());
                intent.putExtra("actionBarTitle", anons1.getTextAnons());
                intent.putExtra("contPrice", anons1.getTextPrice());
                intent.putExtra("contStock", anons1.getTextStock());
                context.startActivity(intent);
            }
        });


        return row;

    }

   static class AnonsHolder{

        ImageView img;
        TextView title;
        TextView price;
        TextView stock;
        TextView content;
        ImageView cart;
    }
}
