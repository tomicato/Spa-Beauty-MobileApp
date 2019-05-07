package pro.comhelps.readdatajson.models;


public class Anons {

    private String textId;
    private String textTitle;
    private String textAnons;
    private String textPrice;
    private String textStock;
    private String textContent;



    public Anons(String textId, String textTitle, String textAnons, String textPrice, String textStock, String textContent){
        this.setTextId(textId);
        this.setTextTitle(textTitle);
        this.setTextAnons(textAnons);
        this.setTextPrice(textPrice);
        this.setTextStock(textStock);
        this.setTextContent(textContent);
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextAnons() {
        return textAnons;
    }

    public void setTextAnons(String textAnons) {
        this.textAnons = textAnons;
    }

    public String getTextPrice() {
        return textPrice;
    }

    public void setTextPrice(String textPrice) {
        this.textPrice = textPrice;
    }

    public String getTextStock() {
        return textStock;
    }

    public void setTextStock(String textStock) {
        this.textStock = textStock;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
