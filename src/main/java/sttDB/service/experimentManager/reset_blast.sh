find . -name \*.*.* -type f -delete
docker stop sttdb-blast
docker run --rm -itp 4567:4567 -v ~/db:/db --name sttdb-blast wurmlab/sequenceserver:1.0.11
