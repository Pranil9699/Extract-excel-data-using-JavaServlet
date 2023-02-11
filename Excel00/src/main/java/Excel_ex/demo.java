package Excel_ex;

import java.lang.reflect.Field;



class DynamicClasss {
	
	private int id;
    public String Pranil0;
	
}

public class demo {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Class<?> dynamicClass=returningclass();
		System.out.println("here come");

	}

	private static Class<?> returningclass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
	    Class<?> dynamicClasss = DynamicClasss.class;
	    for(int i = 0; i <10; i++ ){
	        String columnName = "Pranil"+i+"";
	        try {
	            Field field = dynamicClasss.getDeclaredField(columnName);
	            field.setAccessible(true);
	            field.set(dynamicClasss, columnName);
	            field.setAccessible(false);
	        } catch (NoSuchFieldException e) {
	            // Field does not exist, create a new one
	            Field field = dynamicClasss.getDeclaredField(columnName);
	            field.setAccessible(true);
	            field.set(dynamicClasss, columnName);
	            field.setAccessible(false);
	        }
	    }
	    return dynamicClasss;
	}

}
