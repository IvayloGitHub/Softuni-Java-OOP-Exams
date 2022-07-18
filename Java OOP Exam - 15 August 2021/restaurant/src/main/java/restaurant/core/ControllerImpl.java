package restaurant.core;

import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.Fresh;
import restaurant.entities.drinks.Smoothie;
import restaurant.entities.healthyFoods.Salad;
import restaurant.entities.healthyFoods.VeganBiscuits;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.tables.InGarden;
import restaurant.entities.tables.Indoors;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.BeverageRepositoryImpl;
import restaurant.repositories.HealthFoodRepositoryImpl;
import restaurant.repositories.TableRepositoryImpl;
import restaurant.repositories.interfaces.*;

import static restaurant.common.ExceptionMessages.*;
import static restaurant.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private HealthFoodRepository<HealthyFood> healthFoodRepository;
    private BeverageRepository<Beverages> beverageRepository;
    private TableRepository<Table> tableRepository;
    private double totalMoney;

    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository, BeverageRepository<Beverages> beverageRepository, TableRepository<Table> tableRepository) {
        this.healthFoodRepository = new HealthFoodRepositoryImpl<>();
        this.beverageRepository  = new BeverageRepositoryImpl<>();
        this.tableRepository = new TableRepositoryImpl<>();
        this.totalMoney = 0;
    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        HealthyFood healthyFood = null;
        if (type.equals("Salad")) {
            healthyFood = new Salad(name, price);
        } else if (type.equals("VeganBiscuits")) {
            healthyFood = new VeganBiscuits(name, price);
        }

        HealthyFood healthyFoodCheck = this.healthFoodRepository.foodByName(name);
        if (healthyFoodCheck == null) {
            this.healthFoodRepository.add(healthyFood);
        } else {
            throw new IllegalArgumentException(String.format(FOOD_EXIST, name));
        }
        return String.format(FOOD_ADDED, name);
    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name){
        Beverages beverages = null;
        if (type.equals("Fresh")) {
            beverages = new Fresh(name, counter, brand);
        } else if (type.equals("Smoothie")) {
            beverages = new Smoothie(name, counter, brand);
        }

        Beverages beveragesToCheck = this.beverageRepository.beverageByName(name, brand);
        if (beveragesToCheck == null) {
            this.beverageRepository.add(beverages);
        } else {
            throw new IllegalArgumentException(String.format(BEVERAGE_EXIST, name));
        }

        return String.format(BEVERAGE_ADDED, type, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table table= null;
        if (type.equals("Indoors")) {
            table = new Indoors(tableNumber, capacity);
        } else if (type.equals("InGarden")) {
            table = new InGarden(tableNumber, capacity);
        }

        Table tableToCheck = this.tableRepository.byNumber(tableNumber);
        if (tableToCheck == null) {
            this.tableRepository.add(table);
        } else {
            throw new IllegalArgumentException(String.format(TABLE_EXIST, tableNumber));
        }

        return String.format(TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserve(int numberOfPeople) {
        Table table = this.tableRepository.getAllEntities()
                .stream()
                .filter(t -> t.getSize() >= numberOfPeople && !t.isReservedTable())
                .findFirst()
                .orElse(null);
        if (table == null) {
            return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }
        table.reserve(numberOfPeople);
        return String.format(TABLE_RESERVED, table.getTableNumber(), numberOfPeople);
    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {
        Table table = this.tableRepository.byNumber(tableNumber);
        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }

        HealthyFood healthyFood = this.healthFoodRepository.foodByName(healthyFoodName);
        if (healthyFood == null) {
            return String.format(NONE_EXISTENT_FOOD, healthyFoodName);
        }

        table.orderHealthy(healthyFood);
        return String.format(FOOD_ORDER_SUCCESSFUL, healthyFoodName, tableNumber);
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {
        Table table = this.tableRepository.byNumber(tableNumber);

        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }

        Beverages beverages = this.beverageRepository.beverageByName(name, brand);

        if (beverages == null) {
            return String.format(NON_EXISTENT_DRINK, name, brand);
        }

        table.orderBeverages(beverages);
        return String.format(BEVERAGE_ORDER_SUCCESSFUL, name, tableNumber);
    }

    @Override
    public String closedBill(int tableNumber) {
        Table table = this.tableRepository.byNumber(tableNumber);
        double bill = table.bill();
        this.totalMoney += bill;
        table.clear();
        return String.format(BILL, tableNumber, bill);
    }


    @Override
    public String totalMoney() {
        return String.format(TOTAL_MONEY, this.totalMoney);
    }
}
