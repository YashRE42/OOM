import java.util.*;

class Item {
    private String itemCode;
    private String itemName;
    private Integer rate;
    private Integer quantity;
    private Integer total;

    public Item(String itemCode, String itemName, Integer rate, Integer quantity) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.rate = rate;
        this.quantity = quantity;
        this.total = quantity*rate;
    }

    public Integer getTotal() {
        return total;
    }

    @Override
    public String toString() {
        String result="";
        result = itemCode + " " 
                + itemName + " " 
                + Integer.toString(rate) + " " 
                + Integer.toString(quantity) + " " 
                + Integer.toString(total) + "\n";
        return result;
    }
}

class Items{
    private Vector<Item> Items;
    private Integer grandTotal;

    public Items() {
        Items = new Vector<>();
        this.grandTotal = 0;
    }

    void addItem(String itemCode, String itemName, Integer rate, Integer quantity){
        Item unit = new Item(itemCode, itemName, rate, quantity);
        Items.add(unit);
        this.grandTotal+=unit.getTotal();
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

public class q1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Integer T = scan.nextInt();
        for(int i=0; i<T; i++){
            Integer n = scan.nextInt();
            Items Items = new Items();
            for(int j=0; j<n; j++){
                Items.addItem(scan.next(), scan.next(),scan.nextInt(),scan.nextInt());
            }
            System.out.print(Items);
        }
    }
}
