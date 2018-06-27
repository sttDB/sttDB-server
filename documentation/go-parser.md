# Go parser

 One of the files used to classify sequence information is the amiGO file. This file contains go terms, where any 
 go term is related with one or more sequences.
 
 ## File format explanation
 
 ```
 GO_Type	Slim_ID	        GO_Name         	Input_Accession	Input_GOID	GOID description
 C	GO:0000228	nuclear chromosome	asd	        GO:0008622	epsilon DNA polymerase complex
 F	GO:0016887	ATPase activity	        asds	        GO:0016887	ATPase activity
 ```
 
 - Go type: It can be C-F-P, Cellular component-Mollecular function-Biological process.
 - Slim Id: Slim is a group of GO terms, so slim id is the identifier for this group.
 - GO_Name: The descriptor for the slim group.
 - Input_Accession: The sequence identifier associated with the Go term.
 - Input_GOID: The Go term identifier.
 - GOID description: The Go term description.
 
 Important: A go slim contains a lot of go terms, and a go term can have a lot of go slims, many to many.
 
 ## What does the go service
 
 - GoUploader: Receives a path of the go file and the experiment, then it send them to the parser.
 - GoParser: Takes a line and splits the terms by the tabulators. Then it send the splitted line to the saver.
 - GoSaver: Takes the line arguments and saves them as a GO term. If the go term is saved and the go slim is not the same 
 as the saved one, it appends the new Slim into the Go term object.
