package pl.msmet.voucherstore.sales;

public interface Inventory {
    boolean isAvailable(String productId);
}
