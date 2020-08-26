import com.azoraqua.qorm.annotation.Column;
import com.azoraqua.qorm.annotation.Table;

@Table(name = "Roles")
public final class Role {

    @Column
    protected int id;

    @Column
    protected String name;
}
