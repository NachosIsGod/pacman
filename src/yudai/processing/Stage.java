package yudai.processing;

import processing.core.PApplet;

public class Stage extends PApplet {
    String[] map = {
            "111111111111111",
            "100000000000001",
            "100111111111111",
            "110111111111111",
            "110111111111111",
            "110000000011111",
            "110101111011111",
            "100001000011111",
            "101100000000011",
            "100010001001111",
            "111010000011111",
            "100010111111111",
            "101110111111111",
            "100000000000000",
            "111111111111111",
    };

    final int point_weight = 33;
    //final int pacman_weight = 8;

    int pacmanX = 9;
    int pacmanY = 9;

    @Override
    public void settings(){
        size(500,500);
    }

    @Override
    public void setup() {
        clear();
        stroke(255);

        //壁描画
        int x, y;
        for (int i = 0; i < map.length; i++) {
            System.out.println(map[i]);

            for(int j=0; j<map[i].length();j++){

                char c = map[i].charAt(j);

                if(c == '1'){

                    stroke(0,0,205);
                    fill(0,0,0);
                    rect(j * point_weight, i * point_weight, point_weight, point_weight);

                    /*
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
        strokeWeight(point_weight -2);
        point(pacmanX * point_weight + point_weight/2,pacmanY * point_weight + point_weight/2);

        //動くよパックマンは
        int bx = pacmanX;
        int by = pacmanY;

        if(keyPressed){
            if(keyCode == 37) bx--;
            if(keyCode == 39) bx++;
            if(keyCode == 40) by++;
            if(keyCode == 38) by--;
        }

        if(bx>0 && bx<map.length && by>0 && by<map[by].length()){
            if(map[by].charAt(bx) == '0'){
                pacmanX = bx;
                pacmanY = by;
            }
        }

        //パックマン参上
        noStroke();
        strokeWeight(point_weight-5);
        stroke(255,255,0);
        point(pacmanX * point_weight + point_weight/2,pacmanY * point_weight + point_weight/2);
    }
}
