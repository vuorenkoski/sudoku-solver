# Määrittelydokumentti - Sudoku-solver

Tämä on Tira-labran (alkukesä 2020) harjoitustyö. Ohjelma ratkaisee sudokuja.

## Toteutettavat algoritmit ja tietorakenteet

Sudokuja ratkaistaan kahdella eri algoritmilla, raakaan laskentavoimaan perustuva algoritmi sekä kehittyneempi algoritmi.

**Brute-Force** menetelmä toteutetaan syvyyshaulla. Syvyyshaussa sudokun tyhjiä soluja lähdetään täyttämään vasemmasta yläkulmasta riveittäin. Pienistä numerosta suurempaan. Yhden haaran haku pysähtyy, mikäli seuraavaan soluun ruutuun ei voi lisätä mitään numeroa (tarkistetaan ettei samaa numeroa ole ryhmässä, rivillä tai sarakkeessa). Haku päättyy kun kaikkiin soluihin on saatu numero.

Tällä menetelmällä Sudoku ratkeaa varmasti, mutta se saattaa viedä aikaa. Aikavaativuus riippuu tyhjien solujen lukumäärästä (n). Teoriassa vaatuvuus on 10^n, mutta käytännössä yhden haaran läpikäynti pysähtyy huomattavasti ennen kuin päästään haaran loppuun asti. Ohjelmalla voisi testata kuinka monta kokeilua tällä algoritmilla keskimäärin tarvitaan. 

Algoritmin käytännön nopeus riippuu käytännössä tyhjien solujen määrän lisäksi siitä, painottuvatko tyhjät solut sudokun yläosaan, jolloin syvyyshaku ajautuu paljon pidemmälle ennen kuin se huomaa haaran päätyvän umpikujaan.


Tietorakenteeksi luodaan oma Ruudukko-luokka, joka sisältää 81 int tyyppistä kokonaislukua. Luokka kuvaa sudoku-ruudukkoa. Luokka sisältää tiedon jokaisen ruudun kohdalta onko se alunperin tyhjä. 

## Ratkaistava ongelma ja perustelut algoritmeille
Ongelmana on Sudokun ratkaiseminen mahdollisimman nopeasti. Brute-Force algoritmi toimii vertailukohtana kehittyneemmälle algoritmille. Kehittyneemmäksi algoritmiksi valittiin xxx, koska

## Ohjelman syötteet ja niiden käyttö
Ohjelmaan syötetään sudokuja tekstitiedostona. Tiedosto voi sisältää yhden tai useamman sudokun. 

Ohjelma tulostaa jokaisen sudokun kohdalta ratkaisun, sekä kummankin algoritmin käyteytyn ajan. Ohjelma kertoo myös tulosteessa mikäli algoritmi ei pystynyt ratkaisemaan sudokua.

## Tavoitteena olevat aika- ja tilavaativuudet
Ohjelman tilavaativuus lienee pieni. Kun tyhjien solujen määrä on n, niin tavoitteena pitänee pitää O(n) tasoa.

## Lähteet

* [Wikipedia: Sudoku solving algorithms](https://en.wikipedia.org/wiki/Sudoku_solving_algorithms)
