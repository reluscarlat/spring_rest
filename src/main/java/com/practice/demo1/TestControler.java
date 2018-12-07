package com.practice.demo1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*")
@Controller // Annotare pentru returnare de HTML si JSP, nu si JSON.
public class TestControler {

    @RequestMapping("/")
    public String home() {
        return "file1";
    }

    @ResponseBody // @Controller + ResponseBody => returneaza JSON si String.
    @RequestMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value="/sendMessageJSON")
    public void printPostedData(@RequestBody String data) {
        System.out.println(data + "/n");
        JsonReader jsonReader = Json.createReader(new StringReader(data ));
        JsonObject object = jsonReader.readObject();
        String message = object.getString("message");
        int id = object.getInt("id");
        jsonReader.close();
        System.out.println("The message is " + message + " and the ID is " + id);
    }

    @GetMapping("/populateForm")
    public String postData1(Model model) {
        model.addAttribute("id",0);
        model.addAttribute("name", "Andrei");
        model.addAttribute("age", 21);
        System.out.println(model.toString());
        return "file1";
    }

    @GetMapping("/populateForm2")
    public ModelAndView printPostedData() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id2",0);
        modelAndView.addObject("name2", "Andrei");
        modelAndView.addObject("age2", 21);
        modelAndView.setViewName("file1");
        System.out.println(modelAndView.toString());
        return modelAndView;
    }

    @PostMapping("/postData")
    public String postDataFromForm(
            @RequestParam(value = "id") int id,
            @RequestParam(value="name") String name,
            @RequestParam(value="age") int age) {
        System.out.println("\nId =" + id);
        System.out.println("Name =" + name);
        System.out.println("Age =" + age);
        return "file1";
    }

    @GetMapping("/getData")   // URL example: localhost:8080/getData?id=999
    public String getData(Model model, @RequestParam(value="id") int id) {
        model.addAttribute("id",id);
        return "file1";
    }

    @GetMapping("/getData2/{id2}/")   // URL example: localhost:8080/getData2/77777/?age=55555
    public String getData2(Model model,
                           @RequestParam(value="age") int age,   // ?age=5555
                           @PathVariable(value="id2") int id2) { // 777777
        model.addAttribute("id2",id2);
        model.addAttribute("age2",age);
        return "file1";
    }

    @ResponseBody
    @PostMapping("/postJsonString")
    public void receiveJsonString(@RequestBody String data) {
        JsonReader jsonReader = Json.createReader(new StringReader(data ));
        JsonObject object = jsonReader.readObject();
        System.out.println(object.getString("name") + " - " + object.getInt("age") );
    }

    @ResponseBody
    @PostMapping("/postObject")
    public void receiveObject(@RequestBody Person person) {
        System.out.println(person.toString());
    }

}
