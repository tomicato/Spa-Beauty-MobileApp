package pro.comhelps.readdatajson.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shop_cart")

public class Cart2 {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cart_img")
    private String img;

    @ColumnInfo(name = "cart_title")
    private String title;

    @ColumnInfo(name = "cart_price")
    private String price;

    @ColumnInfo(name = "cart_qty")
    private int qty;

    @ColumnInfo(name = "cart_sum")
    private int sum;

    public Cart2(String img, String title, String price, int qty, int sum) {
        this.img = img;
        this.title = title;
        this.price = price;
        this.qty = qty;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
