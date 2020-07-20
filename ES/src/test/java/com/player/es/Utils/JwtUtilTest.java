package com.player.es.Utils;

import org.junit.jupiter.api.Test;

/**
 * jwt工具类
 */

public class JwtUtilTest {

    @Test
    public void toPrint() {
      JwtUtils DEMO = new JwtUtils();
      DEMO.toPrint();
      System.out.println(DEMO.getSecret());

    }

}

