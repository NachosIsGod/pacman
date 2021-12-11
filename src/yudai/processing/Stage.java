package yudai.processing;

import processing.core.PApplet;

public class Stage extends PApplet {
    String[] map = {
            "111111111111111",
            "100000000000001",
            "100111111111111",
            "110111111000111",
            "110111111101111",
            "110000000011111",
            "110101111011111",
            "100001000011111",
            "101100000000011",
            "100010001001111",
            "111010000011111",
            "100010111111111",
            "101110111111111",
            "100000000000001",
            "111111111111111",
    };

    final int point_weight = 33;
    //final int pacman_weight = 8;

    enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE,
    }

    float pacmanX = 9;
    float pacmanY = 9;

    float bx;
    float by;

    long lastProckTime = System.currentTimeMillis();

    Direction pacmanDir = Direction.NONE;

    boolean bKeyPressed = false;

    @Override
    public void settings(){
        size(530,530);
    }

    @Override
    public void setup() {
        clear();
        stroke(255);

        //座標描画
        fill(255);
        textSize(20);
        text("1    2    3    4    5    6    7    8    9   10  11  12  13  14  15", 41, 30);

        textAlign(CENTER);
        for(int i = 0; i < 15; i++) {
            text(i+1, 17, 55 + 33*i);
        }

        //壁描画
        for (int i = 0; i < map.length; i++) {
            System.out.println(map[i]);

            for(int j=0; j<map[i].length();j++){

                char c = map[i].charAt(j);
                //i は縦、jは横

                if(c == '1'){

                    int numSpace = width - 497;

                    //四角い壁
                    stroke(0,0,205);
                    fill(255);
                    rect(j * point_weight + numSpace, i * point_weight + numSpace, point_weight, point_weight);

                    /*
                    //丸い壁
                    stroke(255,255,255);
                    strokeWeight(point_weight);
                    point(j * point_weight + point_weight/2, i * point_weight + point_weight/2);
                    */
                }
            }
        }
    }

    @Override
    public void draw(){

        //パックマン増殖防止
        noStroke();
        stroke(0);
        strokeWeight(point_weight -4);
        point(pacmanX * point_weight + point_weight/2,pacmanY * point_weight + point_weight/2);

        //動くよパックマンは
        //初期値のpacmanX,Yは9
        bx = pacmanX;
        by = pacmanY;

        if (!bKeyPressed && keyPressed){
            if(keyCode == 37) pacmanDir = Direction.LEFT;
            if(keyCode == 39) pacmanDir = Direction.RIGHT;
            if(keyCode == 40) pacmanDir = Direction.DOWN;
            if(keyCode == 38) pacmanDir = Direction.UP;
        }

        long now = System.currentTimeMillis();
        long dt = now - lastProckTime;

        if(dt > 60){
            if(pacmanDir == Direction.LEFT) bx -=0.2f;
            if(pacmanDir == Direction.RIGHT) bx +=0.2f;
            if(pacmanDir == Direction.DOWN) by +=0.2f;
            if(pacmanDir == Direction.UP) by -=0.2f;

            lastProckTime = now;
        }

        //位置計算

        //なぜかbx,yがめちゃ細かい数になってる問題修正
        /*
        bx *= 10;
        bx = Math.round(bx);
        bx /= 10;

        by *= 10;
        by = Math.round(by);
        by /= 10;
         */

        int floorBx = (int)Math.floor(bx);
        int floorBy = (int)Math.floor(by);
        int intBx, intBy;

        int overX = Math.round((bx - floorBx) * 10);
        int overY = Math.round((by - floorBy) * 10);

        if(overX >= 2 && overX <= 5){
            intBx = (int)Math.floor(bx);
        }else{
            intBx = (int)Math.ceil(bx);
        }
        if(overY >= 2 && overY <= 5){
            intBy = (int)Math.floor(by);
        }else{
            intBy = (int)Math.ceil(by);
        }

        System.out.println(bx + " が " + intBx + "    " + by + " が " + intBy);


        //壁の当たり判定
        if(intBx>0 && intBx<map.length && intBy>0 && intBy<map[intBy].length()){
            if(map[intBy].charAt(intBx) == '0'){
                pacmanX = bx;
                pacmanY = by;
            }
        }

        //パックマン参上
        noStroke();
        strokeWeight(point_weight-7);
        stroke(255,255,0);
        point(pacmanX * point_weight + point_weight/2,pacmanY * point_weight + point_weight/2);

        bKeyPressed = keyPressed;
    }
}
