all: core analysis generation io evaluation util main

util:
	javac -d . src/nonograms/Util.java

main:
	javac -d . src/nonograms/Main.java

core:
	javac -d . src/nonograms/core/*.java

io:
	javac -d . src/nonograms/io/*.java

evaluation:
	javac -d . src/nonograms/evaluation/*.java

analysis:
	javac -d . src/nonograms/analysis/*.java

generation:
	javac -d . src/nonograms/generation/*.java

clean:
	rm -f nonograms/Main.class
	rm -f nonograms/core/*.class
	rm -f nonograms/generation/*.class
	rm -f nonograms/io/*.class
	rm -f nonograms/evaluation/*.class
	rm -f nonograms/analysis/*.class
