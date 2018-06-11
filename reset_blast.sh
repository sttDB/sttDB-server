#!/bin/sh

find $1 -name \*.fasta.* -type f -delete
docker stop sttdb-blast
docker run -d --rm -p 4567:4567 -v $1:/db --name sttdb-blast wurmlab/sequenceserver:1.0.11 >&2
echo "RESET DONE"