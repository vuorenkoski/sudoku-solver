# Sudoku-solver
Tira-labran harjoitustyö, alkukesä 2020

## Viikkoraportit
* [Viikko 1](dokumentaatio/viikkoraportti1.md)

## Dokumentaatio
* [Määrittelydokumentti](dokumentaatio/maarittelydokumentti.md)

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

Ohjelman argumenttina voi antaa syötteen sisältävän tiedoston nimen. Muuten syöte luetaan tiedostosta "sudoku.ss". Tiedosto voi sisältää useamman sudokun. Nämä tulee erotella tyhällä rivillä.
```
java -jar target/Sudokusolver-1.0-SNAPSHOT.jar
```


JavaDoc luonti

```
mvn javadoc:javadoc
```

