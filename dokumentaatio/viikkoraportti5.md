# Viikko 5 - raportti

## Viikon tehtävät
* Poistettu matriisi luokista getterit ja setterit
* Uusi algoritmi pienimmän sarakkeen löytämiseksi

## Ohjelman edistyminen
Luokista MatrixNode, ColumnNode ja Rownode poistettiin getterit ja setterit. Niiden tilalle koodiin tehtiin suorat viittaukset olioiden muuttujiin: Vaikutus nopeuteen: ei merkittävää vaikutusta (n. 20%).

Algoritmissa pitää löytää usein sarake, jossa on vähiten soluja. Käytännössä pienimmässä sarakkeessa niitä on 0-2. Aiemmin kaikki sarakkeet käytin läpi ja valittiin pienin. Nyt koko ajan ylläpidetään kolmea taulukkoa, jossa linkitetty lista sarakkeisiin joissa on 0, 1 tai 2 solua. Vaikutus nopeuteen: ei juuri vaikutusta.

## Mitä opin
* 

## Epäselvää

## Seuraavat tehtävät
* Käyttöliittymän ja koodin stilisointi ja dokumentaation viimeistely

## Tuntikirjanpito
* 3.6: 3 tuntia
* 4.6: 4 tuntia
