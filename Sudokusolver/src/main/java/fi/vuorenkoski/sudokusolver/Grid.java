package fi.vuorenkoski.sudokusolver;

/**
 * Luokka kuvaa Sudoku-ruudukkoa
 * @author Lauri Vuorenkoski
 */
public class Grid {
    private int[] data;
    private int empty;
    private int size;
    private int dataSize;
    private int gridSize;

    public Grid(int size) {
        this.size = size;
        this.gridSize = size * size;
        this.dataSize = this.gridSize * this.gridSize;
        this.data = new int[dataSize];
        this.empty = dataSize;
        for (int i = 0; i < dataSize; i++) {
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
        for (int y = 1; y <= this.gridSize; y++) {
            int i = 0;
            for (int x = 1; x <= this.gridSize; x++) {
                if (lines[j].charAt(i) != '.') {
                    if (this.size == 3) {
                        this.setCell(x, y, lines[j].charAt(i) - '0');
                    }
                    if (this.size == 4) {
                        if (lines[j].charAt(i) <= '9') {
                            this.setCell(x, y, lines[j].charAt(i) - 47);
                        } else {
                            this.setCell(x, y, lines[j].charAt(i) - 54);
                        }
                    }
                    if (this.size == 5) {
                        this.setCell(x, y, lines[j].charAt(i) - 64);
                    }
                }
                i++;
                if (x % this.size == 0) {
                    i++;
                }
            }
            j++;
            if (y % this.size == 0) {
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
        if (number > 0 && this.data[x - 1 + (y - 1) * this.gridSize] == 0) {
            this.empty--;
        } else if (number == 0 && this.data[x - 1 + (y - 1) * this.gridSize] != 0) {
            this.empty++;
        }

        this.data[x - 1 + (y - 1) * this.gridSize] = number;
    }
            
    /**
     * Metodi palauttaa solun arvon. 
     * 
     * @param x solun sarake
     * @param y solun rivi
     * @return Solun arvo
     */
    public int getCell(int x, int y) {
        return this.data[x - 1 + (y - 1) * this.gridSize];
    }    

    /**
     * Metodi palauttaa tyhjien solujen määrän
     * 
     * @return Solun arvo
     */
    public int numberOfEmptyCells() {
        return empty;
    }

    public int getSize() {
        return size;
    }

    public int getGridSize() {
        return gridSize;
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
        int number = this.data[x + y * this.gridSize];
        for (int i = 0; i < this.gridSize; i++) {
            if (i != y && this.data[x + i * this.gridSize] == number) {
                ok = false;
            } 
            if (i != x && this.data[i + y * this.gridSize] == number) {
                ok = false;
            }
        }
        int yy = y - y % this.size;
        int xx = x - x % this.size;
        
        for (int i = yy; i < yy + this.size; i++) {
            for (int j = xx; j < xx + this.size; j++) {
                if (i != y && j != x && this.data[i * this.gridSize + j] == number) {
                    ok = false;
                }
            }
        }
        return ok;
    }
    
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < this.dataSize; i++) {
            if (i % this.gridSize != 0 && i % this.size == 0) {
                string.append("!");
            }
            if (this.data[i] == 0) {
                string.append(".");
            } else {
                if (this.size == 3) {
                    string.append(this.data[i]);
                }
                if (this.size == 4) {
                    if (this.data[i] < 11) {
                        string.append(this.data[i] - 1);
                    } else {
                        string.append((char) (this.data[i] + 54));
                    }
                }
                if (this.size == 5) {
                    string.append((char) (this.data[i] + 64));
                }
            }
            if (i != this.dataSize - 1 && (i + 1) % this.gridSize == 0) {
                string.append("\n");
            }
            if (i != this.dataSize - 1 && (i + 1) % (this.gridSize * this.size) == 0) {
                if (this.size == 3) {
                    string.append("---!---!---\n");
                }
                if (this.size == 4) {
                    string.append("----!----!----!----\n");
                }
                if (this.size == 5) {
                    string.append("-----!-----!-----!-----!-----\n");
                }
            }
        }
        string.append("\n");
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
        if (this.size !=  other.getSize()) {
            return false;
        }
        for (int x = 1; x <= this.gridSize; x++) {
            for (int y = 1; y <= this.gridSize; y++) {
                if (this.getCell(x, y) != other.getCell(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}
