#!/bin/sh
# AUTO-GENERATED FILE, DO NOT EDIT!
if [ -f $1.org ]; then
  sed -e 's!^G:/public/IDE/Cygwin/bin/lib!/usr/lib!ig;s! G:/public/IDE/Cygwin/bin/lib! /usr/lib!ig;s!^G:/public/IDE/Cygwin/bin/bin!/usr/bin!ig;s! G:/public/IDE/Cygwin/bin/bin! /usr/bin!ig;s!^G:/public/IDE/Cygwin/bin/!/!ig;s! G:/public/IDE/Cygwin/bin/! /!ig;s!^G:!/cygdrive/g!ig;s! G:! /cygdrive/g!ig;s!^D:!/cygdrive/d!ig;s! D:! /cygdrive/d!ig;s!^C:!/cygdrive/c!ig;s! C:! /cygdrive/c!ig;' $1.org > $1 && rm -f $1.org
fi
