package org.sandy.math

import java.util

/**
 * Created by sandy on 12/4/2015.
 */
class MyBigInteger(numStr : String) {

  if(numStr == null || numStr.length == 0){
    throw new RuntimeException("Why not put some valid entries")
  }
  var number = new Array[Short](numStr.length)

  var index = 0
  for(x <- numStr){
    if( x.toShort > 48 + 9 || x.toShort < 48)
      throw new RuntimeException("Invalid entry:"+ numStr)

    number(index) = (x - 48 ).toShort
    index += 1
  }

  def add( num : MyBigInteger): MyBigInteger ={
    var num1 = this.number
    var num2 = num.number
    if(this.number.length < num.number.length){
      val temp = num1
      num1 = num2
      num2 = temp
    }

    var result = new String
    var overFlow = 0
    var j = num2.length - 1
    for(i <- (num1.length - 1).to(0, -1)){
      var num2j = 0
      if(j >= 0) {
        num2j = num2(j)
        j -= 1
      }
      val r = num1(i) + num2j + overFlow
      if(r >= 10){
        result = (r-10) + result
        overFlow = 1
      } else {
        result = r + result
        overFlow = 0
      }
    }
    if(overFlow == 1){
      result = "1" + result
    }
    return new MyBigInteger(result.toString)
  }

  def mul(myBigInteger: MyBigInteger): MyBigInteger={
    val num1 = this.number
    val num2 = myBigInteger.number

    var overflow = 0
    var result = new MyBigInteger("0")
    var pow = 0
    for( i <- (num1.length-1).to(0, -1)){
      val x = num1(i)
      var r = ""
      for(j <- (num2.length-1).to(0,-1)){
        var t = overflow
        val y = num2(j)
        t += x*y
        if(t > 10){
          r = (t%10) + r
          overflow = (t/10)
        } else{
          overflow = 0
          r = t + r
        }
      }
      while(overflow > 0){
        r = (overflow%10) + r
        overflow = (overflow/10)
      }
      for(i <- 0 until pow)
        r += "0"
      pow += 1
      result = result.add(new MyBigInteger(r))
    }
    return result
  }
  override def toString(): String ={
    return number.mkString("")
  }
}

object  Main{
  def main(args: Array[String]) {
    val num1 = new MyBigInteger("99")
    val num2 = new MyBigInteger("199")
    println(String.format("%s + %s = %s", num1, num2, num1.add(num2) ) )
    println(String.format("%s * %s = %s", num1, num2, num1.mul(num2) ) )
  }
}