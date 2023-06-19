package com.ttm.crm.server.history;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.dao.AccountDao;
import com.ttm.crm.server.dao.ContactDao;
import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.dao.UserDao;
import com.ttm.crm.server.entity.Account;
import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.entity.User;

@Component
public class Differ {

	/* Compare two objects and generate a list of differences.
	 * 
	 * See AccountService.java for code required in add, change and delete
	 * methods of each Service that is to be logged. (I think this belongs
	 * in the Service, not the DAO, as the idea is that the DAO is a very simple
	 * interface to the DB layer).
	 * 
	 * Use @SkipHistoryLogging on a field to bypass it.
	 * 
	 * Use @TranslateHistory on a field to map in a more complicated way,
	 * currently this supports user ID's so that rather than seeing the 
	 * user ID int changing, they see the underlying user name.
	 * 
	 * use @MapIdTo for classes like Account where the ID field is also
	 * the implicit accountId field.
	 * 
	 * Use @UserTableName to log the table name with something other than
	 * the class name.
	 * 
	 * Use @UserFieldName to log the field name with something other than the
	 * prettified (in History.java) variable name. Otherwise a variable
	 * named eg. thisIsMe will be logged as 'This Is Me' automatically.
	 * 
	 * IMPORTANT, class classes must have an integer ID field.
	 * 
	 * IMPORTANT. classes with accountId or contactId fields must be spelt
	 * exactly like that or they won't work properly.
	 * 
	 * IMPORTANT, only checks primitives, could obviously be updated
	 * to handle collections and such. Also only handles SOME primitives.
	 *
	 * IMPORTANT, does not check for inherited properties.
	 * 
	 * Anything unhandled should throw an Exception.
	 */
	
	// TODO UPDATE COMMENTS
	
	private AccountDao accountDao;
	private ContactDao contactDao;
	private HistoryDao historyDao;
	private UserDao userDao;
	
	@Autowired
	public Differ(AccountDao accountDao, ContactDao contactDao, HistoryDao historyDao, UserDao userDao) {
		this.accountDao = accountDao;
		this.contactDao = contactDao;
		this.historyDao = historyDao;
		this.userDao = userDao;
	}
		
	public void logDiffs(Object oldObject, Object newObject) {
		Class<?> oldClass = oldObject.getClass();
		Class<?> newClass = newObject.getClass();
		if(!(oldClass.getName().equals(newClass.getName()))) {
			throw new RuntimeException("differ: trying to compare different classes");
		}
		Field[] oldFields = oldObject.getClass().getDeclaredFields();
		EntityInfo entityInfo = new EntityInfo(oldObject);
		for(Field oldField : oldFields) {
			if(oldField.getAnnotation(SkipHistoryLogging.class) != null) {
				continue;
			}
			Field newField = null;
			try {
				newField = newClass.getDeclaredField(oldField.getName());
			} catch(Exception e) {
				// generally impossible (is class name always fully qualified?)
				throw new RuntimeException(e);
			}
			oldField.setAccessible(true);
			newField.setAccessible(true);
			TranslateHistory translateHistory = oldField.getAnnotation(TranslateHistory.class);
			Diff diff = null;
			if(translateHistory != null) {
				diff = diffSpecial(translateHistory, oldField, oldObject, newField, newObject);
			} else {
				switch(oldField.getType().getName()) {
					//@formatter:off
					case "java.lang.String": {
						diff = diffString(oldField, oldObject, newField, newObject);
						break;
					}
					case "boolean": {
						diff = diffBoolean(oldField, oldObject, newField, newObject);
						break;
					}
					case "float": {
						System.out.println("TODO: TRACK HISTORY FLOAT");
						break;
					}
					case "int": {
						diff = diffInt(oldField, oldObject, newField, newObject);
						break;
					}
					case "long": {
						diff = diffLong(oldField, oldObject, newField, newObject);
						break;
					}
					case "java.sql.Timestamp": {
						// TODO
						System.out.println("TODO: TRACK HISTORY TIMESTAMP");
						break;
					}
					case "java.util.ArrayList": {
						System.out.println("TODO: TRACK HISTORY ARRAYLIST");
						// TODO
						break;
					}
					case "java.util.Date": {
						diff = diffDate(oldField, oldObject, newField, newObject);
						break;
					}
					case "java.time.OffsetDateTime": {
						diff = diffOffsetDateTime(oldField, oldObject, newField, newObject);
						break;
					}
					case "java.util.List": {
						// TODO
						break;
					}
					default: {
						throw new RuntimeException("differ: dont know this class: " + oldField.getType().getName());
					}
					//@formatter:on
				}
			}
			if(diff != null) {
				History history = new History(entityInfo.getAccountId(), entityInfo.getContactId(), diff.fieldName, diff.oldValue, diff.newValue, entityInfo.getId(), entityInfo.getTableName());
				historyDao.add(history);
			}
		}
	}
	
