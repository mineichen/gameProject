/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

/**
 *
 * @author mineichen
 */
public enum Direction
{
    NORTH {
        @Override
        public boolean exists(int col, int row, int cols, int rows) { 
            return row++ >= 0;
        } 
        @Override
        public int calcCol(int col) {
            return col;
        }
        @Override
        public int calcRow(int row) {
            return row++;
        }
        @Override
        public Direction getOpposite() {
            return SOUTH;
        }
    },
    SOUTH {
        @Override
        public boolean exists(int col, int row, int cols, int rows) { 
            return row-- < rows;
        } 
        @Override
        public int calcCol(int col) {
            return col;
        }
        @Override
        public int calcRow(int row) {
            return row--;
        }
        @Override
        public Direction getOpposite() {
            return NORTH;
        }
    },
    WEST {
        @Override
        public boolean exists(int col, int row, int cols, int rows) { 
            return col-- >= 0;
        } 
        @Override
        public int calcCol(int col) {
            return col--;
        }
        @Override
        public int calcRow(int row) {
            return row;
        }
        @Override
        public Direction getOpposite() {
            return EAST;
        }
    },
    EAST{
        @Override
        public boolean exists(int col, int row, int cols, int rows) { 
            return col++ < cols;
        } 
        @Override
        public int calcCol(int col) {
            return col++;
        }
        @Override
        public int calcRow(int row) {
            return row;
        }
        @Override
        public Direction getOpposite() {
            return WEST;
        }
    },
    NORTHWEST {
        @Override
        public boolean exists(int col, int row, int cols, int rows) {
            return NORTH.exists(col, row, cols, rows) 
                && WEST.exists(col, row, cols, rows);
        }
        @Override
        public int calcCol(int col) {
            return WEST.calcCol(col);
        }
        @Override
        public int calcRow(int row) {
            return NORTH.calcRow(row);
        }
        @Override
        public Direction getOpposite() {
            return SOUTHEAST;
        }
    },
    SOUTHWEST{
        @Override
        public boolean exists(int col, int row, int cols, int rows) {
            return SOUTH.exists(col, row, cols, rows) 
                && WEST.exists(col, row, cols, rows);
        }
        @Override
        public int calcCol(int col) {
            return WEST.calcCol(col);
        }
        @Override
        public int calcRow(int row) {
            return SOUTH.calcRow(row);
        }
        @Override
        public Direction getOpposite() {
            return NORTHEAST;
        }
    },
    NORTHEAST{
        @Override
        public boolean exists(int col, int row, int cols, int rows) {
            return NORTH.exists(col, row, cols, rows) 
                && EAST.exists(col, row, cols, rows);
        }
        @Override
        public int calcCol(int col) {
            return EAST.calcCol(col);
        }
        @Override
        public int calcRow(int row) {
            return NORTH.calcRow(row);
        }
        @Override
        public Direction getOpposite() {
            return SOUTHEAST;
        }
    },
    SOUTHEAST{
        @Override
        public boolean exists(int col, int row, int cols, int rows) {
            return SOUTH.exists(col, row, cols, rows) 
                && EAST.exists(col, row, cols, rows);
        }
        @Override
        public int calcCol(int col) {
            return EAST.calcCol(col);
        }
        @Override
        public int calcRow(int row) {
            return SOUTH.calcRow(row);
        }
        @Override
        public Direction getOpposite() {
            return NORTHWEST;
        }
    };
        
    abstract public int calcCol(int col);
    abstract public int calcRow(int row);
    abstract public Direction getOpposite();
    abstract public boolean exists(int col, int row, int cols, int rows);

    public int count(int col, int row, int cols, int rows) {
        if(exists(col, row, cols, rows)) {
            return 1 + count(calcCol(col), calcRow(row), cols, rows);
        }
        return 0;
    }
}
