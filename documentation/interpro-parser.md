# Interpro Parser

The interpro family is one of the integrated files in the system. Its information classifies every sequence in an Interpro group.

## File format explanation
The file has this format:
```
#counter	transcript_id	        interpro	description
1	        comp153003_c0_seq1	PF03165	        MH1 domain
2	        comp153003_c0_seq1	PF00859	        CTF/NF-I family
```

- The counter is the row number of the file, which is completely ignored.
- The transcript_id is the sequence identifier which is related with the interpro.
- The interpro is the identifier of this classifier.
- The description is the description of this interpro classifier.

The interpro identifier and the transcript identifier are not unique. This means that they can appear in different rows.

## What does the interpro service

The interpro service contains 4 classes.
- InterproUploader, receives an interpro file and the experiment name where it pertains.
- TrapidInterproParser, parses the lines into LineItems, loading all the file into memory.
- LineItems, contains a sequence identifier with the related interpro classifier and description.
- InterproStorer, stores all the LineItems in memory into the mongoDB.