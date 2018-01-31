#!/bin/bash
j=$(basename $1)
/usr/bin/makeblastdb -in $1 -dbtype $2 -title $j
killall sequenceserver
/usr/bin/screen -dmS ss /usr/local/bin/sequenceserver -d=/home//blast/public/blast/ -H 127.0.0.1 -p 4567 > /dev/null
exit