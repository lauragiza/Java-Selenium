# Java-Selenium
Instrukcja uruchomienia skryptu:

⦁	Przygotowanie środowiska:
⦁	Popranie i zainstalowanie java-jdk
https://www.oracle.com/technetwork/java/javase/downloads/index.html

⦁	Pobranie i zainstalowanie Eclipse IDE 
http://www.eclipse.org/downloads/
Przy instalacji należy wybrać Eclipse IDE for Java Developers

⦁	Pobranie projektu z Github i rozpakowanie go

⦁	Uruchomienie projektu:

⦁	File -> New -> Java Project -> Należy odznaczyć „Use default location” -> Browse - należy podać lokalizację rozpakowanego projektu pobranego z Git (wybrać folder Test) i kliknąć Finish

⦁	W pliku test.java w linii kodu
 System.setProperty("webdriver.gecko.driver", "C:\\Users\\laura.giza\\workspace\\Test\\geckodriver.exe");
po przecinku należy podać lokalizację do geckodriver.exe (znajduje się on w pobranym projekcie)

⦁	W pliku Log4j.properties w każdym teście należy podać lokalizację, gdzie chcemy aby były zapisywane logi, przykładowo w teście Registration należy zmienić ścieżkę po znaku= 
          
      log4j.appender.logger.File=C:\\Users\\laura.giza\\14testvod.txt
⦁	Należy kliknąć prawym przyciskiem myszy na Test -> Run As -> 2 JUnit Test





Uwagi:

⦁	W przypadku nie zapisywania się logów należy wybrać Run –> Run Configurations -> z lewej strony rozwinąć JUnit -> wybrać Test -> przejść do zakładki Classpath z prawej strony -> kliknąć na User Entries -> Nacisnąć przycisk z prawej strony Advanced… -> OK -> Rozwinąć kolejno Test -> src -> Test -> zaznaczyć Test -> OK -> Apply -> Run

⦁	W przypadku problemu z geckodriverem (skrypt może się wcale nie uruchamiać), należy w linię kodu:
System.setProperty("webdriver.gecko.driver", "path\\geckodriver.exe"); 
Zmienić na:
System.setProperty("webdriver.firefox.marionette", "path\\geckodriver.exe"); 
Może być to związane z wersję Firefox, skrypt był pisany dla wersji 60.

⦁	W rozytorium występuje tylko plik z roszerzeniem .java natomiast pliki tekstowe nie zostały dodane,aby wszytsko działało poprawnie nalezy je dodać
