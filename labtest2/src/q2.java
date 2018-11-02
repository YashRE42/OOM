import java.util.*;

class Item {
    private String itemCode;
    private String itemName;
    private Integer rate;
    private Integer quantity;
    private Integer total;

    public Item(String itemCode, String itemName, Integer rate, Integer quantity) {
        setItemCode(itemCode);
        setItemName(itemName);
        setRate(rate);
        setQuantity(quantity);
    }

    public Integer getTotal() {
        return total;
    }

    @Override
    public String toString() {
        String result="";
        result = getItemCode() + " "
                + getItemName() + " "
                + Integer.toString(getRate()) + " "
                + Integer.toString(getQuantity()) + " "
                + Integer.toString(getTotal()) + "\n";
        return result;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

class Items{
    private Vector<Item> Items;
    private Integer grandTotal;

    public Items() {
        Items = new Vector<>();
        this.grandTotal = 0;
    }

    void addItem(Item item){
        Items.add(item);
        this.grandTotal+=item.getTotal();
    }

    @Override
    public String toString() {
        String result ="";
        for(int i=0; i<Items.size();i++){
            result = result + Items.get(i).toString();
        }
        result = result + Integer.toString(this.grandTotal)+ "\n";

        return result;
    }
}

class unit extends Item{
    private String itemType;

    public unit(String itemType, String itemCode, String itemName, Integer rate, Integer quantity) {
        super(itemCode, itemName, rate, quantity);
        this.itemType = itemType;
        Integer total = rate*quantity;
        super.setTotal(total);
    }
}

class discounted extends Item{
    private String itemType;
    private Integer discountPercentage;
    public discounted(String itemType, String itemCode, String itemName, Integer rate, Integer quantity, Integer discountPercentage) {
        super(itemCode, itemName, rate, quantity);
        this.itemType = itemType;
        Integer total = rate*quantity;
        Integer discountedTotal = total*(100-discountPercentage)/100;
        super.setTotal(discountedTotal);
    }
}

class offer extends Item{
    private String itemType;
    private Integer discountPercentage;
    private Integer offerQuantity;
    public offer(String itemType, String itemCode, String itemName, Integer rate, Integer quantity, Integer discountPercentage, Integer offerQuantity) {
        super(itemCode, itemName, rate, quantity);
        this.itemType = itemType;
        Integer currentDiscount = discountPercentage;
        if(quantity<offerQuantity){
            currentDiscount=0;
        }
        Integer total = rate*quantity;
        Integer discountedTotal = total*(100-currentDiscount)/100;
        super.setTotal(discountedTotal);
    }
}

public class q2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Integer T = scan.nextInt();
        for(int i=0; i<T; i++){
            Integer n = scan.nextInt();
            Items Items = new Items();
            for(int j=0; j<n; j++){
                String itemType=scan.next();
                if(itemType.compareTo("unit")==0){
                    unit unit = new unit(itemType,scan.next(), scan.next(),scan.nextInt(),scan.nextInt());
                    Item temp = unit;
                    Items.addItem(temp);
                }
                if(itemType.compareTo("discounted")==0){
                    discounted discounted = new discounted(itemType,scan.next(), scan.next(),scan.nextInt(),scan.nextInt(),scan.nextInt());
                    Item temp = discounted;
                    Items.addItem(temp);
                }
                if(itemType.compareTo("offer")==0){
                    offer offer = new offer(itemType,scan.next(), scan.next(),scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt());
                    Item temp = offer;
                    Items.addItem(temp);
                }
            }
            System.out.print(Items);
        }
    }
}
