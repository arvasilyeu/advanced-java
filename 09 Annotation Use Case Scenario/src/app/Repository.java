package app;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Repository<T> {

	public void save(T t) {
		var clazz = t.getClass();

		var tableName = clazz.getSimpleName().toLowerCase();

		var classAnnotations = clazz.getAnnotationsByType(Entity.class);
		if (classAnnotations.length > 0 && !classAnnotations[0].value().isEmpty()) {
			tableName = classAnnotations[0].value();
		}

		var fields = clazz.getDeclaredFields();
		ArrayList<String> fieldList = new ArrayList<>();
		
		
		for (var field : fields) {
			var annotations = field.getDeclaredAnnotationsByType(Field.class);
			if (annotations.length == 0) {
				continue;
			}

			var annotation = annotations[0];
			var fieldName = annotation.columnName();
			var isKey = annotation.isKey();

			if (fieldName.length() == 0) {
				fieldName = field.getName();
			}
			
			if (!isKey) {
				fieldList.add(fieldName);
			}
		}
		
		String sqlFields = fieldList.stream().collect(Collectors.joining(","));
		String sqlPlaceholders = fieldList.stream().map(s -> "?").collect(Collectors.joining(","));
		
		var sql = String.format("insert into %s (%s) values (%s)", tableName, sqlFields, sqlPlaceholders);
		
		System.out.println(sql);

	}
}
