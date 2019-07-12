/**
 *@Description:
 */
package com.sbcm;

import java.math.BigDecimal;
import java.util.Random;


public class Test {

    public static BigDecimal getRandomMoney(RedPackage _redPackage) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_redPackage.remainSize == 1) {
            _redPackage.remainSize--;
            return _redPackage.remainMoney.setScale(2, BigDecimal.ROUND_DOWN);
        }

        BigDecimal random = BigDecimal.valueOf(Math.random());
        BigDecimal min   = BigDecimal.valueOf(0.01);

        BigDecimal halfRemainSize = BigDecimal.valueOf(_redPackage.remainSize).divide(new BigDecimal(2), BigDecimal.ROUND_UP);
        BigDecimal max1 = _redPackage.remainMoney.divide(halfRemainSize, BigDecimal.ROUND_DOWN);
        BigDecimal minRemainAmount = min.multiply(BigDecimal.valueOf(_redPackage.remainSize - 1)).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal max2 = _redPackage.remainMoney.subtract(minRemainAmount);
        BigDecimal max = (max1.compareTo(max2) < 0) ? max1 : max2;

        BigDecimal money = random.multiply(max).setScale(2, BigDecimal.ROUND_DOWN);
        money = money.compareTo(min) < 0 ? min: money;

        _redPackage.remainSize--;
        _redPackage.remainMoney = _redPackage.remainMoney.subtract(money).setScale(2, BigDecimal.ROUND_DOWN);;
        return money;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            RedPackage moneyPackage = new RedPackage();
            moneyPackage.remainMoney = BigDecimal.valueOf(100);
            moneyPackage.remainSize = 5;

            while (moneyPackage.remainSize != 0) {
                System.out.print(getRandomMoney(moneyPackage)  + "   ");
            }

            System.out.println();
        }
    }

    static class RedPackage {
        int    remainSize;
        BigDecimal remainMoney;
    }
}