package guiludescher.checkyourcheckers;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static guiludescher.checkyourcheckers.GameManager.INVALID_SPACE;
import static guiludescher.checkyourcheckers.GameManager.SPACE_OK;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class GameManagerTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

}
