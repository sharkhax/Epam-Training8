package test.com.drobot.task8;

import com.drobot.task8.controller.Invoker;
import com.drobot.task8.exception.CommandException;
import com.drobot.task8.model.entity.CustomBook;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class RemovingTest {

    @Test
    public void removeTest_True() {
        boolean result;
        Invoker invoker = new Invoker();
        Optional<List<CustomBook>> optional = Optional.empty();
        try {
            optional = invoker.processRequest("remove_book", "32");
        } catch (CommandException e) {
            fail(null, e);
        }
        result = optional.isPresent();
        assertTrue(result);
    }

    @Test
    public void removeTest_False() {
        boolean result;
        Invoker invoker = new Invoker();
        Optional<List<CustomBook>> optional = Optional.empty();
        try {
            optional = invoker.processRequest("remove_book", "23");
        } catch (CommandException e) {
            fail(null, e);
        }
        result = optional.isPresent();
        assertFalse(result);
    }
}
