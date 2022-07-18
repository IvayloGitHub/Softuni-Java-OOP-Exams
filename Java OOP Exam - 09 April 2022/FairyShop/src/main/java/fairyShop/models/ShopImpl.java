package fairyShop.models;

import java.util.Collection;

public class ShopImpl implements Shop {
    @Override
    public void craft(Present present, Helper helper) {
        for (Instrument instrument: helper.getInstruments()) {
            while(!instrument.isBroken() && !present.isDone()) {
                helper.work();
                instrument.use();
                present.getCrafted();
            }
            if (present.isDone()) {
                break;
            }
        }
    }
}
