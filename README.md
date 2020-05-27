# Sudoku-solver
Tira-labran harjoitustyö, alkukesä 2020

## Viikkoraportit
* [Viikko 1](dokumentaatio/viikkoraportti1.md)
* [Viikko 2](dokumentaatio/viikkoraportti2.md)
* [Viikko 3](dokumentaatio/viikkoraportti3.md)

## Dokumentaatio
* [Määrittelydokumentti](dokumentaatio/maarittelydokumentti.md)
* [Käyttöohje](dokumentaatio/maarittelydokumentti.md)

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

Ohjelman argumenttina voi antaa syötteen sisältävän tiedoston nimen. Muuten syöte luetaan tiedostosta "sudoku.ss". Tiedosto voi sisältää useamman sudokun. Ennen varsinaista sudokua on rivi joka kertoo sudokun koon: 3=9x9, 4=16x16, 5=25x25. 

Solun vaihtoehdot: 3x3=1..9, 4x4=0..9 ja A..F, 5x5=A..Y

```
java -jar target/Sudokusolver-1.0-SNAPSHOT.jar
```


JavaDoc luonti

```
mvn javadoc:javadoc
```

