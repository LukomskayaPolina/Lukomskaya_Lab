import java.util.ArrayList;
import java.util.List;

class Airplane {
    private String name;
    private String model;
    private int capacity; // пассажировместимость
    private int cargoCapacity; // грузоподъемность

    public Airplane(String name, String model, int capacity, int cargoCapacity) {
        this.name = name;
        this.model = model;
        this.capacity = capacity;
        this.cargoCapacity = cargoCapacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    @Override
    public String toString() {
        return name + " (" + model + "): " +
                "Passengers=" + capacity + ", Cargo=" + cargoCapacity + " kg";
    }
}

// Класс для пассажирских самолетов
class PassengerAirplane extends Airplane {
    public PassengerAirplane(String name, String model, int capacity, int cargoCapacity) {
        super(name, model, capacity, cargoCapacity);
    }
}

// Класс для грузовых самолетов
class CargoAirplane extends Airplane {
    public CargoAirplane(String name, String model, int cargoCapacity) {
        super(name, model, 0, cargoCapacity); // Вместимость пассажиров = 0
    }
}

// Класс для авиакомпании
class Airline {
    protected String name;
    private List<Airplane> fleet; // список самолетов

    public Airline(String name) {
        this.name = name;
        this.fleet = new ArrayList<>();
    }

    // Добавление самолета в авиакомпанию
    public void addAirplane(Airplane airplane) {
        fleet.add(airplane);
    }

    // Подсчет общей вместимости пассажиров
    public int calculateTotalCapacity() {
        int totalCapacity = 0;
        for (Airplane airplane : fleet) {
            totalCapacity += airplane.getCapacity();
        }
        return totalCapacity;
    }

    // Подсчет общей грузоподъемности
    public int calculateTotalCargoCapacity() {
        int totalCargoCapacity = 0;
        for (Airplane airplane : fleet) {
            totalCargoCapacity += airplane.getCargoCapacity();
        }
        return totalCargoCapacity;
    }

    // Вывод всех самолетов
    public void displayFleet() {
        for (Airplane airplane : fleet) {
            System.out.println(airplane);
        }
    }
}

// Главный класс для запуска программы
public class Main {
    public static void main(String[] args) {
        // Создаем авиакомпанию
        Airline airline = new Airline("SkyFly");

        // Добавляем самолеты
        airline.addAirplane(new PassengerAirplane("Boeing 737", "737-800", 160, 20000));
        airline.addAirplane(new CargoAirplane("Antonov An-124", "Ruslan", 120000));
        airline.addAirplane(new PassengerAirplane("Airbus A380", "A380", 850, 40000));

        // Выводим информацию о самолётах
        System.out.println("Флот авиакомпании " + airline.name + ":");
        airline.displayFleet();

        // Вычисляем общую вместимость и грузоподъемность
        System.out.println("Общая пассажирская вместимость: " + airline.calculateTotalCapacity() + " пассажиров");
        System.out.println("Общая грузоподъемность: " + airline.calculateTotalCargoCapacity() + " кг");
    }
}