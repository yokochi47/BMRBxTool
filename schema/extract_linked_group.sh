#!/bin/bash

dict2sdb_info=dict2sdb.info

if [ ! -e $dict2sdb_info ] ; then

 echo Not found $dict2sdb_info
 eixt 1

fi

linked_group=linked_group

echo "#" > $linked_group
echo loop_ >> $linked_group
echo _pdbx_item_linked_group.category_id >> $linked_group
echo _pdbx_item_linked_group.link_group_id >> $linked_group
echo _pdbx_item_linked_group.label >> $linked_group
echo _pdbx_item_linked_group.context >> $linked_group
echo _pdbx_item_linked_group.condition_id >> $linked_group

while read line
do

 child_category_id=`echo $line | cut -d '"' -f 4`
 group_id=`echo $line | cut -d '"' -f 2`
 parent_category_id=`echo $line | cut -d '"' -f 8 | cut -d '.' -f 1 | sed -e 's/^_//'`

 echo "'"$child_category_id"'" $group_id "'"$child_category_id:$parent_category_id:$group_id"'" . . >> $linked_group

done < $dict2sdb_info

echo "#" >> $linked_group
echo loop_ >> $linked_group
echo _pdbx_item_linked_group_list.child_category_id >> $linked_group
echo _pdbx_item_linked_group_list.link_group_id >> $linked_group
echo _pdbx_item_linked_group_list.child_name >> $linked_group
echo _pdbx_item_linked_group_list.parent_name >> $linked_group
echo _pdbx_item_linked_group_list.parent_category_id >> $linked_group

while read line
do

 child_category_id=`echo $line | cut -d '"' -f 4`
 group_id=`echo $line | cut -d '"' -f 2`
 parent_category_id=`echo $line | cut -d '"' -f 8 | cut -d '.' -f 1 | sed -e 's/^_//'`
 child_item_id=`echo $line | cut -d '"' -f 6`
 parent_item_id=`echo $line | cut -d '"' -f 8`

 echo "'"$child_category_id"'" $group_id "'"$child_item_id"'" "'"$parent_item_id"'" "'"$parent_category_id"'" >> $linked_group

done < $dict2sdb_info


