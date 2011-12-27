/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fibers;

import java.util.Random;

/**
 *
 * @author jcatkeson, February 2009
 */
/* ************************************************************************************************************* */
public final class Logic {
  /* ****************************************************************************************************************************************************************************************************** */
  public static double infinity = (1.0e200);
  public static double fudge = (1.0 / infinity);
  /* ****************************************************************************************************************************************************************************************************** */
  public static boolean Multi_Xor(long bits, int bitlen) {// Returns XOR result on many bits.
    long onebit, bcnt, sum;
    if (false) {
      sum = 0;
      for (bcnt = 0; bcnt < bitlen; bcnt++) {
        sum += (bits & 0x1);// just add them up and return oddness
        bits >>= 1;// shift right
      }
      return ((sum & 0x1) == 0x1);// true if odd
    } else {
      sum = 0;
      for (bcnt = 0; bcnt < bitlen; bcnt++) {
        onebit = (bits >> bcnt) & 0x1;
        sum = sum ^ onebit;// toggle back and forth
      }
      return (sum > 0);
    }
  }
  //--------------------------------------------
  public static boolean Multi_And(long bits, int bitlen) {// Returns AND result on many bits.
    long onebit, bcnt, sum;
    sum = 1;
    for (bcnt = 0; bcnt < bitlen; bcnt++) {
      onebit = (bits >> bcnt) & 0x1;
      sum &= onebit;
    }
    return (sum > 0);
  // return ((~bits) > 0);// not definable by bitlen though.
  }
  //--------------------------------------------
  public static boolean Multi_Or(long bits) {// Returns OR result on many bits.
    return (bits > 0);
  }
  //--------------------------------------------
  public static void Bit_To(long Bits, int First, int Last, double[] vec) {/* Load a bit pattern into a vector. */
    for (int cnt = First; cnt <= Last; cnt++) {// least significant digit becomes lowest index
      vec[cnt] = (Bits & 1) == 1 ? 1.0 : -1.0;
      Bits = Bits >> 1;
    }
  }/* Bit_To */
  //--------------------------------------------
  public static long Bit_From(double[] vec, int First, int Last) {/* Load a vector into a bit pattern. */
    long Bits = 0;
    for (int cnt = First; cnt <= Last; cnt++) {// least significant digit becomes lowest index
      Bits |= ((vec[cnt] >= 0) ? 1 : 0) >> cnt;
    }
    return Bits;
  }/* Bit_From */
  //--------------------------------------------
  //static Random wheel = new Random(124);// random seed
  static Random wheel = new Random();// random seed
  //static Random wheel = new Random(1256);// random seed
  //static Random wheel = new Random(207784311651128);// random seed
  //static Random wheel = new Random(-8742448824652078965);// random seed
  //
};/* Logic */
