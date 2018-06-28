# Kegg parser

The kegg term is one of the sequence classifier of the application (like the interpro or go terms).

 ## File format explanation
 
 ```
 Pathway Reconstruction Result
 
 Hide all objects
 Metabolism
 Global and overview maps
 01100 Metabolic pathways (746)
   K00002
    asd
 01110 Biosynthesis of secondary metabolites (215)
   K00002
    asds
 ```
 
 A kegg term is composed of three pathways, the kegg identifier, and a description, that is the appendage of the three pathways.
 
 In the kegg files, the pathways are the first terms to appear. In the example above, the first pathway obtained 
 is the "Metabolism" pathway. When we encounter the first pathway, we know that the two consequent lines are the other two pathways.
 Furthermore, we know that the first three lines of the file are not pathways.
 
 The first time we encounter a whitespace, is because the kegg identifier appears.
 After this line, the sequence identifiers appear and the parser can relate the kegg with the sequences.
 
 Finally, the pathways can change. We can differ them from the kegg/sequence by watching the start of line and if it contains 
 spaces.
 
 ## What does the kegg service
 
 - KeggUploader: Receives a path of the kegg file and the experiment, then it send them to the parser.
 - KeggParser: Differs between the lines containing pathways, kegg identifiers or a set of sequence identifiers.
 - KeggSaver: Takes the different pathways and saves them in memory, then they are used to save different kegg terms and add info into sequences.