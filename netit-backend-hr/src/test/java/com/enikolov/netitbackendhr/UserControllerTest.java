package com.enikolov.netitbackendhr;

import com.enikolov.netitbackendhr.controllers.html.UserController;
import com.enikolov.netitbackendhr.enums.UserRole;
import com.enikolov.netitbackendhr.models.DTO.ChangeMailDTO;
import com.enikolov.netitbackendhr.models.DTO.UserDTO;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController controller;

    @Autowired
    private UserRepository repository;

    @Test
    public void checkIsAvailable(){
        assertNotNull(this.controller);
    }

    @Test
    public void testRegisterNewUserAllOk(){
        UserDTO newUser = new UserDTO();
        newUser.setUsername("testuser");
        newUser.setPassword("123456");
        newUser.setConfirmPassword("123456");
        newUser.setMail("testuser@test.bg");
        newUser.setUserRole("EMPLOYEE");

        RedirectView register = this.controller.createUser(newUser);

        String result = register.getUrl();

        assertEquals(result, "/login");

    }

    @Test
    public void testRegisterNewUserPasswordNotMatch(){
        UserDTO newUser = new UserDTO();
        newUser.setUsername("testuser");
        newUser.setPassword("123456");
        newUser.setConfirmPassword("123477");
        newUser.setMail("testuser@test.bg");
        newUser.setUserRole("EMPLOYEE");

        RedirectView register = this.controller.createUser(newUser);

        String result = register.getUrl();

        assertEquals(result, "/register");

    }

    @Test
    public void testDeleteUserAllOk(){
        int userId = 2;
        RedirectView result = this.controller.deleteUser(userId);

        assertEquals(result.getUrl(), "/success");
    }

    @Test
    public void testDeleteUserIdNotExist(){
        int userId = 2222225;
        RedirectView result = this.controller.deleteUser(userId);

        assertEquals(result.getUrl(), "/fail");
    }

    @Test
    public void testChangeMailIdNotExist(){
        ChangeMailDTO changeMailDTO = new ChangeMailDTO();
        changeMailDTO.setUserId(155000);
        changeMailDTO.setNewMail("newtestmail@tmail.bg");

        String result = this.controller.processChangeEmail(changeMailDTO);

        assertEquals(result, "fail");
    }

    @Test
    public void testChangeMailAllOk(){
        ChangeMailDTO changeMailDTO = new ChangeMailDTO();
        changeMailDTO.setUserId(3);
        changeMailDTO.setNewMail("newtestmail@tmail.bg");

        String result = this.controller.processChangeEmail(changeMailDTO);

        assertEquals(result, "success");
    }



}
