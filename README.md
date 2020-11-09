# Sudoku-solver
Tira-labran harjoitustyö, alkukesä 2020

## Dokumentaatio
* [Määrittelydokumentti](dokumentaatio/maarittelydokumentti.md)
* [Toteutusdokumentti](dokumentaatio/toteutusdokumentti.md)
* [Käyttöohje](dokumentaatio/kaytto-ohje.md)
* [Testausdokumentti](dokumentaatio/testausdokumentti.md)

## Komentorivitoiminnot

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Checkstyle raportti

```
mvn jxr:jxr checkstyle:checkstyle
```

Ohjelman suoritus

```
mvn compile exec:java -Dexec.mainClass=fi.vuorenkoski.sudokusolver.Main
```

Suorituskelpoinen jar-tiedosto

```
mvn package
```

Ohjelman suoritus

```
java -jar target/Sudokusolver-1.0.jar
```


JavaDoc luonti

```
mvn javadoc:javadoc
```

