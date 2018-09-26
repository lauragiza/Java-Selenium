package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
//import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringJoiner;

import javax.net.ssl.HttpsURLConnection;

//import java.util.logging.Logger;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.awt.AWTException;
import java.awt.List;
import java.awt.Robot;

//import org.apache.logging.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
//¿eby test zakupy vod by³ pozytywny zmieñ sciezke w pliku tekstowym do filmu

public class TEST {
private WebDriver driver;//stworzenie drivera
static ArrayList<String> errors = new ArrayList<String>();//stworzenie array listy


static Logger logger;//stworzenie loggera
//static Logger logerlive;//stworzenie drugiego logera s¹ potrzebne do zapisu informacji w logach

@Before
public void setUp() {   /*********PODAJ scie¿ke do gecko drivera**********/
   System.setProperty("webdriver.gecko.driver", "C:\\Users\\laura.giza\\workspace\\Test\\geckodriver.exe");  //uruchomienie gecko drivera(wystartowanie nowgo ona przegladarki) 
   
 driver = new FirefoxDriver();//stworzenie drivera
}

@Test//registration of an individual customer

public void registration() throws InterruptedException, IOException {
	 logger = Logger.getLogger("Registration");//logi
	 logger.debug("Start");
	 
	driver.get("https://ncplusgo.pl/");//wejscie na strone o podanym adresie
	 logger.debug("Wejœcie na ncplusgo");
	driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
    WebElement link = driver.findElement(By.linkText("zaloguj"));//znalezienie zaloguj
    link.click();//zlikniecie w zaloguj
    logger.debug("Rejstracja");
    driver.findElement(By.xpath("/html/body/main/div/div/div/form/h2/span/a")).click();//przycisk zarejestruj
    
    ///bez œcie¿ek podanie samych nazw
    URL url = getClass().getResource("DataRegistration.txt");
    File file = new File(url.getPath());
 
    ArrayList<String> errors;//stworzenie obiektu array listy
  //  errors= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataRegistration.txt");//œciezka do pliku z którego maj.a byæ zczytane dane do rejstracji
    errors = readFile(file.toString());
    
    logger.debug("Wpisywanie danych do rejstracji");
    driver.findElement(By.id("Login")).sendKeys(errors.get(0));//znalezienie login i wpisanie
    driver.findElement(By.id("LeftSubscriberNumber")).sendKeys(errors.get(1));//wpisanie numeru aboneta
    driver.findElement(By.id("RightSubscriberNumber")).sendKeys(errors.get(2));//wpianie numeru abonenta
    WebElement select = driver.findElement(By.id("IdentificationType"));//
    Select options = new Select(select);//select do listy rozwijaqnej
    options.selectByVisibleText("Klient indywidualny");// wybor opcji na stronie
    driver.findElement(By.id("Identification")).sendKeys(errors.get(3));//pesel
    driver.findElement(By.id("Password")).sendKeys(errors.get(4));//has³o
    driver.findElement(By.id("PasswordConfirm")).sendKeys(errors.get(5));//powtórzenie has³a
    driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div[2]/div[3]/button")).click();//zarejsetruj
   // searchinformation();
    logger.debug("Koniec wpisywania danych, rejstracja");
    searchinformation();
    Thread.sleep(3000);//czekaj 3 sekundy
  
    logger.debug("Szukanie b³edów");
///stworzenie petli do wy³apywania b³edów jêsli wystapia zapisuje je do pliku i wyœwietla na konsoli
    WebElement element = null;
    boolean flag = false;
        for (int i = 0; i< 10; i++) {// iterating 10 times , próbuej 10 razy znalezc b³ad na stronie
            try {
                element = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/p"));//œciezka do b³edu
                if (element != null) {//jesli element jest rózny od null
                    flag = true;
                    break;
                   
                } 
                else {  logger.debug("B³ad:");
                }
            } catch (Exception e) {}//wyjatek
        }
        
       // try{
        	if(flag){
        		 
      	  writeFile();//wywo³anie funkci do zapisania b³edu do pliku txt
      	  logger.debug("B³êdna rejstracja");
      	     System.out.println("Rejstracja przebieg³a niepomyœlnie");//wyœwietlenie w konsoli
      	                      
       }
              
      else {logger.debug("Brak b³êdów");
    	  System.out.println("Rejstracja dziêkujemy");//poprawna rejstracja
      logger.debug("Prawid³owa rejstracja");
      
      logger.debug("Wejœcie w maila podanego przy rejstracji");
      driver.get("https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");//wejœcie na mail
      logger.debug("Logowanie siê przez maila");
      	    	WebElement gmail = driver.findElement(By.xpath("//*[@id=\"identifierId\"]"));//znalezienoie pola z loginem
      	    	gmail.click() ;//kilkanie na pole login
      	    	
      	      URL url1 = getClass().getResource("DataGmail.txt");
      	    File file1 = new File(url1.getPath());
      	
      	    	 ArrayList<String> mail;
      	    	mail = readFile(file1.toString());
      	      // mail= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataGmail.txt");//sciezka do pliku z danymi do logowania na mail
      	    	gmail.sendKeys(mail.get(0));//login wpiasnie
      	    	
      	    	driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/content")).click();//dalej
      	    	WebElement pass;
      	 	Thread.sleep(2000);
      	    	pass=driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/content/section/div/content/div[1]/div/div[1]/div/div[1]/input"));//has³o znalezie miejsca do wpisania
      	   
      	    	
      	    	pass.sendKeys(mail.get(2));//has³o wpisanie
      	    	logger.debug("Koniec logowania");
      	    	Thread.sleep(1000);
      	    	logger.debug("Filtrowanie maili");
      	    	driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/content/span")).click();//dalej
      	    	
      	    	driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/header/div[2]/div[2]/div/form/div/input")).sendKeys("nc+");//filtruj
      	    	driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/header/div[2]/div[2]/div/form/button[4]")).click();//lupa
      	    	Thread.sleep(2000);
      	    	String zmienna;
      	    	logger.debug("Wejœcie w odpowiedniego maila ");
      	    	driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[2]/div/div[1]/div/div[2]/div[4]/div[1]/div/table/tbody/tr[1]/td[5]/div[2]/span[1]/span")).click();//ostatni wyslany mail
      	    	WebElement activ;
      	    	logger.debug("Klikniêcie link aktywacyjny");
      	    	activ = driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[2]/div/div[1]/div/div[3]/div/table/tr/td[1]/div[2]/div[2]/div/div[3]/div/div/div/div/div/div[1]/div[2]/div[3]/div[3]/div[1]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr/td[2]/table[1]/tbody/tr[5]/td/table/tbody/tr/td/p/font/font/a"));//link
      	    	zmienna=activ.getText();//pobranie tekstu
      	    	driver.get(zmienna);
      	    
      	    	
      	    	Thread.sleep(2000);
      	    	 }
   //    }
     //  catch(Exception e) {System.out.println("wyjatek");}
       
     
       
       	   //pierwsze logowanie
        	searchinformation();
        	logger.debug("Pierwsze logowanie");
        	newcountlogin();//wywo³anie mestody logowania z nowgo konta
        	logger.debug("Akceptacja regulaminu");
    	driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div/div/div[2]/div[1]/div/label/span")).click();//regulamin akceptacja
    	driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div/div/div[2]/div[2]/div/div/button")).click();   	//dalej
    
    	driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a")).click();//zamkniecie bledu
    	driver.findElement(By.xpath("/html/body/div[3]")).click();//tlo
    	Thread.sleep(2000);
    	driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div/div[1]/div[3]/button")).click();//przycisk
    	Thread.sleep(5000);
    	logger.debug("Logowanie powiod³o siê");
       	   //usuwanie konta z wsi
    	logger.debug("Usuwanie konta z WSI ");
    	
       	    	loginwsi();//logowanie do wsi
       	    	driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul[1]/li[1]/a")).click();//konta
       	 	driver.findElement(By.xpath("//*[@id=\"collapse-label\"]")).click();
       	
       	 logger.debug("Wybranie konta");
       	 WebElement filtr = driver.findElement(By.xpath("//*[@id=\"accountEmail\"]"));//email
     	filtr.click();
     	
     	 URL url2 = getClass().getResource("DataGmail.txt");
         File file2 = new File(url2.getPath());
     	ArrayList<String> wsi;
     	wsi = readFile(file2.toString());
        // wsi= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataGmail.txt");//plik z danymi do logowania
    
     	
     	filtr.sendKeys(wsi.get(0));//login pobrany z txt
     	Thread.sleep(3000);
     	driver.findElement(By.xpath("//*[@id=\"doFilter\"]")).click();//filtruj
     	driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[8]/a/span")).click();
     	  WebElement select2 = driver.findElement(By.id("status"));
     	    Select options2 = new Select(select2);
     	   logger.debug("Dezaktywacja konta");
     	    options2.selectByVisibleText("TERMINATED");// wybor opcji na stronie
     	   Thread.sleep(3000);
     	driver.findElement(By.xpath("//*[@id=\"editFormSubmitButton\"]")).click();  
     	Thread.sleep(3000);
     	
     	
    	
	    	
       	    	
       }
       


@Test//login 
public void login() throws InterruptedException, IOException {
	 logger = Logger.getLogger("Login");//logi
	 logger.debug("Start");
	  driver.get("https://ncplusgo.pl/");
	  logger.debug("Wejœcie na stronê ncplusgo");
	  driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
      loginmethod();
 
      logger.debug("Koniec testu");
      Thread.sleep(3000);   
     
}

@Test//kupowanie vod
public void buy() throws IOException, InterruptedException {
	 logger = Logger.getLogger("Buy");//logi
	 logger.debug("Start");
	 logger.debug("Wejœcie na stronê ncplusgo");
	 driver.get("https://ncplusgo.pl/");
	 //logowanie
	 driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
	 logger.debug("Start logowanie");
	 WebElement link;
	    link = driver.findElement(By.linkText("zaloguj"));
	    link.click();
	    Thread.sleep(3000);
	   
	    
	    URL url = getClass().getResource("dataBuy.txt");
        File file = new File(url.getPath());
	    ArrayList<String> logg;
	    //logg= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\dataBuy.txt");
		logg = readFile(file.toString());

	    WebElement input = driver.findElement(By.id("Email"));
	    input.sendKeys(logg.get(0));//login
	    input = driver.findElement(By.id("Password"));
	    input.sendKeys(logg.get(1));//haslo
	 
	    WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
	    button.click();
	    
	    logger.debug("Szukanie b³edów");
	    searcherror();

	    logger.debug("Koniec logowania");
	 
	  //driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
	  Thread.sleep(2000); 
	  logger.debug("Wejœcie VOD");
	  driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[7]")).click();//vod
	  Thread.sleep(5000);
	  logger.debug("Wejœcie Premiery");
	  driver.findElement(By.xpath("/html/body/main/div/div/div[2]/div/div[1]/div[2]/div[1]/ul/li[1]/div/div/a[2]/img")).click();//premiery vod
	  Thread.sleep(3000);
	  logger.debug("Wybranie filmu");
	  driver.findElement(By.xpath(logg.get(2))).click();//wybrany FILM SCIEZKA w pliku dataBuy wymaga zmiany zeby test mog³ odbyc sie za kazdym razem
	  Thread.sleep(3000);
	  if(driver.getPageSource().contains("zamów")) {
	  logger.debug("Zamówienie");
	  driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/ul/li/a/span[1]")).click();//zamów
	  Thread.sleep(3000);
	  logger.debug("Podanie has³a");
	  driver.findElement(By.xpath("//*[@id=\"Password\"]")).sendKeys(logg.get(1));//has³o
	  Thread.sleep(3000);
	  logger.debug("Potwierzdenie");
	  driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/form/div[2]/button")).click();//potwierdz
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/span")).click();//zamkniecie okna z potwierdzeniem zakupu
	  Thread.sleep(4000);
	  logger.debug("Odtworzenie materia³u");
	  driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[3]/ul/li/a/span[2]")).click();//odtwórz
	  Thread.sleep(7000);
	  //driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[4]/a/img")).click();//wyloguj
	  
	  searcherror();}
	  else {logger.debug("materia³ zosta³ ju¿ kupiony");}
	  
	 } 
	 


@Test // Sprawdzenie sekcji
public void sections() throws InterruptedException, IOException {
	logger = Logger.getLogger("Sections");//logi
	 logger.debug("Start testu");
    driver.get("https://ncplusgo.pl/");
    logger.debug("Wejœcie na stronê");
  driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();
    logger.debug("Logowanie");
    loginmethod();
    logger.debug("Logowanie koniec");
    //driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
    Thread.sleep(3000); 
    logger.debug("Wejœcie w teraz w TV");
    driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[2]")).click();//teraz w tv
    Thread.sleep(5000);
    logger.debug("Wejœcie w Tv na ¿yczenie");
    driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[4]")).click();//Tv na zyczenie 
    Thread.sleep(5000);
    logger.debug("Wejœcie VOD");
    driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[7]")).click();//vod
    Thread.sleep(5000);
    logger.debug("Wejœcie w Program TV");
    driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[3]")).click();//program tv
    Thread.sleep(5000);
    logger.debug("Wejœcie w moje konto");
    driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[3]/a/img")).click();//moje konto
    Thread.sleep(5000);
}


@Test//Sprawdzenie funkcjonalnoœci przypomni9enie o audytach
public void functionalitiesRemider() throws IOException, InterruptedException {
	logger = Logger.getLogger("Remider");//logi
	 logger.debug("Start testu");
	 driver.get("https://ncplusgo.pl/");
	 logger.debug("Wejœcie w ncplusgo");
	   driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();
	 logger.debug("Poczatek logowania");
	    loginmethod();
	    logger.debug("Koniec logowania");
	    //driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
	    logger.debug("Wejœcie w program Tv");
	    driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[3]")).click();//program tv
	    Thread.sleep(3000);
	    logger.debug("Wejœcie w canal+");
	    driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/div[2]/div[2]/div[1]/div/div[1]/a/img")).click();//canal+
	    Thread.sleep(5000);
	    
	    logger.debug("Wybranie filmu");
	    driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/section/ol/li[7]/ul/li[3]/ul/li[1]/div/h2/a")).click();//film

	    logger.debug("Ustawienie przypomnienia na 15 minut");
	    driver.findElement(By.linkText("przypomnij")).click();//zegar
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/ul/li/a/ul/li[4]")).click();//15 minut
	    Thread.sleep(3000);
	    
	    //usuwanie
	    logger.debug("Wejœcie w powiadomienia");
	    driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[2]/div/a/img")).click();//powiadomienia
	    driver.findElement(By.xpath("/html/body/main/div/div/div[2]/div[1]/ul/li/div/div[2]/h2/a")).click();//film
	    driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/a[2]")).click();//ustaw przypomnienie
	    Thread.sleep(3000);
	    logger.debug("Usuwanie przypomnienia");
	    driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/a[2]/ul/li[1]")).click();//bez przypomienia
	    driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[2]/div/a/img")).click();//powiadomienia
}


@Test
//Sprawdzenie wyszukiwarki
public void serach() throws IOException, InterruptedException {
	logger = Logger.getLogger("Search");//logi
	 logger.debug("Start testu");
	 driver.get("https://ncplusgo.pl/");
	 logger.debug("Wejœcie na strone ncplusgo");
	   driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();
	    loginmethod();
	    //driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();
	   
	    WebElement search = driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[1]/a/img"));//wyszukiwanie
        search.click();
    
        Thread.sleep(3000);
        
        URL url = getClass().getResource("SearchMovie.txt");
        File file = new File(url.getPath());
    	
    
        ArrayList<String> movie;
    	movie = readFile(file.toString());
        //movie= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\SearchMovie.txt");//sciezka do pliku do wyszukania wybranego filmu
        
        WebElement s = driver.findElement(By.id("search"));//szukaj pola do wszukiwania
        s.click();
        Thread.sleep(3000);
        logger.debug("Szukanie materia³u belfer");
        s.sendKeys(movie.get(0));//wybrany materia³
        driver.findElement(
                By.xpath("/html/body/header/div/div/div/div/nav/ul/li[1]/div/div/form/fieldset/div[1]/button")).click();//lupa
        logger.debug("Wybranie okreslonego odcinka");
       driver.findElement(By.linkText("BELFER 2 ODC. 1")).click();//w³aczenie odcinka
       Thread.sleep(4000);
      //  driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[3]/ul/li/a/span[1]/span[2]")).click();
       
	
       
}

@Test//zmiana has³a
public void changePasswordWSI() throws IOException, InterruptedException {
	logger = Logger.getLogger("ChangePassWSI");//logi
	 logger.debug("Start testu");
	
	loginwsi();
	 logger.debug("Wejœcie w Konta");
	driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul[1]/li[1]/a")).click();//konta
	Thread.sleep(3000);
	 logger.debug("Filtruj");
	driver.findElement(By.xpath("//*[@id=\"collapse-label\"]")).click();//filtruk
	 logger.debug("Podanie okreœlony emaila");
	WebElement filtr = driver.findElement(By.xpath("//*[@id=\"accountEmail\"]"));//email
	Thread.sleep(3000);
	filtr.click();
	
	 URL url = getClass().getResource("DataRegistration.txt");
     File file = new File(url.getPath());
 	
	ArrayList<String> wsi;
	wsi = readFile(file.toString());
	
   // wsi= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataRegistration.txt");
	filtr.sendKeys(wsi.get(0));//login pobrany z txt
	Thread.sleep(3000);
	driver.findElement(By.xpath("//*[@id=\"doFilter\"]")).click();//filtruj
	 logger.debug("Szczegó³y");
	driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr/td[8]/a/span")).click();//szczego³y konta
	Thread.sleep(3000);
	 logger.debug("Reset has³a");
	driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]/a")).click();//resetuj has³o
	Thread.sleep(3000);
	 logger.debug("Podanie nowego has³a");
	WebElement pass;
	pass=driver.findElement(By.xpath("//*[@id=\"password\"]")); //nowe haslo
	Thread.sleep(3000);
	pass.click();
	
	
	 URL url1 = getClass().getResource("NewPassToWSI.txt");
     File file1 = new File(url1.getPath());
 
