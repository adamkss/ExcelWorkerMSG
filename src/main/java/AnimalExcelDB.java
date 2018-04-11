import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnimalExcelDB implements AnimalAcces {
    String filePath;
    List<Animal> animals;
    XSSFWorkbook xssfWorkbook;
    Map<String, XSSFSheet> sheets;

    {
        animals = new ArrayList<>();
        sheets = new HashMap<>();
    }

    public AnimalExcelDB(String path) throws IOException {
        filePath = path;
        FileInputStream inputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        for (int sheetIndex = 0; sheetIndex < xssfWorkbook.getNumberOfSheets(); sheetIndex++) {
            sheets.put(xssfWorkbook.getSheetAt(sheetIndex).getSheetName(), xssfWorkbook.getSheetAt(sheetIndex));
        }

        sheets.entrySet().parallelStream().forEach(entry -> {
            for(Row currentRow : entry.getValue()) {
                String name = currentRow.getCell(0).getStringCellValue();
                int age = (int) currentRow.getCell(1).getNumericCellValue();
                String breed = currentRow.getCell(2).getStringCellValue();
                animals.add(new Animal(entry.getValue().getSheetName(), name, age, breed));
            }
        });

    }

    @Override
    public List<Animal> getAnimals() {
        return animals;
    }

    @Override
    public void addAnimal(Animal animal) {

        XSSFSheet sheet = sheets.get(animal.getType());
        XSSFRow newRow = sheet.createRow(sheet.getLastRowNum() +1);
        newRow.createCell(0).setCellValue(animal.getName());
        newRow.createCell(1).setCellValue(animal.getAge());
        newRow.createCell(2).setCellValue(animal.getBreed());

        updateSource();

    }

    private void updateSource() {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            xssfWorkbook.write(outputStream);
        } catch (Exception e) {
            System.out.println("Can't...");
        }
    }

    @Override
    public List<Animal> getAnimalsByType(String type) {
        return animals.stream()
                .filter(animal -> animal.getType().equals(type))
                .collect(Collectors.toList());
    }

    public List<Animal> getAnimalsByName(String name) {
        return animals.stream()
                .filter(animal -> animal.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> getAnimalsByAge(int age) {
        return animals.stream()
                .filter(animal -> animal.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> getAnimalsByBreed(String breed) {
        return animals.stream()
                .filter(animal -> animal.getBreed().equals(breed))
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> getAnimalsByCustomPredicate(Predicate<? super Animal> predicate) {
        return animals.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public void removeAnimal(Animal a) {
        XSSFSheet sheet = sheets.get(a.getType());
        final List<Integer> rowsToDelete=new ArrayList<>();
        sheet.forEach(row -> {
            if(row.getCell(0).getStringCellValue().equals(a.getName())
                    || row.getCell(1).getNumericCellValue() == a.getAge()
                    || row.getCell(2).getStringCellValue().equals(a.getBreed()))
                rowsToDelete.add(row.getRowNum());
        });

        rowsToDelete.stream().forEach(rowNum -> {
            removeRow(sheet, rowNum);
        });

        updateSource();
    }

    private void removeRow(XSSFSheet sheet,int rowIndex){
        int lastRowNum=sheet.getLastRowNum();
        if(rowIndex >= 0 && rowIndex < lastRowNum){
            sheet.shiftRows(rowIndex+1,lastRowNum, -1);
        }

        if(rowIndex==lastRowNum){
            XSSFRow removingRow=sheet.getRow(rowIndex);
            if(removingRow!=null){
                sheet.removeRow(removingRow);
            }
        }
    }
}
