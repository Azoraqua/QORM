import com.azoraqua.qorm.annotation.Column;
import com.azoraqua.qorm.annotation.Table;

@Table(name = "Users")
public final class User {

    @Column(primary = true, auto = true)
    protected int id;

    @Column
    protected String name;

    @Column
    protected String password;

    @Column
    protected Role role;
}
