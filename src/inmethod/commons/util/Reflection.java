package inmethod.commons.util;

import java.lang.reflect.*;

public class Reflection {

  /**
   * 可動態取得未知的類別,進而使用其物件.
   * <pre>
   *   if we create a class and want to use it dynamically
   *   ex:
   *     1:  inmethod.mybean aObj = new mybean(String,String);
   *     2:
   *     Object obj[] = new Object[2];
   *     obj[0] = "ddd";
   *     obj[1] = "eee";
   *     inmethod.mybean aObject =
   *     (mybean) getObject("inmethod.mybean",obj);
   * </pre>
   * @param sClassName dynamic class name
   * @param objs Object array used as parameter
   * @return Object u want to new dynamically
   */
  public static Object newInstance(String sClassName ,Object[] objs) throws Exception{
      Class aClass = Class.forName(sClassName);
      Class aParameterClasses[] ;

      // Get all Constructors
      java.lang.reflect.Constructor[] aClassConstructors = aClass.getConstructors();
      for(int i=0;i<aClassConstructors.length;i++){
        aParameterClasses = aClassConstructors[i].getParameterTypes();
        if( aParameterClasses.length == 0 && objs==null )
          return aClassConstructors[i].newInstance();
        else if( objs!=null && aParameterClasses.length == objs.length ){
          Object aReturnObject = null;
          try{

            aReturnObject = aClassConstructors[i].newInstance( objs);
            return aReturnObject;
          }catch(Exception ee){
        	  ee.printStackTrace();
          }

        }

      }
      return null;
  }


  public static void main(String af[]){

    try{
       Object[] a1 = new Object[3];
       java.sql.Connection aConn  =null;
       a1[0] = aConn;
       Object k = newInstance("inmethod.commons.util.FromtwAuth",a1);

       System.out.println(k);
    }catch(Exception ex){
     ex.printStackTrace();
    }
  }
}