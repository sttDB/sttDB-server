### incrontab entries
### Edit your incrontab with `incrontab -e`
```
*/2 * * * * /home/blast/public/blast/genomic/ IN_MOVED_TO /home/blast/addblastdb.sh $@$# nucl
*/2 * * * * /home/blast/public/blast/protein/ IN_MOVED_TO /home/blast/addblastdb.sh $@$# prot
```