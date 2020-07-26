package test.com.drobot.task8;

import com.drobot.task8.controller.Invoker;
import com.drobot.task8.exception.CommandException;
import com.drobot.task8.model.entity.CustomBook;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class AddingTest {

    @Test
    public void addTest_True() {
        boolean result;
        Invoker invoker = new Invoker();
        Optional<List<CustomBook>> optional = Optional.empty();
        try {
            optional =
                    invoker.processRequest("add_book", "Java book", "2009", "89", "Author", "Another Author");
        } catch (CommandException e) {
            fail(null, e);
        }
        result = optional.isPresent();
        assertTrue(result);
    }

    @Test
    public void addTest_False() {
        boolean result;
        Invoker invoker = new Invoker();
        Optional<List<CustomBook>> optional = Optional.empty();
        try {
            optional =
                    invoker.processRequest("add_book", "book1", "1954", "541", "Arkadiy");
        } catch (CommandException e) {
            fail(null, e);
        }
        result = optional.isPresent();
        assertFalse(result);
    }
}
