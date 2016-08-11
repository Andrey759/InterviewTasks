import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {

    private static class DictEntry {
        String code, description;

        public DictEntry(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String toString() {
            return description + ":" + code;
        }
    }

    private static class NamedDictEntry {
        String discriminator;
        DictEntry dictEntry;

        public NamedDictEntry(String discriminator, DictEntry dictEntry) {
            this.dictEntry = dictEntry;
            this.discriminator = discriminator;
        }
        public String getDiscriminator() {
            return discriminator;
        }
        public DictEntry getDictEntry() {
            return dictEntry;
        }
    }

    private static class LastDiscriminator {
        String value;

        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public boolean hasValue() {
            return StringUtils.isNotBlank(value);
        }
    }


    public static void main(String... args) throws IOException, InvalidFormatException {
        InputStream inputStream = new FileInputStream("dict_ex.xlsx");

        Workbook book = WorkbookFactory.create(inputStream);
        final Sheet sheet = book.getSheetAt(0);

        LastDiscriminator lastDiscriminator = new LastDiscriminator();
        Map<String, List<DictEntry>> result = StreamSupport.stream(sheet.spliterator(), false)
                .map(row -> getNamedDictEntryOrNull(row, lastDiscriminator))
                .filter(namedDictEntry -> namedDictEntry != null)
                .collect(Collectors.toMap(
                        NamedDictEntry::getDiscriminator,
                        namedDictEntry -> namedDictEntry.getDictEntry() != null ?
                                Lists.newArrayList(namedDictEntry.getDictEntry()) : Lists.newArrayList(),
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        }
                ));
        result.forEach((discr, dictEnty) -> System.out.println(discr + ": " + dictEnty));
    }

    private static NamedDictEntry getNamedDictEntryOrNull(Row row, LastDiscriminator lastDiscriminator) {
        if (isValidTitleRow(row)) {
            return null;
        }
        if (isValidDiscriminatorRow(row)) {
            lastDiscriminator.setValue(read(row.getCell(2)));
            return new NamedDictEntry(lastDiscriminator.getValue(), null);
        }
        if (isValidDictEntryRow(row)) {
            if (!lastDiscriminator.hasValue()) {
                throw new RuntimeException("Discriminator row must be before DictEntry row");
            }
            return new NamedDictEntry(lastDiscriminator.getValue(),
                    new DictEntry(read(row.getCell(0)), read(row.getCell(1))));
        }
        if (isValidBlankRow(row)) {
            return null;
        }
        throw new RuntimeException("Unknown row: '" + StringUtils.join(row, "|") + "'");
    }

    private static boolean isValidTitleRow(Row row) {
        return hasValue(row.getCell(0)) && "Code".equals(read(row.getCell(0)))
                && hasValue(row.getCell(1)) && "Description".equals(read(row.getCell(1)))
                && hasValue(row.getCell(2)) && "Discriminator".equals(read(row.getCell(2)));
    }

    private static boolean isValidDiscriminatorRow(Row row) {
        return !hasValue(row.getCell(0)) && hasValue(row.getCell(2));
    }

    private static boolean isValidDictEntryRow(Row row) {
        return hasValue(row.getCell(0)) && hasValue(row.getCell(1)) && !hasValue(row.getCell(2));
    }

    private static boolean isValidBlankRow(Row row) {
        return !hasValue(row.getCell(0)) && !hasValue(row.getCell(1)) && !hasValue(row.getCell(2));
    }

    private static boolean hasValue(Cell cell) {
        return cell != null && (cell.getCellType() == Cell.CELL_TYPE_NUMERIC ||
                (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isNotBlank(cell.getStringCellValue())));
    }

    private static String read(Cell cell) {
        return cell.getCellType() == Cell.CELL_TYPE_STRING ? cell.getStringCellValue() :
                new DecimalFormat("#.###").format(cell.getNumericCellValue());
    }

}
