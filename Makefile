DB_NAME = src/cookie.db
CLASS_PATH = bin

compile:
	mingw32-make clean
	javac -cp .:src/* -d $(CLASS_PATH)/ src/app/*.java -d $(CLASS_PATH)/ src/prjct/*.java

run:
	mingw32-make compile
	java -cp .:src/sqlite-jdbc.jar $(CLASS_PATH).src.prjct.CrustyCookiesApplication

database:
	rm -f $(DB_NAME)
	winpty sqlite3 $(DB_NAME) < src/cookie.sql

clean:
	rm -f bin/*.class

now:
	java -cp .:src/sqlite-jdbc.jar bin.src.prjct.CrustyCookiesApplication
