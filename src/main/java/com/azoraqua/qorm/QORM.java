package com.azoraqua.qorm;

import com.azoraqua.qorm.annotation.Column;
import com.azoraqua.qorm.annotation.Table;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class QORM {

    private static final String SEPERATOR = "---------------------------------------------------";

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
    private MysqlDataSource source;

    public QORM(Properties properties) {
        this.init(properties);
    }

    @SuppressWarnings("unchecked")
    public <T> void analyse(T obj) {
        try {
            final Class<? extends T> clazz = (Class<? extends T>) obj.getClass();

            if (clazz.isAnnotationPresent(Table.class)) {
                final Table table = clazz.getDeclaredAnnotation(Table.class);
                System.out.println(SEPERATOR);
                System.out.println("Object Type = " + clazz.getSimpleName());
                System.out.println("Object Hash = " + Integer.toHexString(obj.hashCode()));
                System.out.println();
                System.out.println("Table Name = " + (table.name().isEmpty() ? clazz.getSimpleName() : table.name()));
                System.out.println();

                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);

                    final boolean isStatic = (field.getModifiers() & Modifier.STATIC) == field.getModifiers();

                    if (field.isAnnotationPresent(Column.class)) {
                        final Column column = field.getDeclaredAnnotation(Column.class);
                        System.out.println("Column Name = " + (column.name().isEmpty() ? field.getName() : column.name()));
                        System.out.println("Column SQL Type = " + (column.type() == JDBCType.NULL ? this.toJDBCType(field.getType()) : column.type()));
                        System.out.println("Column Primary = " + column.primary());
                        System.out.println("Column Auto_Increment = " + column.auto());
                        System.out.println("Column Nullable = " + column.nullable());

                        final Object val = field.get((isStatic ? null : obj));
                        final Class<?> valClass = val.getClass();

                        System.out.println("Field Type = " + field.getType().getSimpleName());
                        System.out.println("Field Value = " + val);
                        System.out.println();

                        if (valClass.isAnnotationPresent(Table.class) && !val.equals(obj)) {
                            analyse(val);
                        }
                    }
                }
            } else {
                throw new IllegalStateException("Not going to save.");
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot access", e);
        }
    }

    public <T> void save(T obj) {
        // TODO: Implement.
    }

    public <T> T load(Class<? extends T> clazz) {
        return null; // TODO: Implement.
    }

    private void init(Properties properties) {
        source = new MysqlDataSource();
        source.setURL(properties.getProperty("url"));
        source.setUser(properties.getProperty("user"));
        source.setPassword(properties.getProperty("password"));
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
