# Sudokusolver - testausdokumentti

## Mitä on testattu, miten tämä tehtiin
Ohjelmaa on testattu sudukoilla, joissa on eri määrä tyhjiä ruutuja. Samoin ohjelmaa on testattu eri kokoisilla sudokuilla (9x9, 16x16, 25x25). Testiaineistossa on kuitenkin vain kolme sudokua, jotka ovat suurempia kuin 9x9. 

9x9 testiaineisto on ladattu [Kjell Ericsonin sivuilta](https://kjell.haxx.se/sudoku/).

## Minkälaisilla syötteillä testaus tehtiin
Ohjelman testauksessa käytettiin seuraavia aineistoja:

1. 6 kappaletta 9x9 sudokua, jossa 48 tyhjää ruutua (3_level48.ss)
1. 7 kappaletta 9x9 sudokua, jossa 55 tyhjää ruutua (3_level55.ss)
1. 6 kappaletta 9x9 sudokua, jossa 64 tyhjää ruutua (3_level64.ss)
1. Yksi 9x9 sudoku, jossa 60 tyhjää ruutua (3_worldsHarderst60.ss)
1. 100 kappaletta 9x9 sudokua, jossa  64 tyhjää ruutua (3_100sudokua64.ss)
1. Yksi 16x16 sudoku, jossa 163 tyhjää ruutua (4_level163.ss)
1. Yksi 25x25 sudoku, jossa 271 tyhjää ruutua (5_level271.ss)
1. Yksi 25x25 sudoku, jossa 372 tyhjää ruutua (5_level372.ss)

## Miten testit voidaan toistaa
Testiaineisto on kansiossa "testidata". Siellä on lisäksi tiedosto johon on lisätty sudokut 1-4 ja 7 (test21.ss). Brute-Force algortimit ei voi ratkaista sudokuja 6 ja 8.

Testipaketin voi toistaa esimerkiksi näin:

```
java -jar target/Sudokusolver-1.0-SNAPSHOT.jar testidata/3_100sudokua64.ss
```

## Ohjelman toiminnan empiirisen testauksen tulosten esittäminen graafisessa muodossa.

Suoritusajat (yksi sudoku keskimäärin). Testikone: AMD Ryzen 5 2600X, Kubuntu 19.10

| koko | tyhjat solut | lukumäärä  | Brute-Force | Algorithm X  | 
| :----:|:-----| :-----|:-----| :-----|
|9x9|48|5|0,36ms|1,51ms|
|9x9|55|5|3,0ms|1,35ms|
|9x9|64|5|6,1s|2,1ms|
|9x9|64|100|4,8s|0,51ms|
|16x16|163|1|ei ratkea|8,3ms|
|25x25|271|1|1,19s|17ms|
|25x25|372|1|ei ratkea|ei ratkea|

100 sudokun patterin testissä mielenkiintoinen ilmiö: 28 ensimmäistä sudokua valmisteluvaihe vie luokkaa 0,5ms ja sen jälkeen luokkaa 0,1ms? Liekö jokin välimuistikysymys?
