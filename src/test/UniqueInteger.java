package test;

import java.math.BigInteger;

/**
 * abstract class,Used to extend and create new bluetooth command.
 * 
 * @author william chen
 *
 */
public abstract class UniqueInteger {


    public int getHandleMessageID(){
      BigInteger bigInt = new BigInteger(getClass().getSimpleName().getBytes() );
      return bigInt.intValue();
    }
	
}
