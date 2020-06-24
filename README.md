# Sudoku-solver
Tira-labran harjoitustyö, alkukesä 2020

## Viikkoraportit
* [Viikko 1](dokumentaatio/viikkoraportti1.md)
* [Viikko 2](dokumentaatio/viikkoraportti2.md)
* [Viikko 3](dokumentaatio/viikkoraportti3.md)
* [Viikko 4](dokumentaatio/viikkoraportti4.md)
* [Viikko 5](dokumentaatio/viikkoraportti5.md)
* [Viikko 6](dokumentaatio/viikkoraportti6.md)
* [Loppupalautus](dokumentaatio/loppupalautus.md)

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

