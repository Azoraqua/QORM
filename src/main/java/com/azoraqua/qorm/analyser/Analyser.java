package com.azoraqua.qorm.analyser;

import com.azoraqua.qorm.annotation.Column;
import com.azoraqua.qorm.annotation.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Analyser {

    private static final Map<Class<?>, JDBCType> TYPE_MAP = new HashMap<>() {{
        super.putIfAbsent(String.class, JDBCType.VARCHAR);
        super.putIfAbsent(Integer.class, JDBCType.INTEGER);
        super.putIfAbsent(int.class, JDBCType.INTEGER);
        super.putIfAbsent(Long.class, JDBCType.BIGINT);
        super.putIfAbsent(long.class, JDBCType.BIGINT);
        super.putIfAbsent(Boolean.class, JDBCType.BOOLEAN);
        super.putIfAbsent(boolean.class, JDBCType.BOOLEAN);
        super.putIfAbsent(Character.class, JDBCType.CHAR);
        super.putIfAbsent(char.class, JDBCType.CHAR);
    }};

    private final List<Data> data;

    public Analyser() {
        this.data = new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    public <T> void analyse(T obj) {
        try {
            final Class<? extends T> clazz = (Class<? extends T>) obj.getClass();

            if (clazz.isAnnotationPresent(Table.class)) {
                final Table table = clazz.getDeclaredAnnotation(Table.class);
                data.add(new TableData(clazz, obj.hashCode(), (table.name().isEmpty() ? clazz.getSimpleName() : table.name())));

                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);

                    final boolean isStatic = (field.getModifiers() & Modifier.STATIC) == field.getModifiers();

                    if (field.isAnnotationPresent(Column.class)) {
                        final Column column = field.getDeclaredAnnotation(Column.class);
                        final Object val = field.get((isStatic ? null : obj));
                        final Class<?> valClass = val.getClass();

                        data.add(new ColumnData(
                            column.name().isEmpty() ? field.getName() : column.name(),
                            column.type() == JDBCType.NULL ? this.toJDBCType(field.getType()) : column.type(),
                            column.primary(),
                            column.auto(),
                            column.nullable(),
                            valClass,
                            val
                        ));

                        if (valClass.isAnnotationPresent(Table.class) && !val.equals(obj)) {
                            analyse(val);
                        }
                    }
                }
            } else {
                throw new IllegalStateException("No table annotation found.");
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot access class/field", e);
        }
    }

    public List<Data> getData() {
        return data;
    }

    private JDBCType toJDBCType(Class<?> clazz) {
        if (TYPE_MAP.containsKey(clazz)) {
            return TYPE_MAP.get(clazz);
        }

        try {
            return JDBCType.valueOf(clazz.getSimpleName().toUpperCase());
        } catch (IllegalArgumentException | EnumConstantNotPresentException e) {
            return JDBCType.OTHER;
        }
    }
}
