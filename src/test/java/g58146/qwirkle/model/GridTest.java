package g58146.qwirkle.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static g58146.qwirkle.model.QwirkleTestUtils.*;

import static org.junit.jupiter.api.Assertions.*;
import static g58146.qwirkle.model.Color.*;
import static g58146.qwirkle.model.Direction.*;
import static g58146.qwirkle.model.Shape.*;

class GridTest {
    private Grid grid;
    private Grid modGrid;
    private final int CENTER=45;

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
    void checkingEmptyGrid(){
        rules_sonia_a();
        assertFalse(grid.isEmpty());
    }

    @Test
    void checkIfTilesAreRemoved(){
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, CROSS);
        var t5 = new Tile(RED, ROUND);
        assertThrows(QwirkleException.class, ()-> addLine(grid, 0, -3,UP, t4, t5));
        //Check if tiles set after the error are removed
        assertNull(get(grid,0,-3));
        assertNull(get(grid,0,-4));
        //Check if tiles set before the error are still there
        assertEquals(t1, get(grid, 0, 0));
        assertEquals(t2, get(grid, -1, 0));
        assertEquals(t3, get(grid, -2, 0));
    }

    @Test
    void rules_sonia_a_adapted_to_fail() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, DIAMOND);
        assertThrows(QwirkleException.class, ()-> grid.firstAdd(UP, t1, t2, t3));
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

    @Test
    void check_if_tile_can_be_placed(){
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, STAR);
        var placableTile = new Tile(RED, CROSS);
        grid.firstAdd(UP, t1, t2, t3);
        assertTrue(grid.checkPlayableMove(placableTile));
    }

    @Test
    void check_if_tile_cant_be_placed(){
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, STAR);
        var placableTile = new Tile(BLUE, CROSS);
        grid.firstAdd(RIGHT, t1, t2, t3);
        assertFalse(grid.checkPlayableMove(placableTile));
    }

    @Test
    void deck_greater_than_7(){
        this.modGrid = this.grid;
        var t1 = createTileAtpos(0,0,createTile(RED,ROUND));
        var t2 = createTileAtpos(0,1,createTile(RED,DIAMOND));
        var t3 = createTileAtpos(0,2,createTile(RED,STAR));
        var t4 = createTileAtpos(0,3,createTile(RED,CROSS));
        var t5 = createTileAtpos(0,4,createTile(RED,PLUS));
        var t6 = createTileAtpos(0,5,createTile(RED,DIAMOND));
        var t7 = createTileAtpos(1,0,createTile(BLUE,ROUND));
        assertThrows(QwirkleException.class, () ->
                modGrid.add(t1,t2,t3,t4,t5,t6,t7));
    }
    // Test for the rules
    @Test
    void sonia_move_1(){
        this.modGrid = this.grid;
        int score;
        var t1 = createTile(RED, PLUS);
        var t2 = createTile(RED, DIAMOND);
        var t3 = createTile(RED, ROUND);
        int expected = 3;
        score=modGrid.firstAdd(DOWN, t1, t2, t3);
        assertEquals(expected,score);
    }

    @Test
    void sonia_move_1_fail(){
        sonia_move_1();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,3,0,Color.GREEN,Shape.ROUND));
        assertNull(get(grid,3,0));
    }

    @Test
    void cedric_move_1(){
        sonia_move_1();
        int score;
        var t1 = createTile(RED,SQUARE);
        var t2 = createTile(BLUE,SQUARE);
        var t3 = createTile(PURPLE,SQUARE);
        int expected = 7;
        score=addLine(modGrid,3,0,RIGHT,t1,t2,t3);
        assertEquals(expected,score);
    }

    @Test
    void cedric_move_1_fail(){
        cedric_move_1();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,3,3,YELLOW,DIAMOND));
    }

    @Test
    void elvire_move_1(){
        cedric_move_1();
        int score;
        int expected = 4;
        score=addTile(modGrid,2,1,BLUE,ROUND);
        assertEquals(expected,score);
    }

    @Test
    void elvire_move_1_fail(){
        elvire_move_1();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,2,2,YELLOW,ROUND));
    }

    @Test
    void vincent_move_1(){
        elvire_move_1();
        int score;
        var t1 = createTile(GREEN,PLUS);
        var t2 = createTile(GREEN,DIAMOND);
        int expected = 6;
        score=addLine(modGrid,0,-1,DOWN,t1,t2);
        assertEquals(expected,score);
    }

    @Test
    void vincent_move_1_fail(){
        vincent_move_1();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,2,-1,GREEN,DIAMOND));
    }

    @Test
    void sonia_move_2(){
        vincent_move_1();
        int score;
        var t1 = createTileAtpos(-1,-1,createTile(GREEN,STAR));
        var t2 = createTileAtpos(2,-1,createTile(GREEN,ROUND));
        int expected = 7;
        score=modGrid.add(t1,t2);

        assertEquals(expected,score);
    }

    @Test
    void sonia_move_2_fail(){
        sonia_move_2();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,0,-1,RED,CROSS));
    }

    @Test
    void cedric_move_2(){
        sonia_move_2();
        int score;
        var t1 = createTile(ORANGE,SQUARE);
        var t2 = createTile(RED,SQUARE);
        int expected = 6;
        score=addLine(modGrid,3,3,DOWN,t1,t2);
        assertEquals(expected,score);
    }

    @Test
    void cedric_move_2_fail(){
        cedric_move_2();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,3,2,ORANGE,SQUARE));
    }

    @Test
    void elvire_move_2(){
        cedric_move_2();
        int score;
        var t1 = createTile(YELLOW,STAR);
        var t2 = createTile(ORANGE,STAR);
        int expected = 3;
        score=addLine(modGrid,-1,-2,LEFT,t1,t2);
        assertEquals(expected,score);
    }

    @Test
    void elvire_move_2_fail(){
        elvire_move_2();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,0,-2,YELLOW,STAR));
    }

    @Test
    void vincent_move_2(){
        elvire_move_2();
        int score;
        var t1 = createTile(ORANGE,CROSS);
        var t2 = createTile(ORANGE,DIAMOND);
        int expected = 3;
        score=addLine(modGrid,0,-3,DOWN,t1,t2);
        assertEquals(expected,score);
    }

    @Test
    void vincent_move_2_fail(){
        vincent_move_2();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,0,-2,ORANGE,DIAMOND));
    }

    @Test
    void sonia_move_3(){
        vincent_move_2();
        int score;
        var t1 = createTile(YELLOW,DIAMOND);
        var t2 = createTile(YELLOW,ROUND);
        int expected = 10;
        score=addLine(modGrid,1,-2,DOWN,t1,t2);
        assertEquals(expected,score);
    }

    @Test
    void sonia_move_3_fail(){
        sonia_move_3();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,1,-2,YELLOW,ROUND));
    }

    @Test
    void cedric_move_3(){
        sonia_move_3();
        int score;
        var t1 = createTile(RED,STAR);
        int expected = 9;
        score=addOneTile(modGrid,-1,0,t1);
        assertEquals(expected,score);
    }

    @Test
    void cedric_move_3_fail(){
        cedric_move_3();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,0,0,RED,STAR));
    }

    @Test
    void elvire_move_3(){
        cedric_move_3();
        int score;
        var t1 = createTile(BLUE,CROSS);
        var t2 = createTile(RED,CROSS);
        var t3 = createTile(ORANGE,CROSS);
        int expected = 18;
        score=addLine(modGrid,4,1,LEFT,t1,t2,t3);
        assertEquals(expected,score);
    }

    @Test
    void elvire_move_3_fail(){
        elvire_move_3();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,3,1,BLUE,CROSS));
    }

    @Test
    void vincent_move_3(){
        elvire_move_3();
        int score;
        var t1 = createTile(YELLOW,SQUARE);
        var t2 = createTile(BLUE,SQUARE);
        int expected = 9;
        score=addLine(modGrid,3,4,DOWN,t1,t2);
        assertEquals(expected,score);
    }

    @Test
    void vincent_move_3_fail(){
        vincent_move_3();
        assertThrows(QwirkleException.class, () ->
                addTile(modGrid,3,3,YELLOW,SQUARE));
    }

    // Scoring tests
    @Test
    void firstMoveScore(){
        this.modGrid = this.grid;
        var t1 = createTile(RED, CROSS);
        var t2 = createTile(RED, DIAMOND);
        var t3 = createTile(RED, ROUND);
        int expected = 3;
        int result = modGrid.firstAdd(UP,t1,t2,t3);
        assertEquals(expected,result);
    }
    @Test
    void simpleQwirkle(){
        this.modGrid = this.grid;
        var t1 = createTile(RED, CROSS);
        var t2 = createTile(RED, DIAMOND);
        var t3 = createTile(RED, ROUND);
        var t4 = createTile(RED, STAR);
        var t5 = createTile(RED, SQUARE);
        var t6 = createTile(RED, PLUS);
        int expected = 12;
        int result = modGrid.firstAdd(UP,t1,t2,t3,t4,t5,t6);
        assertEquals(expected,result);
    }
    @Test
    void plicPlocScore(){
        firstMoveScore();
        var t1 = createTileAtpos(-1,-1,new Tile(BLUE,DIAMOND));
        var t2 = createTileAtpos(-1,1,new Tile(PURPLE,DIAMOND));
        int expected = 3;
        int result = modGrid.add(t1,t2);
        assertEquals(expected,result);
    }
    @Test
    void oneTileTestScore(){
        plicPlocScore();
        var t1 = createTile(PURPLE,ROUND);
        int expected = 4;
        int result = addOneTile(modGrid,-2,1,t1);
        assertEquals(expected,result);
    }
    @Test
    void qwirkleScore(){
        plicPlocScore();
        var t1 = createTile(YELLOW,DIAMOND);
        var t2 = createTile(ORANGE,DIAMOND);
        var t3 = createTile(GREEN,DIAMOND);
        int expected = 12;
        int result = addLine(modGrid,-1,2,RIGHT,t1,t2,t3);
        assertEquals(expected,result);
    }
    @Test
    void doubleQwirkle(){
        //First line
        var t1 = createTile(RED, CROSS);
        var t2 = createTile(RED, DIAMOND);
        var t3 = createTile(RED, ROUND);
        var t4 = createTile(RED, STAR);
        var t5 = createTile(RED, SQUARE);
        grid.firstAdd(DOWN,t1,t2,t3,t4,t5);
        //Second line
        var t6 = createTile(RED, PLUS);
        var t7 = createTile(BLUE, PLUS);
        var t8 = createTile(GREEN, PLUS);
        var t9 = createTile(YELLOW, PLUS);
        var t10 = createTile(ORANGE, PLUS);
        var t11 = createTile(PURPLE, PLUS);
        int expected = 24;
        int result =addLine(grid,5,0,RIGHT,t6,t7,t8,t9,t10,t11);
        assertEquals(expected,result);
    }

    private int addTile(Grid grid,int row,int col,Color c, Shape s){
        return grid.add(createTileAtpos(row, col,new Tile(c,s)));
    }

    private int addLine(Grid grid,int row,int col, Direction d,Tile...t){
        return grid.add(row+CENTER,col+CENTER,d,t);
    }

    private int addOneTile(Grid grid,int row,int col,Tile t){
        return grid.add(row+CENTER,col+CENTER,t);
    }

    private Tile createTile(Color c,Shape s){
        return new Tile(c,s);
    }
}