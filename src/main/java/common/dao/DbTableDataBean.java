package common.dao;

public class DbTableDataBean {
	String columnName;
	String columnValue;
	DbDataTypeEnum dataType;

	public DbTableDataBean(String columnName, String columnValue, DbDataTypeEnum dataType) {
		super();
		this.columnName = columnName;
		this.columnValue = columnValue;
		this.dataType = dataType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public DbDataTypeEnum getDataType() {
		return dataType;
	}

	public void setDataType(DbDataTypeEnum dataType) {
		this.dataType = dataType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result + ((columnValue == null) ? 0 : columnValue.hashCode());
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DbTableDataBean other = (DbTableDataBean) obj;
		if (columnName == null) {
			if (other.columnName != null) {
				return false;
			}
		} else if (!columnName.equals(other.columnName)) {
			return false;
		}
		if (columnValue == null) {
			if (other.columnValue != null) {
				return false;
			}
		} else if (!columnValue.equals(other.columnValue)) {
			return false;
		}
		if (dataType != other.dataType) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DbTableDataBean [columnName=" + columnName + ", columnValue=" + columnValue + ", dataType=" + dataType
				+ "]";
	}

}
