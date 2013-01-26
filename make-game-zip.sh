#!/bin/bash
rm ggj2013.zip
dir=`mktemp -d lolwatXXXXXX`
curdir=`pwd`
echo $dir
mkdir $dir/ggj2013
cp -r ggj2013* $dir/ggj2013/
cp -r *.bat $dir/ggj2013/
cp -r *.command $dir/ggj2013/
cd $dir
zip -r ggj2013.zip ggj2013
mv ggj2013.zip $curdir
cd $curdir
rm -rf $dir
