package inmethod.commons.rdb;

/**
 * 動態組合Sql的where condition.
 * @author william chen
 */
public class SQLCondition {

  private StringBuffer sSQL = null;
  private int iBlockStartNum = 0;
  private int iBlockEndNum = 0;

  public SQLCondition(){
  }

  public SQLCondition(String sSql){
    sSQL = new StringBuffer(sSql);
  }

  public SQLCondition(SQLCondition aSqlCondition){
    this(aSqlCondition.toString());
  }

  /**
   * add block ()
   * ex.  ( sSQL )
   */
  public void addBlock(){
    sSQL.insert(0,'(');
    sSQL.append(')');
  }

  /**
   * start a block
   */
  public void startBlock(){
    iBlockStartNum++;
    iBlockEndNum++;
  }

  /**
   * End block
   */
  public void endBlock() throws Exception{
    if( iBlockStartNum>(iBlockEndNum-1) ){
      throw new Exception("end Block exception");
    }
    if(this.sSQL!=null ){
      if( (iBlockEndNum-1)>=0 ){
        sSQL.append(')');
        iBlockEndNum--;
      }
    }
  }

  /**
   * show Sql statement
   */
  public String toString(){
    if( sSQL==null ) return null;
    for(int i=0;i<iBlockEndNum-iBlockStartNum;i++)
      sSQL.append(')');
    return sSQL.toString();
  }

  /**
   * add "and" sql statement
   * @param sSqlCondition
   */
  public void and(String sSqlCondition) throws Exception{
    if( sSQL==null )
      sSQL = new StringBuffer(sSqlCondition);
    else{
      if( iBlockStartNum>0 ){
        sSQL.append(" and ");
        for(int i=0;i<iBlockStartNum;i++)
          sSQL.append("(");
        sSQL.append(sSqlCondition);
        iBlockStartNum = 0;
      }
      else
        sSQL.append(" and " + sSqlCondition);
    }
  }

  /**
   * add "and" sql statement
   * @param aSqlCondition
   */
  public void and(SQLCondition aSqlCondition) throws Exception{
    startBlock();
    and(aSqlCondition.toString());
    endBlock();
  }

  /**
   * add "or" sql statement
   * @param sSqlCondition
   */
  public void or(String sSqlCondition) throws Exception{
    if( sSQL==null )
      sSQL = new StringBuffer(sSqlCondition);
    else{
      if( iBlockStartNum>0 ){
        sSQL.append(" or (" + sSqlCondition);
        iBlockStartNum--;
      }
      else
        sSQL.append(" or " + sSqlCondition);
    }
  }

  /**
   * add "or" sql statement
   * @param aSqlCondition
   */
  public void or(SQLCondition aSqlCondition) throws Exception{
    startBlock();
    or(aSqlCondition.toString());
    endBlock();
  }


  public static void main(String arv[]){

    try{
     SQLCondition aSqlCondition = new SQLCondition();
     aSqlCondition.or("aa.aa=bb.aa");
     aSqlCondition.or("cc.aa=aa.aa");
     aSqlCondition.addBlock();
     aSqlCondition.startBlock();
     aSqlCondition.startBlock();
     aSqlCondition.and("kk=kk");
     aSqlCondition.endBlock();
     aSqlCondition.startBlock();
     aSqlCondition.or("yy=yy");
     aSqlCondition.or("zz=zz");

     SQLCondition aSqlCondition2 = new SQLCondition("aaa.aa=bbb.aa");
     aSqlCondition2.or("ccc.aa=aaa.aa");

     aSqlCondition.and(aSqlCondition2);

     System.out.println( aSqlCondition );
/* sample
     SQLCondition aSqlCondition = new SQLCondition();
     aSqlCondition.or("aa.aa=bb.aa");
     aSqlCondition.or("cc.aa=aa.aa");
     aSqlCondition.addBlock();
     aSqlCondition.startBlock();
     aSqlCondition.and("kk=kk");
     aSqlCondition.endBlock();
     System.out.println( aSqlCondition );
*/
    }catch(Exception ex){};
  }
}