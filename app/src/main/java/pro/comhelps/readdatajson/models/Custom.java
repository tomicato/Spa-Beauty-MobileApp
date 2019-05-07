package pro.comhelps.readdatajson.models;

public class Custom {


    private String textImage;
    private String textName;
    private String textPrice;
    private String textQty;
    private String textSum;
    private String textStatus;


    public Custom(String textImg, String textName, String textPrice, String textQty, String textSum, String textStatus) {

        this.textImage = textImg;
        this.textName = textName;
        this.textPrice = textPrice;
        this.textQty = textQty;
        this.textSum = textSum;
        this.textStatus = textStatus;
    }

    public String getTextImage() {
        return textImage;
    }

    public void setTextImage(String textImage) {
        this.textImage = textImage;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getTextPrice() {
        return textPrice;
    }

    public void setTextPrice(String textPrice) {
        this.textPrice = textPrice;
    }

    public String getTextQty() {
        return textQty;
    }

    public void setTextQty(String textQty) {
        this.textQty = textQty;
    }

    public String getTextSum() {
        return textSum;
    }

    public void setTextSum(String textSum) {
        this.textSum = textSum;
    }

    public String getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }
}
