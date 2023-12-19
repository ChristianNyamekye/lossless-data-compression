import java.io.*;
import java.util.*;

/**
 *  Lossless file compression; Huffman Encoding; for PS-3
 * @author Christian Nyamekye, Dartmouth CS10 Winter 2023
 * @author Daniel Amoateng
 *
 */

public class HuffmanEncoding implements Huffman{
    /**
     *
     * @param pathName - path to a file to read
     * @return countFrequencies - map of characters and their frequencies
     * @throws IOException
     */
    public Map<Character, Long> countFrequencies(String pathName) throws IOException {
        Map<Character, Long> characterFrequency = new TreeMap<Character, Long>();
        BufferedReader in = new BufferedReader(new FileReader(pathName));

        int character;
        while ( (character = in.read()) != -1) {
            char c = (char) character;
            if (characterFrequency.containsKey(c)) characterFrequency.put(c, characterFrequency.get(c) + 1);
            else characterFrequency.put(c, 1L);
        }
        in.close();
        return characterFrequency;
    }

    /**
     *
     * @param frequencies a map of Characters with their frequency counts from countFrequencies
     * @return - returns a tree of characters and their frequencies with most frequent characters up the tree and less
     * frequent character lower the tree
     */

    @Override
    public BinaryTree<CodeTreeElement> makeCodeTree(Map<Character, Long> frequencies) {
        Comparator<BinaryTree<CodeTreeElement>> frequencyCompare = new TreeComparator();
        PriorityQueue<BinaryTree<CodeTreeElement>> priorityQueue = new PriorityQueue<BinaryTree<CodeTreeElement>>(frequencyCompare);

        // Boundary case for when file is empty
        if (frequencies.size() == 0) try {
            throw new Exception("Empty File");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // makes a codeTreeElement from all characters in the map and adds them to the priorityQueue
        for (Map.Entry<Character, Long> entry : frequencies.entrySet()){
            CodeTreeElement characterTree = new CodeTreeElement(entry.getValue(), entry.getKey());
            BinaryTree<CodeTreeElement> tree = new BinaryTree<>(characterTree);
            priorityQueue.add(tree);
        }

        // Boundary case for when file has a single character --> frequency relationship
        if (priorityQueue.size() == 1){
            BinaryTree<CodeTreeElement> T1 = priorityQueue.peek();
            BinaryTree<CodeTreeElement> T2 = null;
            CodeTreeElement r = new CodeTreeElement(T1.getData().getFrequency(), null);
            BinaryTree<CodeTreeElement> singleT = new BinaryTree<>(r, T1, T2);
            return singleT;
        }

        // case for all other cases
        while (priorityQueue.size() > 1) {
            BinaryTree<CodeTreeElement> T1 = priorityQueue.remove();
            BinaryTree<CodeTreeElement> T2 = priorityQueue.remove();
            long frequencyT1T2 = (T1.getData().getFrequency() + T2.getData().getFrequency());
            CodeTreeElement r = new CodeTreeElement(frequencyT1T2, null);

            BinaryTree<CodeTreeElement> T = new BinaryTree<>(r, T1, T2);

            priorityQueue.add(T);
        }
        // returns the element in the priorityQueue
        return priorityQueue.peek();
    }

    /**
     *
     * @param codeTree the tree for encoding characters produced by makeCodeTree
     * @return returns a map of codes and the data in binary form
     */

    @Override
    public Map<Character, String> computeCodes(BinaryTree<CodeTreeElement> codeTree) {

        Map<Character, String> mapCodes = new TreeMap<Character, String>();
        Map<Character, String> map = traverseCodeTree(codeTree, mapCodes, "");
        return map;
    }

    /**
     *
     * @param node - Code tree to traverse
     * @param computedCodes - Map that stores characters and their binary data
     * @param pathSoFar - tracks the path traversed through the tree
     * @return returns map of computed codes
     */
    public Map<Character, String> traverseCodeTree(BinaryTree<CodeTreeElement> node,  Map<Character, String> computedCodes, String pathSoFar) {
        if(node.hasLeft())traverseCodeTree(node.getLeft(), computedCodes, pathSoFar + "0");
        if(node.hasRight())traverseCodeTree(node.getRight(), computedCodes, pathSoFar + "1");
        if(node.isLeaf())computedCodes.put(node.getData().getChar(), pathSoFar);
        return computedCodes;
    }

    /**
     * Compresses an input file and writes it into a new file
     * @param codeMap - Map of characters to codes produced by computeCodes
     * @param pathName - File to compress
     * @param compressedPathName - Store the compressed data in this file
     * @throws IOException
     */

    @Override
    public void compressFile(Map<Character, String> codeMap, String pathName, String compressedPathName) throws IOException {
        try{
            BufferedReader input = new BufferedReader(new FileReader(pathName));
            BufferedBitWriter bitOutput = new BufferedBitWriter(compressedPathName);

            int character;
            boolean bit = false;
            while ((character = input.read()) != -1) {
                char c = (char) character;
                String codeWord = codeMap.get(c);
                for (int j = 0; j < codeWord.length(); j++) {
                    if (codeWord.charAt(j) == '0') bit = false;
                    if (codeWord.charAt(j) == '1') bit = true;
                    bitOutput.writeBit(bit);
                }
            }
            bitOutput.close();
            input.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Decompresses a compressed file and writes it into an output file
     * @param compressedPathName - file created by compressFile
     * @param decompressedPathName - store the decompressed text in this file, contents should match the original file before compressFile
     * @param codeTree - Tree mapping compressed data to characters
     * @throws IOException
     */

    @Override
    public void decompressFile(String compressedPathName, String decompressedPathName, BinaryTree<CodeTreeElement> codeTree) throws IOException {
        try{
            BufferedBitReader bitInput = new BufferedBitReader(compressedPathName);
            BufferedWriter output = new BufferedWriter(new FileWriter(decompressedPathName));

            BinaryTree<CodeTreeElement> tracker = codeTree;

            while (bitInput.hasNext()) {

                boolean bit = bitInput.readBit();

                if (!bit) tracker = tracker.getLeft();
                else tracker = tracker.getRight();

                if (tracker.isLeaf()) {
                    output.write(tracker.getData().getChar());
                    tracker = codeTree;
                }
            }
            output.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



}
