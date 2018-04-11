import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Animal {
    private String type;
    private String name;
    private int age;
    private String breed;
/*
    public Animal(String type, String name, int age, String breed) {

        this.type = type;
        this.name = name;
        this.age = age;
        this.breed = breed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Objects.equals(type, animal.type) &&
                Objects.equals(name, animal.name) &&
                Objects.equals(breed, animal.breed);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, name, age, breed);
    }*/
}
