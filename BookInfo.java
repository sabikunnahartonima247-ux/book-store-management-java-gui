public class BookInfo {
    private String book1Quantity;
    private String book2Quantity;
    private int totalPrice;

    public BookInfo(String book1Quantity, String book2Quantity) {
        this.book1Quantity = book1Quantity;
        this.book2Quantity = book2Quantity;
    }

    public String getBook1Quantity() {
        return book1Quantity;
    }

    public String getBook2Quantity() {
        return book2Quantity;
    }

    public int getTotalPrice() {
        int b1 = Integer.parseInt(book1Quantity);
        int b2 = Integer.parseInt(book2Quantity);
        totalPrice = (b1 * 200) + (b2 * 350);
        return totalPrice;
	}
}