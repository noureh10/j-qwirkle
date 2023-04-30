package g58146.qwirkle.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import g58146.qwirkle.model.QwirkleException;

import static org.junit.jupiter.api.Assertions.*;
import static g58146.qwirkle.model.Color.*;
import static g58146.qwirkle.model.Direction.*;
import static g58146.qwirkle.model.Shape.*;
import static g58146.qwirkle.model.QwirkleTestUtils.*;


class GridTest {
    private Grid grid;
    private Grid modGrid;
    

    @BeforeEach
    void setUp() {
        grid = new Grid();
    }
    
    @Test
    void firstAdd_one_tile() {
        var tile = new Tile(BLUE, CROSS);
        grid.firstAdd(RIGHT, tile);
        assertSame(get(grid, 0, 0), tile);
    }

    @Test
    void rules_sonia_a() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        assertEquals(t1, get(grid, 0, 0));
        assertEquals(t2, get(grid, -1, 0));
        assertEquals(t3, get(grid, -2, 0));
    }
    
    @Test
    void rules_sonia_a_adapted_to_fail() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, DIAMOND);
        assertThrows(QwirkleException.class, ()->{
            grid.firstAdd(UP, t1, t2, t3);
        });
        assertNull(get(grid,0,0));
        assertNull(get(grid,-1,0));
        assertNull(get(grid,-2,0));
    }
    @Test
    void first_add_test() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, STAR);
        grid.firstAdd(RIGHT, t1, t2, t3);
        assertEquals(get(grid,0,0),t1);
        assertEquals(get(grid,0,1),t2);
        assertEquals(get(grid,0,2),t3);
    }
    @Test
    void first_add_test_2() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, STAR);
        grid.firstAdd(LEFT, t1, t2, t3);
        assertEquals(get(grid,0,0),t1);
        assertEquals(get(grid,0,-1),t2);
        assertEquals(get(grid,0,-2),t3);
    } 
    @Test
    void firstAdd_cannot_be_called_twice() {
        Tile redcross = new Tile(RED, CROSS);
        Tile reddiamond = new Tile(RED, DIAMOND);
        grid.firstAdd(UP, redcross, reddiamond);
        assertThrows(QwirkleException.class, () -> grid.firstAdd(DOWN, redcross, reddiamond));
    }

    @Test
    void firstAdd_must_be_called_first_simple() {
        Tile redcross = new Tile(RED, CROSS);
        assertThrows(QwirkleException.class, () -> add(grid, 0, 0, redcross));
    }
    
    @Test
    @DisplayName("get outside the grid should return null, not throw")
    void can_get_tile_outside_virtual_grid() {
        var g = new Grid();
        assertDoesNotThrow(() -> get(g, -250, 500));
        assertNull(get(g, -250, 500));
    }
    // Test for the rules
    @Test
    void sonia_move_1(){
        this.modGrid = this.grid;
        //Adding all the tiles
        addTile(modGrid, 0, 0, Color.RED, Shape.PLUS);
        addTile(modGrid, 1, 0, Color.RED, Shape.DIAMOND);
        addTile(modGrid, 2, 0, Color.RED, Shape.ROUND);
        addTile(modGrid, 3, 0, Color.RED, Shape.SQUARE);
        addTile(modGrid, -1, -1, Color.GREEN, Shape.STAR);
        addTile(modGrid, 0, -1, Color.GREEN, Shape.PLUS);
        addTile(modGrid, 1, -1, Color.GREEN, Shape.DIAMOND);
        addTile(modGrid, 2, -1, Color.GREEN, Shape.ROUND);
        addTile(modGrid, 2, 1, Color.BLUE, Shape.ROUND);
        addTile(modGrid, 3, 1, Color.BLUE, Shape.SQUARE);
        addTile(modGrid, 3, 2, Color.PURPLE, Shape.SQUARE);
    }
    @Test
    void sonia_move_1_fail(){
        sonia_move_1();
        assertThrows(QwirkleException.class, () -> 
                addTile(modGrid,2,-2,Color.GREEN,Shape.ROUND));
        assertNull(get(grid,2,-2));
    }
    @Test
    void cedric_move_1(){
        sonia_move_1();
        addTile(modGrid,3,3,Color.ORANGE,Shape.SQUARE);
        addTile(modGrid,4,3,Color.RED,Shape.SQUARE);
    }
    @Test
    void cedric_move_1_fail(){
        cedric_move_1();
        assertThrows(QwirkleException.class, () -> 
                addTile(modGrid,1,1,Color.YELLOW,Shape.DIAMOND));
    }
    @Test
    void elvire_move_1(){
        cedric_move_1();
        addTile(modGrid,-1,-2,Color.YELLOW,Shape.STAR);
        addTile(modGrid,-1,-3,Color.ORANGE,Shape.STAR);
    }
    @Test
    void elvire_move_fail(){
        elvire_move_1();
        assertThrows(QwirkleException.class, () -> 
                addTile(modGrid,0,-2,Color.BLUE,Shape.DIAMOND));
    }
    @Test
    void vincent_move_1(){
        elvire_move_1();
        addTile(modGrid,0,-3,Color.ORANGE,Shape.CROSS);
        addTile(modGrid,1,-3,Color.ORANGE,Shape.DIAMOND);
        
    }
    @Test
    void vincent_move_1_fail(){
        vincent_move_1();
        assertThrows(QwirkleException.class, () -> 
                addTile(modGrid,1,-2,Color.YELLOW,Shape.ROUND));
    }
    @Test 
    void sonia_move_2(){
        vincent_move_1();
        addTile(modGrid,1,-2,Color.YELLOW,Shape.DIAMOND);
        addTile(modGrid,2,-2,Color.YELLOW,Shape.ROUND);
    }
    @Test
    void sonia_move_2_fail(){
        sonia_move_2();
        assertThrows(QwirkleException.class, () -> 
                addTile(modGrid,2,-3,Color.PURPLE,Shape.ROUND));
    }
    @Test
    void cedric_move_2(){
        sonia_move_2();
        addTile(modGrid,-1,0,Color.RED,Shape.STAR);
    }
    @Test
    void cedric_move_2_fail(){
        cedric_move_2();
         assertThrows(QwirkleException.class, () -> 
                addTile(modGrid,0,-2,Color.PURPLE,Shape.PLUS));
    }
    @Test
    void elvire_move_2(){
        cedric_move_2();
        addTile(modGrid,4,-1,Color.ORANGE,Shape.CROSS);
        addTile(modGrid,4,0,Color.RED,Shape.CROSS);
        addTile(modGrid,4,1,Color.BLUE,Shape.CROSS);
    }
    @Test
    void elvire_move_2_fail(){
        elvire_move_2();
        addTile(modGrid,2,4,Color.PURPLE,Shape.CROSS);
    }
    @Test
    void vincent_move_2(){
        elvire_move_2();
        addTile(modGrid,3,4,Color.YELLOW,Shape.SQUARE);
        addTile(modGrid,4,4,Color.BLUE,Shape.SQUARE);
    }
    @Test
    void vincent_move_2_fail(){
        vincent_move_2();
         assertThrows(QwirkleException.class, () -> 
                addTile(modGrid,2,4,Color.YELLOW,Shape.CROSS));
    }
    private void addTile(Grid grid,int row,int col,Color c, Shape s){
        grid.add(createTileAtpos(row, col, new Tile(c, s)));
    }
}