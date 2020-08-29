package com.azoraqua.qorm.analyser;

import com.azoraqua.qorm.annotation.Column;
import com.azoraqua.qorm.annotation.Table;
import com.azoraqua.qorm.com.azoraqua.qorm.hasher.Hasher;

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
                final TableData tableData = new TableData(clazz, obj.hashCode(), (table.name().isEmpty() ? clazz.getSimpleName() : table.name()));
                data.add(tableData);

                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);

                    final boolean isStatic = (field.getModifiers() & Modifier.STATIC) == field.getModifiers();

                    if (field.isAnnotationPresent(Column.class)) {
                        final Column column = field.getDeclaredAnnotation(Column.class);
                        final Object val = field.get((isStatic ? null : obj));
                        final Class<?> valClass = val.getClass();
                        final Class<? extends Hasher> hasherClass = column.hasher();
                        final Hasher hasher = hasherClass.newInstance();
                        final byte[] hashedValue = hasher.hash(String.valueOf(val));

                        data.add(new ColumnData(
                            tableData,
                            column.name().isEmpty() ? field.getName() : column.name(),
                            column.type() == JDBCType.NULL ? this.toJDBCType(field.getType()) : column.type(),
                            column.primary(),
                            column.auto(),
                            column.nullable(),
                            hasher,
                            valClass,
                            val,
                            String.valueOf(val).length(),
                            hashedValue,
                            hashedValue.length,
                            column.length()
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

    public void describe(boolean verbose) {
        for (Data d : data) {
            System.out.println(verbose ? d.toString() : reduce(d.toString()));
        }
    }

    public void describe() {
        describe(false);
    }

    public void cleanup() {
        data.clear();
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

    private String reduce(String str) {
        return str
            .replaceAll("@[a-zA-Z0-9]+", "")
            .replace("primary=false, ", "")
            .replace("autoIncrement=false, ", "")
            .replace("nullable=false, ", "");
    }
}
