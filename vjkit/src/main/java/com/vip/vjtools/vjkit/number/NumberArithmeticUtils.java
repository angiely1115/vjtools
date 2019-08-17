package com.vip.vjtools.vjkit.number;


import java.math.BigDecimal;
import java.util.Optional;

/**
 * 运算基础工具类
 */
public class NumberArithmeticUtils {
    private NumberArithmeticUtils() {
    }

    /**
	 * BigDecimal的加法运算封装
	 * @param b1
	 * @param bn
	 * @return
	 */
   public static BigDecimal safeAdd(BigDecimal b1, BigDecimal... bn) {
       if (null == b1) {
           b1 = BigDecimal.ZERO;
       }
       if (null != bn) {
           for (BigDecimal b : bn) {
               b1 = b1.add(null == b ? BigDecimal.ZERO : b);
           }
       }
       return b1;
   }
 
   /**
    * Integer加法运算的封装
    * @param b1   第一个数
    * @param bn   需要加的加法数组
    * @return
    */
   public static Integer safeAdd(Integer b1, Integer... bn) {
       if (null == b1) {
           b1 = 0;
       }
       Integer r = b1;
       if (null != bn) {
           for (Integer b : bn) {
               r += Optional.ofNullable(b).orElseGet(()->0);
           }
       }
       return r > 0 ? r : 0;
   }
 

   /**
    * BigDecimal的安全减法运算
    * @param b1		   被减数
    * @param bn        需要减的减数数组
    * @return
    */
   public static BigDecimal safeSubtract(BigDecimal b1, BigDecimal... bn) {
       if (null == b1) {
           b1 = BigDecimal.ZERO;
       }
       BigDecimal r = b1;
       if (null != bn) {
           for (BigDecimal b : bn) {
               r = r.subtract((null == b ? BigDecimal.ZERO : b));
           }
       }
       return  r;
   }
 
   /**
    * 整型的减法运算，小于0时返回0
    * @param b1
    * @param bn
    * @return
    */
   public static Integer safeSubtract(Integer b1, Integer... bn) {
       if (null == b1) {
           b1 = 0;
       }
       Integer r = b1;
       if (null != bn) {
           for (Integer b : bn) {
               r -= Optional.ofNullable(b).orElseGet(()-> 0);
           }
       }
       return  r > 0 ? r : 0;
   }

   /**
    * BigDecimal的除法运算封装
    * 默认返回小数位后2位，用于金额计算
    * @param b1
    * @param b2
    * @return
    */
   public static BigDecimal safeDivide(BigDecimal b1, BigDecimal b2) {
       if (BigDecimal.ZERO.compareTo(b2)==0) {
           throw new IllegalArgumentException("divided by 0");
       }
           return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
   }

    /**
     * 百分比
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal safeDividePercentage(BigDecimal b1, BigDecimal b2) {
        if (BigDecimal.ZERO.compareTo(b2)==0) {
            throw new IllegalArgumentException("divided by 0");
        }
        b1 = b1.multiply(BigDecimal.valueOf(100));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }
 
   /**
    * BigDecimal的乘法运算封装
    * @param b1
    * @param b2
    * @return
    */
   public static BigDecimal safeMultiply(BigDecimal b1, BigDecimal b2) {
       if (null == b1 || null == b2) {
           return BigDecimal.ZERO;
       }
       return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
   }
 
}