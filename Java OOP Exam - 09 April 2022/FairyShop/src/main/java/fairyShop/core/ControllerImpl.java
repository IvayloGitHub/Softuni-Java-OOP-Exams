package fairyShop.core;

import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;
import fairyShop.repositories.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static fairyShop.common.ConstantMessages.*;
import static fairyShop.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private Repository<Helper> helpers;
    private Repository<Present> presents;

    private Shop shop;


    public ControllerImpl() {
        this.helpers = new HelperRepository<>();
        this.presents = new PresentRepository<>();
        this.shop = new ShopImpl();
    }

    @Override
    public String addHelper(String type, String helperName) {
        Helper helper;
        if (type.equals("Happy")) {
            helper = new Happy(helperName);
        } else if (type.equals("Sleepy")) {
            helper = new Sleepy(helperName);
        } else{
            throw new IllegalArgumentException(HELPER_TYPE_DOESNT_EXIST);
        }
        this.helpers.add(helper);
        return String.format(ADDED_HELPER, type, helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
        Instrument instrument = new InstrumentImpl(power);
        Helper helper = this.helpers.findByName(helperName);
        if (helper == null) {
            throw new IllegalArgumentException(HELPER_DOESNT_EXIST);
        }
        helper.addInstrument(instrument);
        return String.format(SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power, helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName, energyRequired);
        this.presents.add(present);
        return String.format(SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        Present present = this.presents.findByName(presentName);
        Helper helper = this.helpers.getModels()
                .stream()
                .filter(h -> h.getEnergy() > 50)
                .findFirst().orElse(null);
        if (helper == null) {
            throw new IllegalArgumentException(NO_HELPER_READY);
        }

        this.shop.craft(present, helper);
        StringBuilder sb = new StringBuilder();
        long brokenInstruments = helper.getInstruments().stream().filter(Instrument::isBroken).count();
        sb.append(String.format(PRESENT_DONE, presentName, present.isDone() ? "done" : "not done"));
        sb.append(String.format(COUNT_BROKEN_INSTRUMENTS, brokenInstruments));
        return sb.toString();
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        long countOfCraftedPresents = this.presents.getModels().stream().filter(Present::isDone).count();
        sb.append(String.format("%d presents are done!", countOfCraftedPresents));
        sb.append(System.lineSeparator());
        sb.append("Helpers info:").append(System.lineSeparator());
        for (Helper helper: this.helpers.getModels()) {
            sb.append(String.format("Name: %s", helper.getName()));
            sb.append(System.lineSeparator());
            sb.append(String.format("Energy: %d", helper.getEnergy()));
            sb.append(System.lineSeparator());
            long notBrokenInstrumentsCount = helper.getInstruments().stream().filter(i -> !i.isBroken()).count();
            sb.append(String.format("Instruments: %d not broken left", notBrokenInstrumentsCount));
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
