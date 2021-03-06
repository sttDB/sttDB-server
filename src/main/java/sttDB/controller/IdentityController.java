package sttDB.controller;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sttDB.domain.CustomUser;

@BasePathAwareController
public class IdentityController {

    @RequestMapping("/identity")
    public @ResponseBody
    PersistentEntityResource getAuthenticatedUserIdentity(PersistentEntityResourceAssembler resourceAssembler){
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser customUser = (CustomUser) obj;

        return resourceAssembler.toResource(customUser);
    }
}