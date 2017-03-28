XapXinh is a media system includes XapXinh Media Player (XMP) and XapXinh Center Server (XCS).

XMP player based on VLC and VLCj and XMP is to control remotely from smartphone, tablet or PC

Many thanks to VLC and VLCj.

Features:

- Play local files

- Search and play Youtube videos

- Search and play albums that hosted on XCS

By default, after started, XMP connects to XCS and get a ID. Then you can use the ID to login to XCS web page to control the XPM

Current XMP release version is 1.0.1. You can download here

Steps to install (Window XP, 7, 8, 10 64bit)

1. Download and unzip to a folder

2. Run xmp.exe

3. Find id and password from taskbar icon info message

4. Use the id and password to login to xapxinh.net

Steps to setup project (Eclipse)

0. Extract vlc.zip from xapxinh.player\store to xapxinh.player (after extracted folder structure is xapxinh.player\vlc)
   
   Copy file xapxinh.player\store\nircmd_64.exe to xapxinh.player\nir\nircmd.exe

1. Import project

2. Install maven dependency by runing script in mvn-install-jshortcut.bat

3. Run AppMain.java as Java Application

4. Right click to AppMain.java -> Run > Run Configurations...

5. Add new Variable Environment: 

- Name: VLC_PLUGIN_PATH 

- Value: Full path to xapxinh.player\vlc

Happy coding!
