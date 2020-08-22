package com.es.Utils;

import org.junit.jupiter.api.Test;
import com.player.es.Utils.JwtUtils;

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

