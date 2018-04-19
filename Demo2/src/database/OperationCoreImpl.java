package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 类名:OperationCoreImplements<br>
 * 作用:该类实现 IOperationCore 接口的所有方法<br>
 * 
 * @author xuenhua
 */
public class OperationCoreImpl implements IOperationCore {
	protected Connection aConnection = null;
	protected Statement aStatement = null;
	protected ResultSet aResultSet = null;
	protected ResultSetMetaData rsmd = null;
	protected static OperationCoreImpl m_instance = null;
	/**
	 * Singleton 即单例(态)模式,用来生成对象唯一实例的方法
	 * @return OperationCoreImplements 的一个实例
	 * @throws Exception
	 */
	public static OperationCoreImpl createFactory() throws Exception {
		if (m_instance == null)
			m_instance = new OperationCoreImpl();
		return m_instance;
	}
	/**
	 * @exception Exception
	 */
	private OperationCoreImpl() throws Exception {
		init();
	}
	/**
	 * 负责初始化 Connection 连接
	 * @throws Exception
	 */
	private void init() throws Exception {
		aConnection = ConnectionFactory.getConnection();
	}

	/**
	 * 释放系统连接资源<br>
	 * 一旦关闭,数据库的操作将全部无效
	 */
	public void dispose() {
		try {
			if (aResultSet != null)
				aResultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (aStatement != null)
				aStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (aConnection != null)
				aConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sql 更新语句 *
	 * @param queryString 查询语句
	 * @return 返回一个<code>ResultSet</code>结果集
	 * @exception SQLException
	 */
	@Override
	public ResultSet executeQuery(String queryString) {
		try {
			aStatement = aConnection.createStatement();
			aResultSet = aStatement.executeQuery(queryString);
		} catch (SQLException e) {
			aResultSet = null;
			e.printStackTrace();
		}
		return aResultSet;
	}

	/**
	 * sql 更新语句
	 * @param updateString 数据库更新语句
	 * @return 更新数据库影响行数
	 * @exception SQLException
	 */
	@Override
	public int executeUpdate(String updateString) {
		int effectedRows = 0;
		try {
			aConnection.setAutoCommit(false);
			aStatement = aConnection.createStatement();
			effectedRows = aStatement.executeUpdate(updateString);
			aConnection.commit();
		} catch (SQLException ex) {
			System.out.println("数据库写操作失败!");
			if (aConnection != null) {
				try {
					aConnection.rollback();
					System.out.println("JDBC 事务回滚成功");
				} catch (SQLException e) {
					System.out.println("JDBC 事务回滚失败");
					e.printStackTrace();
				}
			}
		}
		return effectedRows;
	}

	/**
	 * 读取 queryString 查询结果集<code>ResultSet</code>表中的所有列名
	 * @param queryString 用于返回<code>ResultSet</code>结果集的语句
	 * @return 表中的所有列名
	 * @throws SQLException
	 */
	@Override
	public Collection<String> getColumnNames(String queryString) {
		ArrayList<String> ColumnNames = new ArrayList<String>();
		try {
			aResultSet = executeQuery(queryString);
			ResultSetMetaData rsmd = aResultSet.getMetaData();
			int j = rsmd.getColumnCount();
			for (int k = 0; k < j; k++) {
				ColumnNames.add(rsmd.getColumnName(k + 1));
			}
		} catch (SQLException e) {
			ColumnNames = null;
			e.printStackTrace();
		}

		return ColumnNames;
	}

	/**
	 * 读取 queryString 查询结果集<code>ResultSet</code>表中的所有字段类 型名称
	 * @param queryString 用于返回查询结果集的语句
	 * @return 表中的所有字段类型名称
	 * @throws SQLException
	 */
	@Override
	public Collection<String> getColumnTypeNames(String queryString) {
		ArrayList<String> ColumnNames = new ArrayList<String>();
		try {
			aResultSet = executeQuery(queryString);
			ResultSetMetaData rsmd = aResultSet.getMetaData();
			int j = rsmd.getColumnCount();
			for (int k = 0; k < j; k++) {
				ColumnNames.add(rsmd.getColumnTypeName(k + 1));
			}
		} catch (SQLException e) {
			ColumnNames = null;
			e.printStackTrace();
		}
		return ColumnNames;
	}

	/**
	 * 读取列名 *
	 * @param columIndex 列索引
	 * @param queryString 提供 ResultSet 二维表的查询字符串
	 * @return ResultSet 表中的指定的列名 *
	 * @exception SQLException
	 */
	@Override
	public String getColumnName(int columIndex, String queryString) {
		String columnName = null;
		try {
			aResultSet = executeQuery(queryString);
			rsmd = aResultSet.getMetaData();
			columnName = rsmd.getColumnName(columIndex + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return columnName;
	}

	/**
	 * 读取列数个数 *
	 * @param queryString 查询语句
	 * @return Transact-SQL 查询后的虚拟表的列数 *
	 * @exception SQLException
	 */
	@Override
	public int getColumnCount(String queryString) {
		int columnCount = 0;
		try {
			aResultSet = executeQuery(queryString);
			ResultSetMetaData rsmd = aResultSet.getMetaData();
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnCount;
	}

	/**
	 * 读取行个数
	 * @param queryString 查询语句
	 * @return Transact-SQL 查询后的虚拟表的行数
	 * @exception SQLException
	 */
	@Override
	public int getRowCount(String queryString) {
		int rowCount = 0;
		try {
			aResultSet = executeQuery(queryString);
			while (aResultSet.next())
				rowCount = aResultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	/**
	 * 获取 ResultSet 二维表中指定位置的值,目前只支持 mysql
	 * @param rowIndex 行索引
	 * @param columnIndex 列索引
	 * @param queryString 产生一个 ResultSet 结果集的查询语句
	 * @return 指定位置的数据记录
	 * @exception SQLException
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex, String queryString) {
		Object values = null;
		try {
			aResultSet = executeQuery(queryString); // 指针下移一行
			aResultSet.absolute(rowIndex + 1);
			values = aResultSet.getObject(columnIndex + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return values;
	}

}