	ArrayList<String> wsinewpass;
	wsinewpass = readFile(file1.toString());
    //wsinewpass= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\NewPassToWSI.txt");
    
    pass.clear();
    Thread.sleep(3000);
	pass.sendKeys(wsinewpass.get(0));//login pobrany z txt
	Thread.sleep(3000);
	 logger.debug("Zapisz");
	driver.findElement(By.xpath("//*[@id=\"resetPasswordFormSubmitButton\"]")).click();//zapisz
	
}

@Test//dodanie urzadzenia
public void addDevice() throws IOException, InterruptedException {
	logger = Logger.getLogger("AddDevice");//logi
	 logger.debug("Start testu");
	 driver.get("https://ncplusgo.pl/");
	 logger.debug("Wejœcie na strone ncplusgo");
	 
	  loginanothercount();//logowanie z konta
	  searcherror();
	  logger.debug("Koniec logowania");
	  logger.debug("Dodanie urzadzenia");
	  driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div/div/div[2]/div[3]/div/div[1]/div/button")).click();//dadaj urzadzenie
	  Thread.sleep(5000);
	  logger.debug("Wylogowanie");
	  driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[4]/a/img")).click();//wyloguj
	 // driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();
	  Thread.sleep(2000);
	  logger.debug("usuniêcie urz¹dzenia");
	  removeDevice();	//usuniecie urzadszenia zeby test mo¿na by³ow wywo³ac jeszcze raz  
	
}

