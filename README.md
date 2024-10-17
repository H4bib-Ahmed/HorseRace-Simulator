# **Horse Race Simulator:**

This Java project attempts to offer several interactive features, including racing tracks and lanes, in a dynamic simulation of a live horse race. Sunny McFarewell started the horse race simulation, 
which is currently being worked on by another developer. The project aims to provide both textual and graphical representations of a realistic and entertaining horse racing experience for users.

## Tools and softwares:

• Java Development Kit (JDK): JDK 1.8 or later is needed to run Java programs, which is necessary to run this program.
Oracle's JDK website offers the most recent version of Java for download and installation.

• Operating System: This software is compatible and and can run on many operating systems such as windows, MacOS and Linux. But
make sure 'java' and 'javac' are in your systems path to compile and run the programs from the command line.

## Features:

• each race is determined by the confidence rating of each horse so if a horse has a higher confidence rating it will move faster
but will have a higher chance of falling down. whilst a horse with lower confidence rating moves much slower but has a less likley
chance to fall down.

• A fallen horse will have their confidence lowered whilst a horse which has a won a race will have their cofidence increased. 

• Multiple features will be added such as letting the user change the track length. letting the user customize the horse for example, 
the name and symbol of the horse and the user can also change the colour of the race tracks background.

• The winners name would be displayed at the end of the race.

• The user can bet on a horse and if the horse wins the user will gain virtual currency whilst if the horse doesnt win and the user loses
the bet the winner will lose virtual currency.


## Installation:

First clone this repository to your local machine, do this by entering your terminal and writing git clone, follwoing the URL
of the repository

Then navigate to the cloned directoy, by using cd, then the name of the file - HorseRacingSimulator

Output And Expected Behaviour:

### Part 1:
Once the program is executed the race will start and it will display the race in terminal. the horses stats will be displayed on the side of the race
for example, the horse symbol, confidence rating and name will be displayed on the side of the race track. the stats will change live whilst the race
is going on for example the confidence rating will increase or decrease. if a horse falls before finishing the race it will display a symbol for the 
fallen horse and the horse will stop moving. whilst if a horse wins the race the race the horse name will be displayed saying "Winner (horse name)".

### Part 2:

Part 2 will contain the GUI for the horse race so when the main class is ran a window will open using Java JDK which will display a nice user friendly
looking GUI for the horse race. The GUI will contain buttons such as start race, customize horse and change Track colour. The GUI will also contain new
features such as allowing the user to bet on a horse and win some virtual currency and the GUI will also let the user choose the track length and will
also allow the user to customize the horse by changing the name and symbol of the horse.

## Java Classes:

### Part 1  and Part 2:

Race - this class outputs the race and does the main functionality of the race.

Horse - The horse class contains the mutator and accessor methods which sets the horse name, confidence rating and symbol

main - This is the main class and this class executes the race class and also adjusts the tracks length and also adds the horses into the lans with the name,symbol and confidence rating.

### Part 2:

RacingApp - This class displays the GUI of the horse race simulator

BettingSystem - this stores the betting system of the horse race simulator by adjusting the odds and virtual currency

## Author:

McFarewell - edited and enhanced by Habib Ahmed



