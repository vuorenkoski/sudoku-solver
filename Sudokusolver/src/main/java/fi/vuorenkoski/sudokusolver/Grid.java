package fi.vuorenkoski.sudokusolver;

/**
 * Luokka kuvaa Sudoku-ruudukkoa
 * @author Lauri Vuorenkoski
 */
public class Grid {
    private int[] data;
    private int empty;

    public Grid() {
        this.data = new int[81];
        this.empty = 81;
        for (int i = 0; i < 81; i++) {
            this.data[i] = 0;
        }
    }

    /**
     * Metodi alustaa ruudukon datalla. 
     * 
     * @param lines Syötteena ruudukko String taulukkona
     */
    public void insertData(String[] lines) {
        int j = 0;
        for (int y = 1; y <= 9; y++) {
            int i = 0;
            for (int x = 1; x <= 9; x++) {
                if (lines[j].charAt(i) != '.') {
                    this.setCell(x, y, lines[j].charAt(i) - '0');
                }
                i++;
                if ((x == 3) || (x == 6)) {
                    i++;
                }
            }
            j++;
            if ((y == 3) || (y == 6)) {
                j++;
            }
        }
    }
    
    /**
     * Metodi muuttaa yhden solun arvoa. 
     * 
     * @param x solun sarake
     * @param y solun rivi
     * @param number solun arvo
     */
    public void setCell(int x, int y, int number) {
        if (number > 0 && this.data[x + y * 9 - 10] == 0) {
            this.empty--;
        } else if (number == 0 && this.data[x + y * 9 - 10] != 0) {
            this.empty++;
        }

        this.data[x + y * 9 - 10] = number;
    }
    
        
    /**
     * Metodi palauttaa solun arvon. 
     * 
     * @param x solun sarake
     * @param y solun rivi
     * @return Solun arvo
     */
    public int getCell(int x, int y) {
        return this.data[x + y * 9 - 10];
    }    

    /**
     * Metodi palauttaa tyhjien solujen määrän
     * 
     * @return Solun arvo
     */
    public int numberOfEmptyCells() {
        return empty;
    }
    
    /**
     * Metodi tarkistaa että solun arvo on hyväksyttävä, eli samaa numeroa ei
     * ole rivillä, sarakkeessa tai ryhmässä.
     * @param x solun sarake
     * @param y solun rivi
     * @return Onko solun arvo ok
     */
    public boolean checkCell(int x, int y) {
        x--;
        y--;
        boolean ok = true;
        int number = this.data[x + y * 9];
        for (int i = 0; i < 9; i++) {
            if (i != y && this.data[x + i * 9] == number) {
                ok = false;
            } 
            if (i != x && this.data[i + y * 9] == number) {
                ok = false;
            }
        }
        int yy = y - y % 3;
        int xx = x - x % 3;
        
        for (int i = yy; i < yy + 3; i++) {
            for (int j = xx; j < xx + 3; j++) {
                if (i != y && j != x && this.data[i * 9 + j] == number) {
                    ok = false;
                }
            }
        }
        return ok;
    }
    
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < 81; i++) {
            if (this.data[i] == 0) {
                string.append(".");
            } else {
                string.append(this.data[i]);
            }
            if ((i % 9 == 2) || (i % 9 == 5)) {
                string.append("!");
            }
            if (i % 9 == 8) {
                string.append("\n");
            }
            if ((i == 26) || (i == 53)) {
                string.append("---!---!---\n");
            }
        }
        return string.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grid other = (Grid) obj;
        for (int x = 1; x <= 9; x++) {
            for (int y = 1; y <= 9; y++) {
                if (this.getCell(x, y)!=other.getCell(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}
