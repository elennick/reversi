"mvn clean test" from the top level to run

requires java 7 (or more recent)
requires maven 3

requirements that were provided from three rings for this "programming challenge":

> The programming challenge is to use one of our game frameworks to
create a simple game of your choice. The framework we'd like you to
use is called PlayN (http://code.google.com/p/playn/). It's a Java 2D
scene graph framework that's pretty similar to Flash's 2D scene graph
(it's even more similar to Starling, a 2D scene graph for Flash that's
built using Flash's accelerated 3D support).
> 
> You can make any game you like. If you have some simple game you think
would be fun to implement, go ahead. Otherwise something like Reversi
is a good combination of simple, light on graphics, and involving
enough game logic to make things interesting (calculating legal moves
and computing which tiles are flipped when a player makes a play). If
you choose a multiplayer game, you can just assume "hotseat" play
(both players at the same computer), you don't have to do networking
or anything like that.
> 
> We're looking for something you can do in two or three, two or three
hour sessions. The first session you'll probably spend getting things
set up and poking around with some examples and getting your own
hello-world bits running. The second and possibly third session would
be spent writing the game. If you run into any roadblocks along the
way, you can email me ([REDACTED]@threerings.net). We're most interested in seeing your
code and how you choose to represent game state and handle user interaction and
compute game logic. We're not trying to find out how good you are at
debugging development environment problems. 
> 
> To get started, you'll need the following things:
> 
> 1. A JDK: http://www.oracle.com/technetwork/java/javase/downloads/jdk-7u4-downloads-1591156.html
> 2. Maven 3 (a Java build tool): http://maven.apache.org/download.html
> 3. Git: http://git-scm.com/
> 
> Check out the PlayN sample projects repository:
> 
> git clone https://code.google.com/p/playn-samples/
> 
> Run the hello world sample:
> 
> cd playn-samples/hello
> mvn test
> 
> You should see a window pop up with a rainbow background, and when you
click it, a little rotating pea should appear where you clicked.

> Assuming that went well, generate your own skeleton project:

> mvn archetype:generate -DarchetypeGroupId=com.googlecode.playn \
-DarchetypeArtifactId=playn-archetype -DarchetypeVersion=1.4

> This will ask you some questions:

> Define value for property 'groupId': :
> Define value for property 'artifactId': :
> Define value for property 'version': 1.0-SNAPSHOT: :
> Define value for property 'package': foobar: :
> Define value for property 'JavaGameClassName': :

    groupId is generally a reverse domain name (like com.threerings)
    artifactId is the name of your project (e.g. reversi)
    version you can leave at 1.0-SNAPSHOT, it doesn't matter
    package will default to your groupId and is the Java package that
    will be used for the skeleton project
    JavaGameClassName will be the name of the main class in your project
    and used as a prefix for other class names (e.g. Reversi)

> Once you enter those things in, a skeleton game project will be
> generated in a directory named by your artifactId. Assuming we use the
> following values:

> Define value for property 'groupId': : com.threerings.challenge
> Define value for property 'artifactId': : reversi
> Define value for property 'version': 1.0-SNAPSHOT: :
> Define value for property 'package': com.threerings.challenge: :
> Define value for property 'JavaGameClassName': : Reversi
> Confirm properties configuration:
> groupId: com.threerings.challenge
> artifactId: reversi
> version: 1.0-SNAPSHOT
> package: com.threerings.challenge
> JavaGameClassName: Reversi
> Y: : y

> You will end up with a directory structure as follows:

> reversi/build.xml
> reversi/core/pom.xml
> reversi/core/src/main/java/com/threerings/challenge/core/Reversi.java
> reversi/core/src/main/java/com/threerings/challenge/resources/images/bg.png
> reversi/java/pom.xml
> reversi/java/src/main/java/com/threerings/challenge/java/ReversiJava.java
> reversi/pom.xml
> reversi/android/...
> reversi/html/...
> reversi/ios/...

> The android, html, and ios directories can be ignored. PlayN is a
cross platform toolkit, but you will just work with the desktop Java
backend. The files in core/ are where your main game code lives.
Reversi.java is the skeleton game code, and resources/images/bg.png is
an image that's included as part of the skeleton game.
ReversiJava.java is the Java backend bootstrap file, you shouldn't
have to change it.

> The first thing you'll want to do is to disable the other platform
backends. Edit reversi/pom.xml and remove the following lines:

> <module>html</module>
> <module>android</module>
> <module>ios</module>

> Then you should be able to run your skeleton game:

> cd reversi
> mvn test

> You should see a rainbow background (but no peas when you click). If
you make it this far without a hitch, then you're off to the races.
Look at the code in playn-samples/showcase/core for examples of how to
do a bunch of standard stuff in PlayN (like creating images and
drawing into them, loading images form files, displaying images on
screen in layers, listening for mouse clicks, and so forth).

> You are also welcome to use Eclipse. Once you've followed the steps
above, you can import the reversi directory into Eclipse. If you
import using "Maven -> Existing Maven Projects" then Eclipse will
automatically set up the dependencies between the core and java
submodules and set up dependencies on the PlayN library. If you're not
familiar with Eclipse, I wouldn't recommend going this route. Just use
your favorite text editor and compile and run your game using "mvn
test".
