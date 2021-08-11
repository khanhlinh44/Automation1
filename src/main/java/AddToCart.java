import java.util.*;

import static java.util.Arrays.asList;

public class AddToCart {

    public static void main(String[] args) {
        HashMap<String, List<String>> productListAdded = new HashMap<>();

        // a. Add the product = POLARIZED FISHING GLASSES to cart, quantity = 3
        productListAdded.put("POLARIZED FISHING GLASSES", asList("$69.99", " Green", "3"));
        System.out.println("cart 1: " + productListAdded);

        //b. Adjust product quantity
        productListAdded.replace("POLARIZED FISHING GLASSES", asList("$69.99", " Green", "2"));
        System.out.println("cart 2: " + productListAdded);

        //c. Add the product = POLARIZED FISHING GLASSES to cart, quantity = 1
        productListAdded.put("SHIRT", asList("$10.00", "", "1"));
        System.out.println("cart 3: " + productListAdded);

        //d. Final cart
        Set<Map.Entry<String, List<String>>> setHashMap = productListAdded.entrySet();

        for (Map.Entry<String, List<String>> i : setHashMap) {
            System.out.println("final cart: " + i.getKey() + "   -->   " + i.getValue());
        }
    }
}
