# Viikko 5 - raportti

## Viikon tehtävät
* Poistettu matriisi luokista getterit ja setterit
* Uusi algoritmi pienimmän sarakkeen löytämiseksi
* Algoritmin toiminnan analyysiä vaativuuden näkökulmasta
* Koodin tehostamisen pähkäilyä
* Koodin viilailua

## Ohjelman edistyminen
Luokista MatrixNode, ColumnNode ja Rownode poistettiin getterit ja setterit. Niiden tilalle koodiin tehtiin suorat viittaukset olioiden muuttujiin: Vaikutus nopeuteen: ei suurta vaikutusta (n. 20%).

Algoritmissa pitää löytää usein sarake, jossa on vähiten soluja. Käytännössä pienimmässä sarakkeessa niitä on 0-2. Aiemmin kaikki sarakkeet käytin läpi joka kerta ja valittiin pienin. Nyt koko ajan ylläpidetään kolmea linkitettyä listaa, joissa 0-2 solun sarakkeet. Vaikutus nopeuteen: ei juuri vaikutusta. 

## Mitä opin
* Suurta parannusta nopeuteen vaikea saada

## Epäselvää
* onko vaativin sudoku (5_level372.ss), jossa on 372 tyhjää ruutua edes ratkaistavissa normaalilla tietokoneella. Nyt algoritmi ei ratkaise sitä järkevässä ajassa.

## Seuraavat tehtävät
* Käyttöliittymän ja koodin stilisointi ja dokumentaation viimeistely

## Tuntikirjanpito
* 3.6: 3 tuntia
* 4.6: 4 tuntia
* 6.6: 3 tuntia