@Test//zmiana has³a
public void chanePassword() throws IOException, InterruptedException {
	logger = Logger.getLogger("ChangePass");//logi
	 logger.debug("Start testu");
	driver.get("https://ncplusgo.pl/");
	 logger.debug("Wejœcie na stronê cnplusgo");
	 logger.debug("Rozpoczêcie logowania");
	newloginTochangepass();//fukcja do nowego konta
	searcherror();
	 logger.debug("koniec logowania");
	  Thread.sleep(3000);
	//driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
	
	  
		 URL url = getClass().getResource("DataNewcountchangepass.txt");
	     File file = new File(url.getPath());
	
	ArrayList<String> wsi;
	wsi = readFile(file.toString());
	   //wsi= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\");
	   Thread.sleep(3000);
	   logger.debug("Wejœcie w moje konto");
	driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[3]/a/img")).click();//moje konto
	 logger.debug("Zmiana has³a");
	driver.findElement(By.xpath("/html/body/main/div/div/div[1]/ul/li[2]/a")).click();//zmiana has³a
	 logger.debug("Podanie starego has³a");
	driver.findElement(By.xpath("//*[@id=\"OldPassword\"]")).sendKeys((wsi.get(1)));//stare has³o
	 logger.debug("podanie nowego has³a");
	driver.findElement(By.xpath("//*[@id=\"Password\"]")).sendKeys(wsi.get(1));//nowe has³o
	 logger.debug("potwierdzenie nowego has³a");
	driver.findElement(By.xpath("//*[@id=\"PasswordConfirm\"]")).sendKeys((wsi.get(1)));//potwierdz nowe has³o
	 logger.debug("Zapisanie");
	driver.findElement(By.xpath("/html/body/main/div/div/div[2]/form/fieldset/div/div[4]/button")).click();//zapisz
	Thread.sleep(3000);
	searchinformation();
	Thread.sleep(2000);
	 logger.debug("Wyloguj");
	  driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[4]/a/img")).click();//wyloguj
}

@Test
public void reminderpassword() throws InterruptedException, IOException {
	logger = Logger.getLogger("Remiderpass");//logi
	 logger.debug("Start testu");
        driver.get("https://ncplusgo.pl/");
        logger.debug("Wejœcie na strone ncplugo");
        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click(); //klikniêcie w przycisk "Rozumiem"
        logger.debug("Klikniêcie zaloguj");
        driver.findElement(By.linkText("zaloguj")).click();//klikniecie w zaloguj
        Thread.sleep(3000);  
        logger.debug("Przypomnij has³o");
        driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div/div[2]/p[2]/a")).click();//przypomnij has³o
        Thread.sleep(3000);
        
        URL url1 = getClass().getResource("DataToREminderPass.txt");
	     File file1 = new File(url1.getPath());

      
       
        ArrayList<String> reg; //u¿ywamy listy z danymi do rejestracji
        reg= readFile(file1.toString());
        //reg = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataToREminderPass.txt");
        logger.debug("Wpisanie maila do wys³ania linku aktywacyjego");
        driver.findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys(reg.get(0));//wpisanie maila
        Thread.sleep(3000);  
        logger.debug("Potwierdzam");
        driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div/div[2]/button")).click();//potwierdzam
        
        Thread.sleep(5000);  
        searchinformation();
        Thread.sleep(3000);  
        logger.debug("Wejœcie na gmail");
        //wejœcie w maila
        driver.get("https://www.gmail.com");
        Thread.sleep(3000);
        
        URL url = getClass().getResource("DataToREminderPass.txt");
	     File file = new File(url.getPath());

        ArrayList<String> loginGmail; //nowa lista
        loginGmail= readFile(file.toString());
       // loginGmail = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataToREminderPass.txt"); 
        logger.debug("Logowanie do maila");
        driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).sendKeys(loginGmail.get(0));//wpisanie login
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/content/span")).click();//dalej
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/content/section/div/content/div[1]/div/div[1]/div/div[1]/input")).click();
        driver.findElement(By.xpath(".//*[@id='password']/div[1]/div/div[1]/input")).sendKeys(loginGmail.get(1));//wpisanie has³o
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/content/span")).click();//zaloguj
        Thread.sleep(3000);
        logger.debug("Filtrowanie poczty");
        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/header/div[2]/div[2]/div/form/div/input")).sendKeys("nc+"); //filtrowanie poczty
        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/header/div[2]/div[2]/div/form/button[4]")).click(); //klikniecie w lupê
        Thread.sleep(3000);
        logger.debug("Wejœcie w maila");
        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[2]/div/div[1]/div/div[2]/div[4]/div[1]/div/table/tbody/tr[1]/td[5]/div[2]/span[1]")).click(); //klikniecie w odpowiedniego emaila
        Thread.sleep(5000);
        logger.debug("Wejœcie w link aktywacyjny");
        WebElement linkNC;
        linkNC = driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[2]/div/div[1]/div/div[3]/div/table/tr/td[1]/div[2]/div[2]/div/div[3]/div/div/div/div/div/div[1]/div[2]/div[3]/div[3]/div[1]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr/td[2]/table[1]/tbody/tr[5]/td/table/tbody/tr/td/p/font/font/a"));  //œcie¿ka do linku
        String valuelinkNC = linkNC.getText();//pobranie testu
        Thread.sleep(5000);
        driver.get(valuelinkNC);
        Thread.sleep(5000);
        logger.debug("Podanie nowego has³a");
        driver.findElement(By.xpath("//*[@id=\"Password\"]")).sendKeys(loginGmail.get(1));//nowe has³o
        driver.findElement(By.xpath("//*[@id=\"PasswordConfirm\"]")).sendKeys(loginGmail.get(1));//nowe has³o
        logger.debug("Potwierdzam");
        driver.findElement(By.xpath("/html/body/main/div/div/form/fieldset/div/div[3]/button")).click();//potwierdzam
        searchinformation();
        Thread.sleep(5000);

        logger.debug("Koniec testu");
    }



@Test//wyszukanie filmu

