package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 类名:DBOperationAdapter<br>
 * 作用:<b>该类中的所有方法对用户透明,提供数据库操作的常用方法</b><br>
 * 说明:该类使用了<b>Adapter</b> 模式与 <b>Singleton</b> 模式,<br/>
 * 类自身为Adpater,OperationCoreImplements为Adapte类;<br>
 * 实例化类自生对象的时候用到了Singleton模式,<br>
 * 即<code>DBOperationAdapter.getInstance()</code><br>
 * @author xuenhua
 */
public class DBOperationAdapter extends ConnectionFactory {
	private static IOperationCore objIOperationCore = null;
	private static DBOperationAdapter m_instance = null;

	private DBOperationAdapter() {
		try {
			objIOperationCore = OperationCoreImpl.createFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DBOperationAdapter getInstance() {
		if (m_instance == null)
			m_instance = new DBOperationAdapter();
		return m_instance;
	}

	/**
	 * sql更新语句 *
	 * @param queryString 查询语句
	 * @return 返回一个<code>ResultSet</code>结果集
	 * @exception SQLException
	 */
	public ResultSet executeQuery(String queryString) throws SQLException {
		return objIOperationCore.executeQuery(queryString);
	}

	/**
	 * sql更新语句
	 * @param updateString 数据库更新语句
	 * @return 更新数据库影响行数
	 * @exception SQLException
	 */
	public int executeUpdate(String updateString) throws SQLException {
		return objIOperationCore.executeUpdate(updateString);
	}

	/**
	 * sql删除语句:updateString
	 * @param deleteString 数据库插入语句
	 * @return 删除数据影响行数
	 * @exception SQLException
	 */
	public int executeDelete(String deleteString) throws SQLException {
		return objIOperationCore.executeUpdate(deleteString);
	}

	/**
	 * sql插入语句:insertString
	 * @param insertString 数据库插入语句
	 * @return 插入数据影响行数 
	 * @exception SQLException
	 */
	public int executeInsert(String insertString) throws SQLException {
		return objIOperationCore.executeUpdate(insertString);
	}

	/**
	 * 读取行个数 
	 * @param queryString 查询语句
	 * @return Transact-SQL 查询后的虚拟表的行数
	 * @exception SQLException
	 */
	public int getRowCount(String queryString) throws SQLException {
		return objIOperationCore.getRowCount(queryString);
	}

	/**
	 * 读取列数个数
	 * @param queryString 查询语句
	 * @return Transact-SQL 查询后的虚拟表的列数
	 * @exception SQLException
	 */
	public int getColumnCount(String queryString) throws SQLException {
		return objIOperationCore.getColumnCount(queryString);
	}

	/**
	 * 读取列名
	 * @param columIndex 列索引
	 * @param queryString 提供ResultSet二维表的查询字符串
	 * @return ResultSet表中的指定的列名
	 * @exception SQLException
	 */
	public String getColumnName(int columIndex, String queryString) throws SQLException {

		return objIOperationCore.getColumnName(columIndex, queryString);
	}

	/**
	 * 读取queryString查询结果集<code>ResultSet</code>表中的所有字段类型名称
	 * @param queryString 用于返回查询结果集的语句
	 * @return 表中的所有字段类型名称
	 * @throws SQLException
	 */
	public Collection<?> getColumnTypeNames(String queryString) throws SQLException {
		return objIOperationCore.getColumnTypeNames(queryString);
	}

	/**
	 * 读取queryString查询结果集<code>ResultSet</code>表中的所有列名
	 * @param queryString 用于返回<code>ResultSet</code>结果集的语句
	 * @return 表中的所有列名
	 * @throws SQLException
	 */
	public Collection<String> getColumnNames(String queryString) throws SQLException {
		return objIOperationCore.getColumnNames(queryString);
	}

	/**
	 * 获取ResultSet二维表中指定位置的值,目前只支持mysql
	 * @param rowIndex 行索引
	 * @param columnIndex 列索引
	 * @param queryString 产生一个ResultSet结果集的查询语句
	 * @return 指定位置的数据记录
	 * @exception SQLException
	 */
	public Object getValueAt(int rowIndex, int columnIndex, String queryString) throws SQLException {
		return objIOperationCore.getValueAt(rowIndex, columnIndex, queryString);
	}

	/**
	 * 释放系统连接资源 <br>
	 * 一旦关闭,数据库的操作将全部无效
	 * @exception SQLException 如果关闭失败将抛出 <code>SQLException</code>
	 */
	public void dispose() throws SQLException {
		objIOperationCore.dispose();
	}
}