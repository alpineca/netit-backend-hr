package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.components.FieldChecker;
import com.enikolov.netitbackendhr.components.InfoMessage;
import com.enikolov.netitbackendhr.enums.MessageStyle;
import com.enikolov.netitbackendhr.models.DTO.ChangePasswordDTO;
import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.services.data.CategoryDataService;
import com.enikolov.netitbackendhr.services.data.CityDataService;
import com.enikolov.netitbackendhr.services.data.MessageDataService;
import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class EditProfileController {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private MessageDataService messageDataService;
    @Autowired
    private CityDataService cityDataService;
    @Autowired
    private CategoryDataService categoryDataService;
    @Autowired
    private FieldChecker fieldChecker;

    @GetMapping("/profile/edit")
    public String getEditProfilePage(Model model){
        User user = this.userDataService.getLoggedUser();
        InfoMessage infoMessage = (InfoMessage) model.asMap().get("infoMessage");
        boolean hasUnread = this.messageDataService.isUnreadMessages();

        if(user.getUserRole().equals("EMPLOYEE")){
            Employee employeeModel = this.userDataService.getLoggedEmployee();
            model.addAttribute("employeeData", employeeModel);
        }
        if(user.getUserRole().equals("EMPLOYER")){
            Employer employerModel = this.userDataService.getLoggedEmployer();
            model.addAttribute("employerData", employerModel);
        }

        model.addAttribute("hasUnread", hasUnread);
        model.addAttribute("infoMessage", infoMessage);
        model.addAttribute("cities",        this.cityDataService.getAllCities());
        model.addAttribute("categories",    this.categoryDataService.getAllCategories());

        model.addAttribute("user", user);
        model.addAttribute("passwordModel", new ChangePasswordDTO());
        return "main/edit-profile";
    }
    @PostMapping("/profile/edit/change-password")
    public RedirectView changePassword(@ModelAttribute ChangePasswordDTO passwordModel,
                                       RedirectAttributes redirectAttributes){
        InfoMessage infoMessage = new InfoMessage();

        if(!passwordModel.getNewPassword().equals(passwordModel.getConfirmNewPassword())){
            infoMessage.setMessage("Passwords not match!");
            infoMessage.setStyle(MessageStyle.ERROR_MSG);

            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return new RedirectView("/profile/edit");
        }
        if(!fieldChecker.isPasswordValid(passwordModel.getNewPassword())){
            infoMessage.setMessage( "Please enter valid password!\n Password must be 6-10 chars " +
                    "and contains lower and upper case letter");
            infoMessage.setStyle(MessageStyle.ERROR_MSG);

            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return new RedirectView("/profile/edit");
        }

        this.userDataService.changePassword(passwordModel.getNewPassword());
        infoMessage.setMessage("Password successfuly changed!");
        infoMessage.setStyle(MessageStyle.SUCCESS_MSG);

        redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
        return new RedirectView("/profile/edit");
    }
    @PostMapping("/profile/edit/change-user-data")
    public RedirectView changeUserData(@ModelAttribute User user,
                                       RedirectAttributes redirectAttributes){
        User loggedUser = this.userDataService.getLoggedUser();
        InfoMessage infoMessage = new InfoMessage();
        boolean isSameEmail = loggedUser.getEmail().equals(user.getEmail());

        if(!this.fieldChecker.isFullnameValid(user.getFullname())){
            infoMessage.setMessage( "Please enter valid full name!\n " +
                    "Full name must contains First name, last name written on latin");
            infoMessage.setStyle(MessageStyle.ERROR_MSG);
            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return new RedirectView("/profile/edit");
        }
        if(!this.fieldChecker.isEmailValid(user.getEmail())){
            infoMessage.setMessage( "Please enter valid email address!");
            infoMessage.setStyle(MessageStyle.ERROR_MSG);
            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return new RedirectView("/profile/edit");
        }
        if(!isSameEmail && !this.fieldChecker.isEmailFree(user.getEmail())){
            infoMessage.setMessage( "This email was used for registration.\n Please enter other email.");
            infoMessage.setStyle(MessageStyle.ERROR_MSG);
            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return new RedirectView("/profile/edit");
        }

        this.userDataService.changeUserData(user);
        infoMessage.setMessage( "User data successfully changed!");
        infoMessage.setStyle(MessageStyle.SUCCESS_MSG);
        redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
        return new RedirectView("/profile/edit");
    }
    @PostMapping("/profile/edit/change-employer-data")
    public RedirectView changeEmployerData(@ModelAttribute Employer employerModel,
                                           RedirectAttributes redirectAttributes){
        InfoMessage infoMessage = new InfoMessage();

        redirectAttributes.addFlashAttribute("message", infoMessage);
        return new RedirectView("/profile/edit");
    }
    @PostMapping("/profile/edit/change-employee-data")
    public RedirectView changeEmployeeData(@ModelAttribute Employee employeeModel,
                                           RedirectAttributes redirectAttributes){
        InfoMessage infoMessage = new InfoMessage();

        redirectAttributes.addFlashAttribute("message", infoMessage);
        return new RedirectView("/profile/edit");
    }

}
