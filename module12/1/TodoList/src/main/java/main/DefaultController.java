package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DefaultController {

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(name = "/")
    public String index(Model model) {
        Iterable<Task> allTasks = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : allTasks){
            tasks.add(task);
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskCount", tasks.size());
        return "index";
    }
}
