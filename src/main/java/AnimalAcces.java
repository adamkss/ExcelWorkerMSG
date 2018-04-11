import java.util.List;
import java.util.function.Predicate;

public interface AnimalAcces {
     List<Animal> getAnimals();
     void addAnimal(Animal animal);
     List<Animal> getAnimalsByType(String type);
     List<Animal> getAnimalsByName(String name);
     List<Animal> getAnimalsByAge(int age);
     List<Animal> getAnimalsByBreed(String breed);
     List<Animal> getAnimalsByCustomPredicate(Predicate<? super Animal> predicate);
     void removeAnimal(Animal a);
}
