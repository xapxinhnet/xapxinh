XapXinh Media Player
====================

XapXinh is a media system including XapXinh Media Player (XMP), IOnOff Music Player (IMP) and XapXinh Media Server

XapXinh helps users control media player remotely via smartphone. With XapXinh, users can use smartphone to browse local files, search albums in cloud server and search youtube video, and play selected media in remote media player (XMP or IMP)

Steps to setup project in eclipse:

1. Import project to eclipse

2. Run command 
   mvn install:install-file -Dfile=jshortcut-0.4.jar -DgroupId=jshortcut -DartifactId=jshortcut -Dversion=0.4 -Dpackaging=jar
   Or open folder xapxinh.player\\player and double click to file mvn-install-jshortcut.bat
 
   (Make sure maven has been installed)

3. Unzip xapxinh.player\store\vlc.zip to folder xapxinh.player

4. To run XMP on Windows from Eclipse:
   Right click to file AppMain.java and Run As -> Java Application

   Right click to file AppMain.java and Run As -> Run Configurations

   Click to tab Environent and add new variable:

   VLC_PLUGIN_PATH Path to vlc folder inside git folder


LICENSE

The xmp project is provided under the GPL, version 3 or later.



