package gifts;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GiftFactoryTests {
     GiftFactory giftFactory;
     Gift gift1;
     Gift gift2;

     @Before
     public void setUp() {
         this.giftFactory = new GiftFactory();
         this.gift1 = new Gift("Nice", 25.0);
         this.gift2 = new Gift("Perfect", 30.0);
     }

     @Test
    public void testGetCount() {
         this.giftFactory.createGift(gift1);
         this.giftFactory.createGift(gift2);
         assertEquals(2, this.giftFactory.getCount());
     }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateGiftThrows() {
        this.giftFactory.createGift(gift1);
        this.giftFactory.createGift(gift2);
        this.giftFactory.createGift(gift1);
    }

    @Test
    public void testCreateGiftWorksFine() {
        this.giftFactory.createGift(gift1);
        assertEquals(1, this.giftFactory.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveGiftThrowsWithNullName() {
        this.giftFactory.createGift(gift1);
        this.giftFactory.removeGift(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveGiftThrowsWithEmptyName() {
        this.giftFactory.createGift(gift1);
        this.giftFactory.removeGift(" ");
    }

    @Test
    public void testRemoveGiftWorksFine() {
        this.giftFactory.createGift(gift1);
        assertTrue(this.giftFactory.removeGift("Nice"));
        assertFalse(this.giftFactory.removeGift("Perfect"));
    }

    @Test
    public void testGetPresentWithLeastMagic() {
        this.giftFactory.createGift(gift1);
        this.giftFactory.createGift(gift2);

        assertEquals(gift1, this.giftFactory.getPresentWithLeastMagic());
    }

    @Test
    public void testGetPresent() {
        this.giftFactory.createGift(gift1);
        this.giftFactory.createGift(gift2);

        assertEquals(gift2, this.giftFactory.getPresent("Perfect"));
    }

    @Test
    public void testGetPresents() {
        this.giftFactory.createGift(gift1);
        this.giftFactory.createGift(gift2);

        List<Gift> collect = new ArrayList<>(giftFactory.getPresents());
        StringBuilder sb = new StringBuilder();
        for (Gift gift : collect) {
            sb.append(gift.getType()).append(gift.getMagic());
        }
        assertEquals("Nice25.0Perfect30.0", sb.toString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testToModifyCollectionThrows() {
        this.giftFactory.createGift(this.gift1);
        this.giftFactory.getPresents().remove(this.gift1);
    }
}
