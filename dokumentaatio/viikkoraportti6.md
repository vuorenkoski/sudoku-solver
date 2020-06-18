# Viikko 6 - raportti

## Viikon tehtävät
* Käyttöliittymän luominen
* Koodin stilisointia ja luokat paketteihin
* Dokumentaation parantelua
* testauksen parantelua
* haarautumisen analysointia (voisiko 5_level372.ss sudokun ratkaista järjellisessä ajassa)

## Ohjelman edistyminen
Eri algoritmeihin vaadittavat luokat siirreettiin omiin paketteihin. Ohjelmalle luotiin alkeellinen käyttöliittymä. Asetukset voi antaa myös parametreina.

Grid luokkaan lisättiin checkIntegrity metodi, joka testaa ettei sudokussa ole tyhjiä ruutuja. Se testaa myös ettei sudokussa ole samaa numeroa kuin kerran jokaisessa rivissä, sarakkeessa ja lohkossa. Alkuasetuksissa voidaan määrittää että testi tehdään jokaiselle ratkaistulle sudokulle. Sitä hyödynnetään myös yksikkötestauksessa.

Algoritmiin X luotiin optio, jolla voi tulostaa dataa haarautumisesta.

## Mitä opin
* np-täydellisten ongelmien aikavaativuus kasvaa nopeasti!

## Epäselvää

## Seuraavat tehtävät
* Viimeistely

## Tuntikirjanpito
* 13.6: 8 tuntia
* 15.6: 2 tuntia
* 18.6: 1 tunti
