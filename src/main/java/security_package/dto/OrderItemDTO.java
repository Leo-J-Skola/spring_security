package security_package.dto;

public class OrderItemDTO {
    // What product
    private String productId;

    // quantity bought
    private int quantity;

    public OrderItemDTO() {
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
