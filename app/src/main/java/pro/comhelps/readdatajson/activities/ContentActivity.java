package pro.comhelps.readdatajson.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.db_components.DbHelper;
import pro.comhelps.readdatajson.db_components.Products;
import pro.comhelps.readdatajson.models.Cart2;


public class ContentActivity extends AppCompatActivity {


    private ImageView img_cont, addCart;;
    private TextView txtContName, txtContPrice, txtContStock, textContent;
    private EditText editQty;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        textContent = findViewById(R.id.txt_content);
        img_cont = findViewById(R.id.img_content);
        txtContName = findViewById(R.id.txt_cont_title);
        txtContPrice = findViewById(R.id.txt_cont_price);
        txtContStock = findViewById(R.id.txt_cont_stock);
        addCart = findViewById(R.id.add_to_cart);
        editQty = findViewById(R.id.ed_qty);

        Intent intent = getIntent();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        String contentText = intent.getStringExtra("contentText");
        String textTitleBar = intent.getStringExtra("actionBarTitle");
        String imgContent = intent.getStringExtra("contentImg");
        String contPrice = intent.getStringExtra("contPrice");
        String contStock = intent.getStringExtra("contStock");

        // Вывод изображения в Анонсе
        ImageLoader.getInstance().displayImage(imgContent, img_cont);
        // Текст - описание
        textContent.setText(contentText);
        // Заголовок окна
        actionBar.setTitle(textTitleBar);
        txtContName.setText(textTitleBar);
        txtContPrice.setText("ЦЕНА " + contPrice + "  \u20BD");


        // Проверка присутствует ли товар на складе
        if (contStock.equals("1")) {
        txtContStock.setText("В НАЛИЧИИ ");
             } else {
                txtContStock.setText("НЕТ В НАЛИЧИИ, ПОД ЗАКАЗ ");
            }

        // Tabs Host
        setTitle("TabHost");
        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Описание");
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Отзывы");
        tabHost.addTab(tabSpec);
        tabHost.setCurrentTab(0);


       // final Cart2ViewModel cart2ViewModel = ViewModelProviders.of(this).get(Cart2ViewModel.class);
        dbHelper = new DbHelper(ContentActivity.this, null, null, 1);

        addCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Products product = new Products(
                        getIntent().getStringExtra("contentImg"),
                        getIntent().getStringExtra("actionBarTitle"),
                        getIntent().getStringExtra("contPrice"),
                        Integer.parseInt(editQty.getText().toString().trim()),
                        Integer.parseInt(getIntent().getStringExtra("contPrice")) * Integer.parseInt(editQty.getText().toString().trim()),
                        Integer.parseInt(getIntent().getStringExtra("prod_id")));
                dbHelper.addProduct(product);

                Toast.makeText(getApplicationContext(), "Товар в корзине", Toast.LENGTH_LONG).show();
                /*
                String pr_img = getIntent().getStringExtra("contentImg");
                String pr_title = getIntent().getStringExtra("actionBarTitle");
                String pr_price = getIntent().getStringExtra("contPrice");
                int pr_qty = Integer.parseInt(editText.getText().toString().trim());
                int pr_sum = Integer.parseInt(pr_price) * pr_qty;

                Cart2 cart2 = new Cart2(pr_img, pr_title, pr_price, pr_qty, pr_sum);
                cart2ViewModel.insert(cart2);
                Toast.makeText(getApplicationContext(), "Товар " + pr_title + " добавлен в корзину ",
                        Toast.LENGTH_SHORT).show();
                */

            }
        });
    }
}
