package pro.comhelps.readdatajson.db_components;

import android.widget.EditText;

public class Products {

    int id;
    String img;
    String title;
    String price;
    int qty;
    int sum;
    int pid;

    public Products(String img, String title, String price, int qty, int sum, int pid) {
        this.img = img;
        this.title = title;
        this.price = price;
        this.qty = qty;
        this.sum = sum;
        this.pid = pid;
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


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