	private Diff diffSpecial(TranslateHistory translateHistory, Field oldField, Object oldObject, Field newField, Object newObject) {
		switch(translateHistory.translateAs()) {
			//@formatter:off
			case ACCOUNT: {
				return diffAccountSpecial(translateHistory, oldField, oldObject, newField, newObject);
			}
			case CONTACT: {
				return diffContactSpecial(translateHistory, oldField, oldObject, newField, newObject);
			}
			case USER: {
				return diffUserSpecial(translateHistory, oldField, oldObject, newField, newObject);
			}
			default: {
				throw new RuntimeException("Differ cannot handle: " + translateHistory.translateAs());	
			}
			//@formatter:on
		}
	}
	
	private Diff diffAccountSpecial(TranslateHistory translateHistory, Field oldField, Object oldObject, Field newField, Object newObject) {
		try {
			int oldAccountId = (int) oldField.get(oldObject);
			String oldAccountName = "<None>";
			if(oldAccountId > 0) {
				Account oldAccount = accountDao.find((int) oldAccountId);
				
				if(oldAccount != null) {
					oldAccountName = oldAccount.getName();
				} else {
					oldAccountName = "";
				}
			}
			int newAccountId = (int) newField.get(newObject);
			String newAccountName = "<None>";
			if(newAccountId > 0) {
				Account newAccount = accountDao.find((int) newAccountId);
				newAccountName = newAccount.getName();
			}
			if(!(oldAccountName.equals(newAccountName))) {
				return new Diff(translateHistory.userFieldName(), oldAccountName, newAccountName);
			} else {
				return null;
			}
		} catch(Exception e) {
			throw new RuntimeException("Differ cannot diff Account " + e.getMessage());
		}
	}

