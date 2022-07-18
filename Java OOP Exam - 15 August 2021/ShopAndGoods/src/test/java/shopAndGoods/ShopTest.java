package shopAndGoods;


import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import java.util.Map;

import static org.junit.Assert.*;

public class ShopTest {
    private Shop shop;

    @Before
    public void setUp() {
        shop = new Shop();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddGoodsThrows() throws OperationNotSupportedException {
        shop.addGoods("Shelves13", new Goods("T-shirts", "1234"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddGoodsThrowsShelfIsOccupied() throws OperationNotSupportedException {
        shop.addGoods("Shelves11", new Goods("T-shirts", "1234"));
        shop.addGoods("Shelves11", new Goods("Jackets", "1523"));
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testAddGoodsItIsAlreadyInShelf() throws OperationNotSupportedException {
        Goods goods = new Goods("T-shirt", "1234");
        shop.addGoods("Shelves11", goods);
        shop.addGoods("Shelves10", goods);
    }

    @Test
    public void testAddGoodsReturnsStr() throws OperationNotSupportedException {
        Goods goods = new Goods("T-shirt", "1234");
        assertEquals("Goods: 1234 is placed successfully!", shop.addGoods("Shelves10", goods));
    }

    @Test
    public void testAddGoodsWorksFine() throws OperationNotSupportedException {
        Goods goods = new Goods("T-shirt", "1234");
        shop.addGoods("Shelves10", goods);
        assertEquals(goods, shop.getShelves().get("Shelves10"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveGoodsThrows() throws OperationNotSupportedException {
        shop.removeGoods("Shelves13", new Goods("T-shirts", "1234"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveGoodsNotExst() throws OperationNotSupportedException {
        Goods goods = new Goods("T-shirts", "1234");
        Goods goods1 = new Goods("Jacket", "567");
        shop.addGoods("Shelves11", goods);
        shop.removeGoods("Shelves11", goods1);
    }

    @Test
    public void testRemoveGoodsReturnsStr() throws OperationNotSupportedException {
        Goods goods = new Goods("T-shirt", "1234");
        shop.addGoods("Shelves10", goods);
        assertEquals("Goods: 1234 is removed successfully!", shop.removeGoods("Shelves10", goods));
    }

    @Test
    public void testRemoveGoodsWorksFine() throws OperationNotSupportedException {
        Goods goods = new Goods("T-shirt", "1234");
        shop.addGoods("Shelves10", goods);
        shop.removeGoods("Shelves10", goods);
        assertNull(shop.getShelves().get("Shelves10"));
    }

}