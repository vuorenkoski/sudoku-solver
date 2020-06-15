# Sudoku-solver - Käyttöohje

Tämä on Tira-labran (alkukesä 2020) harjoitustyö. Ohjelma ratkaisee sudokuja. Sudokujen mahdolliset koot: 9x9, 16x16 ja 25x25.

## Ohjelman toimintaperiaate
Sudokuja ratkaistaan kahdella eri algoritmilla, raakaan laskentavoimaan perustuva algoritmi sekä kehittyneempi algoritmi.

Ohjelma ottaa syötteenä tiedoston, joka sisältää yhden tai useamman sudokun. Tiedoston nimi annetaan ohjelman parametrina. Mikäli ohjelmalle ei anneta parametria, käytetään käynnistyshakemistossa oleva sudoku.ss -tiedostoa.

Myös ohjelman asetukset voidaan antaa parametreina. Valitsimet:
x - Käytä algorithm X
b - Käytä Brute-Force
s - Tulosta alkuperäinen sudoku
r - Tulosta ratkaisu
t - Tarkista kaikki valmiit sudokut
d - Tulostaa dataa haarautumisesta (haarautumisen analyysiä varten)
h - Ohje

Ennen sudokujen ratkaisemista ohjelmassa voi muuttaa asetuksia ellei niitä ole annettu parametreina. Oletusasetuksena on, että kummatkin algoritmit suoritetaan, eikä sudokuja tulosteta ennen tai jälkeen ratkaisun. 

Algoritmin suoritus käynnistyy kun painetaan vain enter ilman asetuksia (tyhjä rivi).

Syötetiedostossa tulee olla ennen jokaista sudokua numero, joka kertoo sudokun koon (3=9x9, 4=16x16, 5=25x25).

Solujen arvot:

* 9x9 sudoku: 1..9
* 16x16 sudoku: 0..9 ja A..F
* 25x25 sudoku: A..Y

Tiedosto voi sisältä useamman sudokun. Tällöin niitä erottaa vain rivi, jossa on sudokun koko. Yksi tiedosto voi sisältää useamman kokoisia sudokuja.

Sudokun tiedostomuoto:

    3
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
    
Ohjelma ratkaisee yhden sudokun kerrallaan. Ensin käyttäen Algorithm X -algoritmia ja sen jälkeen Brute-Forece algoritmia (riippuen asetuksista). Kummankin osalta ohjelma tulostaa algoritmin viemän ajan sekä tekstin "VASTAUSTA EI LÖYTYNYT" jos ratkaiseminen ei onnistunut. Lisäksi ohjelma kertoo mikäli ratkaisut eivät olleet identtisiä ("RATKAISUT EROAVAT").

Suorituksen lopuksi ohjelma tulostaa keskimääräisen aika-tarpeen kummankin algoritmin osalta. Se myös tulostaa viestin siitä, olivatko ratkaisut identtiset kaikkien tiedoston sudokujen osalta. Se myös ilmoittaa mikäli jonkin sudokun ratkaisu ei onnistunut tai ratkaisu on väärin (mikäli lopputarkastus on laitettu asetuksissa päälle).

