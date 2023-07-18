package ru.madykhan.api.ToyShopProgram;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Toy {
    private int id;
    private String name;
    private int quantity;
    private double weight;

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

class ToyStore {
    private List<Toy> toys;
    private List<Toy> prizeToys;

    public ToyStore() {
        toys = new ArrayList<>();
        prizeToys = new ArrayList<>();
    }

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void updateToyWeight(int toyId, double weight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(weight);
                break;
            }
        }
    }

    public void drawPrizeToys() {
        double totalWeight = 0.0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        Random random = new Random();
        double randomNumber = random.nextDouble() * totalWeight;

        double cumulativeWeight = 0.0;
        for (Toy toy : toys) {
            cumulativeWeight += toy.getWeight();
            if (randomNumber <= cumulativeWeight) {
                prizeToys.add(toy);
                break;
            }
        }
    }

    public Toy getPrizeToy() {
        if (!prizeToys.isEmpty()) {
            Toy prizeToy = prizeToys.remove(0);
            try {
                FileWriter writer = new FileWriter("prize_toys.txt", true);
                writer.write(prizeToy.getId() + ", " + prizeToy.getName() + "\n");
                writer.close();
            } catch (IOException e) {
                System.out.println("Failed to write prize toy to file: " + e.getMessage());
            }
            return prizeToy;
        }
        return null;
    }
}

public class ToyShopProgram {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        // Добавляем игрушки
        Toy toy1 = new Toy(1, "Мяч", 10, 20);
        Toy toy2 = new Toy(2, "Кукла", 5, 30);
        Toy toy3 = new Toy(3, "Конструктор", 8, 50);
        toyStore.addToy(toy1);
        toyStore.addToy(toy2);
        toyStore.addToy(toy3);

        // Обновляем вес игрушек
        toyStore.updateToyWeight(1, 15);
        toyStore.updateToyWeight(2, 25);
        toyStore.updateToyWeight(3, 35);

        // Розыгрыш игрушек
        toyStore.drawPrizeToys();

        // Получаем призовую игрушку
        Toy prizeToy = toyStore.getPrizeToy();
        if (prizeToy != null) {
            System.out.println("Призовая игрушка: " + prizeToy.getName());
        }
    }
}