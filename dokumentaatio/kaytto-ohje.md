# Sudoku-solver - Käyttöohje

Tämä on Tira-labran (alkukesä 2020) harjoitustyö. Ohjelma ratkaisee sudokuja. Sudokujen mahdolliset koot: 9x9, 16x16 ja 25x25.

## Ohjelman toimintaperiaate
Sudokuja ratkaistaan kahdella eri algoritmilla, raakaan laskentavoimaan perustuva algoritmi sekä kehittyneempi algoritmi.

Ohjelma ottaa syötteenä tiedoston joka sisältää yhden tai useamman sudokun. Tiedon nimi annetaan ohjelman parametrina. Mikäli ohjelmalle ei anneta parametria, käytetään käynnistyshakemistossa oleva sudoku.ss -tiedostoa.

Ennen jokaista sudokua syötetiedostossa on rivi, joka kertoo sudokujen koon (3=9x9, 4=16x16, 5=25x25).

Solujen arvot:

* 9x9 sudoku: 1..9
* 16x16 sudoku: 0..9 ja A..F
* 25x25 sudoku: A..Y

Tiedosto voi sisältä useamman sudoku. Tällöin niitä erottaa vain rivi jossa on sudokun koko. Yksi tiedosto voi sisältää useamman kokoisia sudokuja.

Sudokun muoto:

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
    
Ohjelma ratkaisee yhden sudokun kerrallaan. Ensin käyttäen Algorithm X -algoritmia ja sen jälkeen Brute-Forece algoritmia. Kummankin osalta ohjelma tulostaa algoritmin viemän ajan sekä onnistuiko sudokun ratkaiseminen. Lisäksi ohjelma kertoo olivatko ratkaisut identtisiä.

Suorituksen lopuksi ohjelma tulostaa keskimääräisen aika-tarpeen kummankin algoritmin osalta.

