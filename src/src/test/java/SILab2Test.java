
import org.example.Item;
import org.example.SILab2;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SILab2Test {

    @Test
    void testAllItemsNull() {
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                SILab2.checkCart(null, "1234567812345678"));
        assertEquals("allItems list can't be null!", ex.getMessage());
    }

    @Test
    public void testValidItemNoDiscountValidCard() {
        Item item = new Item("Book", 1, 100, 0.0f);
        int result = (int) SILab2.checkCart(List.of(item), "1234567812345678");
        assertEquals(100, result);
    }

    @Test
    void testItemWithDiscountInvalidCard() {
        Item item = new Item("Shirt", 1, 400, 0.1f);
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                SILab2.checkCart(List.of(item), "12345678abcd5678"));
        assertEquals("Invalid character in card number!", ex.getMessage());
    }

    @Test
    void testInvalidItemName() {
        Item item = new Item(null, 1, 100, 0.0f);
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                SILab2.checkCart(List.of(item), "1234567812345678"));
        assertEquals("Invalid item!", ex.getMessage());
    }

    // Multiple condition tests (A || B || C)

    @Test
    void testABC_FFF() {
        Item item = new Item("A", 1, 100, 0.0f);
        int result = (int) SILab2.checkCart(List.of(item), "1234567812345678");
        assertEquals(100, result); // no -30
    }

    @Test
    void testABC_TFF() {
        Item item = new Item("B", 1, 350, 0.0f);
        int result = (int) SILab2.checkCart(List.of(item), "1234567812345678");
        assertEquals(320, result); // 350 - 30
    }

    @Test
    void testABC_FTF() {
        Item item = new Item("C", 1, 100, 0.2f);
        int result = (int) SILab2.checkCart(List.of(item), "1234567812345678");
        assertEquals(50, result); // (100 - 20) = 80 - 30 = 50
    }

    @Test
    void testABC_FFT() {
        Item item = new Item("D", 15, 100, 0.0f);
        int result = (int) SILab2.checkCart(List.of(item), "1234567812345678");
        assertEquals(1470, result); // (100 * 15) = 1500 - 30 = 1470
    }

    @Test
    void testABC_TTF() {
        Item item = new Item("E", 1, 350, 0.2f);
        int result = (int) SILab2.checkCart(List.of(item), "1234567812345678");
        assertEquals(250, result); // 350 - 70 = 280 - 30 = 250
    }

    @Test
    void testABC_TFT() {
        Item item = new Item("F", 20, 350, 0.0f);
        int result = (int) SILab2.checkCart(List.of(item), "1234567812345678");
        assertEquals(6920, result); // (350 * 20) = 7000 - 30 = 6970
    }

    @Test
    void testABC_FTT() {
        Item item = new Item("G", 20, 100, 0.2f);
        int result = (int) SILab2.checkCart(List.of(item), "1234567812345678");
        assertEquals(1370, result); // (100 * 20) = 2000 - 400 = 1600 - 30 = 1570
    }

    @Test
    void testABC_TTT() {
        Item item = new Item("H", 20, 350, 0.2f);
        int result = (int) SILab2.checkCart(List.of(item), "1234567812345678");
        assertEquals(5320, result); // (350*20)=7000 - 1400=5600 - 30=5570
    }
}
