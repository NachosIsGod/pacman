package yudai.processing;

import processing.core.PApplet;

public class Stage extends PApplet {
    String[] map = {
            "111111111111111",
            "100000010000001",
            "101111010111101",
            "101100000001101",
            "100001101100001",
            "101110000011101",
            "101000010000101",
            "101011000110101",
            "100011101100001",
            "100111000111001",
            "110000010000011",
            "100100111001001",
            "110110000011011",
            "100000010000001",
            "111111111111111",
    };

    final int point_weight = 33;
    //final int pacman_weight = 8;

    enum pacmanDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE,
    }

    enum enemyDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    float pacmanX = 7;
    float pacmanY = 9;

    float enemyX = 7;
    float enemyY = 3;

    float pbx;
    float pby;

    float ebx;
    float eby;

    int numSpace = width - 497;

    long eLastProckTime = System.currentTimeMillis();
    long pLastProckTime = System.currentTimeMillis();

    pacmanDirection pacmanDir = pacmanDirection.NONE;

    enemyDirection enemyDir = enemyDirection.UP;

    boolean bKeyPressed = false;

    @Override
    public void settings(){
        size(530,530);
    }

    @Override
    public void draw(){
        clear();

        drawBackground();
        drawEnemy();
        drawPacman();
    }

    private void drawEnemy() {
        int numSpace = width - 497;

        //パックマン増殖防止
        noStroke();
        stroke(0);
        strokeWeight(point_weight - 4);
        point(enemyX * point_weight + point_weight / 2 + numSpace, enemyY * point_weight + point_weight / 2 + numSpace);

        ebx = enemyX;
        eby = enemyY;

        long enow = System.currentTimeMillis();
        long edt = enow - eLastProckTime;

        if (edt > 25) {
            int intEbx = Math.round(ebx);
            int intEby = Math.round(eby);

            if (enemyDir == enemyDirection.LEFT) {
                ebx -= 0.2f;
                intEbx = Math.round(ebx - 0.5f);
            }
            if (enemyDir == enemyDirection.RIGHT) {
                ebx += 0.2f;
                intEbx = Math.round(ebx + 0.5f);
            }
            if (enemyDir == enemyDirection.DOWN) {
                eby += 0.2f;
                intEby = Math.round(eby + 0.5f);
            }
            if (enemyDir == enemyDirection.UP) {
                eby -= 0.2f;
                intEby = Math.round(eby - 0.5f);
            }

            //壁の当たり判定
            if (intEby > 0 && intEby < map.length && intEbx > 0 && intEbx < map[intEby].length()) {
                if (map[intEby].charAt(intEbx) == '0') {
                    enemyX = ebx;
                    enemyY = eby;
                }else{
                    while(map[intEby].charAt(intEbx) == '0') {

                        double random = Math.random();
                        System.out.println(random);

                        random *= 10;
                        System.out.println(random);

                        random = Math.ceil(random);
                        System.out.println(random);

                        random -= 2;
                        System.out.println(random);

                        random /= 2;
                        System.out.println(random);

                        random = Math.floor(random);
                        System.out.println(random);

                        int dirRandom = (int) random;
                        System.out.println(dirRandom);

                        switch (dirRandom) {
                            case 1:
                                enemyDir = enemyDirection.UP;
                                System.out.println("↑");
                                break;

                            case 2:
                                enemyDir = enemyDirection.DOWN;
                                System.out.println("↓");
                                break;

                            case 3:
                                enemyDir = enemyDirection.LEFT;
                                System.out.println("←");
                                break;

                            default:
                                enemyDir = enemyDirection.RIGHT;
                                System.out.println("→");
                                break;
                        }
                    }
                }
            }
            eLastProckTime = enow;
        }
        //パックマン参上
        noStroke();
        strokeWeight(point_weight-7);
        stroke(0,0,255);
        point(enemyX * point_weight + point_weight/2+numSpace,enemyY * point_weight + point_weight/2+numSpace);

    }

    private void drawPacman(){
        int numSpace = width - 497;

        //パックマン増殖防止
        noStroke();
        stroke(0);
        strokeWeight(point_weight -4);
        point(pacmanX * point_weight + point_weight/2+numSpace,pacmanY * point_weight + point_weight/2+numSpace);

        pbx = pacmanX;
        pby = pacmanY;

        if (!bKeyPressed && keyPressed){
            if(keyCode == 37) pacmanDir = pacmanDirection.LEFT;
            if(keyCode == 39) pacmanDir = pacmanDirection.RIGHT;
            if(keyCode == 40) pacmanDir = pacmanDirection.DOWN;
            if(keyCode == 38) pacmanDir = pacmanDirection.UP;
        }

        long pnow = System.currentTimeMillis();
        long pdt = pnow - pLastProckTime;

        if(pdt > 25){
            int intPbx = Math.round(pbx);
            int intPby = Math.round(pby);

            if(pacmanDir == pacmanDirection.LEFT){
                pbx -=0.2f;
                intPbx = Math.round(pbx - 0.5f);
            }
            if(pacmanDir == pacmanDirection.RIGHT){
                pbx +=0.2f;
                intPbx = Math.round(pbx + 0.5f);
            }
            if(pacmanDir == pacmanDirection.DOWN){
                pby +=0.2f;
                intPby = Math.round(pby + 0.5f);
            }
            if(pacmanDir == pacmanDirection.UP){
                pby -=0.2f;
                intPby = Math.round(pby - 0.5f);
            }

            //壁の当たり判定
            if(intPby>0 && intPby<map.length && intPbx>0 && intPbx<map[intPby].length()){
                if(map[intPby].charAt(intPbx) == '0'){
                    pacmanX = pbx;
                    pacmanY = pby;
                }
            }
            pLastProckTime = pnow;
        }

        //パックマン参上
        noStroke();
        strokeWeight(point_weight-7);
        stroke(255,255,0);
        point(pacmanX * point_weight + point_weight/2+numSpace,pacmanY * point_weight + point_weight/2+numSpace);

        bKeyPressed = keyPressed;
    }

    private void drawBackground(){
        //座標描画
        fill(255);
        textSize(20);
        noStroke();
        strokeWeight(0);
        for(int i =0; i< 15; i++){
            text(i, 49 + 33*i, 30);
        }

        textAlign(CENTER);
        for(int i = 0; i < 15; i++) {
            text(i, 17, 55 + 33*i);
        }

        //壁描画
        for (int i = 0; i < map.length; i++) {

            for(int j=0; j<map[i].length();j++){

                char c = map[i].charAt(j);
                //i は縦、jは横

                if(c == '1'){

                    int numSpace = width - 497;

                    //四角い壁
                    stroke(0,0,205);
                    fill(255);
                    rect(j * point_weight + numSpace, i * point_weight + numSpace, point_weight, point_weight);
                }
            }
        }
    }
}