public void SearchMovie15() throws InterruptedException, IOException {
	logger = Logger.getLogger("Movie");//logi
	 logger.debug("Start testu");
   driver.get("https://ncplusgo.pl/");
   logger.debug("Wejœcie na stronê");
   driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click(); // Wybranie po czym driver ma szukac elementu     
   logger.debug("Logowanie");
   loginmethod();
   Thread.sleep(3000);
   logger.debug("Koniec logowania");
   logger.debug("Wejœcie w program Tv");
   driver.findElement(By.xpath(".//*[@id='top']/div/div/div/div/div/nav/a[3]")).click(); //wejœcie w sekcjê "Program TV"    
   Thread.sleep(3000);
   logger.debug("Ustawienie widoku klasycznego");
   driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[1]/div/ul/li[2]/label")).click(); //klikniêcie w widok klasyczny
   
   //sekcja canal+
   logger.debug("Sekcja canal+");
   driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[2]/div/div/ul/li[1]/label")).click(); //klikniêcie w sekcjê canal+             
   Thread.sleep(3000);
   WebElement dropDownButton1 = driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]")); //rozwiniêcie listy
   dropDownButton1.click();
   logger.debug("wybranie programu dla jutra");
   WebElement options1 = driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]/ul/li[3]/a")); //klikniêcie jutro
   options1.click();
   Thread.sleep(3000);
   logger.debug("Sprawdzenie czy program wystepuje");
   //sprawdza czy wystêpuje program
   WebElement element = null;
   try {//sprobowanie znaleznienia metria³u
       element = driver.findElement(By.partialLinkText("Baba Jaga"));//znalezie filmu
       logger.debug("Znalezienie filmu jutro");
       System.out.println("Program EPG jest dostêpny dla sekcji CANAL+ jutro");
    Thread.sleep(3000);
       }                           
    catch (Exception e) { logger.debug("Materia³ nie jest jutro dostêpny na na canal+");
    	System.out.println("Nie ma dostepnego na Canal+ jutro");
    	}

   Thread.sleep(3000);
   WebElement dropDownButton2 = driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]")); //rozwiniêcie listy
   dropDownButton2.click();
   logger.debug("Szukanie filmu dla pojutrza");
   WebElement options2 = driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/div/div/div[1]/ul/li[4]/a")); //klikniêcie w pojutrze
   options2.click();
   Thread.sleep(3000);
   
 //sprawdza czy wystêpuje program
   try {
       element = driver.findElement(By.partialLinkText("EPG"));
       logger.debug("Znalezienie filmu pojurze");
       System.out.println("Program EPG jest dostêpny dla sekcji CANAL+ pojutrze");
    Thread.sleep(3000);
       }                           
    catch (Exception e) {
    	System.out.println("Nie ma dostepnego na Canal+ pojutrze");
    	 logger.debug("nie ma dostepnego materia³u pojutrze");
    }
   
   driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[2]/div/div/ul/li[1]/label")).click(); //klikniêcie w sekcjê canal+ aby j¹ dezaktywowaæ
   Thread.sleep(5000);
   logger.debug("Wybranie sekcji sportowy");
   //sekcja sportowy
   driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[2]/div/div/ul/li[17]/label")).click(); //klikniêcie w sekcjê sportowy
   Thread.sleep(3000);
   WebElement dropDownButton3 = driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]")); //rozwiniêcie listy
   dropDownButton3.click();
   logger.debug("Wybranie jutra");
   WebElement options3 = driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]/ul/li[3]/a")); //klikniêcie jutro
   options3.click();
   Thread.sleep(5000);
   
   //sprawdza czy wystêpuje program
   try {
       element = driver.findElement(By.partialLinkText("EPG"));//szukanie filmu o padnym tytule
       logger.debug("Znalezienie filmu jutro");
       System.out.println("Program EPG jest dostêpny dla sekcji sportowy jutro");
    Thread.sleep(3000);
       }                           
    catch (Exception e) {
    	System.out.println("Nie ma dostepnego na sportowy jutro");
    	 logger.debug("Materia³ nie jest dostêpny w sekcji sportowy jutro");
    }

   Thread.sleep(3000);
   WebElement dropDownButton4 = driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]")); //rozwiniêcie listy
   dropDownButton4.click();
   logger.debug("Wybranie pojutrza");
   WebElement options4 = driver.findElement(By.xpath("/html/body/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]/ul/li[4]/a")); //klikniêcie w pojutrze
   options4.click();
   Thread.sleep(3000);
   
 //sprawdza czy wystêpuje program
   try {
       element = driver.findElement(By.partialLinkText("BABA JAGA"));//sparwdzenie filmu o podanym tytule
       System.out.println("Program EPG jest dostêpny dla sekcji sportowy pojutrze");
       logger.debug("Znalezienie filmu pojutrze");
    Thread.sleep(3000);
       }                           
    catch (Exception e) {
    	System.out.println("Nie ma dostepnego na sportowy pojutrze");
    	 logger.debug("Materia³ nie jest dostêpny w sekcji sportowy pojutrze");
    }
   logger.debug("Koniec testu");
}



@Test//live
public void playCanalLive() throws IOException, InterruptedException {
	logger = Logger.getLogger("TestplayCanalLive");//loggery potzebne do zapisu w logach
	 logger.debug("Start, przejscie do ncplugo");
	 
	driver.get("https://ncplusgo.pl/");
	 logger.debug(" ncplugo");
    driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
    
    
    Thread.sleep(6000);
    logger.debug("poczatek logowania");//do logow
    loginmethod();//logowanie
    logger.debug("koniec logowania");//do logow
    Thread.sleep(3000);
    
    driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[3]")).click();//program tv
    Thread.sleep(2000);
    driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[1]/div/ul/li[2]/label")).click();//klastycznie
    Thread.sleep(3000);
    logger.debug("start wyszukiwania po œcie¿kach");//do logow
    searchByPathlive();//trzeba klikac w tytul w kwadracie nie na ca³y kwadrat i pobierac takiego xpath
    Thread.sleep(3000);
}
    //trzeba sprobowac w³¹czyc  kazdy kwadracik w³¹czyc na stronie poniewa¿ live ca³y czas siê zmienia, sprawdzamy 8 kwadracików na których mog¹ byæ dostepne filmy
    
   

@Test
public void PlayVod() throws IOException, InterruptedException {

	 
	 logger = Logger.getLogger("TestPlayVod");//logi
	 logger.debug("Start");
	 
	 
	driver.get("https://ncplusgo.pl/");
	logger.debug("Navigate to ncplugo");//logi
driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
logger.debug("Click logowanie");

loginmethod();//metoda logowania
logger.debug("Koniec logowania");

Thread.sleep(2000); 
logger.debug("Wejœcie w Vod");
driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[7]")).click();//vod
Thread.sleep(2000); 
logger.debug("Wybranie filmu");
driver.findElement(By.xpath("/html/body/main/div/div/div[2]/div/div[2]/div[2]/div[1]/ul/li[2]/div/div[2]/h2/a")).click();//wybranie filmu

Thread.sleep(5000);
logger.debug("odtwarzanie materia³u");
driver.findElement(By.linkText("odtwórz")).click();//odtworz
String g = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/h2")).getText();
Thread.sleep(9000);
logger.debug("szukanie b³edów 1 film "+g);
searcherrorvod();//funkcja do szukania b³edów
logger.debug("koniec szukania b³edów 1 filmu");


driver.navigate().back();//powrot do poprzedniej strony
   	
    	
Thread.sleep(2000);
driver.findElement(By.xpath("/html/body/main/div/div/div[2]/div/div[2]/div[2]/div[1]/ul/li[3]/div/div[2]/h2/a")).click();//kolejny film
Thread.sleep(5000);
logger.debug("odtwarzanie materia³u");
driver.findElement(By.linkText("odtwórz")).click();//odtworz
String d = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/h2")).getText();
Thread.sleep(9000);
logger.debug("szukanie b³edów 2 film "+d);
searcherrorvod();
logger.debug("koniec szukania b³edów 2 filmu");
driver.navigate().back();//powrot

Thread.sleep(2000);
driver.findElement(By.xpath("/html/body/main/div/div/div[2]/div/div[2]/div[2]/div[1]/ul/li[4]/div/div[2]/h2/a")).click();//kolejny film
Thread.sleep(5000);
logger.debug("odtwarzanie materia³u");
driver.findElement(By.linkText("odtwórz")).click();//odtworz
String h = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/h2")).getText();
Thread.sleep(9000);
logger.debug("szukanie b³edów 3 film "+h);
searcherrorvod();
logger.debug("koniec szukania b³edów 3 filmu");
driver.navigate().back();//powrot

Thread.sleep(2000);
driver.findElement(By.xpath("/html/body/main/div/div/div[2]/div/div[2]/div[2]/div[1]/ul/li[5]/div/div[2]/h2/a")).click();//kolejny film
Thread.sleep(5000);
logger.debug("odtwarzanie materia³u");
driver.findElement(By.linkText("odtwórz")).click();//odtworz
String j = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/h2")).getText();
Thread.sleep(9000);
logger.debug("szukanie b³edów 4 film "+j);
searcherrorvod();
logger.debug("koniec szukania b³edów 4 filmu");
driver.navigate().back();//cofnij
Thread.sleep(2000);
}


@Test
public void actionPlayer() throws IOException, InterruptedException {
	 logger = Logger.getLogger("Testactionplayer");//logi
	 logger.debug("Start");
	driver.get("https://ncplusgo.pl/");//wejœcie na stronê ncplugGo
	 logger.debug("Strona ncplugo");
	driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//klikniecie rozumiem
	Thread.sleep(5000);
	logger.debug("Poczatek logowania");
	loginmethod();//metoda do logowania
	logger.debug("Koniec logowania");
	Thread.sleep(5000);//CZEKANIE NA ZA£ADOWANIE STRONY
	logger.debug("Wejœcie w program Tv");
	 driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[3]")).click();//program tv
	    Thread.sleep(2000);
	    logger.debug("Ustawienie widoku klsycznego");
	    driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[1]/div/ul/li[2]/label")).click();//klastycznie widok
	    Thread.sleep(3000);
	
	searchByPathplayer();
	
}

@Test

// PC05 Wymiana urz¹dzenia


