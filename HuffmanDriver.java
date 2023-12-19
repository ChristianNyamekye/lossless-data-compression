import java.io.IOException;
import java.util.Map;

/**
 * Main method that tests Huffman Encoding
 * @throws java.io.IOException
 */


public class HuffmanDriver {
    public static void main(String[] args) throws IOException {

        HuffmanEncoding mine = new HuffmanEncoding();
        Map<Character, Long> frequencies = mine.countFrequencies("inputs/mine.txt");
        BinaryTree<CodeTreeElement> codeTree = mine.makeCodeTree(frequencies);
        Map<Character, String> codeMap = mine.computeCodes(codeTree);
        mine.compressFile(codeMap, "inputs/mine.txt", "inputs/mine_compressed.txt");
        mine.decompressFile("inputs/mine_compressed.txt", "inputs/mine_decompressed.txt", codeTree);


    }
}