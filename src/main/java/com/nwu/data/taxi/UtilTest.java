package com.nwu.data.taxi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class UtilTest {
    public static void main(String[] args) {
        int x=10;
        int y=25;
        int z=x+y;
        Random rand = new Random();
        List<Integer> a = new ArrayList<>();
        for(int i=0; i<10; i++) {
            a.add(rand.nextInt(50));
        }

        a.forEach(v-> {if (v/2 == 0) v = v*2;
        });

        a.sort(Comparator.comparingInt(v -> v/2));
        System.out.printf(a.toString());
    }
}
