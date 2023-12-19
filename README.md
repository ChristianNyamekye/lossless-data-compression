# Lossless Data Compression Using Huffman Encoding

## Overview

This project demonstrates the implementation of Huffman encoding for lossless data compression and decompression, a significant technique in the field of data storage and transfer. Leveraging the Java programming language, this assignment integrates various concepts including trees, maps, priority queues, and file I/O operations to achieve efficient data compression.

Huffman encoding, a pivotal algorithm in lossless data compression, was recently utilized for storing images in DNA, showcasing its relevance and versatility in modern computational applications.

## Technicalities

### Huffman Encoding Algorithm

- **Variable-Length Encoding**: Unlike ASCII's 7-bit fixed encoding, Huffman encoding uses variable-length encoding where frequently occurring characters are encoded with shorter code words than less frequent ones. This approach significantly reduces the overall file size.
- **Fixed Encoding**: The encoding is fixed, meaning each character is represented by the same code throughout the entire file. This contrasts with adaptive encoding schemes that may change the encoding as the file is processed.

### Core Components

- **Trees**: Utilized for building the Huffman coding tree, a binary tree where each leaf node represents a character and its frequency in the file.
- **Priority Queues**: Employed for efficiently building the Huffman tree by prioritizing lower frequency nodes.
- **Maps**: Used for mapping characters to their respective Huffman codes.
- **File I/O**: Handling reading from and writing to files for the compression and decompression processes.

## Tools and Technologies

- **Language**: Java (Version: specify version if needed)
- **Data Structures**: Implementation of trees, priority queues, and hash maps.
- **File Handling**: Java I/O classes for reading and writing files.
- **Testing and Debugging**: JUnit for unit testing and debugging tools within an IDE (e.g., IntelliJ IDEA or Eclipse).

## Usage

The project contains code for both compressing and decompressing files using the Huffman encoding algorithm. The main components include:

1. **Compression**: Reads the original file, computes the frequency of each character, constructs the Huffman tree, and writes the compressed file along with the tree structure.
2. **Decompression**: Reads the compressed file and the Huffman tree, and reconstructs the original file.

## Background and Theory

Developed by David Huffman as a term project, Huffman encoding was one of the earliest methods for lossless file compression. It is based on the principle of variable-length encoding and guarantees the smallest possible fixed encoding for a file.

The detailed description of the Huffman encoding process, along with pseudocode, is provided in the textbook (Pages 595-597).

## Resources

- **Source Code and Inputs**: Available in `compression.zip`.
- **Reference Textbook**: [Textbook Name], Pages 595-597.
