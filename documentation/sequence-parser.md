# Trinity sequence parser

The most important part of the application is the sequence information. The nucleotide sequences are the base of the information,
all the other information is related to this part.

## File format
```
>comp6_c0_seq1 len=299 path=[1:0-144 306:145-298]
GGTTAGTGAGCTTTAACCAACCTGATAGCCTCGCTGGCCCACAGTGGGGGAGAGAGAGGA
TGCAGTTGATTACTGTGTTGTTTTGTGTGTGTTTATACGTCTGTGGCTGGGATGAGACAG
AGGAGCAAACAATTCATTAAAGAATGCATTCACACTATAGTGGCTGGGTACAAGCGGTTT
CAATGTTTCTCTGTGTGTGTTCCTCACGTGATCTGCGCTCGCGTCAAAAGGGCCTTGCTG
CAAAGCTTCACCCGCCCAATGATCTGTCATTCACCGTAGGAATGTCATACATTAGTGTG
```

`>comp6_c0_seq1` : Trinity identifier. Unique in the file and in the experiment.

`len=299` : Length of the transcript. Optional parameter.

`path` : Not relevant in the project, is saved in case of use by other bio-tools.

`GGTTA...` : The transcript of this sequence, it can be a nucleotide, a protein...

## Fasta file service

The service contains three classes and one interface:

- FastaInfoSaver: Interface that must be implemented to save information about sequences.
- FastaUploader: Receives a FastaInfoSaver and a FastaParser, to parse and treat the old and new sequences.
- FastaParser: Gets the first line of a sequence, starting by ">" and saves the rest of lines until the next start of sequence.
- NucleotideSaver: Saves the information of a parsed line in an entity. 
