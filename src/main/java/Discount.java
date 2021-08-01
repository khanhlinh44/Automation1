import java.util.Scanner;

public class Discount {

    // Hàm nhập discount - tương tự với hành động buyer nhập discount trên trang checkout
    public static String enterDiscountCode() {
        Scanner ip = new Scanner(System.in);
        System.out.println("Enter discount code: ");
        return ip.nextLine();
    }

    /**
     * Làm tròn 2 chữ số sau dấy phẩy
     * Hành động: discount value mình get được ở dashboard thường là kiểu String và có thể chưa có 2 chữ số sau dấu phẩy. VD: Discount value = 10
     * Mục đích: Làm tròn 2 chữ số sau dấy phẩy để compare với discount value trên trang checkout
     */
    public static String roundOffTo2DecPlaces(String val) {
        return String.format("%.2f", Float.parseFloat(val));
    }

    public static void main(String[] args) {
        String discountCodeDB = "testing";
        String discountValueDB = "5";
        String discountCodeSF = "";
        String discountValueSF = "-$5.00";
        boolean isMatching = true;

        // 1. verify discount code
        discountCodeSF = enterDiscountCode();
        isMatching = discountCodeSF.equalsIgnoreCase(discountCodeDB);

        if (isMatching) {
            System.out.println("discount code on checkout page is matching with discount on dashboard");

            // 2. Nếu discount matching thì verify tiếp discount value
            discountValueDB = "-$" + roundOffTo2DecPlaces(discountValueDB);
            if (discountValueDB.equals(discountValueSF)) {
                System.out.println("testcase passed");
            }
        } else {
            System.out.println("discount code on checkout page is not matching with discount on dashboard");
            System.out.println("testcase failed");
        }
    }
}