package inmethod.commons.rdb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.sql.*;

/**
 * DataSet like SQL ResultSet.
 * @author william chen
 */
public class DataSet{

  private ArrayList vDataValue = new ArrayList();
  private String strTableName = null;
  private int iCurIndex = -1;

  public DataSet() {
      clear();
  }

  /**
   * delete object of current index
   * @throws if delete fail
   * 
   */
  public void delete() throws Exception{
    if( iCurIndex>0 && vDataValue!=null ){
      vDataValue.remove(iCurIndex);
      iCurIndex--;
    }
  }

  /**
   * insert object 
   * @param aData
   * @throws Exception
   */
  public void addData(Object aData) throws Exception {
    if (vDataValue == null)
      vDataValue = new ArrayList();
    vDataValue.add(aData);
  }


  /**
   * set current cursor index to first.
   */
  public void first()
  {
    iCurIndex = 0;
  }

  /**
   * set current cursor index to -1.
   */
  public void beforeFirst()
  {
    iCurIndex = -1;
  }

  /**
   * move current cursor forward 
   * @return true success , false fail
   */
  public boolean previous()
  {
    if( iCurIndex > 0 )
    {
      iCurIndex--;
      return true;
    }
    else
      return false;
  }

  /**
   * move current cursor next
   * @return true success , false fail
   */
  public boolean next()
  {
    if((iCurIndex+1) < vDataValue.size())
    {
      iCurIndex++;
      return true;
    }
    else
      return false;
  }

  /**
   * move current cursor to specify index
   * @param iRow 絕對位置
   */
  public void absolute(int iRow)
  {
    iCurIndex = iRow;
  }

  /**
   * get object
   * @return obj specify index
   * @throws Exception
   */
  public Object getData() throws Exception
  {
    return vDataValue.get(iCurIndex);
  }

  /**
   * get total object numbers
   * @return int 物件數量
   */
  public int getTotalCount(){
    return this.vDataValue.size();
  }

  /**
   * convert to  Collection .
   * @return Collection Collection物件
   */
  public Collection getCollection(){
    return this.vDataValue;
  }

  /**
   *convert to  Iterator .
   * @return Iterator Iterator物件
   */
  public Iterator getIterator(){
    return this.vDataValue.iterator();
   }


  /**
   * remove all objects
   */
  private void clear(){
    if(vDataValue != null)
      vDataValue.clear();
  }//end of clear
}