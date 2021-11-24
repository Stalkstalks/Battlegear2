Mine and Blade : Battlegear - 2
===============================

![](bg-logo.png)
	
Minecraft version: 1.7.10  
Forge Version: 1.7.10-10.13.4.1614

This is the second iteration of Mine & Blade: Battlegear, the popular dual-wielding and combat mod for Minecraft.
The mod has a heavy dependency on the use of ASM (coremod) to edit the bytecode of the minecraft base classes. 

What's New
----------

* New arrows

Ice Packed arrow, effective against fire immune creatures.

Holy Torch arrow, it helps you face the darkness

* New Bows 

Iron and Diamond bow, that have a cool arrow render and more durability.

* Separate key category
 
* GUI clean up-->:

Mod updater "Mud" removed.

Now the Battlegear slots are shown when you are in the dual wielding mode.

* New config option-->:

B:"Always Show Battlegear Slots"

Installation
------------
**To make a fork**
The files contained within the repository must be placed inside a minecraft forge-universal src installation.
Due to potential legal issues of re-distributing Mojang .java files, all base class edits are distributed as .java.patch files. They serve only as documentation.
You can set a dev environment by Gradle, with IntelliJIdea:
`` gradlew setupDecompWorkspace idea genIntellijRuns``
or Eclipse:
`` gradlew setupDecompWorkspace eclipse``
then import the build.gradle file.

**To make an addon**
The files in /battlegear api folder can be used as external libraries.
M.U.D and Battlegear are independent.
To load them while making the addon, simply put them in the /mods folder in your mod run installation.

Compiling the Mod
-----------------
The mod can be compiled using the following gradle command
``gradlew build``

This will generate a jar file in the battlegear dist folder, along with its checksum.

Translations
------------
If you can help to update any of the translation files please fork & make a pull request.

We currently have translation for the following languages (please note that many of these may be out of date)
* English US (default)
* English UK (mainly just change armor -> armour)
* English Pirate
* French
* Polish
* Chinese 
* German
* Spanish
* Russian

Please feel free to add to this list or update any of the current language files. They can be found in battlegear mod src/minecraft/assets/battlegear2/lang/.


Some Notes for Texture Pack Makers
----------------------------------

Some of the rendering capabilities of the mod are still in development, especially concerning the heraldry. The following are a few notes.
* The patterns should only have white. The white section will determine the secondary colour. The primary colour will be the alpha section. Semi transparent sections should also work for better blending.
* The icons should be greyscale and alpha values. They also require at least 1 pixal border around the whole image that should be kept black. This is the reason all of them are 18x18 pixals (16x16 plus a 1px border all around)
