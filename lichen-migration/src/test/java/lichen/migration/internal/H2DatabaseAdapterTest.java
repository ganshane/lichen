package lichen.migration.internal;

import lichen.migration.model.Comment;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-01-17
 */
public class H2DatabaseAdapterTest {
    @Test
    public void test_comment() {
        H2DatabaseAdapter adapter = new H2DatabaseAdapter(Option.none(String.class));
        String sql = adapter.commentColumnSql(Option.none(String.class), "test", "col", new Comment() {
            @Override
            public String getValue() {
                return "comment";
            }
        });

        Assert.assertEquals("COMMENT ON COLUMN  TEST .COL IS 'comment'", sql);
    }
}
