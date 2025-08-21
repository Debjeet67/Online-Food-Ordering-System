// CartItem.java
public class CartItem {
    private FoodItem item;
    private int quantity;

    public CartItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public FoodItem getItem() { return item; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int qty) { this.quantity = qty; }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return item.getName() + " x" + quantity + " = ₹" + String.format("%.2f", getSubtotal());
    }
}

