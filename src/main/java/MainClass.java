import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        new MainClass().go(new AnimalExcelDB("C:\\ExcelFiles\\Animals.xlsx"));
    }

    private void go(AnimalAcces animalAcces){

        animalAcces.getAnimalsByType("Dogs").forEach(a -> {
            System.out.println(a.getName());
        });

        animalAcces.addAnimal(new Animal("Dogs","asd2",22,"bred"));
        animalAcces.addAnimal(new Animal("Cats","cv",44,"gfdg"));
        animalAcces.removeAnimal(new Animal("Dogs","Lily",4,"Mixed"));
    }
}
