import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AlbumTest.class,
        ArtistTest.class,
        GenreTest.class,
        HistoryTest.class,
        MainTest.class,
        PlaylistTest.class,
        RecommendationTest.class,
        ReviewTest.class,
        SearchTest.class,
        SongTest.class,
        SubscriptionTest.class,
        UserTest.class,
})
@SelectPackages({
        "dao",
        "steps"
})
public class AllTests {
}
