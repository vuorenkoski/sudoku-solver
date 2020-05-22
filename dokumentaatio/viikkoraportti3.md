# Viikko 3 - raportti

## Viikon tehtävät
* tehokkaamman algoritmin toteutus. Tämä olikin aika vaiheikas eri ratkaisujen kokeilemisen prosessi. Päällisin puolin Algorithm X vaikuttaa kohtuu simppeliltä, mutta käytännön toteutus (etenkin tehokkaast) ei ole aivan yksinkertainen.
* Käyttöohjeen lisääminen

## Ohjelman edistyminen
Ohjelmassa toimii nyt myös Algorithm X. Tosin sitä varmasti voi vielä tehostaa. Testiaineistona oleva tason 17 sudokut ratkeaa Algorithm X:n avulla keskimäärin 7ms kun brute-force ottaa keskimäärin 17 sekuntia (3_level17.ss). 16x16 (4_sudoku.ss) ratkeaa 46ms. Testiaineistona oleva 25x25 (5_sudoku.ss) ratkesi n. 70 sekunnissa. Kaksi viimeistä ei ratkea brute-force algoritmilla missään järkevässä ajassa.

Algorithm X toteutus on rakennettu kaksisuuntaisten linkitettyjen listojen avulla. Linkitetyt listat kulkevat vaakasuuntaan ja pystysuuntaan. Lisäksi poistetuille riveille ja sarakkeille on vielä oma linkitetty lista, jotta ne voidaan palauttaa.

## Mitä opin
* Linkitettyjen listojen toteutusta, Algorithm X toteutusta ja täydellinen peite konseptia.

## Epäselvää

## Seuraavat tehtävät
* Algoritmin virittely tehokkaammaksi.
* Ohjelmassa on yksi valmis javan algoritmi, joka järjestää taulukon. Tähän pitää tehdä oma ratkaisu.
* Matriisin valmistelun välivaiheena luodaan 729x324 taulukko (25x25 sudokulla 15625x2500). Tämä välivaihe voitaneen jättää pois.

## Tuntikirjanpito
* 21.5: 6 tuntia
* 22.5: 6 tuntia