	private Diff diffContactSpecial(TranslateHistory translateHistory, Field oldField, Object oldObject, Field newField, Object newObject) {
		try {
			int oldContactId = (int) oldField.get(oldObject);
			String oldContactName = "<None>";
			if(oldContactId > 0 ) {
				Contact oldContact = contactDao.find((int) oldContactId);
				if (oldContact != null) {
					oldContactName = oldContact.getFirstName() + " " + oldContact.getLastName();
				} else {
					oldContactName = "";
				}
			}
			int newContactId = (int) newField.get(newObject);
			String newContactName = "<None>";
			if(newContactId > 0) {
				Contact newContact = contactDao.find((int) newContactId);
				newContactName = newContact.getFirstName() + " " + newContact.getLastName();
			}
			if(!(oldContactName.equals(newContactName))) {
				return new Diff(translateHistory.userFieldName(), oldContactName, newContactName);
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Differ cannot diff Contact " + e.getMessage());
		}
	}

	private Diff diffUserSpecial(TranslateHistory translateHistory, Field oldField, Object oldObject, Field newField, Object newObject) {
		try {
			int oldUserId = (int) oldField.get(oldObject);
			User oldOwnerUser = userDao.find((int) oldUserId);
			String oldUserName = oldOwnerUser.getFirstName() + " " + oldOwnerUser.getLastName();
			int newUserId = (int) newField.get(newObject);
			User newOwnerUser = userDao.find((int) newUserId);
			String newUserName = newOwnerUser.getFirstName() + " " + newOwnerUser.getLastName();
			if(!(oldUserName.equals(newUserName))) {
				return new Diff(translateHistory.userFieldName(), oldUserName, newUserName);
			} else {
				return null;
			}
		} catch(Exception e) {
			throw new RuntimeException("Differ cannot diff User " + e.getMessage());
		}
	}
	
	private Diff diffBoolean(Field oldField, Object oldObject, Field newField, Object newObject) {
		try {
			boolean oldValue = (boolean) oldField.get(oldObject);
			boolean newValue = (boolean) newField.get(newObject);
			if(oldValue != newValue) {
				return new Diff(newField.getName(), String.valueOf(oldValue), String.valueOf(newValue));
			} else {
				return null;
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Diff diffString(Field oldField, Object oldObject, Field newField, Object newObject) {
		try {
			String oldValue = (String) oldField.get(oldObject);
			String newValue = (String) newField.get(newObject);
			if(oldValue == null || newValue == null) {
				if(oldValue != newValue) {
					String oldValueSafe = "";
					String newValueSafe = "";
					if(oldValue != null) {
						oldValueSafe = oldValue;
					}
					if(newValue != null) {
						newValueSafe = newValue;
					}
					return new Diff(oldField.getName(), oldValueSafe, newValueSafe);
				}
				return null;
			}
			if(!(oldValue.equals(newValue))) {
				return new Diff(newField.getName(), oldValue, newValue);
			} else {
				return null;
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Diff diffInt(Field oldField, Object oldObject, Field newField, Object newObject) {
		try {
			int oldValue = (int) oldField.get(oldObject);
			int newValue = (int) newField.get(newObject);
			if(oldValue != newValue) {
				return new Diff(oldField.getName(), String.valueOf(oldValue), String.valueOf(newValue));
			} else {
				return null;
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Diff diffLong(Field oldField, Object oldObject, Field newField, Object newObject) {
		try {
			long oldValue = (long) oldField.get(oldObject);
			long newValue = (long) newField.get(newObject);
			if(oldValue != newValue) {
				return new Diff(oldField.getName(), String.valueOf(oldValue), String.valueOf(newValue));
			} else {
				return null;
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Diff diffDate(Field oldField, Object oldObject, Field newField, Object newObject) {
		try {
			// should be same format as used in Angular so log looks the same
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String oldValue = "<None>";
			if(oldField != null && oldField.get(oldObject) != null) {
				oldValue = dateFormat.format((Date) oldField.get(oldObject));
			}
			String newValue = "<None>";
			if(newField != null && newField.get(newObject) != null) {
				dateFormat.format((Date) newField.get(newObject));
			}
			if(oldValue == null || newValue == null) {
				if(oldValue != newValue) {
					String oldValueSafe = "";
					String newValueSafe = "";
					if(oldValue != null) {
						oldValueSafe = oldValue;
					}
					if(newValue != null) {
						newValueSafe = newValue;
					}
					return new Diff(oldField.getName(), oldValueSafe, newValueSafe);
				}
			}
			if(!(oldValue.equals(newValue))) {
				return new Diff(newField.getName(), oldValue, newValue);
			} else {
				return null;
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Diff diffOffsetDateTime(Field oldField, Object oldObject, Field newField, Object newObject) {
		try {
			// should be same format as used in Angular so log looks the same
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String oldValue = dateTimeFormatter.format((OffsetDateTime) oldField.get(oldObject));
			String newValue = dateTimeFormatter.format((OffsetDateTime) newField.get(newObject));
			if(oldValue == null || newValue == null) {
				if(oldValue != newValue) {
					String oldValueSafe = "";
					String newValueSafe = "";
					if(oldValue != null) {
						oldValueSafe = oldValue;
					}
					if(newValue != null) {
						newValueSafe = newValue;
					}
					return new Diff(oldField.getName(), oldValueSafe, newValueSafe);
				}
			}
			if(!(oldValue.equals(newValue))) {
				return new Diff(newField.getName(), oldValue, newValue);
			} else {
				return null;
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}