package com.ttm.crm.server.history;

import java.lang.reflect.Field;

public class EntityInfo {

	private int accountId;
	private int contactId;
	private int id;
	private String tableName;
	
	public EntityInfo(Object object) {
		accountId = getInt(object, "accountId");
		contactId = getInt(object, "contactId");
		id = getInt(object, "id");
		tableName = getTableName(object);
	}	
	
	private String getTableName(Object object) {
		try {
			return object.getClass().getAnnotation(UserTableName.class).value();
		} catch(Exception e) {
			return object.getClass().getSimpleName();
		}
	}
	
	private int getInt(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);    
			field.setAccessible(true);
			return (int) field.get(object);
		} catch(Exception e) {
			if(e instanceof NoSuchFieldException) {
				if(fieldName.equals("id")) {
					throw new RuntimeException("class " + object.getClass().getName() + " does not have an id field");
				} else {
					// override Contact and Account where id is the accountId
					try {
						String mapIdTo = object.getClass().getAnnotation(MapIdTo.class).value();
						if(mapIdTo.equals(fieldName)) {
							return getInt(object, "id");
						} else {
							return -1;
						}
					// some records will legitimately not have an accountId or a contactId
					} catch (Exception e2) {
						return -1;
					}
				}
			} else {
				System.out.println(e + e.getMessage());
			}
		}
		return -1;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	
}
