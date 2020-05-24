# Viikko 3 - raportti

## Viikon tehtävät
* tehokkaamman algoritmin toteutus. Tämä olikin aika vaiheikas eri ratkaisujen kokeilemisen prosessi. Päällisin puolin Algorithm X vaikuttaa kohtuu simppeliltä, mutta käytännön toteutus (etenkin tehokkaast) ei ole aivan yksinkertainen.
* Käyttöohjeen lisääminen

## Ohjelman edistyminen
Ohjelmassa toimii nyt myös Algorithm X. Tosin sitä varmasti voi vielä tehostaa. Testiaineistona oleva tason 64 sudokut (64 tyhjää ruutua) ratkeaa Algorithm X:n avulla keskimäärin 3ms kun brute-force ottaa keskimäärin 17 sekuntia (3_level64.ss). 16x16 (4_sudoku.ss) ratkeaa 38ms. Testiaineistona oleva 25x25 (5_sudoku_271.ss) ratkesi 200ms (brute-force ottaa 4,5 sekuntia). Sen sijaan vaativampi 25x25 sudoku (5_sudoku_372.ss) ei ratkea kehittyneemmälään algoritmilla järkevässä ajassa. 

Algorithm X toteutus on rakennettu kaksisuuntaisten linkitettyjen listojen avulla. Linkitetyt listat kulkevat vaakasuuntaan ja pystysuuntaan. Lisäksi poistetuille riveille  on vielä oma linkitetty lista, jotta ne voidaan palauttaa.

## Mitä opin
* Linkitettyjen listojen toteutusta, Algorithm X toteutusta ja täydellinen peite konseptia.

## Epäselvää

## Seuraavat tehtävät
* Algoritmin virittely tehokkaammaksi.
* Matriisin valmistelun välivaiheena luodaan 729x324 taulukko (25x25 sudokulla 15625x2500). Tämä välivaihe voitaneen jättää pois. Valmsiteluun menee huomattava osa algoritmin käyttämästä ajasta.

## Tuntikirjanpito
* 21.5: 6 tuntia
* 22.5: 6 tuntia
* 23.5: 5 tuntia
