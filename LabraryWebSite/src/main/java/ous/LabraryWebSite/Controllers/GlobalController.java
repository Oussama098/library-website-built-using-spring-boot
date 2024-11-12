package ous.LabraryWebSite.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ous.LabraryWebSite.Services.CartService;

@ControllerAdvice
public class GlobalController {
    @Autowired
    private CartService cartService;

    @ModelAttribute("size")
    public long sizeOfCart() {
        return cartService.getSizeOfCart();
    }
}
