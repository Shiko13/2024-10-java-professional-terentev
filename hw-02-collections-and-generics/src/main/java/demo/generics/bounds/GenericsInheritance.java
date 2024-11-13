package demo.generics.bounds;

import demo.generics.bounds.entries.Animal;
import demo.generics.bounds.entries.Cat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GenericsInheritance {
    private static final Logger logger = LoggerFactory.getLogger(GenericsInheritance.class);

    public static void main(String[] args) {

        Animal cat = new Cat();
        logAnimal(cat);

        List<Cat> catList = new ArrayList<>();
        logCats(catList);
        // logAnimals(catList); //ошибка
        logAnimals(List.of(new Animal()));
    }

    private static void logAnimal(Animal animal) {
        logger.info("animal:{}", animal);
    }

    private static void logAnimals(List<Animal> animals) {
        logger.info("animals:{}", animals);
    }

    private static void logCats(List<Cat> cats) {
        logger.info("cats:{}", cats);
    }
}