public void PC05changeDevice() throws InterruptedException, IOException, URISyntaxException {
           
	logger = Logger.getLogger("ChangeDevice");//logi
	 logger.debug("Start");
//Tu jest przesy³any POST do formularza odpowiadaj¹cego za dodanie nowego urz¹dzenia, wykonuje siê 3 razy aby dodaæ 3 nowe urz¹dzenia
//pêtla podaj¹ca ró¿ne wartoœci device key, wszystkie s¹ zafa³szowane (inne ni¿ naszego urz¹dzenia)
	 logger.debug("Wys³anie POST 4 razy z róznym device key");     
    for (int i=5; i<9; i++) {
                   String url = "https://ncplusgo.pl/konto/logowanie?returnUrl=%2F"; //adres do logowania
                   URL obj = new URL(url);
                   HttpsURLConnection con = (HttpsURLConnection) obj.openConnection(); //otwieramy po³¹czenie

                   con.setRequestMethod("POST"); 
                   con.setDoOutput(true);
                   
                   
                   URL url1 = getClass().getResource("devices.txt");
          	     File file = new File(url1.getPath());

                   ArrayList<String> devices; //u¿ywamy listy z danymi do rejestracji
                   devices= readFile(file.toString());
              // devices = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\Test\\devices.txt");

                   //Wype³nienie formularza
                   Map<String,Object> arguments2 = new HashMap<>();
                   arguments2.put("ReturnUrl", devices.get(0));
                   arguments2.put("DeviceKey", devices.get(i));
                   arguments2.put("Email", devices.get(1));
                   arguments2.put("Password", devices.get(2));
                   arguments2.put("Devices.DeviceName", devices.get(4));
                   arguments2.put("ManageDevices", devices.get(3));                

                   //kodowanie
                   StringJoiner sj = new StringJoiner("&");
                   for(Entry<String, Object> entry : arguments2.entrySet())
                       sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
                            + URLEncoder.encode((String) entry.getValue(), "UTF-8"));
                   byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
                   int length = out.length;

                   con.setFixedLengthStreamingMode(length);
                   con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                   con.connect();
                   try(OutputStream os = con.getOutputStream()) {
                       os.write(out);

           }
           }
   
           //Teraz dezaktywujemy sesje na WSI (poniewa¿ poprzednie POSTy nam aktywowa³y dwie sesje), jeœli tego byœmy nie zrobili pojawi³ by siê b³¹d przy kolejnym zalogowaniu
           deleteSession();
           
           //Nastêpnie wykonujemy logowanie z poziomu uzytkownika z tego samego konta, tutaj device key jest prawdzimy z naszego urz¹dzenia
           
           driver.get("https://ncplusgo.pl/");
           logger.debug("Strona ncplusgo");
       driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click(); //klikniêcie w przycisk "Rozumiem"
       logger.debug("Logowanie z poziomu uzytkownika");
       driver.findElement(By.linkText("zaloguj")).click();
       
       
       URL url1 = getClass().getResource("devices.txt");
	     File file = new File(url1.getPath());

       ArrayList<String> devices; //nowa lista
       devices= readFile(file.toString());
       //devices = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\Test\\devices.txt"); 
       
       driver.findElement(By.id("Email")).sendKeys(devices.get(1));
       driver.findElement(By.id("Password")).sendKeys(devices.get(2));
       driver.findElement(By.xpath("//button[@type='submit']")).click(); //klikniêcie w przycisk zaloguj 
       logger.debug("Koniec logowania");
       Thread.sleep(2000); 
       logger.debug("Dodawanie urz¹dzenia");
       plusDevice(); //dodanie urz¹dzenia
       Thread.sleep(2000);
       logger.debug("Wejœcie na WSi ");
       driver.get("http://ncplus-wsi-sec.redcdn.pl:10081/wsi/");
       driver.findElement(By.xpath("html/body/div[1]/div/div[2]/ul[1]/li[1]/a")).click(); //wejœcie w zak³adkê konta
       driver.findElement(By.xpath(".//*[@id='collapse-label']")).click(); //przycisk filtracji
       driver.findElement(By.xpath("//*[@id=\"accountEmail\"]")).sendKeys(devices.get(1)); //wpisanie emaila
       driver.findElement(By.xpath("//*[@id=\"doFilter\"]")).click();
       Thread.sleep(2000);
       driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[8]/a/span")).click(); //klikniêcie w aktywne konto
       Thread.sleep(2000);
       driver.findElement(By.xpath("/html/body/div[3]/ul/li[4]/a")).click();
       logger.debug("Dezaktywacja aktywnych urz¹dzeñ: 1");
       //wybranie przycisku z listy rozwijanej
       driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[8]/span")).click(); //klikamy w pierwsze urz¹dzenie
       Thread.sleep(2000);
       driver.findElement(By.id("status"));
       driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[2]/select/option[2]")).click(); //dezaktywujemy
       driver.findElement(By.xpath("//*[@id=\"editFormSubmitButton\"]")).click();
       Thread.sleep(2000);
       logger.debug("Dezaktywacja aktywnych urz¹dzeñ: 2");
       driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[2]/td[8]/span")).click(); //klikamy w drugie urz¹dzenie
       Thread.sleep(2000);
       driver.findElement(By.id("status"));
       driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[2]/select/option[2]")).click(); //dezaktywujemy
       driver.findElement(By.xpath("//*[@id=\"editFormSubmitButton\"]")).click();
       Thread.sleep(2000);
       logger.debug("Dezaktywacja aktywnych urz¹dzeñ: 3");
       driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[3]/td[8]/span")).click(); //klikamy w trzecie urz¹dzenie
       Thread.sleep(2000);
       driver.findElement(By.id("status"));
       driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[2]/select/option[2]")).click(); //dezaktywujemy
       driver.findElement(By.xpath("//*[@id=\"editFormSubmitButton\"]")).click();
       Thread.sleep(2000);
       logger.debug("Dezaktywacja aktywnych urz¹dzeñ: 4");
       driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[4]/td[8]/span")).click(); //klikamy w czwarte urz¹dzenie
       Thread.sleep(2000);
       driver.findElement(By.id("status"));
       driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[2]/select/option[2]")).click(); //dezaktywujemy
       driver.findElement(By.xpath("//*[@id=\"editFormSubmitButton\"]")).click();
       Thread.sleep(2000);
       Thread.sleep(5000);
       
       
    }  

@Test
public void checkBlack() throws InterruptedException, IOException {
	
	 logger = Logger.getLogger("Testcheckblack");//logi
	 logger.debug("Start");
driver.get("https://ncplusgo.pl/");//wejœcie na stronê ncplugGo
logger.debug("Strona ncplusgo");
        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//klikniecie rozumiem
        Thread.sleep(5000);
   	 logger.debug("Logowanie start");
        loginmethod();//metoda do logowania
        logger.debug("koniec logowania");
        Thread.sleep(3000);
        logger.debug("Wejœcie w program Tv");
        driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[3]")).click();//program tv
        Thread.sleep(2000);
        logger.debug("Ustawienie klasyczne");
        driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[1]/div/ul/li[2]/label")).click();//klastycznie
        Thread.sleep(3000);
        searchByPathblack();
        
        Thread.sleep(3000);
        logger.debug("koniec testu");
}

@Test
public void searchAlotofLive() throws InterruptedException, IOException {
	logger = Logger.getLogger("AlotOfLive");//logi
	 logger.debug("Start");
	 driver.get("https://ncplusgo.pl/");//wejœcie na stronê ncplugGo
	 logger.debug("Strona ncplusgo");
	  driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//klikniecie rozumiem
      Thread.sleep(5000);
 	 logger.debug("Logowanie start");
 	loginmethod();//metoda do logowania
    logger.debug("koniec logowania");
    Thread.sleep(3000);
    logger.debug("Wejœcie w Teraz w TV");
    driver.findElement(By.xpath("/html/body/header/div/div/div/div/div/nav/a[2]")).click();//teraz w tv
    Thread.sleep(3000);
    logger.debug("Sczytywanie z listy live, które chcemy sprawdziæ");
    
    URL url1 = getClass().getResource("PathToalotofLive.txt");
    File file = new File(url1.getPath());

 
 
    ArrayList<String> film; //u¿ywamy listy z œcie¿kami do live
    film= readFile(file.toString());
    //film= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\PathToalotofLive.txt");
   
    for (int i=0; i<5; i++) {
    	
    	 driver.findElement(By.xpath(film.get(i))).click();
    	  Thread.sleep(6000);
    	 searcherror();
  Thread.sleep(5000);
   String s=driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div/div[2]/h2/a")).getText();
   logger.debug("odtworzenie materia³u "+s);
   
    }
    logger.debug("Koniec testu");
}

