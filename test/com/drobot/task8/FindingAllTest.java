package test.com.drobot.task8;

import com.drobot.task8.controller.Invoker;
import com.drobot.task8.exception.CommandException;
import com.drobot.task8.model.entity.CustomBook;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class FindingAllTest {

    @Test
    public void findAllTest_True() {
        final int numberOfRows = 22;
        boolean result = false;
        Invoker invoker = new Invoker();
        Optional<List<CustomBook>> optional = Optional.empty();
        List<CustomBook> books;
        try {
            optional =
                    invoker.processRequest("find_all_books", "release_year");
        } catch (CommandException e) {
            fail(null, e);
        }
        if (optional.isPresent()) {
            books = optional.get();
            result = books.size() == numberOfRows;
            for (int i = 0; i < books.size(); i++) {
                System.out.println(i + 1 + ". " + books.get(i));
            }
        }
        assertTrue(result);
    }
}
