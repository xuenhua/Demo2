package database;

import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.Collection;
public interface IOperationCore {
	/** sql更新语句
	*
	* @param queryString 查询语句
	* @return 返回一个<code>ResultSet</code>结果集
	* @exception SQLException 
	* */
	ResultSet executeQuery(String queryString) throws SQLException; 
	/**
	* sql更新语句 
	* @param updateString 数据库更新语句
	* @return 更新数据库影响行数 
	* @exception SQLException 
	* */
	int executeUpdate(String updateString) throws SQLException; 
	/**
	* 读取行个数 
	* @param queryString 查询语句
	* @return Transact-SQL 查询后的虚拟表的行数
	* @exception SQLException 
	* */
	int getRowCount(String queryString) throws SQLException; 
	/**
	* 读取列数个数
	* @param queryString 查询语句
	* @return Transact-SQL 查询后的虚拟表的列数
	* @exception SQLException 
	* */
	int getColumnCount(String queryString) throws SQLException; 
	/**
	* 读取列名 
	* @param columIndex 列索引
	* @param queryString 提供ResultSet二维表的查询字符串
	* @return ResultSet表中的指定的列名 
	* @exception SQLException 
	* */
	String getColumnName(int columIndex, String queryString) throws SQLException;
	/**
	* 读取queryString查询结果集<code>ResultSet</code>表中的所有列名
	* @param queryString 用于返回<code>ResultSet</code>结果集的语句
	* @return 表中的所有列名
	* @throws SQLException 
	* */
	Collection<String> getColumnNames(String queryString) throws SQLException;
	/**
	* 读取queryString查询结果集<code>ResultSet</code>表中的所有字段类型名称 
	* @param queryString 用于返回查询结果集的语句
	* @return 表中的所有字段类型名称
	* @throws SQLException 
	* */
	Collection<?> getColumnTypeNames(String queryString) throws SQLException;
	/**
	* 获取ResultSet二维表中指定位置的值,目前只支持mysql 
	* @param rowIndex 行索引
	* @param columnIndex 列索引
	* @param queryString 产生一个ResultSet结果集的查询语句
	* @return 指定位置的数据记录
	* @exception SQLException
	* */
	Object getValueAt(int rowIndex, int columnIndex, String queryString) throws SQLException;
	/**
	* 释放系统连接资源
	* <br>一旦关闭,数据库的操作将全部无效
	* @exception SQLException 如果关闭失败将抛出<code>SQLException</code>
	*/
	void dispose() throws SQLException;

}