//PC17 test dodatkowy - VODsearch
@Test
public void PC18VODsearch() throws IOException, InterruptedException {

logger = Logger.getLogger("VODsearch");
logger.info("Start testu");
    driver.get("https://ncplusgo.pl/");
    logger.info("Za³adowanie strony www.ncplusgo.pl");
            driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click(); // klikniêcie w przycisk "Rozumiem"
            logger.info("Logowanie");
            loginmethod();
            logger.info("Koniec logowania");
            Thread.sleep(2000);
            
            URL url1 = getClass().getResource("SearchMovie.txt");
            File file = new File(url1.getPath());
            
            ArrayList<String> movieSearch; // nowa lista
            movieSearch= readFile(file.toString());
           // movieSearch = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\Test\\SearchMovie.txt");
            
            logger.info("Rozpoczêcie sprawdzania wyszukiwarki dla VOD");
            for (int i = 1; i<6; i++) {
                    
                    driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[1]/a/img")).click();// wyszukiwanie
                    Thread.sleep(3000);
                    driver.findElement(By.xpath("//*[@id=\"search\"]")).click();//klikniecie w pole wyszukiwania
                    driver.findElement(By.id("search")).sendKeys(movieSearch.get(i));// wybrany materia³   
                    Thread.sleep(3000);
                    driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[1]/div/div/form/fieldset/div[1]/button")).click(); // lupa
                    logger.info("Wyszukiwanie: " + movieSearch.get(i));
                    Thread.sleep(3000);
                    if (driver.getPageSource().contains(movieSearch.get(i))) {
                    logger.info("Znaleziono wyniki dla: " + movieSearch.get(i));
                    driver.findElement(By.xpath("/html/body/main/div/div[2]/ul/li[1]/div/div[2]")).click(); //klikniêcie w pierwszy materia³
                    Thread.sleep(3000);
                    try {        
            WebElement ss = null;
            WebElement element = null;
            element = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/h2"));
            String s = element.getText();
        ss = driver.findElement(By.linkText("odtwórz"));
        
        if (ss != null) {
        ss.click();
        logger.info("Odtworzenie filmu: " + s);
        Thread.sleep(8000);// zmien na 2 minuty               
            try {
            	
            searcherror();                    
            } catch (Exception e) {}
        }} catch (Exception e) {logger.info("Kolekcja niedostêpna w Twoim abonamencie");
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/a")).click();}
                    
            }
            }
            logger.info("Zakoñczenie sprawdzania wyszukiwarki dla VOD");
            driver.findElement(By.xpath("/html/body/header/div/div/div/div/nav/ul/li[4]/a/img")).click();// wyloguj
            logger.info("Wylogowanie");
            logger.info("Koniec testu");
}





/*************************METODY*************************/

public void searchByPathblack() throws IOException {
	 URL url1 = getClass().getResource("PathLive.txt");
     File file = new File(url1.getPath());
     
        ArrayList<String> filmPath;//u¿ywamy listy z œcie¿kami do kwadratów w którcy znajduj¹ siê filmy
        filmPath= readFile(file.toString());
   // filmPath = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\PathLive.txt");
    
    for (int i=0; i<8; i++) {
                try {
                        WebElement a = null;
                        logger.debug("Szukanie matreia³u"+i);
                        a = driver.findElement(By.xpath(filmPath.get(i)));//szukanie filmu pos ciezkach zapisanych w pliku
                        if (a != null) {
                                a.click();
                                Thread.sleep(2000);
                                
                                try {
                                	String g = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/h2")).getText();
                                        WebElement ss = null;
                                        logger.debug("Odtwarzanie materia³u"+g);
                                        ss = driver.findElement(By.linkText("odtwórz"));
                                        if (ss != null) {
                                                ss.click();
                                                Thread.sleep(6000);// zmien na 2 minuty
                                                screen();
                                      
                                                try {
                                                        searcherrorlive();//do sprawdzenie czy wsystapi³ b³ad
                                                 
                                                }
 
                                                catch (Exception e) {
                                                }
                                        }
                                } catch (Exception e) {
                                }
                        }
                        logger.debug("Koniec odtwarzania");
                        driver.navigate().back();//cofnij
                        Thread.sleep(5000);
                        driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[1]/div/ul/li[2]/label")).click();// klastycznie
                        Thread.sleep(5000);
                } catch (Exception e) {logger.debug("nie ma obecnie tego materia³u w tym miejscu");
               
                }
                
                
}

}
public void screen() throws IOException {

     WebElement c;
    c= driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/h2"));
     String s=c.getText();//pobranie tytu³u filmu
   
      
     logger.debug("Robienie screena");
       //robienie scrrena
                 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                 
               /************************************* ŒCIE¯KA do robienia screen*************/
                  FileUtils.copyFile(scrFile, new File("..\\Test\\src\\test\\Screen\\screenshot"+s+".png"));//w tym miejscu zapisze sie scrren  
                // FileUtils.copyFile(scrFile, new File("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\screenshot"+s+".png"));//w tym miejscu zapisze sie scrren
               
                 System.out.println("Screenshot taken");
           
}




public static ArrayList<String> readFile(String filePath) throws IOException {//metoda do zczytywania danych z pilku
	String s;
	ArrayList<String> result = new ArrayList<String>();
FileReader fr = new FileReader(filePath);
Scanner plik =new Scanner(fr);

	while(plik.hasNextLine()) {//prawda dopki jest nastepny element
		s=plik.nextLine();
		result.add(s);
		
	}
	plik.close();
	return result;
	
}
//do zapisania b³edu do pliku i logu
private  void writeFile2() throws IOException {//wpisywanie b³edów do pliku
	String filename="fileWithErrors12.txt";//zapisuje sie w pliku o panej nazwie
	FileWriter fileWrite = new FileWriter(filename);
	
	 String errors=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/p")).getText();//pobranie textu
	
	
	fileWrite.write(errors.toString());

	fileWrite.close();
	System.out.println(errors);//wyswietlenie b³edu
	
	logger.debug("Error is");
	logger.error(errors);
}
////do zapisania b³edu do pliku
private  void writeFile() throws IOException {//wpisywanie b³edów do pliku
	String filename="fileWithErrors12.txt";
	FileWriter fileWrite = new FileWriter(filename);
	
	 String errors=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/p")).getText();
	
	
	fileWrite.write(errors.toString());

	fileWrite.close();
	System.out.println(errors);
	

	logger.error(errors);
	//logerlive.error(errors);
}

public void loginmethod() throws IOException {
	//driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();
	     WebElement link;
    link = driver.findElement(By.linkText("zaloguj"));
    link.click();
    
    URL url1 = getClass().getResource("DataToLogin");
    File file = new File(url1.getPath());
     
    ArrayList<String> logg;
   logg= readFile(file.toString());
   // logg= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataToLogin");


    WebElement input = driver.findElement(By.id("Email"));
    input.sendKeys(logg.get(0));//login
    input = driver.findElement(By.id("Password"));
    input.sendKeys(logg.get(1));//haslo
 
    WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
    button.click();
    logger.debug("Szukanie b³edów przy logowaniu");
    WebElement element = null;
    boolean flag = false;
        for (int i = 0; i< 10; i++) {// iterating 10 times 
           try {	
                element = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/p"));//sciezka do b³edu
                if (element != null) {
                    flag = true;
                    break;
                   
                } 
                else {}
            } catch (Exception e) {}
       }
        
 try{
	 if(flag){ 
		 logger.debug("Wystapi³ b³¹d");
	  writeFile();//zapis do pliku
	     System.out.println("Logowanie nie powiod³o siê");
	                      
 }
        
else { logger.debug("brak b³edu");
	    	 
	    	 }
 }
 catch(Exception e) {System.out.println("Logowanie poprawne");
 logger.debug("brak b³edu");}
 } 
    	
    //metoda do logowania do wsi
public void loginwsi() throws IOException {
	logger.debug("WejœcieWSI ");
	 driver.get("http://ncplus-wsi-sec.redcdn.pl:10081/wsi/");
	 logger.debug("Logowanie WSI ");
	   
	 
	  URL url1 = getClass().getResource("DataToWSI.txt");
	    File file = new File(url1.getPath());
	     
	  
	   
    ArrayList<String> wsi;
wsi= readFile(file.toString());
   // wsi= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataToWSI.txt");//sciezka do pliku z danym i do logowania


    WebElement input = driver.findElement(By.xpath("/html/body/div/div[2]/form/input[1]"));//login
    input.click();
    input.sendKeys(wsi.get(0));
    input = driver.findElement(By.xpath("/html/body/div/div[2]/form/input[2]"));//has³o
    input.click();
    input.sendKeys(wsi.get(1));
    driver.findElement(By.xpath("/html/body/div/div[2]/form/button")).click();//zaloguj
    logger.debug("Koniec logowania ");
}
//metoda do logowania
public void newcountlogin() throws IOException {//do pierwszego logowania 
    WebElement link;
link = driver.findElement(By.linkText("zaloguj"));
link.click();

URL url1 = getClass().getResource("DataRegistration.txt");
File file = new File(url1.getPath());
 
ArrayList<String> logg;
logg= readFile(file.toString());
//logg= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataRegistration.txt");//plik z danymi do logowania


WebElement input = driver.findElement(By.id("Email"));
input.sendKeys(logg.get(0));//login
input = driver.findElement(By.id("Password"));
input.sendKeys(logg.get(4));//has³o

WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
button.click();
//sprawdzanie b³edów
WebElement element = null;
boolean flag = false;
   for (int i = 0; i< 10; i++) {// iterating 10 times 
      try {
           element = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/p"));
           if (element != null) {
               flag = true;
               break;
           } 
           else {}
       } catch (Exception e) {}
  }
   
try{
if(flag){ 
   
 writeFile();
    System.out.println("Logowanie nie powiod³o siê");
                     
}
   
else {
   	 
   	 }
}
catch(Exception e) {System.out.println("Logowanie poprawne");}
} 
//usuwanie urzadzenia z wsi
	public void removeDevice() throws IOException, InterruptedException {
		loginwsi();
		  driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul[1]/li[1]/a")).click();//konta
	 	 	driver.findElement(By.xpath("//*[@id=\"collapse-label\"]")).click();
	 	 WebElement filtr = driver.findElement(By.xpath("//*[@id=\"accountEmail\"]"));//email
		filtr.click();
		
		URL url1 = getClass().getResource("DataToChangeDevice.txt");
		File file = new File(url1.getPath());
		 
		
		
		ArrayList<String> wsi;
		wsi= readFile(file.toString());
	   //wsi= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataToChangeDevice.txt");
		filtr.sendKeys(wsi.get(0));//login pobrany z txt
		 logger.debug("Filtruj wed³ug emaila");
		driver.findElement(By.xpath("//*[@id=\"doFilter\"]")).click();//filtruj
		
		driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[8]/a/span")).click();//wybor
		 logger.debug("Wejscie w urzadzenia");
		driver.findElement(By.xpath("/html/body/div[3]/ul/li[4]/a")).click();//urzadzenia
		 logger.debug("Szczegó³y");
		driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[8]/span")).click();//szczegoly
	
		 logger.debug("Usuñ urz¹dzenie");
		 driver.findElement(By.id("status"));
		
		driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[2]/select/option[2]")).click();
		 logger.debug("Zapisz");
 	
		driver.findElement(By.xpath("//*[@id=\"editFormSubmitButton\"]")).click();  //zapisz
 	
		
	}
	
	//metoda do logowania
public void loginanothercount() throws IOException {
	 logger.debug("Logowanie");
	  WebElement link;
	    link = driver.findElement(By.linkText("zaloguj"));
	    link.click();
	    driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();
	    
	    
	    URL url1 = getClass().getResource("DataToChangeDevice.txt");
		File file = new File(url1.getPath());
		 
		
		
	    ArrayList<String> logg;
	   logg= readFile(file.toString());
	    //logg= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataToChangeDevice.txt");


	    WebElement input = driver.findElement(By.id("Email"));
	    input.sendKeys(logg.get(0));
	    input = driver.findElement(By.id("Password"));
	    input.sendKeys(logg.get(1));
	 
	    WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
	    button.click();
	   
}
//metoda do logowani na nowe konto do zmiany has³a
public void newloginTochangepass() throws IOException {
	WebElement link;
    link = driver.findElement(By.linkText("zaloguj"));
    link.click();
    driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a[2]")).click();//rozumiem
    
    
    URL url1 = getClass().getResource("DataNewcountchangepass.txt");
  		File file = new File(url1.getPath());	 
  	 
    ArrayList<String> logg;
    logg= readFile(file.toString());
   // logg= readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\DataNewcountchangepass.txt");


    WebElement input = driver.findElement(By.id("Email"));//login
    input.sendKeys(logg.get(0));//uzupe³nienie pola z loginem
    input = driver.findElement(By.id("Password"));//haslo
    input.sendKeys(logg.get(1));///uzupe³nienie pola z has³em
 
    WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
    button.click();
	
}
public void searchinformation() throws IOException {
	WebElement element = null;
	boolean flag = false;
	    for (int i = 0; i< 10; i++) {// iterating 10 times 
	        try {
	            element = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/p"));
	            if (element != null) {
	                flag = true;
	                
	                break;
	            } 
	            else { 
	            }
	        } catch (Exception e) {}
	    }
	    
	   // try{
	    	if(flag){
	    	//String info=element.getText();
	    logger.debug("Komunikat ");	 
	  	  writeFile();
	  	     System.out.println("Komunikat");
	  	                      
	   }
	          
	  else {
		  logger.debug("Brak komunikatów");
		  System.out.println("Brak komunikatów");
	  }
}
//metoda do szukania b³edów
public void searcherror() throws IOException {
	WebElement element = null;
	boolean flag = false;
	    for (int i = 0; i< 10; i++) {// iterating 10 times 
	        try {
	            element = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/p"));
	            if (element != null) {
	                flag = true;
	                
	                break;
	            } 
	            else { logger.debug("brak b³edów");
	            }
	        } catch (Exception e) {}
	    }
	    
	   // try{
	    	if(flag){
	    		 
	  	  writeFile();
	  	     System.out.println("b³¹d");
	  	                      
	   }
	          
	  else {
		  logger.debug("brak b³edów");
		  System.out.println("Brak b³edów");
	  }
	    	logger.debug("Koniec szukania b³êdów");
}
public void searcherrorvod() throws IOException {
	WebElement element = null;
	boolean flag = false;
	    for (int i = 0; i< 10; i++) {// iterating 10 times 
	        try {
	            element = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/p"));
	            if (element != null) {
	                flag = true;
	                
	                break;
	            } 
	            else { 
	            }
	        } catch (Exception e) {}
	    }
	    
	   // try{
	    	if(flag){
	    		 
	  	  writeFile();
	  	     System.out.println("B³ad");
	  	                      
	   }
	          
	  else {
		  logger.debug("brak b³edów");
		  System.out.println("Brak b³edów");
	  }
}
//szukanie b³eów dla live
public void searcherrorlive() throws IOException {
	logger.debug("Szulkanie b³edu przy odtwarzaniu");
	WebElement element = null;
	boolean flag = false;
	    for (int i = 0; i< 10; i++) {// iterating 10 times 
	        try {
	            element = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/p"));
	            if (element != null) {
	                flag = true;
	                
	                break;
	            } 
	            else { 
	            }
	        } catch (Exception e) {}
	    }
	    
	   // try{
	    	if(flag){
	    		
	  	  writeFile2();
	  	     System.out.println("B³ad");
	  	                      
	   }
	          
	  else {logger.debug("brak b³edów");
		  System.out.println("Brak b³edów");
	  }
}

//metoda odpowiedzialna za znajdowanie materia³u w kwadracie, odtworzenie go i wyœwietlenie b³edu
public void searchByPathplayer() throws IOException {
	logger.debug("Szukanie filmów");
	
	 URL url1 = getClass().getResource("PathLive.txt");
		File file = new File(url1.getPath());	 
	 
    ArrayList<String> filmPath; //u¿ywamy listy z œcie¿kami do kwadratów w którcy znajduj¹ siê filmy
    filmPath= readFile(file.toString());
//filmPath = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\PathLive.txt");
//logerlive.debug("Search movie");
for (int i=0; i<8; i++) {
            try {
                    WebElement a = null;
                    a = driver.findElement(By.xpath(filmPath.get(i)));
                    if (a != null) {
                            a.click();
                            String g = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/h2")).getText();
                            logger.debug("Film "+g);
                            Thread.sleep(2000);
                            try {
                                    WebElement ss = null;
                                    ss = driver.findElement(By.linkText("odtwórz"));//przycisk play
                                    Thread.sleep(2000);
                                    if (ss != null) {
                                            ss.click();
                                          // ruszanie myszki, ¿eby ikony sie pojawia³y i wszystkie akcje sie wykona³y
                                      
                                            
                                            buffer();
                                            
                                            Thread.sleep(5000);// zmien na 2 minuty
                                         
                                            sound();
                                            Thread.sleep(5000);
                                            fullScreen();
                                           Thread.sleep(5000);
                                            clock();
                                            
                                           Thread.sleep(5000);
                                            
                                            qualityIkon();
                                   } 
                                         
                                    Thread.sleep(5000);
                                            searcherrorlive();
                                  
                                  
                            }
                                    catch (Exception e) {}
                    }
                    
                    driver.navigate().back();
                    Thread.sleep(5000);
                    driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[1]/div/ul/li[2]/label")).click();// klastycznie
                    Thread.sleep(5000);
            } catch (Exception e) {}
       }
}
//szukanie live po sciezkach
public void searchByPath() throws IOException {
        
	URL url1 = getClass().getResource("PathLive.txt");
	File file = new File(url1.getPath());	 
 


        ArrayList<String> filmPath; //u¿ywamy listy z œcie¿kami do kwadratów w którcy znajduj¹ siê filmy
        filmPath= readFile(file.toString());
    //filmPath = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\PathLive.txt");
    //logerlive.debug("Search movie");
    for (int i=0; i<8; i++) {
                try {
                        WebElement a = null;
                        a = driver.findElement(By.xpath(filmPath.get(i)));
                        if (a != null) {
                                a.click();
                                Thread.sleep(2000);
                                try {
                                        WebElement ss = null;
                                        ss = driver.findElement(By.linkText("odtwórz"));
                                        if (ss != null) {
                                                ss.click();
                                                Thread.sleep(6000);// zmien na 2 minuty
                                                try {
                                                        searcherrorlive();
                                                      

                                                }

                                                catch (Exception e) {
                                                }
                                        }
                                } catch (Exception e) {
                                }
                        }

                        driver.navigate().back();
                        Thread.sleep(5000);
                        driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[1]/div/ul/li[2]/label")).click();// klastycznie
                        Thread.sleep(5000);
                } catch (Exception e) {
                }
}

}
//sprawdzenie czy sie film buforuje
public void buffer() {//szuka czy wystepuje bufowrowanie
	logger.debug("Sprawdzenie buffora");
	 try {
		 musemove();
	     	WebElement a=null;
          a=driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[5]"));
          Thread.sleep(3000);
     if(a != null) {
     	System.out.println("Ikona Buforowania wystêpuje");
     	
     	}
     else{ System.out.println("Ikona Buforowania nie wystêpuje");}
    }
    catch (Exception e) {System.out.println(" wystêpuje");	}
	 }
   //odtworzenie trybu pe³noekranowego filmu
public void fullScreen() throws InterruptedException, AWTException {//w³acz tryb pe³noekranowy
	//try {
	logger.debug("Sprawdzenie trybu pe³noekranowego");
	musemove();
		driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[4]/div")).click();//klikniecie film
		driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/button[2]")).click();//tryb pe³noekranowy
		System.out.println("Tryb Pe³noekranowy w³¹czony");
		Thread.sleep(4000);
		 driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[4]/div")).click();//klikniecie film
		driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/button[2]")).click();//trub niepe³noekranowy
		Thread.sleep(4000);
	//}
	//catch (Exception e) {System.out.println("Tryb Pe³noekranowy - nie ma ");}
		logger.debug("Koniec sprawdzania trybu pe³noekranowego");
}
//zmianna jakosci materia³u

public void qualityIkon() throws InterruptedException, IOException {
	logger.debug("Sprawdzenie jakosci");
	URL url1 = getClass().getResource("PathQuality.txt");
	File file = new File(url1.getPath());	 
        
	  ArrayList<String> Path; //u¿ywamy listy z œcie¿kami do kwadratów w którcy znajduj¹ siê filmy

      Path= readFile(file.toString());
	    //Path = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\PathQuality.txt");
	    WebElement i;
	 try {//probuje znalezc ikone z jakoscia obrazu
		 WebElement b;
		b= driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[4]/div"));//klikniecie filmu	
	    b.click();//klikniecie filmu
		musemove();//ruszenie myszka na film
		 WebElement t;
         t = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[7]"));//jakoœæ obrazu ikona
        t.click();//klikniecie w ikone
         System.out.println("ikona kilkniecie");
         driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[7]/div[1]/ul/li[1]"));//wybór jakoœci
         System.out.println("Jakoœ zmieniona 1");
         logger.debug("Zmianna jakoœci 1");
         Thread.sleep(6000);//czekanie
        
       b.click();//klikniecie film
        musemove();
       t.click();//jakoœæ obrazu
        driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[7]/div[1]/ul/li[2]")).click();//wybór jakoœci
         System.out.println("Jakoœ zmieniona 2");
         logger.debug("Zmianna jakoœci 2");
         Thread.sleep(6000);
         
     b.click();//klikniecie film
         musemove(); 
      t.click();//jakoœæ obrazu
         driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[7]/div[1]/ul/li[3]")).click();//wybór jakoœci
         System.out.println("Jakoœ zmieniona 3");
         logger.debug("Zmianna jakoœci 3");
         Thread.sleep(6000);
         
       b.click();//klikniecie film
        musemove();
      t.click();//jakoœæ obrazu
       driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[7]/div[1]/ul/li[4]")).click();//wybór jakoœci
         System.out.println("Jakoœ zmieniona 4");
         logger.debug("Zmianna jakoœci 4");
        Thread.sleep(6000);
        
       b.click();//klikniecie film
         musemove();
       t.click();//jakoœæ obrazu
       driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[7]/div[1]/ul/li[5]")).click();//wybór jakoœci
         System.out.println("Jakoœ zmieniona 5");
         logger.debug("Zmianna jakoœci 5");
         Thread.sleep(6000);
  
       b.click();//klikniecie film
         musemove();
     t.click();//jakoœæ obrazu
         driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[7]/div[1]/ul/li[6]")).click();//wybór jakoœci
         System.out.println("Jakoœ zmieniona 6");
         logger.debug("Zmianna jakoœci 6");
         Thread.sleep(6000);
         }
 
         catch (Exception b) {System.out.println("wyj¹tek");}
	 logger.debug("Koniec sprawdzania jakosci");
}
//zmian dzwieku materia³u
public void sound() throws InterruptedException, AWTException {
	//try {
	logger.debug("Sprawdzenie dŸwiêku");
		driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[4]/div")).click();//klikniecie film
		 musemove();
	driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[9]")).click();//klika w dzwiek
	System.out.println("Sprawdzono pozniom natê¿enia d¿wiêku");
	Thread.sleep(4000);
	driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[4]/div")).click();//klikniecie film
	 musemove();
	driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[9]")).click();//klika w dzwiek
	Thread.sleep(4000);
	//}

   // catch (Exception e) {}
	logger.debug("Koniec sprawdzenia dŸwiêku");
}
//odczytanie czsu materia³u

public void clock() throws AWTException {//metoda do czytania czasu z materia³u 
	logger.debug("Odczytanie czasu materia³u");
	WebElement clock;
	driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[4]/div")).click();//klikniecie film
	 musemove();
	
	clock = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[6]/div[2]/div"));//szukianie czasu
	String s;
	s = clock.getText();//popbranie czasu
	logger.debug("Czas"+s);
	System.out.println(s);//wyswietlenie czasu na konsoli
	
}
// metoda dodanie urz¹dzenia

public void plusDevice() throws InterruptedException, IOException {
    
        //Pojawienie siê okna dodanie urz¹dzenia
    WebElement urzadzenia =  null;
    for (int i = 0; i< 5; i++) {// iterating 10 times 
        try {
         urzadzenia = driver.findElement(By.xpath("/html/body/main/div/div/div/form/h2")); //szukanie okna z dodaniem urz¹dzenia
         
         driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div/div/div[2]/div[3]/div/div[1]/div/button")).click(); //klikniêcie Tak, dodaj
         Thread.sleep(5000);
         try { searcherror();
        	 driver.findElement(By.xpath("/html/body/div[2]/div/div/div/a")).click(); } //klikniêcie w x 
         catch (Exception e) {}
         driver.findElement(By.xpath("/html/body/div[3]")).click(); //klikniêcie w t³o
         Thread.sleep(5000);
         logger.debug("klikniecie w pierwsze uzradzenie ");
         driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div/div/div[2]/ul/li[1]")).click(); //klikniecie w pierwsze urz¹dzenie
            driver.findElement(By.xpath("/html/body/main/div/div/div/form/fieldset/div/div/div[2]/div[3]/div/div[1]/div/button")).click();
         Thread.sleep(3000);
         logger.debug("Szukanie b³edów");
        searcherror(); //jeœli wyst¹pi b³¹d to zostanie zwrócony
        logger.debug("Koniec szukania b³edów");}
        catch (Exception e) {}
        }
    }



//metoda dezaktywacji sesji na WSI

public void deleteSession() throws InterruptedException, IOException {
        loginwsi();
        logger.debug("Wejœcie w konta");
    driver.findElement(By.xpath("html/body/div[1]/div/div[2]/ul[1]/li[1]/a")).click(); //wejœcie w zak³adkê konta
    logger.debug("Filtracja");
    driver.findElement(By.xpath(".//*[@id='collapse-label']")).click(); //przycisk filtracji
    
    URL url1 = getClass().getResource("devices.txt");
	File file = new File(url1.getPath());	 
  
    ArrayList<String> device; //nowa lista
    device= readFile(file.toString());
  //  device = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\Test\\devices.txt"); 
    logger.debug("Wpisanie maila");
    driver.findElement(By.xpath("//*[@id=\"accountEmail\"]")).sendKeys(device.get(1)); //wpisanie emaila
    driver.findElement(By.xpath("//*[@id=\"doFilter\"]")).click();
    Thread.sleep(3000);
    driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[8]/a/span")).click(); //klikniêcie w aktywne konto
    Thread.sleep(3000);
    logger.debug("sesje");
    driver.findElement(By.xpath("/html/body/div[3]/ul/li[3]/a")).click(); //klikniêcie w zak³adkê sesje
    Thread.sleep(2000);
    driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[2]/td[9]/span")).click(); //klikniêcie w drugie od góry urz¹dzenie
    logger.debug("Dezaktywacja sesji na WSI");
    driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div/select/option[2]")).click(); //klikniêcie w INACTIVE
    driver.findElement(By.xpath("//*[@id=\"editFormSubmitButton\"]")).click(); //klikniêcie w zapisz
    Thread.sleep(2000);
    driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[9]/span")).click(); //klikniêcie w pierwsze od góry urz¹dzenie
    driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div/select/option[2]")).click(); //klikniêcie w INACTIVE
    driver.findElement(By.xpath("//*[@id=\"editFormSubmitButton\"]")).click(); //klikniêcie w zapisz
    
}  

public void musemove() throws AWTException {//metoda do ruszania myszka na odtwarzany maeria³ live
	 Point coordinates = driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[4]/div")).getLocation();//ciezka do lokazlizacji matreria³u
     Robot robot = new Robot();
     robot.mouseMove(coordinates.getX(),coordinates.getY()+120);//wspo³rzedne
     robot.mouseMove(coordinates.getX(),coordinates.getY()+125);
}
public void searchByPathlive() throws IOException {
	logger.debug("Szukanie b³edów");
	  URL url1 = getClass().getResource("PathLive.txt");
		File file = new File(url1.getPath());	 
	  
	   
	   
    ArrayList<String> filmPath; //u¿ywamy listy z œcie¿kami do kwadratów w którcy znajduj¹ siê filmy
    filmPath= readFile(file.toString());
//filmPath = readFile("C:\\Users\\laura.giza\\workspace\\Test\\src\\test\\PathLive.txt");

for (int i=0; i<8; i++) {
            try {
                    WebElement a = null;
                    a = driver.findElement(By.xpath(filmPath.get(i)));
                    if (a != null) {
                            a.click();
                            Thread.sleep(2000);
                            try {
                                    WebElement ss = null;
                                    String s=driver.findElement(By.xpath("/html/body/main/div/div[2]/div[1]/div/h2")).getText();
                                    logger.debug("odtworzenie materia³u"+s);
                                    ss = driver.findElement(By.linkText("odtwórz"));
                                    if (ss != null) {
                                            ss.click();
                                            Thread.sleep(6000);// zmien na 2 minuty
                                            try {
                                            	
                                                    searcherrorlive();
                                                 

                                            }

                                            catch (Exception e) {
                                            }
                                    }
                            } catch (Exception e) {
                            }
                    }
                    logger.debug("Koniec szukania b³edów"); 
                    driver.navigate().back();
                    Thread.sleep(5000);
                    driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div/div[1]/div/ul/li[2]/label")).click();// klastycznie
                    Thread.sleep(5000);
            } catch (Exception e) {
            }
}

}
@After
public void end() {
driver.quit();
}
}
