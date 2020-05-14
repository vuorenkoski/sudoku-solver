# Määrittelydokumentti - Sudoku-solver

Tämä on Tira-labran (alkukesä 2020) harjoitustyö. Ohjelma ratkaisee sudokuja.

## Toteutettavat algoritmit ja tietorakenteet

Sudokuja ratkaistaan kahdella eri algoritmilla, raakaan laskentavoimaan perustuva algoritmi sekä kehittyneempi algoritmi.

**Brute-Force** menetelmä toteutetaan syvyyshaulla. Syvyyshaussa sudokun tyhjiä soluja lähdetään täyttämään vasemmasta yläkulmasta riveittäin. Pienistä numerosta suurempaan. Yhden haaran haku pysähtyy, mikäli seuraavaan soluun ruutuun ei voi lisätä mitään numeroa (tarkistetaan ettei samaa numeroa ole ryhmässä, rivillä tai sarakkeessa). Haku päättyy kun kaikkiin soluihin on saatu numero.

Tällä menetelmällä Sudoku ratkeaa varmasti, mutta se saattaa viedä aikaa. Aikavaativuus riippuu tyhjien solujen lukumäärästä (n). Teoriassa vaatuivuus on O(10^n), mutta käytännössä yhden haaran läpikäynti pysähtyy huomattavasti ennen kuin päästään haaran loppuun asti. Ohjelmalla voisi testata kuinka monta kokeilua tällä algoritmilla keskimäärin tarvitaan. 

Algoritmin käytännön nopeus riippuu käytännössä tyhjien solujen määrän lisäksi siitä, painottuvatko tyhjät solut sudokun yläosaan, jolloin syvyyshaku ajautuu paljon pidemmälle ennen kuin se huomaa haaran päätyvän umpikujaan.

Raakaa laskentavoimaa käyttävän algoritmin lisäksi toteutetaan **Kehittyneempi algoritmi**. Tämä algoritmi rakennetaan päättelymenetelmistä, joita ihminenkin käyttäisi sudokujen ratkaisemiseen (kts. Tips on Solving Sudoku Puzzles). Tuolla sivuilla kuvatuista menetelmistä toteutaan niin moni, kuin on tarvetta. Toki voi olla että vaikeampien suodukujen osalta loppuratkaisuun tarvitaan vielä brute-force algoritmia, mutta tavoitteena on että tätä ei tarvita. 

Toinen vaihtoehto olisi käsitellä sudokua Exact cover -ongelmana. Ja sitten käyttää Knuth's

**Tietorakenteeksi** luodaan oma Ruudukko-luokka, joka sisältää 81 int tyyppistä kokonaislukua. Luokka kuvaa sudoku-ruudukkoa. Luokka sisältää tiedon jokaisen ruudun kohdalta onko se alunperin tyhjä. 

Lisäksi kehittyneempään algoritmia varten tarvitaan tietorakenne tyhjien ruutujen mahdollisille vaihtoehdoille. Voi olla että tarvitaan vielä jotakin rivi,sarake ja ryhmäkohtaisia tietorakenteita.

## Ratkaistava ongelma ja perustelut algoritmeille
Ongelmana on Sudokun ratkaiseminen mahdollisimman nopeasti. Brute-Force algoritmi toimii vertailukohtana kehittyneemmälle algoritmille. 

## Ohjelman syötteet ja niiden käyttö
Ohjelmaan syötetään sudokuja tekstitiedostona. Tiedosto voi sisältää yhden tai useamman sudokun. Tiedostomuoto:

    .47!9..!6.5
    81.!.75!24.
    ...!...!3.7
    ---!---!---
    67.!...!.92
    58.!.96!4..
    ...!.2.!7..
    ---!---!---
    3.6!...!..4
    ...!21.!97.
    ..1!.6.!...

Mikäli tiedosto sisältää useamman sudokun, on ne erotettu tyhjällä rivillä.

Ohjelma tulostaa jokaisen sudokun kohdalta ratkaisun, sekä kummankin algoritmin käyteytyn ajan. Ohjelma kertoo myös tulosteessa mikäli algoritmi ei pystynyt ratkaisemaan sudokua.

Testidataa löytyy [Kjell Ericsonin sivuilta](https://kjell.haxx.se/sudoku/).

## Tavoitteena olevat aika- ja tilavaativuudet
Ohjelman tilavaativuus lienee pieni. Kun tyhjien solujen määrä on n, niin kehittyneemmän algoritmin tavoitteena pitänee pitää O(n) tasoa (lieneekö mahdollista?).

## Lähteet
* [Wikipedia: Sudoku solving algorithms](https://en.wikipedia.org/wiki/Sudoku_solving_algorithms)
* [Tips on Solving Sudoku Puzzles](https://www.kristanix.com/sudokuepic/sudoku-solving-techniques.php)
* [Solving sudoku](http://www.angusj.com/sudoku/hints.php)
* [Exact cover](https://en.wikipedia.org/wiki/Exact_cover)
* [World's hardest sudoku: can you crack it?](https://www.telegraph.co.uk/news/science/science-news/9359579/Worlds-hardest-sudoku-can-you-crack-it.html)
