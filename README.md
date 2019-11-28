# indymatic
Automatic Indy Enrolment System

## Clone and set up
Clone the repository by typing `git clone https://github.com/stnwtr/indymatic.git` in the terminal.

## The `priorities` file
Create a file which contains all the priorities for hour and day.
* `#` only at line beginning are for comments
* `t` takes an entry by teacher
* `r` takes an entry by room

### Construction
The priority file contains lines of priority ordered entries.
Each line has a few fields which are separated by colons (`:`).
The case of the entries does not matter.

A line is structured as follow:
```
day : hour : priority : type : id : subject : activity
```
* `day` is the day of the event (`Mo`, `Mi`, `Fr`)
* `hour` is the hour the indy lesson is in (`3`, `4`)
* `priority` determines the entry priority. `1` means highest priority, `2` is a lower priority and `3` is even lower (range from `1` to `n`)
* `type` Can either be `T` for teacher or `T` for room.
* `id` The id of the teacher or the room (`Ham`, `Er`, `L01`, `NTL2`)
* `subject` is the subject to do in this indy lesson
* `activity` is the activity a student is planning to do

### Examples
Here are a few example lines for the priority file.
```
Mo : 3 : 1 : r : l01 : am : differentialgleichungen
Mo : 3 : 2 : t : ham : insy : mysql ddl dml dql
```
[This](https://github.com/stnwtr/indymatic/blob/master/src/main/resources/priorities.cfg) is a sample file you could use as it is.

## Launch the program
To launch the automatic indy entry tool you first have to compile it.
You can do this using the command line.
With `./gradlew fatJar` or `./gradlew.bat fatJar` you can build the jar file including dependencies.
Once you got the jar file you can run the tool using the command line again.
Type `java -jar indymatic.jar <username> <password> <number> <config>` to run the program.
* `username` is the indy username
* `password` is the indy password
* `number` is the number of upcoming events you want to enrol into
* `config` is the according to priority file construction above created config file
