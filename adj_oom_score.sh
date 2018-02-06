#!/bin/bash

pids=`pidof $1`

if [ $? != 0 ] ; then
 echo "Couldn't find process named $1"
 exit 1
fi

for pid in $pids
do

 oom_score_adj=`cat /proc/$pid/oom_score_adj`

 echo "pid:           "$pid
 echo "oom_score_adj: "$oom_score_adj

 if [ $oom_score_adj != -1000 ] ; then

  echo -17 > /proc/$pid/oom_adj
  oom_score_adj=`cat /proc/$pid/oom_score_adj`
  echo "oom_score_adj: "$oom_score_adj

 fi

done

