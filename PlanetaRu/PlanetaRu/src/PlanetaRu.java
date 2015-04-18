import java.io.*;
import java.util.*;

public class PlanetaRu {

    private static final String tmpIn = "tmpIn";
    private static final String tmpOut = "tmpOut";
    private String fileIn;
    private String fileOut;
    private int memorySize;
    private long fileSize;

    public PlanetaRu(String fileIn, String fileOut, int memorySize) {
        this.fileIn = fileIn;
        this.fileOut = fileOut;
        this.memorySize = memorySize;
        fileSize = new File(fileIn).length();
    }

    public static void main(String[] args) {
        PlanetaRu instance = new PlanetaRu("Dates", "DatesOut", 1000000 * 8);
        try {
            Calendar startTime = Calendar.getInstance();
            instance.sortDates();
            System.out.println("Время работы: " + ((Calendar.getInstance().getTimeInMillis() - startTime.getTimeInMillis()) / 1000) + " секунд");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private LinkedList<Long> readBlock(long position, long length, RandomAccessFile inputFile) throws IOException {
        LinkedList<Long> block = new LinkedList<>();
        inputFile.seek(position);
        long endPosition = position + length;
        if(endPosition > fileSize) endPosition = fileSize;
        for (; position < endPosition; position+=8)
            block.add(inputFile.readLong());
        return block;
    }
    private void mergeBlocks(LinkedList<Long> block1, LinkedList<Long> block2, DataOutputStream outputFile) throws IOException {
        if(block1.isEmpty()) {
            for (Long date : block2)
                outputFile.writeLong(date);
            block2.clear();
        } else if(block2.isEmpty()) {
            for (Long date : block1)
                outputFile.writeLong(date);
            block1.clear();
        } else while(!block1.isEmpty() && !block2.isEmpty()) {
            outputFile.writeLong(block1.peekFirst() > block2.peekFirst() ? block1.pollFirst() : block2.pollFirst());
        }
    }

    private void firstStep() throws IOException {
        RandomAccessFile inputFile = new RandomAccessFile(fileIn, "r");
        DataOutputStream outputFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tmpOut), memorySize));
        for(int i = 0; i < fileSize; i+=memorySize) {
            LinkedList<Long> block = readBlock(i, memorySize, inputFile);
            Collections.sort(block, Comparator.reverseOrder());
            while(!block.isEmpty())
                outputFile.writeLong(block.pollFirst());
        }
        outputFile.close();
    }

    private void nextStep(int blockSize) throws IOException {
        if(blockSize >= fileSize)
            return;

        long position1 = 0, position2 = blockSize;
        long endPosition1 = blockSize < fileSize ? blockSize : fileSize;
        long endPosition2 = blockSize * 2 < fileSize ? blockSize * 2 : fileSize;

        LinkedList<Long> block1 = new LinkedList<>(), block2 = new LinkedList<>();
        if(new File(tmpIn).exists()) new File(tmpIn).delete();
        new File(tmpOut).renameTo(new File(tmpIn));

        RandomAccessFile inputFile = new RandomAccessFile(tmpIn, "r");
        DataOutputStream outputFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tmpOut), memorySize / 2));

        while(position1 < fileSize) {

            while (!block1.isEmpty() || !block2.isEmpty() || position1 < endPosition1 || position2 < endPosition2) {
                if (block1.isEmpty() && position1 < endPosition1) {
                    block1 = readBlock(position1, memorySize / 4, inputFile);
                    position1 += memorySize / 4;
                }
                if (block2.isEmpty() && position2 < endPosition2) {
                    block2 = readBlock(position2, memorySize / 4, inputFile);
                    position2 += memorySize / 4;
                }
                mergeBlocks(block1, block2, outputFile);
            }

            position1 += memorySize;
            position2 += memorySize;
            endPosition1 += (memorySize * 2);
            endPosition2 += (memorySize * 2);
            if(endPosition1 > fileSize) endPosition1 = fileSize;
            if(endPosition2 > fileSize) endPosition2 = fileSize;
        }

        inputFile.close();
        outputFile.flush();
        outputFile.close();

        nextStep(blockSize * 2);
    }

    public void sortDates() throws IOException {
        firstStep();
        nextStep(memorySize);
        new File(tmpIn).delete();
        if(new File(fileOut).exists()) new File(fileOut).delete();
        new File(tmpOut).renameTo(new File(fileOut));
    }

}
